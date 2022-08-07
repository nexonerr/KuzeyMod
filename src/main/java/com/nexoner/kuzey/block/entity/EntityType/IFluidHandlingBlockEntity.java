package com.nexoner.kuzey.block.entity.EntityType;

import net.minecraftforge.fluids.FluidStack;

public interface IFluidHandlingBlockEntity {
    void setFluid(FluidStack fluid);
    FluidStack getFluid();
}
