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
    private static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    private static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    private static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, KuzeyMod.MOD_ID);

    public static final RegisteredFluid EMRE_ESSENCE = new RegisteredFluid(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,"emre_essence",250,8,40,20,100,0xbf3de514,
            Material.WATER,SoundEvents.BUCKET_EMPTY_LAVA);

    public static final RegisteredFluid WEAK_COMBUSTIBLE_FLUID = new RegisteredFluid(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,"weak_combustible_fluid",250,8,25,15,120,0xffe2e991,
            Material.WATER,SoundEvents.BUCKET_FILL);
    public static final RegisteredFluid COMBUSTIBLE_FLUID = new RegisteredFluid(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,"combustible_fluid",250,8,35,15,120,0xffb3b860,
            Material.WATER,SoundEvents.BUCKET_FILL);
    public static final RegisteredFluid STRONG_COMBUSTIBLE_FLUID = new RegisteredFluid(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,"strong_combustible_fluid",250,8,45,15,120,0xffbaab3a,
            Material.WATER,SoundEvents.BUCKET_FILL);

    public static final RegisteredFluid WITHER_ESSENCE = new RegisteredFluid(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,"wither_essence",3000,8,500,5,120,0xff1181B7,
            Material.WATER,SoundEvents.BUCKET_FILL);
    public static final RegisteredFluid ENRICHED_WITHER_ESSENCE = new RegisteredFluid(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,"enriched_wither_essence",3000,8,700,5,120,0xff00ffa9,
            Material.WATER,SoundEvents.BUCKET_FILL);
    public static final RegisteredFluid FILTRATED_WITHER_ESSENCE = new RegisteredFluid(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,"filtrated_wither_essence",3000,8,900,5,120,0xff2492ce,
            Material.WATER,SoundEvents.BUCKET_FILL);
    public static final RegisteredFluid PURE_WITHER_ESSENCE = new RegisteredFluid(WATER_STILL_RL,WATER_FLOWING_RL,WATER_OVERLAY_RL,"pure_wither_essence",3000,8,1200,5,120,0xff00fce4,
            Material.WATER,SoundEvents.BUCKET_FILL);

    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
    }

}

