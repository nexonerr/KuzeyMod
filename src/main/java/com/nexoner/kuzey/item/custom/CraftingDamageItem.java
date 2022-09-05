package com.nexoner.kuzey.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CraftingDamageItem extends Item {
    int craftDamage;
    public CraftingDamageItem(int pCraftDamage ,Properties pProperties) {
        super(pProperties);
        craftDamage = pCraftDamage;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {

        ItemStack newStack = itemStack.copy();
        newStack.setDamageValue(itemStack.getDamageValue() + craftDamage);

        return newStack;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }
}
