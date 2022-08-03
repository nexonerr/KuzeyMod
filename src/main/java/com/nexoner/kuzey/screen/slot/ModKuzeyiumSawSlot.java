package com.nexoner.kuzey.screen.slot;

import com.nexoner.kuzey.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModKuzeyiumSawSlot extends SlotItemHandler {
    public ModKuzeyiumSawSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return ModItems.KUZEYIUM_SAW.get().getDefaultInstance().sameItemStackIgnoreDurability(stack);
    }
}
