package com.nexoner.kuzey.item;

import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public  static final ForgeTier KUZEYIUM = new ForgeTier(4, 2666,14f,7f,24,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.KUZEYIUM_INGOT.get()));

    public  static final ForgeTier INFUSED_KUZEYIUM = new ForgeTier(5,3222,15f,8f,26,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.INFUSED_KUZEYIUM_GEM.get()));
}
