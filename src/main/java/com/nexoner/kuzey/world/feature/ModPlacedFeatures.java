package com.nexoner.kuzey.world.feature;


import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModPlacedFeatures {

    public static final Holder<PlacedFeature> KUZEYIAN_PLACED = PlacementUtils.register("kuzeyian_placed",
            ModConfiguredFeatures.KUZEYIAN_SPAWN,RarityFilter.onAverageOnceEvery(KuzeyCommonConfigs.kuzeyianTreeRate.get()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);

    public static final Holder<PlacedFeature> KUZEYIUM_ORE_PLACED = PlacementUtils.register("kuzeyium_ore_placed",
            ModConfiguredFeatures.KUZEYIUM_ORE, ModOrePlacement.commonOrePlacement(KuzeyCommonConfigs.kuzeyiumOreRate.get(), // VeinsPerChunk
                    HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.aboveBottom(KuzeyCommonConfigs.kuzeyiumOreMinSpawn.get()),VerticalAnchor.aboveBottom(KuzeyCommonConfigs.kuzeyiumOreMaxSpawn.get()),KuzeyCommonConfigs.kuzeyiumOreInner.get()))));

    public static final Holder<PlacedFeature> EMRE_ESSENCE_ROCK_ORE_PLACED = PlacementUtils.register("emre_essence_rock_ore_placed",
            ModConfiguredFeatures.EMRE_ESSENCE_ROCK_ORE, ModOrePlacement.commonOrePlacement(KuzeyCommonConfigs.emreEssenceRockOreRate.get(), // VeinsPerChunk
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(KuzeyCommonConfigs.emreEssenceRockOreMinSpawn.get()), VerticalAnchor.absolute(KuzeyCommonConfigs.emreEssenceRockOreMaxSpawn.get()))));

    public static final Holder<PlacedFeature> EMRE_ESSENCE_ROCK_ORE_MEGA_VEIN_PLACED = PlacementUtils.register("emre_essence_rock_ore_mega_vein_placed",
            ModConfiguredFeatures.EMRE_ESSENCE_ROCK_ORE_MEGA_VEIN, ModOrePlacement.rareOrePlacement(KuzeyCommonConfigs.emreEssenceRockOreMegaRate.get(), // Once Every x Chunks
                    HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.absolute(KuzeyCommonConfigs.emreEssenceRockOreMegaInner.get()), VerticalAnchor.absolute(KuzeyCommonConfigs.emreEssenceRockOreMegaMaxSpawn.get()), KuzeyCommonConfigs.emreEssenceRockOreMegaInner.get()))));

}
