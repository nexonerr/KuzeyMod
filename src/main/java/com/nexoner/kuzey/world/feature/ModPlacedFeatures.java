package com.nexoner.kuzey.world.feature;


import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class ModPlacedFeatures {

    public static final Holder<PlacedFeature> KUZEYIAN_PLACED = PlacementUtils.register("kuzeyian_placed",
            ModConfiguredFeatures.KUZEYIAN_SPAWN,RarityFilter.onAverageOnceEvery(200), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);

}
