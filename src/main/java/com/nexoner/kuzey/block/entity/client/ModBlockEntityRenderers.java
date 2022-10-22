package com.nexoner.kuzey.block.entity.client;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.entity.ModBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = KuzeyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModBlockEntityRenderers {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.EMRE_ESSENCE_INFUSER_BLOCK_ENTITY.get(),
                EmreEssenceInfuserBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.COMBUSTIBLE_FLUID_GENERATOR_BLOCK_ENTITY.get(),
                CombustibleFluidGeneratorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.WITHER_SKELETON_CONTAINMENT_CHAMBER_BLOCK_ENTITY.get(),
                WitherSkeletonContainmentChamberBlockEntityRenderer::new);
    }
}
