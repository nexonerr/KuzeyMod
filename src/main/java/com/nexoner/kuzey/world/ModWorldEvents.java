package com.nexoner.kuzey.world;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.world.gen.ModTreeGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KuzeyMod.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        ModTreeGeneration.generateTrees(event);
    }
}
