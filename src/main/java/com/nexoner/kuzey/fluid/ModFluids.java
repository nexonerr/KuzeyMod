package com.nexoner.kuzey.fluid;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, KuzeyMod.MOD_ID);

    public static final RegistryObject<FlowingFluid> EMRE_ESSENCE_FLUID
            = FLUIDS.register("emre_essence_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.EMRE_ESSENCE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> EMRE_ESSENCE_FLOWING
            = FLUIDS.register("emre_essence_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.EMRE_ESSENCE_PROPERTIES));


    public static final ForgeFlowingFluid.Properties EMRE_ESSENCE_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> EMRE_ESSENCE_FLUID.get(), () -> EMRE_ESSENCE_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(40).luminosity(8).viscosity(20).sound(SoundEvents.MINECART_INSIDE_UNDERWATER).overlay(WATER_OVERLAY_RL)
            .color(0xbf3de514)).slopeFindDistance(1).levelDecreasePerBlock(7)
            .block(() -> ModFluids.EMRE_ESSENCE_BLOCK.get()).bucket(() -> ModItems.EMRE_ESSENCE_BUCKET.get());

    public static final RegistryObject<LiquidBlock> EMRE_ESSENCE_BLOCK = ModBlocks.BLOCKS.register("emre_essence",
            () -> new LiquidBlock(() -> ModFluids.EMRE_ESSENCE_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()));

    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
    }

}
