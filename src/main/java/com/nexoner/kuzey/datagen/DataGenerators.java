package com.nexoner.kuzey.datagen;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.integration.tic.ModTICMaterialDataGenerators;
import com.nexoner.kuzey.integration.tic.ModTICMaterialRenderInfo;
import com.nexoner.kuzey.integration.tic.ModTICMaterialTextures;
import com.nexoner.kuzey.integration.tic.ModTICMaterials;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

@Mod.EventBusSubscriber(modid = KuzeyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(new ModRecipeProvider(generator));
        generator.addProvider(new ModLootTableProvider(generator));
        generator.addProvider(new ModItemModelProvider(generator,existingFileHelper));
        generator.addProvider(new ModBlockStateProvider(generator,existingFileHelper));
        generator.addProvider(new ModLanguageProvider(generator, KuzeyMod.MOD_ID, "en_us"));

        if (ModList.get().isLoaded("tconstruct")){
            ModTICMaterialDataGenerators.gatherData(event);
        }
    }
}
