package com.nexoner.kuzey.util;

import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.networking.packet.FluidSyncPacket;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class CustomFluidTank extends FluidTank {
    public BlockEntity blockEntity;
    public Fluid filteredFluid;
    public TagKey<Fluid> fluidTag;
    public CustomFluidTank(int capacity, BlockEntity blockEntity) {
        super(capacity);
        this.blockEntity = blockEntity;
    }
    public CustomFluidTank(int capacity, BlockEntity blockEntity, Fluid filteredFluid) {
        super(capacity);
        this.blockEntity = blockEntity;
        this.filteredFluid = filteredFluid;
    }
    public CustomFluidTank(int capacity, BlockEntity blockEntity, TagKey<Fluid> fluidTag) {
        super(capacity);
        this.blockEntity = blockEntity;
        this.fluidTag = fluidTag;
    }

    public void fillExtra(int amount, Fluid fluid, FluidAction action){
        FluidStack stack = new FluidStack(fluid, amount);
        fill(stack, action);
    }

    public void setAmount(int amount){
        if(amount <= getCapacity()){
        if (getFluidAmount() > amount){
            int toDrain = getFluidAmount() - amount;
            drain(toDrain, FluidAction.EXECUTE);
        } else if (amount > getFluidAmount()){
            int toFill = amount - getFluidAmount();
            fillExtra(toFill, getFluid().getFluid(), FluidAction.EXECUTE);
        }}}

    @Override
    protected void onContentsChanged() {
        blockEntity.setChanged();
        if (!blockEntity.getLevel().isClientSide()) {
            ModPackets.sendToClients(new FluidSyncPacket(this.fluid, blockEntity.getBlockPos()));
        }
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        if (filteredFluid == null && fluidTag == null){
            return true;
        } if (Registry.FLUID.getHolderOrThrow(Registry.FLUID.getResourceKey(stack.getFluid()).get()).is(fluidTag)){return true;}
        return stack.getFluid() == filteredFluid;
    }
}
