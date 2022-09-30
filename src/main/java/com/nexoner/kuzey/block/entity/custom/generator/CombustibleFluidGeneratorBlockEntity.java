package com.nexoner.kuzey.block.entity.custom.generator;

import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.block.entity.template.AbstractFluidGeneratorEntity;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.fluid.ModFluids;
import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.networking.packet.EnergySyncPacket;
import com.nexoner.kuzey.networking.packet.FluidSyncPacket;
import com.nexoner.kuzey.screen.CombustibleFluidGeneratorMenu;
import com.nexoner.kuzey.screen.EmreEssenceGeneratorMenu;
import com.nexoner.kuzey.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class CombustibleFluidGeneratorBlockEntity extends AbstractFluidGeneratorEntity {

    public CombustibleFluidGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COMBUSTIBLE_FLUID_GENERATOR_BLOCK_ENTITY.get(), pPos, pBlockState, new TranslatableComponent("block.kuzey.combustible_fluid_generator"),
                KuzeyCommonConfigs.combustibleFluidGeneratorCapacity.get(), KuzeyCommonConfigs.combustibleFluidGeneratorMaxExtracted.get(), KuzeyCommonConfigs.combustibleFluidGeneratorProduced.get(), 1,
                KuzeyCommonConfigs.combustibleFluidGeneratorFluidCapacity.get(),ModTags.Fluids.COMBUSTIBLE_FLUID_GENERATOR_FLUIDS, KuzeyCommonConfigs.combustibleFluidGeneratorFluidFiltering.get());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        ModPackets.sendToClients(new FluidSyncPacket(this.fluidTank.getFluid(), worldPosition));
        ModPackets.sendToClients(new EnergySyncPacket(energyStorage.getEnergyStored(),worldPosition));
        return new CombustibleFluidGeneratorMenu(pContainerId,pInventory,this,this.data);
    }

    @Override
    public void defineFluidBurnRate(HashMap<Fluid, Integer> map) {
        map.put(ModFluids.WEAK_COMBUSTIBLE_FLUID.get(),20);
        map.put(ModFluids.COMBUSTIBLE_FLUID.get(),20);
        map.put(ModFluids.STRONG_COMBUSTIBLE_FLUID.get(),20);
        map.put(Fluids.LAVA,10);
    }

    @Override
    public void defineFluidGenerated(HashMap<Fluid, Integer> map) {
        map.put(ModFluids.WEAK_COMBUSTIBLE_FLUID.get(), (int) (produced * 0.7));
        map.put(ModFluids.COMBUSTIBLE_FLUID.get(),(int) (produced * 1));
        map.put(ModFluids.STRONG_COMBUSTIBLE_FLUID.get(),(int) (produced * 1.35));
        map.put(Fluids.LAVA,(int) (produced * 0.4));
    }

    @Override
    public void defineFluidBurnRateMB(HashMap<Fluid, Integer> map) {
        map.put(ModFluids.WEAK_COMBUSTIBLE_FLUID.get(),2);
        map.put(ModFluids.COMBUSTIBLE_FLUID.get(),2);
        map.put(ModFluids.STRONG_COMBUSTIBLE_FLUID.get(),2);
        map.put(Fluids.LAVA,4);
    }

    @Override
    public void setFluid(FluidStack fluid) {
        fluidTank.setFluid(fluid);
    }

    @Override
    public FluidStack getFluid() {
        return fluidTank.getFluid();
    }
}
