package com.nexoner.kuzey.screen.menutype;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidMenu {
    void setFluid(FluidStack fluidStack);
    FluidStack getFluid();
    BlockEntity getBlockEntity();
}
