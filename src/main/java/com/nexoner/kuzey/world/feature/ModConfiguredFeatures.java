package com.nexoner.kuzey.world.feature;

import com.nexoner.kuzey.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;


public class ModConfiguredFeatures {
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> KUZEYIAN_TREE =
            FeatureUtils.register("kuzeyian", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.KUZEYIAN_LOG.get()),
                    new ForkingTrunkPlacer(5,6,3),
                    BlockStateProvider.simple(ModBlocks.KUZEYIAN_LEAVES.get()),
                    new BushFoliagePlacer(ConstantInt.of(1),ConstantInt.of(1),4),
                    new TwoLayersFeatureSize(2,0,4)).ignoreVines().build());

                    public static final Holder<PlacedFeature> KUZEYIAN_CHECKED = PlacementUtils.register
                            ("kuzeyian_checked", KUZEYIAN_TREE, PlacementUtils.filteredByBlockSurvival(ModBlocks.KUZEYIAN_SAPLING.get()));

                    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ? >> KUZEYIAN_SPAWN =
                            FeatureUtils.register("kuzeyian_spawn", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(KUZEYIAN_CHECKED, 0.5f)), KUZEYIAN_CHECKED));
}
