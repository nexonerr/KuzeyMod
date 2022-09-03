package com.nexoner.kuzey.integration.tic;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.FluidObject;


public class ModTICMaterialFluids {
    public static final ResourceLocation WATER_OVERLAY = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, KuzeyMod.MOD_ID);


    //Molten Kuzeyium
    public static final ResourceLocation STILL_MOLTEN_KUZEYIUM = new ResourceLocation("kuzey:block/fluid/molten_kuzeyium/still");
    public static final ResourceLocation FLOWING_MOLTEN_KUZEYIUM = new ResourceLocation("kuzey:block/fluid/molten_kuzeyium/flowing");

    public static final RegistryObject<ForgeFlowingFluid.Source> MOLTEN_KUZEYIUM_FLUID
            = FLUIDS.register("molten_kuzeyium", () -> new ForgeFlowingFluid.Source(ModTICMaterialFluids.MOLTEN_KUZEYIUM_PROPERTIES));

    public static final RegistryObject<ForgeFlowingFluid.Flowing> MOLTEN_KUZEYIUM_FLOWING
            = FLUIDS.register("molten_kuzeyium_flowing", () -> new ForgeFlowingFluid.Flowing(ModTICMaterialFluids.MOLTEN_KUZEYIUM_PROPERTIES));


    public static final ForgeFlowingFluid.Properties MOLTEN_KUZEYIUM_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> MOLTEN_KUZEYIUM_FLUID.get(), () -> MOLTEN_KUZEYIUM_FLOWING.get(), FluidAttributes.builder(STILL_MOLTEN_KUZEYIUM, FLOWING_MOLTEN_KUZEYIUM)
            .density(1500).viscosity(8000).temperature(1400).overlay(WATER_OVERLAY).sound(SoundEvents.BUCKET_FILL_LAVA)).slopeFindDistance(1).levelDecreasePerBlock(3)
            .block(() -> ModTICMaterialFluids.MOLTEN_KUZEYIUM_BLOCK.get()).bucket(() -> ModTICMaterialItems.MOLTEN_KUZEYIUM_BUCKET.get());

    public static final RegistryObject<LiquidBlock> MOLTEN_KUZEYIUM_BLOCK = ModBlocks.BLOCKS.register("molten_kuzeyium",
            () -> new LiquidBlock(() -> ModTICMaterialFluids.MOLTEN_KUZEYIUM_FLUID.get(), BlockBehaviour.Properties.of(Material.LAVA)
                    .noCollission().strength(100f).noDrops()));

    public static final FluidObject<ForgeFlowingFluid> MOLTEN_KUZEYIUM_OBJECT = new FluidObject<ForgeFlowingFluid>(new ResourceLocation(KuzeyMod.MOD_ID, "molten_kuzeyium"), "molten_kuzeyium",  MOLTEN_KUZEYIUM_FLUID, MOLTEN_KUZEYIUM_FLOWING, MOLTEN_KUZEYIUM_BLOCK);


    //Molten Infused Kuzeyium
    public static final ResourceLocation STILL_MOLTEN_INFUSED_KUZEYIUM = new ResourceLocation("kuzey:block/fluid/molten_infused_kuzeyium/still");
    public static final ResourceLocation FLOWING_MOLTEN_INFUSED_KUZEYIUM = new ResourceLocation("kuzey:block/fluid/molten_infused_kuzeyium/flowing");

    public static final RegistryObject<ForgeFlowingFluid.Source> MOLTEN_INFUSED_KUZEYIUM_FLUID
            = FLUIDS.register("molten_infused_kuzeyium", () -> new ForgeFlowingFluid.Source(ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_PROPERTIES));

    public static final RegistryObject<ForgeFlowingFluid.Flowing> MOLTEN_INFUSED_KUZEYIUM_FLOWING
            = FLUIDS.register("molten_infused_kuzeyium_flowing", () -> new ForgeFlowingFluid.Flowing(ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_PROPERTIES));


    public static final ForgeFlowingFluid.Properties MOLTEN_INFUSED_KUZEYIUM_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> MOLTEN_INFUSED_KUZEYIUM_FLUID.get(), () -> MOLTEN_INFUSED_KUZEYIUM_FLOWING.get(), FluidAttributes.builder(STILL_MOLTEN_INFUSED_KUZEYIUM, FLOWING_MOLTEN_INFUSED_KUZEYIUM)
            .density(1500).viscosity(8000).temperature(1700).overlay(WATER_OVERLAY).sound(SoundEvents.BUCKET_FILL_LAVA)).slopeFindDistance(1).levelDecreasePerBlock(3)
            .block(() -> ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_BLOCK.get()).bucket(() -> ModTICMaterialItems.MOLTEN_INFUSED_KUZEYIUM_BUCKET.get());

    public static final RegistryObject<LiquidBlock> MOLTEN_INFUSED_KUZEYIUM_BLOCK = ModBlocks.BLOCKS.register("molten_infused_kuzeyium",
            () -> new LiquidBlock(() -> ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_FLUID.get(), BlockBehaviour.Properties.of(Material.LAVA)
                    .noCollission().strength(100f).noDrops()));

    public static final FluidObject<ForgeFlowingFluid> MOLTEN_INFUSED_KUZEYIUM_OBJECT = new FluidObject<ForgeFlowingFluid>(new ResourceLocation(KuzeyMod.MOD_ID, "molten_infused_kuzeyium"), "molten_infused_kuzeyium", MOLTEN_INFUSED_KUZEYIUM_FLUID, MOLTEN_INFUSED_KUZEYIUM_FLOWING, MOLTEN_INFUSED_KUZEYIUM_BLOCK);

    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
    }

}
