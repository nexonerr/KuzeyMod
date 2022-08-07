package com.nexoner.kuzey;

import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.fluid.ModFluids;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.recipe.ModRecipes;
import com.nexoner.kuzey.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(KuzeyMod.MOD_ID)
public class KuzeyMod
{
    public static final String MOD_ID = "kuzey";
    public static final Logger LOGGER = LogManager.getLogger();

    public KuzeyMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);
        ModFluids.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.KUZEYIUM_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLACK_CARROTS_PLANT.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.KUZEYIAN_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.KUZEYIAN_SAPLING.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.KUZEYIUM_PURIFICATION_CHAMBER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.KUZEYIUM_WORKSTATION.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(ModFluids.EMRE_ESSENCE_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.EMRE_ESSENCE_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.EMRE_ESSENCE_FLUID.get(), RenderType.translucent());

        MenuScreens.register(ModMenuTypes.KUZEYIUM_PURIFICATION_CHAMBER_MENU.get(), KuzeyiumPurificationChamberScreen::new);
        MenuScreens.register(ModMenuTypes.KUZEYIUM_WORKSTATION_MENU.get(), KuzeyiumWorkstationScreen::new);
        MenuScreens.register(ModMenuTypes.EMRE_ESSENCE_EXTRACTOR_MENU.get(), EmreEssenceExtractorScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModPackets.register();
        });
    }}
