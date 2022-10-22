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
        public static final TagKey<Block> ABUNDANT_RESOURCES = tag("abundant_resources");

        public static final TagKey<Block> KUZEYIUM_BLOCKS = forgeTag("storage_blocks/kuzeyium");
        public static final TagKey<Block> KUZEYIUM_ORES = forgeTag("ores/kuzeyium");
        public static final TagKey<Block> RAW_KUZEYIUM_BLOCK = forgeTag("storage_blocks/raw_kuzeyium");

        public static final TagKey<Block> INFUSED_KUZEYIUM_BLOCKS = forgeTag("storage_blocks/infused_kuzeyium");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(KuzeyMod.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name){
            return BlockTags.create(new ResourceLocation("forge", name));
        }

    }
    public static class Items {
        public static final TagKey<Item> KUZEYIAN_LOGS = tag("kuzeyian_logs");
        public static final TagKey<Item> ORGANIC_COMBUSTIBLE = tag("organic_combustible");

        public static final TagKey<Item> KUZEYIUM_INGOTS = forgeTag("ingots/kuzeyium");
        public static final TagKey<Item> KUZEYIUM_BLOCKS = forgeTag("storage_blocks/kuzeyium");
        public static final TagKey<Item> KUZEYIUM_NUGGETS = forgeTag("nuggets/kuzeyium");
        public static final TagKey<Item> RAW_KUZEYIUM = forgeTag("raw_materials/kuzeyium");
        public static final TagKey<Item> KUZEYIUM_ORES = forgeTag("ores/kuzeyium");
        public static final TagKey<Item> RAW_KUZEYIUM_BLOCK = forgeTag("storage_blocks/raw_kuzeyium");

        public static final TagKey<Item> INFUSED_KUZEYIUM_BLOCKS = forgeTag("storage_blocks/infused_kuzeyium");
        public static final TagKey<Item> INFUSED_KUZEYIUM_GEMS = forgeTag("gems/infused_kuzeyium");

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
        public static final TagKey<Fluid> WITHER_SKELETON_CONTAINMENT_CHAMBER_FLUIDS = tag("wither_skeleton_containment_chamber_fluids");
        public static final TagKey<Fluid> EMRE_ESSENCE_GENERATOR_FLUIDS = tag("emre_essence_generator_fluids");
        public static final TagKey<Fluid> COMBUSTIBLE_FLUID_GENERATOR_FLUIDS = tag("combustible_fluid_generator_fluids");

        public static final TagKey<Fluid> MOLTEN_KUZEYIUM = tag("molten_kuzeyium");
        public static final TagKey<Fluid> MOLTEN_INFUSED_KUZEYIUM = tag("molten_infused_kuzeyium");

        private static TagKey<Fluid> tag(String name) {
            return FluidTags.create(new ResourceLocation(KuzeyMod.MOD_ID, name));
        }

        private static TagKey<Fluid> forgeTag(String name) {
            return FluidTags.create(new ResourceLocation("forge", name));
        }
    }

}
