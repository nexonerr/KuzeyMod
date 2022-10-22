package com.nexoner.kuzey.fluid;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.item.ModCreativeModeTab;
import com.nexoner.kuzey.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RegisteredFluid {

    public final ResourceLocation STILL_RL;
    public final ResourceLocation FLOWING_RL;

    public final ForgeFlowingFluid.Properties PROPERTIES;
    public final RegistryObject<ForgeFlowingFluid.Source> FLUID;
    public final RegistryObject<ForgeFlowingFluid.Flowing> FLUID_FLOW;
    public final RegistryObject<LiquidBlock> BLOCK;
    public final RegistryObject<BucketItem> BUCKET;

    public final String name;
    public final int temperature;
    public final int light;
    public final int density;
    public final int viscosity;
    public final int strength;
    public final int color;
    public final Material material;
    public final SoundEvent soundEvent;

    public final DeferredRegister<Fluid> FLUIDS = ModFluids.FLUIDS;

    public RegisteredFluid(ResourceLocation still_rl, ResourceLocation flowing_rl, ResourceLocation overlay_rl, String name, int temperature, int light, int density, int viscosity, int strength, int color, Material material, SoundEvent soundEvent){
        STILL_RL = still_rl;
        FLOWING_RL = flowing_rl;

        this.name = name;
        this.temperature = temperature;
        this.light = light;
        this.density = density;
        this.viscosity = viscosity;
        this.strength = strength;
        this.color = color;
        this.material = material;
        this.soundEvent = soundEvent;

        FLUID = FLUIDS.register(name, () -> new ForgeFlowingFluid.Source(getProperties()));
        FLUID_FLOW = FLUIDS.register(name + "_flowing", () -> new ForgeFlowingFluid.Flowing(getProperties()));

        BLOCK = ModBlocks.BLOCKS.register(name, () -> new LiquidBlock(() -> FLUID.get(), BlockBehaviour.Properties.of(material).noCollission().strength(strength).noDrops()));
        BUCKET = ModItems.ITEMS.register(name + "_bucket", () -> new BucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ModCreativeModeTab.KUZEY_TAB)));

        PROPERTIES = new ForgeFlowingFluid.Properties(
                () -> FLUID.get(), () -> FLUID_FLOW.get(), FluidAttributes.builder(STILL_RL, FLOWING_RL)
                .density(density).luminosity(light).viscosity(viscosity).sound(soundEvent).overlay(overlay_rl)
                .color(color)).slopeFindDistance(7).levelDecreasePerBlock(2)
                .block(() -> BLOCK.get()).bucket(() -> BUCKET.get());
    }

    public RegisteredFluid(String name, int temperature, int light, int density, int viscosity, int strength, int color, Material material, SoundEvent soundEvent){
        STILL_RL = new ResourceLocation(KuzeyMod.MOD_ID, "block/fluid/" + name + "_still");
        FLOWING_RL = new ResourceLocation(KuzeyMod.MOD_ID, "block/fluid/" + name + "_flow");

        this.name = name;
        this.temperature = temperature;
        this.light = light;
        this.density = density;
        this.viscosity = viscosity;
        this.strength = strength;
        this.color = color;
        this.material = material;
        this.soundEvent = soundEvent;

        FLUID = FLUIDS.register(name, () -> new ForgeFlowingFluid.Source(getProperties()));
        FLUID_FLOW = FLUIDS.register(name + "_flowing", () -> new ForgeFlowingFluid.Flowing(getProperties()));

        BLOCK = ModBlocks.BLOCKS.register(name, () -> new LiquidBlock(() -> FLUID.get(), BlockBehaviour.Properties.of(material).noCollission().strength(strength).noDrops()));
        BUCKET = ModItems.ITEMS.register(name + "_bucket", () -> new BucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ModCreativeModeTab.KUZEY_TAB)));

        PROPERTIES = new ForgeFlowingFluid.Properties(
                () -> FLUID.get(), () -> FLUID_FLOW.get(), FluidAttributes.builder(STILL_RL, FLOWING_RL)
                .density(density).luminosity(light).viscosity(viscosity).sound(soundEvent)
                .color(color)).slopeFindDistance(7).levelDecreasePerBlock(2)
                .block(() -> BLOCK.get()).bucket(() -> BUCKET.get());
    }

    public ForgeFlowingFluid.Properties getProperties() {
        return PROPERTIES;
    }

}
