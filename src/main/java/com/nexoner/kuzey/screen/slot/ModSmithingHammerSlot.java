package com.nexoner.kuzey.screen.slot;

import com.nexoner.kuzey.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ModSmithingHammerSlot extends SlotItemHandler {
    public ModSmithingHammerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return ModItems.KUZEYIUM_SMITHING_HAMMER.get().getDefaultInstance().sameItemStackIgnoreDurability(stack);
    }
}
