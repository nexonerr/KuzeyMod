package com.nexoner.kuzey.world.gen;

import com.nexoner.kuzey.world.feature.ModPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;

public class ModOreGeneration {
    public static void generateOres(final BiomeLoadingEvent event) {
        List<Holder<PlacedFeature>> base =
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        base.add(ModPlacedFeatures.KUZEYIUM_ORE_PLACED);
        base.add(ModPlacedFeatures.EMRE_ESSENCE_ROCK_ORE_PLACED);
        base.add(ModPlacedFeatures.EMRE_ESSENCE_ROCK_ORE_MEGA_VEIN_PLACED);
    }
}
