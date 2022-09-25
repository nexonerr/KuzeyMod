package com.nexoner.kuzey.block.entity.template;

import com.nexoner.kuzey.util.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractGeneratorEntity extends BlockEntity implements MenuProvider {
    private final TranslatableComponent displayName;
    public final CustomEnergyStorage energyStorage;

    public final int maxExtracted;
    public final int produced;

    public final ItemStackHandler itemHandler;

    public AbstractGeneratorEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, TranslatableComponent name, int pCapacity, int pMaxExtracted, int pProduced, int pSlots) {
        super(pType, pPos, pBlockState);
        energyStorage = createEnergyStorage(pCapacity,pMaxExtracted);

        displayName = name;
        maxExtracted = pMaxExtracted;
        produced = pProduced;

        itemHandler = new ItemStackHandler(pSlots){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<CustomEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        return super.getCapability(cap,side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
        this.lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("energy",getEnergy());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        energyStorage.setEnergy(pTag.getInt("energy"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public int getEnergy(){
        return energyStorage.getEnergyStored();
    }
    public int getMaxEnergy(){
        return energyStorage.getMaxEnergyStored();
    }

    private CustomEnergyStorage createEnergyStorage(int capacity, int maxExtracted){
        return new CustomEnergyStorage(this, capacity, 0, maxExtracted,0);
    }

    public boolean canInsertEnergy(AbstractGeneratorEntity entity){
        CustomEnergyStorage energyStorage = entity.energyStorage;
        if (energyStorage.getSpace() > 0){
            return energyStorage.getSpace() >= produced;
        }
        return false;
    }
    public void outputEnergy(){
        if (energyStorage.canExtract()){
            for (Direction direction : Direction.values()){
                final BlockEntity entity = level.getBlockEntity(worldPosition.relative(direction));

                if (entity == null){
                    continue;
                }

                entity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(storage -> {
                    final int canSend = energyStorage.extractEnergy(maxExtracted, true);
                    final int canReceive = storage.receiveEnergy(canSend, false);
                    final int toExtract = Math.min(canSend, canReceive);
                    energyStorage.setEnergy(energyStorage.getEnergyStored() - toExtract);
                        });
            }
        }
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState){
        AbstractGeneratorEntity entity = (AbstractGeneratorEntity) this.level.getBlockEntity(this.getBlockPos());
        tickCustom(entity);
        outputEnergy();
    }

    public void tickCustom(AbstractGeneratorEntity entity){}

}
