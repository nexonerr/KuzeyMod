package com.nexoner.kuzey.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class OutputOnlyFluidTank extends CustomFluidTank{
    public OutputOnlyFluidTank(int capacity, BlockEntity blockEntity) {
        super(capacity, blockEntity);
    }

    public OutputOnlyFluidTank(int capacity, BlockEntity blockEntity, Fluid filteredFluid) {
        super(capacity, blockEntity, filteredFluid);
    }

    public OutputOnlyFluidTank(int capacity, BlockEntity blockEntity, TagKey<Fluid> fluidTag) {
        super(capacity, blockEntity, fluidTag);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return 0;
    }

    @Override
    public void fillExtra(int amount, Fluid fluid, FluidAction action) {
        FluidStack stack = new FluidStack(fluid, amount);
        fillAux(stack, action);
    }

    public int fillAux(FluidStack resource, FluidAction action)
    {
        if (resource.isEmpty() || !isFluidValid(resource))
        {
            return 0;
        }
        if (action.simulate())
        {
            if (fluid.isEmpty())
            {
                return Math.min(capacity, resource.getAmount());
            }
            if (!fluid.isFluidEqual(resource))
            {
                return 0;
            }
            return Math.min(capacity - fluid.getAmount(), resource.getAmount());
        }
        if (fluid.isEmpty())
        {
            fluid = new FluidStack(resource, Math.min(capacity, resource.getAmount()));
            onContentsChanged();
            return fluid.getAmount();
        }
        if (!fluid.isFluidEqual(resource))
        {
            return 0;
        }
        int filled = capacity - fluid.getAmount();

        if (resource.getAmount() < filled)
        {
            fluid.grow(resource.getAmount());
            filled = resource.getAmount();
        }
        else
        {
            fluid.setAmount(capacity);
        }
        if (filled > 0)
            onContentsChanged();
        return filled;
    }
}
