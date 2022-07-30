package com.nexoner.kuzey.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public  static final ForgeTier KUZEYIUM = new ForgeTier(4,2666,14f,7f,24,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.KUZEYIUM_INGOT.get()));
}
