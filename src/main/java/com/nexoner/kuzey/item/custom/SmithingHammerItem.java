package com.nexoner.kuzey.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SmithingHammerItem extends Item {
    public SmithingHammerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {

        ItemStack newStack = itemStack.copy();
        newStack.setDamageValue(itemStack.getDamageValue() + 1);

        return newStack;
    }


}
