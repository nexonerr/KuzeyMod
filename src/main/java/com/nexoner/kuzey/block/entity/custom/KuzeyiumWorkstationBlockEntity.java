package com.nexoner.kuzey.block.entity.custom;

import com.nexoner.kuzey.block.custom.EmreEssenceExtractorBlock;
import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.recipe.KuzeyiumWorkstationRecipe;
import com.nexoner.kuzey.screen.KuzeyiumWorkstationMenu;
import com.nexoner.kuzey.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class KuzeyiumWorkstationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 5, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == 2)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == 3)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == 4)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> (i == 0 && s.getItem() == ModItems.KUZEYIUM_SAW.get()) || (i == 1 && s.getItem() == ModItems.KUZEYIUM_SMITHING_HAMMER.get()))));

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress;
    private int defaultRecipeTime = 600;
    private int defaultToolDamage = 4;

    public KuzeyiumWorkstationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.KUZEYIUM_WORKSTATION_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return KuzeyiumWorkstationBlockEntity.this.progress;
                    case 1: return KuzeyiumWorkstationBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0: KuzeyiumWorkstationBlockEntity.this.progress = pValue; break;
                    case 1: KuzeyiumWorkstationBlockEntity.this.maxProgress = pValue; break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.kuzey.kuzeyium_workstation");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new KuzeyiumWorkstationMenu(pContainerId,pInventory,this,this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            if(side == null) {
                return lazyItemHandler.cast();
            }
            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(EmreEssenceExtractorBlock.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }}
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("kuzeyium_workstation.progress",progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("kuzeyium_workstation.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, KuzeyiumWorkstationBlockEntity pBlockEntity) {
        if(hasRecipe(pBlockEntity)) {
            pBlockEntity.maxProgress = getRecipeTime(pBlockEntity);
            pBlockEntity.progress++;
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    private static boolean hasRecipe(KuzeyiumWorkstationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<KuzeyiumWorkstationRecipe> match = level.getRecipeManager()
                .getRecipeFor(KuzeyiumWorkstationRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory) && hasTools(entity)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem()) && hasEnoughDurability(inventory,entity);
    }

    private static void craftItem(KuzeyiumWorkstationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<KuzeyiumWorkstationRecipe> match = level.getRecipeManager()
                .getRecipeFor(KuzeyiumWorkstationRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            if(entity.itemHandler.getStackInSlot(0).hurt(getToolDamage(entity), new Random(), null)) {
                entity.itemHandler.extractItem(0,1, false);
            }
            if(entity.itemHandler.getStackInSlot(1).hurt(getToolDamage(entity), new Random(), null)) {
                entity.itemHandler.extractItem(1,1, false);
            }
            entity.itemHandler.extractItem(2,1,false);
            entity.itemHandler.extractItem(3,1,false);
            entity.itemHandler.extractItem(4,1,false);
            entity.itemHandler.setStackInSlot(5, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(5).getCount() + match.get().getResultItem().getCount()));

            entity.resetProgress();
        }
    }

    private static boolean hasTools(KuzeyiumWorkstationBlockEntity entity){
        return entity.itemHandler.getStackInSlot(0).getItem() == ModItems.KUZEYIUM_SAW.get() && entity.itemHandler.getStackInSlot(1).getItem() == ModItems.KUZEYIUM_SMITHING_HAMMER.get();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(5).getItem() == output.getItem() || inventory.getItem(5).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(5).getMaxStackSize() > inventory.getItem(5).getCount();
    }

    private static boolean hasEnoughDurability(SimpleContainer inventory, KuzeyiumWorkstationBlockEntity entity){
        boolean sawDurability = inventory.getItem(0).getMaxDamage() - inventory.getItem(0).getDamageValue() >= getToolDamage(entity);
        boolean hammerDurability = inventory.getItem(1).getMaxDamage() - inventory.getItem(1).getDamageValue() >= getToolDamage(entity);
        return hammerDurability && sawDurability;
    }

    private static int getRecipeTime(KuzeyiumWorkstationBlockEntity entity){
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(KuzeyiumWorkstationRecipe.Type.INSTANCE, inventory, level).map(KuzeyiumWorkstationRecipe::getRecipeTime).orElse(entity.defaultRecipeTime);
    }

    private static int getToolDamage(KuzeyiumWorkstationBlockEntity entity){
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(KuzeyiumWorkstationRecipe.Type.INSTANCE, inventory, level).map(KuzeyiumWorkstationRecipe::getToolDamage).orElse(entity.defaultToolDamage);
    }

}
