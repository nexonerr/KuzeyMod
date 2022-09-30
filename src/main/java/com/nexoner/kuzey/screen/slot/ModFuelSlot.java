package com.nexoner.kuzey.screen.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModFuelSlot extends SlotItemHandler {
    public ModFuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return !stack.hasContainerItem() && ForgeHooks.getBurnTime(stack,null) > 0;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
