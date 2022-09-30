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

    public static final RegistryObject<ForgeFlowingFluid.Source> EMRE_ESSENCE_FLUID
            = FLUIDS.register("emre_essence", () -> new ForgeFlowingFluid.Source(ModFluids.EMRE_ESSENCE_PROPERTIES));

    public static final RegistryObject<ForgeFlowingFluid.Flowing> EMRE_ESSENCE_FLOWING
            = FLUIDS.register("emre_essence_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.EMRE_ESSENCE_PROPERTIES));


    public static final ForgeFlowingFluid.Properties EMRE_ESSENCE_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> EMRE_ESSENCE_FLUID.get(), () -> EMRE_ESSENCE_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(40).luminosity(8).viscosity(20).sound(SoundEvents.BUCKET_EMPTY_LAVA).overlay(WATER_OVERLAY_RL)
            .color(0xbf3de514)).slopeFindDistance(1).levelDecreasePerBlock(7)
            .block(() -> ModFluids.EMRE_ESSENCE_BLOCK.get()).bucket(() -> ModItems.EMRE_ESSENCE_BUCKET.get());

    public static final RegistryObject<LiquidBlock> EMRE_ESSENCE_BLOCK = ModBlocks.BLOCKS.register("emre_essence",
            () -> new LiquidBlock(() -> ModFluids.EMRE_ESSENCE_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(100f).noDrops()));


    public static final RegistryObject<ForgeFlowingFluid.Source> WEAK_COMBUSTIBLE_FLUID
            = FLUIDS.register("weak_combustible_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.WEAK_COMBUSTIBLE_FLUID_PROPERTIES));

    public static final RegistryObject<ForgeFlowingFluid.Flowing> WEAK_COMBUSTIBLE_FLUID_FLOWING
            = FLUIDS.register("weak_combustible_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.WEAK_COMBUSTIBLE_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties WEAK_COMBUSTIBLE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> WEAK_COMBUSTIBLE_FLUID.get(), () -> WEAK_COMBUSTIBLE_FLUID_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(25).luminosity(8).viscosity(15).sound(SoundEvents.BUCKET_FILL).overlay(WATER_OVERLAY_RL)
            .color(0xffe2e991)).slopeFindDistance(6).levelDecreasePerBlock(2)
            .block(() -> ModFluids.WEAK_COMBUSTIBLE_FLUID_BLOCK.get()).bucket(() -> ModItems.WEAK_COMBUSTIBLE_FLUID_BUCKET.get());

    public static final RegistryObject<LiquidBlock> WEAK_COMBUSTIBLE_FLUID_BLOCK = ModBlocks.BLOCKS.register("weak_combustible_fluid",
            () -> new LiquidBlock(() -> ModFluids.WEAK_COMBUSTIBLE_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(120f).noDrops()));


    public static final RegistryObject<ForgeFlowingFluid.Source> COMBUSTIBLE_FLUID
            = FLUIDS.register("combustible_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.COMBUSTIBLE_FLUID_PROPERTIES));

    public static final RegistryObject<ForgeFlowingFluid.Flowing> COMBUSTIBLE_FLUID_FLOWING
            = FLUIDS.register("combustible_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.COMBUSTIBLE_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties COMBUSTIBLE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> COMBUSTIBLE_FLUID.get(), () -> COMBUSTIBLE_FLUID_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(35).luminosity(8).viscosity(15).sound(SoundEvents.BUCKET_FILL).overlay(WATER_OVERLAY_RL)
            .color(0xffb3b860)).slopeFindDistance(6).levelDecreasePerBlock(2)
            .block(() -> ModFluids.COMBUSTIBLE_FLUID_BLOCK.get()).bucket(() -> ModItems.COMBUSTIBLE_FLUID_BUCKET.get());

    public static final RegistryObject<LiquidBlock> COMBUSTIBLE_FLUID_BLOCK = ModBlocks.BLOCKS.register("combustible_fluid",
            () -> new LiquidBlock(() -> ModFluids.COMBUSTIBLE_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(120f).noDrops()));


    public static final RegistryObject<ForgeFlowingFluid.Source> STRONG_COMBUSTIBLE_FLUID
            = FLUIDS.register("strong_combustible_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.STRONG_COMBUSTIBLE_FLUID_PROPERTIES));

    public static final RegistryObject<ForgeFlowingFluid.Flowing> STRONG_COMBUSTIBLE_FLUID_FLOWING
            = FLUIDS.register("strong_combustible_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.STRONG_COMBUSTIBLE_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties STRONG_COMBUSTIBLE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> STRONG_COMBUSTIBLE_FLUID.get(), () -> STRONG_COMBUSTIBLE_FLUID_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(45).luminosity(8).viscosity(15).sound(SoundEvents.BUCKET_FILL).overlay(WATER_OVERLAY_RL)
            .color(0xffbaab3a)).slopeFindDistance(6).levelDecreasePerBlock(2)
            .block(() -> ModFluids.STRONG_COMBUSTIBLE_FLUID_BLOCK.get()).bucket(() -> ModItems.STRONG_COMBUSTIBLE_FLUID_BUCKET.get());

    public static final RegistryObject<LiquidBlock> STRONG_COMBUSTIBLE_FLUID_BLOCK = ModBlocks.BLOCKS.register("strong_combustible_fluid",
            () -> new LiquidBlock(() -> ModFluids.STRONG_COMBUSTIBLE_FLUID.get(), BlockBehaviour.Properties.of(Material.WATER)
                    .noCollission().strength(140f).noDrops()));

    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
    }

}
