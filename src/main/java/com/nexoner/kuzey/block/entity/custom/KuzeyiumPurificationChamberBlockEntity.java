package com.nexoner.kuzey.block.entity.custom;

import com.nexoner.kuzey.block.custom.KuzeyiumPurificationChamberBlock;
import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.recipe.KuzeyiumPurificationChamberRecipe;
import com.nexoner.kuzey.screen.KuzeyiumPurificationChamberMenu;
import com.nexoner.kuzey.util.CustomEnergyStorage;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class KuzeyiumPurificationChamberBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<CustomEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public final CustomEnergyStorage energyStorage;

    private int capacity = 80000;
    private int maxReceived = 20000;
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress;
    private int defaultUsageCost = 10000;
    private int defaultRecipeTime = 300;

    public KuzeyiumPurificationChamberBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.KUZEYIUM_PURIFICATION_CHAMBER_BLOCK_ENTITY.get(), pPos, pBlockState);
        energyStorage = createEnergyStorage();
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return KuzeyiumPurificationChamberBlockEntity.this.progress;
                    case 1: return KuzeyiumPurificationChamberBlockEntity.this.maxProgress;
                    case 2: return KuzeyiumPurificationChamberBlockEntity.this.getEnergy();
                    case 3: return KuzeyiumPurificationChamberBlockEntity.this.getMaxEnergy();
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                int energyStored = KuzeyiumPurificationChamberBlockEntity.this.getEnergy();
                int maxEnergy = KuzeyiumPurificationChamberBlockEntity.this.getMaxEnergy();
                switch (pIndex){
                    case 0: KuzeyiumPurificationChamberBlockEntity.this.progress = pValue; break;
                    case 1: KuzeyiumPurificationChamberBlockEntity.this.maxProgress = pValue; break;
                    case 2: energyStored = pValue; break;
                    case 3: maxEnergy = pValue; break;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };

    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.kuzey.kuzeyium_purification_chamber");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new KuzeyiumPurificationChamberMenu(pContainerId,pInventory,this,this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        } else if (cap == CapabilityEnergy.ENERGY){
            return lazyEnergyHandler.cast();
        }
        return super.getCapability(cap, side);
    }


    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        this.lazyEnergyHandler = LazyOptional.of(() -> this.energyStorage);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("kuzeyium_purification_chamber.progress",progress);
        tag.putInt("kuzeyium_purification_chamber.energy",getEnergy());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("kuzeyium_purification_chamber.progress");
        energyStorage.setEnergy(getUpdateTag().getInt("kuzeyium_purification_chamber.energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    private static boolean hasRecipe(KuzeyiumPurificationChamberBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<KuzeyiumPurificationChamberRecipe> match = level.getRecipeManager()
                .getRecipeFor(KuzeyiumPurificationChamberRecipe.Type.INSTANCE, inventory, level);


        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }

    private static void craftItem(KuzeyiumPurificationChamberBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<KuzeyiumPurificationChamberRecipe> match = level.getRecipeManager()
                .getRecipeFor(KuzeyiumPurificationChamberRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,1, false);
            entity.itemHandler.extractItem(1,1, false);

            entity.itemHandler.setStackInSlot(2, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(2).getCount() + match.get().getResultItem().getCount()));

            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(2).getItem() == output.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    private CustomEnergyStorage createEnergyStorage(){
        return new CustomEnergyStorage(this, capacity, maxReceived,0,0);
    }

    private static int getUsageCost(KuzeyiumPurificationChamberBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(KuzeyiumPurificationChamberRecipe.Type.INSTANCE, inventory, level).map(KuzeyiumPurificationChamberRecipe::getUsageCost).orElse(entity.defaultUsageCost);
    }

    private static int getRecipeTime(KuzeyiumPurificationChamberBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(KuzeyiumPurificationChamberRecipe.Type.INSTANCE, inventory, level).map(KuzeyiumPurificationChamberRecipe::getRecipeTime).orElse(entity.defaultRecipeTime);
    }

    public int getEnergy(){
        return energyStorage.getEnergyStored();
    }

    public int getMaxEnergy(){
        return energyStorage.getMaxEnergyStored();
    }
    private boolean hasEnoughEnergy(int usageCost){
        return energyStorage.getEnergyStored() >= usageCost;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        KuzeyiumPurificationChamberBlockEntity pBlockEntity = (KuzeyiumPurificationChamberBlockEntity) this.level.getBlockEntity(this.getBlockPos());
        if(hasRecipe(pBlockEntity) && hasEnoughEnergy(getUsageCost(pBlockEntity))) {
            pBlockEntity.maxProgress = getRecipeTime(pBlockEntity);
            pBlockEntity.progress++;
            energyStorage.setEnergy(energyStorage.getEnergyStored() - getUsageCost(pBlockEntity));
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }
}
