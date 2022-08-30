package com.nexoner.kuzey.block.entity.custom;

import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.block.custom.EmreEssenceExtractorBlock;
import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.block.entity.entityType.IFluidHandlingBlockEntity;
import com.nexoner.kuzey.block.entity.entityType.IItemStackRenderingBlockEntity;
import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.networking.packet.FluidSyncPacket;
import com.nexoner.kuzey.networking.packet.ItemStackSyncPacket;
import com.nexoner.kuzey.recipe.EmreEssenceInfuserRecipe;
import com.nexoner.kuzey.screen.EmreEssenceInfuserMenu;
import com.nexoner.kuzey.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
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

public class EmreEssenceInfuserBlockEntity extends BlockEntity implements MenuProvider, IFluidHandlingBlockEntity, IKuzeyConstants, IItemStackRenderingBlockEntity {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide){ModPackets.sendToClients(new ItemStackSyncPacket(this,worldPosition));}
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
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public final CustomEnergyStorage energyStorage;
    public final CustomFluidTank fluidTank;

    private final int capacity = 160000;
    private final int maxReceived = 32000;
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress;
    private final int defaultUsageCost = 25000;
    private final int defaultRecipeTime = 440;
    private final int fluidCapacity = 10000;
    private final boolean fluidFiltering = true;
    private final int BUCKET_VOLUME = IKuzeyConstants.BUCKET_VOLUME;

    public EmreEssenceInfuserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.EMRE_ESSENCE_INFUSER_BLOCK_ENTITY.get(), pPos, pBlockState);
        energyStorage = createEnergyStorage();
        fluidTank = createFluidTank();
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return EmreEssenceInfuserBlockEntity.this.progress;
                    case 1: return EmreEssenceInfuserBlockEntity.this.maxProgress;
                    case 2: return EmreEssenceInfuserBlockEntity.this.getEnergy();
                    case 3: return EmreEssenceInfuserBlockEntity.this.getMaxEnergy();
                    case 4: return EmreEssenceInfuserBlockEntity.this.getFluidAmount();
                    case 5: return EmreEssenceInfuserBlockEntity.this.getFluidCapacity();
                    case 6: return GeneralUtils.boolToInt(EmreEssenceInfuserBlockEntity.this.hasDecondensator(pPos));
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                int energyStored = EmreEssenceInfuserBlockEntity.this.getEnergy();
                int maxEnergy = EmreEssenceInfuserBlockEntity.this.getMaxEnergy();
                int fluidAmount = EmreEssenceInfuserBlockEntity.this.getFluidAmount();
                int fluidCapacity = EmreEssenceInfuserBlockEntity.this.getFluidCapacity();
                int decondensator = GeneralUtils.boolToInt(EmreEssenceInfuserBlockEntity.this.hasDecondensator(pPos));
                switch (pIndex){
                    case 0: EmreEssenceInfuserBlockEntity.this.progress = pValue; break;
                    case 1: EmreEssenceInfuserBlockEntity.this.maxProgress = pValue; break;
                    case 2: energyStored = pValue; break;
                    case 3: maxEnergy = pValue; break;
                    case 4: fluidAmount = pValue; break;
                    case 5: fluidCapacity = pValue; break;
                    case 6: decondensator = pValue; break;
                }
            }

            @Override
            public int getCount() {
                return 7;
            }
        };

    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("block.kuzey.emre_essence_infuser");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        ModPackets.sendToClients(new FluidSyncPacket(this.fluidTank.getFluid(), worldPosition));
        return new EmreEssenceInfuserMenu(pContainerId,pInventory,this,this.data);
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
        tag.putInt("emre_essence_infuser.progress",progress);
        tag.putInt("emre_essence_infuser.energy",getEnergy());
        tag = fluidTank.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("emre_essence_infuser.progress");
        energyStorage.setEnergy(nbt.getInt("emre_essence_infuser.energy"));
        fluidTank.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    private boolean hasRecipe(EmreEssenceInfuserBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<EmreEssenceInfuserRecipe> match = level.getRecipeManager()
                .getRecipeFor(EmreEssenceInfuserRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent() && this.hasInputFluid(entity)
                && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, match.get().getResultItem());
    }

    private void craftItem(EmreEssenceInfuserBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<EmreEssenceInfuserRecipe> match = level.getRecipeManager()
                .getRecipeFor(EmreEssenceInfuserRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.itemHandler.extractItem(0,1, false);
            fluidTank.drain(match.get().getFluidInput().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            entity.itemHandler.setStackInSlot(1, new ItemStack(match.get().getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(1).getCount() + match.get().getResultItem().getCount()));
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

    private boolean hasInputFluid(EmreEssenceInfuserBlockEntity entity){
        return fluidTank.getFluid().getFluid() == getFluidInput(entity).getFluid() && fluidTank.getFluid().getAmount() >= getFluidInput(entity).getAmount();
    }

    private CustomEnergyStorage createEnergyStorage(){
        return new CustomEnergyStorage(this, capacity, maxReceived,0,0);
    }

    private CustomFluidTank createFluidTank(){
        if (!fluidFiltering){
        return new CustomFluidTank(fluidCapacity, this){
            @Override
            protected void onContentsChanged() {
                if (!level.isClientSide()) {
                    ModPackets.sendToClients(new FluidSyncPacket(this.fluid, worldPosition));
                }}};}
        return new CustomFluidTank(fluidCapacity, this, ModTags.Fluids.EMRE_ESSENCE_INFUSER_FLUIDS){
            @Override
            protected void onContentsChanged() {
                if (!level.isClientSide()) {
                    ModPackets.sendToClients(new FluidSyncPacket(this.fluid, worldPosition));
                }}};}

    private static int getUsageCost(EmreEssenceInfuserBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(EmreEssenceInfuserRecipe.Type.INSTANCE, inventory, level).map(EmreEssenceInfuserRecipe::getUsageCost).orElse(entity.defaultUsageCost);
    }

    private static int getRecipeTime(EmreEssenceInfuserBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(EmreEssenceInfuserRecipe.Type.INSTANCE, inventory, level).map(EmreEssenceInfuserRecipe::getRecipeTime).orElse(entity.defaultRecipeTime);
    }
    private static FluidStack getFluidInput(EmreEssenceInfuserBlockEntity entity){
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(EmreEssenceInfuserRecipe.Type.INSTANCE, inventory, level).map(EmreEssenceInfuserRecipe::getFluidInput).orElse(new FluidStack(Fluids.WATER, 1));
    }

    @Override
    public void setFluid(FluidStack fluid) {
        fluidTank.setFluid(fluid);
    }

    public FluidStack getFluid(){
        return fluidTank.getFluid();
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
        return itemHandler.getStackInSlot(2).getCount() > 0;
    }

    private boolean hasDecondensator(BlockPos pPos){
        BlockState blockToCheck = level.getBlockState(pPos.below(1));
        if (blockToCheck.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            return blockToCheck.getBlock() == ModBlocks.DECONDENSATOR.get() && blockToCheck.getValue(BlockStateProperties.HORIZONTAL_FACING) == level.getBlockState(pPos).getValue(BlockStateProperties.HORIZONTAL_FACING);
        } return false;
    }

    private void transferLiquidFromItem(){
        if (fluidTank.getSpace() > 0){
        ItemStack fluidTankItem = itemHandler.getStackInSlot(2);
        if (fluidTankItem.getItem() instanceof BucketItem && fluidTankItem.getItem() != Items.BUCKET){
            if (fluidTank.getSpace() >= 0){
                fluidTankItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler ->{
                    Fluid fluidToFill = handler.getFluidInTank(0).getFluid();
                    itemHandler.extractItem(2,1,false);
                    itemHandler.insertItem(2,new ItemStack(Items.BUCKET,1),false);
                    fluidTank.fillExtra(BUCKET_VOLUME,fluidToFill, IFluidHandler.FluidAction.EXECUTE);
                });
            }
        } else {
        fluidTankItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler->{
            int toDrain = Math.min(Math.min(handler.getFluidInTank(0).getAmount(), BUCKET_VOLUME), fluidTank.getSpace());
            if (toDrain > 0){
                Fluid fluidToFill = handler.getFluidInTank(0).getFluid();
                handler.drain(toDrain, IFluidHandler.FluidAction.EXECUTE);
                fluidTank.fillExtra(toDrain, fluidToFill, IFluidHandler.FluidAction.EXECUTE);
            }
        });
    }}}

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        EmreEssenceInfuserBlockEntity pBlockEntity = (EmreEssenceInfuserBlockEntity) this.level.getBlockEntity(this.getBlockPos());
        if(hasRecipe(pBlockEntity) && hasEnoughEnergy(getUsageCost(pBlockEntity)) && hasDecondensator(pPos)) {
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
            transferLiquidFromItem();
        }
    }

    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(1).isEmpty()) {
            stack = itemHandler.getStackInSlot(1);
        } else {
            stack = itemHandler.getStackInSlot(0);
        }

        return stack;
    }

    @Override
    public void setHandler(ItemStackHandler handler) {
        copyHandlerContents(handler);
    }

    private void copyHandlerContents(ItemStackHandler handler) {
        for (int i = 0; i < handler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, handler.getStackInSlot(i));
        }
    }

    @Override
    public ItemStackHandler getItemStackHandler() {
        return this.itemHandler;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        ModPackets.sendToClients(new ItemStackSyncPacket(itemHandler,worldPosition));
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = saveWithoutMetadata();
        load(compound);
        ModPackets.sendToClients(new ItemStackSyncPacket(itemHandler,worldPosition));
        return compound;
    }

}
