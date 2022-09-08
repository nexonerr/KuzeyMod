package com.nexoner.kuzey.world.feature;

import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.util.ModOreFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
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
                            FeatureUtils.register("kuzeyian_spawn", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(KUZEYIAN_CHECKED, ((float) KuzeyCommonConfigs.kuzeyianTreeSpawnChance.get()) / 10f)), KUZEYIAN_CHECKED));


                    public static final List<OreConfiguration.TargetBlockState> KUZEYIUM_ORES = List.of(
                        OreConfiguration.target(ModOreFeatures.END_STONE_ORE_REPLACABLES, ModBlocks.KUZEYIUM_ORE.get().defaultBlockState()));

                    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> KUZEYIUM_ORE = FeatureUtils.register("kuzeyium_ore",
                        Feature.ORE, new OreConfiguration(KUZEYIUM_ORES, KuzeyCommonConfigs.kuzeyiumOreVeinSize.get()));


                    public static final List<OreConfiguration.TargetBlockState> EMRE_ESSENCE_ROCK_ORES = List.of(
                            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.EMRE_ESSENCE_ROCK_ORE.get().defaultBlockState()),
                            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_EMRE_ESSENCE_ROCK_ORE.get().defaultBlockState()));

                    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> EMRE_ESSENCE_ROCK_ORE = FeatureUtils.register("emre_essence_rock_ore",
                            Feature.ORE, new OreConfiguration(EMRE_ESSENCE_ROCK_ORES, KuzeyCommonConfigs.emreEssenceRockOreVeinSize.get()));

                    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> EMRE_ESSENCE_ROCK_ORE_MEGA_VEIN = FeatureUtils.register("emre_essence_rock_ore_mega_vein",
                            Feature.ORE, new OreConfiguration(EMRE_ESSENCE_ROCK_ORES, KuzeyCommonConfigs.emreEssenceRockOreMegaVeinSize.get()));
}
