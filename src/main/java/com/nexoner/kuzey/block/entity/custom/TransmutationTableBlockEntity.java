package com.nexoner.kuzey.block.entity.custom;

import com.nexoner.kuzey.block.custom.TransmutationTableBlock;
import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.block.entity.entityType.IEnergyHandlingBlockEntity;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.networking.packet.EnergySyncPacket;
import com.nexoner.kuzey.recipe.TransmutationTableRecipe;
import com.nexoner.kuzey.screen.TransmutationTableMenu;
import com.nexoner.kuzey.util.CustomEnergyStorage;
import com.nexoner.kuzey.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

public class TransmutationTableBlockEntity extends BlockEntity implements MenuProvider, IEnergyHandlingBlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == 0)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == 0)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == 0)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> i == 0)));

    private LazyOptional<CustomEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public final CustomEnergyStorage energyStorage;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress;

    public TransmutationTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TRANSMUTATION_TABLE_BLOCK_ENTITY.get(), pPos, pBlockState);
        energyStorage = createEnergyStorage();
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return TransmutationTableBlockEntity.this.progress;
                    case 1: return TransmutationTableBlockEntity.this.maxProgress;
                    case 2: return TransmutationTableBlockEntity.this.getEnergy();
                    case 3: return TransmutationTableBlockEntity.this.getMaxEnergy();
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                int energyStored = TransmutationTableBlockEntity.this.getEnergy();
                int maxEnergy = TransmutationTableBlockEntity.this.getMaxEnergy();
                switch (pIndex){
                    case 0: TransmutationTableBlockEntity.this.progress = pValue; break;
                    case 1: TransmutationTableBlockEntity.this.maxProgress = pValue; break;
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
        return new TranslatableComponent("block.kuzey.transmutation_table");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        ModPackets.sendToClients(new EnergySyncPacket(energyStorage.getEnergyStored(),worldPosition));
        return new TransmutationTableMenu(pContainerId,pInventory,this,this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        if(side == null) {
            return lazyItemHandler.cast();
        }
        if(directionWrappedHandlerMap.containsKey(side)) {
            Direction localDir = this.getBlockState().getValue(TransmutationTableBlock.FACING);

            if(side == Direction.UP || side == Direction.DOWN) {
                return directionWrappedHandlerMap.get(side).cast();
            }

            return switch (localDir) {
                default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
            };
        }} else if (cap == CapabilityEnergy.ENERGY){
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
        tag.putInt("transmutation_table.progress",progress);
        tag.putInt("transmutation_table.energy",getEnergy());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("transmutation_table.progress");
        energyStorage.setEnergy(nbt.getInt("transmutation_table.energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    private static boolean hasRecipe(TransmutationTableBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<TransmutationTableRecipe> match = level.getRecipeManager()
                .getRecipeFor(TransmutationTableRecipe.Type.INSTANCE, inventory, level);


        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem()) && hasEnoughInputItems(inventory, entity);
    }

    private static void craftItem(TransmutationTableBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<TransmutationTableRecipe> match = level.getRecipeManager()
                .getRecipeFor(TransmutationTableRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,getInputAmount(entity), false);

            entity.itemHandler.setStackInSlot(1, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(1).getCount() + match.get().getResultItem().getCount()));

            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack output) {
        return inventory.getItem(1).getItem() == output.getItem() || inventory.getItem(1).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(1).getMaxStackSize() > inventory.getItem(1).getCount();
    }

    private static boolean hasEnoughInputItems(SimpleContainer inventory, TransmutationTableBlockEntity entity) {
        return inventory.getItem(0).getCount() >= getInputAmount(entity);
    }

    private CustomEnergyStorage createEnergyStorage(){
        return new CustomEnergyStorage(this, KuzeyCommonConfigs.transmutationTableCapacity.get(), KuzeyCommonConfigs.transmutationTableMaxReceived.get(),0,0){
            @Override
            public void onEnergyChanged() {
                if (!level.isClientSide){
                    ModPackets.sendToClients(new EnergySyncPacket(energyStorage.getEnergyStored(),worldPosition));
                }
            }
        };
    }

    private static int getUsageCost(TransmutationTableBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(TransmutationTableRecipe.Type.INSTANCE, inventory, level).map(TransmutationTableRecipe::getUsageCost).orElse(KuzeyCommonConfigs.transmutationTableDefaultUsageCost.get());
    }

    private static int getRecipeTime(TransmutationTableBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(TransmutationTableRecipe.Type.INSTANCE, inventory, level).map(TransmutationTableRecipe::getRecipeTime).orElse(KuzeyCommonConfigs.transmutationTableDefaultRecipeTime.get());
    }

    private static int getInputAmount(TransmutationTableBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(TransmutationTableRecipe.Type.INSTANCE, inventory, level).map(TransmutationTableRecipe::getInputAmount).orElse(1);
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
        TransmutationTableBlockEntity pBlockEntity = (TransmutationTableBlockEntity) this.level.getBlockEntity(this.getBlockPos());
        if(hasRecipe(pBlockEntity) && hasEnoughEnergy(getUsageCost(pBlockEntity))) {
            pBlockEntity.maxProgress = getRecipeTime(pBlockEntity);
            pBlockEntity.progress++;
            energyStorage.removeEnergy(getUsageCost(pBlockEntity));
            setChanged(pLevel, pPos, pState);
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
        }
    }

    @Override
    public void setEnergy(int energy) {
        energyStorage.setEnergy(energy);
    }

    @Override
    public CustomEnergyStorage getEnergyStorage() {
        return energyStorage;
    }
}
