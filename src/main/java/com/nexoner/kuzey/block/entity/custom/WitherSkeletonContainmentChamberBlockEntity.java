package com.nexoner.kuzey.block.entity.custom;

import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.block.entity.entityType.IFluidHandlingBlockEntity;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.fluid.ModFluids;
import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.networking.packet.FluidSyncPacket;
import com.nexoner.kuzey.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class WitherSkeletonContainmentChamberBlockEntity extends BlockEntity implements IFluidHandlingBlockEntity, IKuzeyConstants {

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public final OutputOnlyFluidTank fluidTank;

    private int progress = 0;

    public WitherSkeletonContainmentChamberBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WITHER_SKELETON_CONTAINMENT_CHAMBER_BLOCK_ENTITY.get(), pPos, pBlockState);
        fluidTank = createFluidTank();

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
         if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }


    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyFluidHandler = LazyOptional.of(() -> this.fluidTank);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putInt("progress",progress);
        tag = fluidTank.writeToNBT(tag);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        progress = nbt.getInt("progress");
        fluidTank.readFromNBT(nbt);
    }

    private OutputOnlyFluidTank createFluidTank(){
        int fluidCapacity = KuzeyCommonConfigs.witherSkeletonContainmentChamberFluidCapacity.get();
        boolean fluidFiltering = KuzeyCommonConfigs.witherSkeletonContainmentChamberFluidFiltering.get();
        if (fluidFiltering == false){
        return new OutputOnlyFluidTank(fluidCapacity, this);
        }
        return new OutputOnlyFluidTank(fluidCapacity, this, ModTags.Fluids.WITHER_SKELETON_CONTAINMENT_CHAMBER_FLUIDS);
    }

    @Override
    public void setFluid(FluidStack fluid) {
        fluidTank.setFluid(fluid);
    }

    public FluidStack getFluid(){
        return fluidTank.getFluid();
    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        int x = pPos.getX();
        int y = pPos.getY();
        int z = pPos.getZ();


        //From LeadItem class
        if (progress <= 0) {
            for (Mob mob : pLevel.getEntitiesOfClass(Mob.class, new AABB(x - 1, y, z - 1, x + 1, y + 1, z + 1))) {
                if (mob.getType() == EntityType.WITHER_SKELETON && fluidTank.getFluidAmount() + KuzeyCommonConfigs.witherSkeletonContainmentChamberFluidProduced.get() <= fluidTank.getCapacity()) {
                    pLevel.playSound(null, mob.getOnPos(), SoundEvents.WITHER_SKELETON_DEATH, SoundSource.BLOCKS, 0.8f, 0.2f);
                    mob.remove(Entity.RemovalReason.KILLED);
                    fluidTank.fillAux(new FluidStack(ModFluids.WITHER_ESSENCE.FLUID.get(), KuzeyCommonConfigs.witherSkeletonContainmentChamberFluidProduced.get()), IFluidHandler.FluidAction.EXECUTE);
                    progress = KuzeyCommonConfigs.witherSkeletonContainmentChamberCooldown.get();
                }
            }
        } else {
            progress--;
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        ModPackets.sendToClients(new FluidSyncPacket(fluidTank.getFluid(),worldPosition));
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compound = saveWithoutMetadata();
        load(compound);
        ModPackets.sendToClients(new FluidSyncPacket(fluidTank.getFluid(),worldPosition));
        return compound;
    }
}
