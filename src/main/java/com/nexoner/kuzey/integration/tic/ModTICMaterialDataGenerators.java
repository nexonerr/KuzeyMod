package com.nexoner.kuzey.integration.tic;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

public class ModTICMaterialDataGenerators {
    public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            if (event.includeServer()) {
                AbstractMaterialDataProvider materials = new ModTICMaterials(generator);
                generator.addProvider(materials);
                generator.addProvider(new ModTICMaterials.ModTICMaterialStats(generator, materials));
                generator.addProvider(new ModTICMaterials.ModTICMaterialTraits(generator, materials));
                generator.addProvider(new ModTICMaterialRecipeProvider(generator));
            }
            if (event.includeClient()) {
                ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
                ModTICMaterialTextures materialSprites = new ModTICMaterialTextures();
                generator.addProvider(new ModTICMaterialRenderInfo(generator, materialSprites));
                generator.addProvider(new MaterialPartTextureGenerator(generator, existingFileHelper, new TinkerPartSpriteProvider(), materialSprites));
            }
        }
    }

