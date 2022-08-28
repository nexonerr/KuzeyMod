package com.nexoner.kuzey.block.entity.entityType;

import net.minecraftforge.fluids.FluidStack;

public interface IFluidHandlingBlockEntity {
    void setFluid(FluidStack fluid);
    FluidStack getFluid();
}
