package com.nexoner.kuzey.util;

import com.nexoner.kuzey.KuzeyMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;


public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> KUZEYIAN_LOGS = tag("kuzeyian_logs");
        public static final TagKey<Block> HAMMER_MINEABLE = tag("hammer_mineable");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(KuzeyMod.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name){
            return BlockTags.create(new ResourceLocation("forge", name));
        }

    }
    public static class Items {
        public static final TagKey<Item> KUZEYIUM_INGOTS = forgeTag("ingots/kuzeyium");
        public static final TagKey<Item> KUZEYIAN_LOGS = tag("kuzeyian_logs");

        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(KuzeyMod.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name){
            return ItemTags.create(new ResourceLocation("forge", name));
        }


    }
    public static class Fluids {
        public static final TagKey<Fluid> EMRE_ESSENCE_EXTRACTOR_FLUIDS = tag("emre_essence_extractor_fluids");
        public static final TagKey<Fluid> EMRE_ESSENCE_INFUSER_FLUIDS = tag("emre_essence_infuser_fluids");

        private static TagKey<Fluid> tag(String name) {
            return FluidTags.create(new ResourceLocation(KuzeyMod.MOD_ID, name));
        }

        private static TagKey<Fluid> forgeTag(String name) {
            return FluidTags.create(new ResourceLocation("forge", name));
        }
    }

}
