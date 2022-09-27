package com.nexoner.kuzey.block.entity.custom.generator;

import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.block.entity.template.AbstractFluidGeneratorEntity;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.fluid.ModFluids;
import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.networking.packet.EnergySyncPacket;
import com.nexoner.kuzey.networking.packet.FluidSyncPacket;
import com.nexoner.kuzey.screen.EmreEssenceGeneratorMenu;
import com.nexoner.kuzey.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class EmreEssenceGeneratorBlockEntity extends AbstractFluidGeneratorEntity {

    public EmreEssenceGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.EMRE_ESSENCE_GENERATOR_BLOCK_ENTITY.get(), pPos, pBlockState, new TranslatableComponent("block.kuzey.emre_essence_generator"),
                KuzeyCommonConfigs.emreEssenceGeneratorCapacity.get(), KuzeyCommonConfigs.emreEssenceGeneratorMaxExtracted.get(), KuzeyCommonConfigs.emreEssenceGeneratorProduced.get(), 1, KuzeyCommonConfigs.emreEssenceGeneratorFluidCapacity.get()
                ,ModTags.Fluids.EMRE_ESSENCE_GENERATOR_FLUIDS, KuzeyCommonConfigs.emreEssenceGeneratorFluidFiltering.get());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        ModPackets.sendToClients(new FluidSyncPacket(this.fluidTank.getFluid(), worldPosition));
        ModPackets.sendToClients(new EnergySyncPacket(energyStorage.getEnergyStored(),worldPosition));
        return new EmreEssenceGeneratorMenu(pContainerId,pInventory,this,this.data);
    }

    @Override
    public void defineFluidBurnRate(HashMap<Fluid, Integer> map) {
        map.put(ModFluids.EMRE_ESSENCE_FLUID.get(), 8);
    }

    @Override
    public void defineFluidGenerated(HashMap<Fluid, Integer> map) {
        map.put(ModFluids.EMRE_ESSENCE_FLUID.get(), produced);
    }

    @Override
    public void defineFluidBurnRateMB(HashMap<Fluid, Integer> map) {
        map.put(ModFluids.EMRE_ESSENCE_FLUID.get(), 1);
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
