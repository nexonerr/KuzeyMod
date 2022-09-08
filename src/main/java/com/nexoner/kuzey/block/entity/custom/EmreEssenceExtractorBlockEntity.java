package com.nexoner.kuzey.block.entity.custom;

import com.nexoner.kuzey.block.custom.EmreEssenceExtractorBlock;
import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.block.entity.entityType.IFluidHandlingBlockEntity;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.networking.packet.FluidSyncPacket;
import com.nexoner.kuzey.recipe.EmreEssenceExtractorRecipe;
import com.nexoner.kuzey.screen.EmreEssenceExtractorMenu;
import com.nexoner.kuzey.util.*;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;

public class EmreEssenceExtractorBlockEntity extends BlockEntity implements MenuProvider, IFluidHandlingBlockEntity, IKuzeyConstants {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> i == 0)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> i == 0)),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> i == 0)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> i == 0)),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> i == 0)));


    private LazyOptional<CustomEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public final CustomEnergyStorage energyStorage;
    public final OutputOnlyFluidTank fluidTank;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress;
    private final int BUCKET_VOLUME = IKuzeyConstants.BUCKET_VOLUME;

    public EmreEssenceExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.EMRE_ESSENCE_EXTRACTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
        energyStorage = createEnergyStorage();
        fluidTank = createFluidTank();
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return EmreEssenceExtractorBlockEntity.this.progress;
                    case 1: return EmreEssenceExtractorBlockEntity.this.maxProgress;
                    case 2: return EmreEssenceExtractorBlockEntity.this.getEnergy();
                    case 3: return EmreEssenceExtractorBlockEntity.this.getMaxEnergy();
                    case 4: return EmreEssenceExtractorBlockEntity.this.getFluidAmount();
                    case 5: return EmreEssenceExtractorBlockEntity.this.getFluidCapacity();
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                int energyStored = EmreEssenceExtractorBlockEntity.this.getEnergy();
                int maxEnergy = EmreEssenceExtractorBlockEntity.this.getMaxEnergy();
                int fluidAmount = EmreEssenceExtractorBlockEntity.this.getFluidAmount();
                int fluidCapacity = EmreEssenceExtractorBlockEntity.this.getFluidCapacity();
                switch (pIndex){
                    case 0: EmreEssenceExtractorBlockEntity.this.progress = pValue; break;
                    case 1: EmreEssenceExtractorBlockEntity.this.maxProgress = pValue; break;
                    case 2: energyStored = pValue; break;
                    case 3: maxEnergy = pValue; break;
                    case 4: fluidAmount = pValue; break;
                    case 5: fluidCapacity = pValue; break;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };

    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.kuzey.emre_essence_extractor");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        ModPackets.sendToClients(new FluidSyncPacket(this.fluidTank.getFluid(), worldPosition));
        return new EmreEssenceExtractorMenu(pContainerId,pInventory,this,this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
            }
        }
          else if (cap == CapabilityEnergy.ENERGY){
            return lazyEnergyHandler.cast();
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }


    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyItemHandler = LazyOptional.of(() -> this.itemHandler);
        this.lazyEnergyHandler = LazyOptional.of(() -> this.energyStorage);
        this.lazyFluidHandler = LazyOptional.of(() -> this.fluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("emre_essence_extractor.progress",progress);
        tag.putInt("emre_essence_extractor.energy",getEnergy());
        tag = fluidTank.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("emre_essence_extractor.progress");
        energyStorage.setEnergy(getUpdateTag().getInt("emre_essence_extractor.energy"));
        fluidTank.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    private static boolean hasRecipe(EmreEssenceExtractorBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<EmreEssenceExtractorRecipe> match = level.getRecipeManager()
                .getRecipeFor(EmreEssenceExtractorRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && entity.canInsertIntoTank(getFluidOutput(entity));
    }

    private void craftItem(EmreEssenceExtractorBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<EmreEssenceExtractorRecipe> match = level.getRecipeManager()
                .getRecipeFor(EmreEssenceExtractorRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,1, false);
            if (match.get().getResultFluid() == null || match.get().getResultFluid().getFluid() == Fluids.EMPTY) {
                throw new NullPointerException("No such fluid to insert");
            }
            this.fluidTank.fillAux(match.get().getResultFluid(), IFluidHandler.FluidAction.EXECUTE);

            entity.resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private CustomEnergyStorage createEnergyStorage(){
        int capacity = KuzeyCommonConfigs.emreEssenceExtractorCapacity.get();
        int maxReceived = KuzeyCommonConfigs.emreEssenceExtractorMaxReceived.get();
        return new CustomEnergyStorage(this, capacity, maxReceived,0,0);
    }

    private OutputOnlyFluidTank createFluidTank(){
        int fluidCapacity = KuzeyCommonConfigs.emreEssenceExtractorFluidCapacity.get();
        boolean fluidFiltering = KuzeyCommonConfigs.emreEssenceExtractorFluidFiltering.get();
        if (fluidFiltering == false){
        return new OutputOnlyFluidTank(fluidCapacity, this){
            @Override
            protected void onContentsChanged() {
                if (!level.isClientSide()) {
                    ModPackets.sendToClients(new FluidSyncPacket(this.fluid, worldPosition));
                }}};}
        return new OutputOnlyFluidTank(fluidCapacity, this, ModTags.Fluids.EMRE_ESSENCE_EXTRACTOR_FLUIDS){
            @Override
            protected void onContentsChanged() {
                if (!level.isClientSide()) {
                    ModPackets.sendToClients(new FluidSyncPacket(this.fluid, worldPosition));
                }}};}




    private static int getUsageCost(EmreEssenceExtractorBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(EmreEssenceExtractorRecipe.Type.INSTANCE, inventory, level).map(EmreEssenceExtractorRecipe::getUsageCost).orElse(KuzeyCommonConfigs.emreEssenceExtractorDefaultUsageCost.get());
    }

    private static int getRecipeTime(EmreEssenceExtractorBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(EmreEssenceExtractorRecipe.Type.INSTANCE, inventory, level).map(EmreEssenceExtractorRecipe::getRecipeTime).orElse(KuzeyCommonConfigs.emreEssenceExtractorDefaultRecipeTime.get());
    }
    private static FluidStack getFluidOutput(EmreEssenceExtractorBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(EmreEssenceExtractorRecipe.Type.INSTANCE, inventory, level).map(EmreEssenceExtractorRecipe::getResultFluid).orElse(new FluidStack(Fluids.WATER, 1));
    }

    @Override
    public void setFluid(FluidStack fluid) {
        fluidTank.setFluid(fluid);
    }

    public FluidStack getFluid(){
        return fluidTank.getFluid();
    }

    private boolean canInsertIntoTank(FluidStack stack){
            if (fluidTank.getFluidAmount() == 0){return true;}
            return (fluidTank.getSpace() >= stack.getAmount() && fluidTank.getFluid().getFluid() == stack.getFluid());
    }

    public int getEnergy(){
        return energyStorage.getEnergyStored();
    }
    public int getMaxEnergy(){
        return energyStorage.getMaxEnergyStored();
    }
    public int getFluidAmount(){
        return fluidTank.getFluidAmount();
    }
    public int getFluidCapacity(){
        return fluidTank.getCapacity();
    }
    
    private boolean hasEnoughEnergy(int usageCost){
        return energyStorage.getEnergyStored() >= usageCost;
    }

    private boolean hasFluidTankInSlot(){
        return itemHandler.getStackInSlot(1).getCount() > 0;
    }

    private void transferLiquidIntoItem(){
        ItemStack toChange = itemHandler.getStackInSlot(1);
        if (toChange.getItem().getRegistryName() == Items.BUCKET.getRegistryName()){
            if (fluidTank.getFluidAmount() >= BUCKET_VOLUME && itemHandler.getStackInSlot(1).getCount() == 1){
                ItemStack toSet = new ItemStack(fluidTank.getFluid().getFluid().getBucket());
                fluidTank.drain(BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE);
                itemHandler.setStackInSlot(1, toSet);
            }
        } else {
        toChange.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler ->{
            int toDrain = Math.min(Math.min(BUCKET_VOLUME, this.fluidTank.getFluidAmount()), handler.getTankCapacity(0) - handler.getFluidInTank(0).getAmount());
            if (toDrain > 0){
                FluidStack stack = fluidTank.drain(toDrain, IFluidHandler.FluidAction.EXECUTE);
                handler.fill(stack, IFluidHandler.FluidAction.EXECUTE);
            }
        });}
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        EmreEssenceExtractorBlockEntity pBlockEntity = (EmreEssenceExtractorBlockEntity) this.level.getBlockEntity(this.getBlockPos());
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
        if (hasFluidTankInSlot()){
            transferLiquidIntoItem();
        }
    }
}
