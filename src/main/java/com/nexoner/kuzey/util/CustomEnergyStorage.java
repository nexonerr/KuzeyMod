package com.nexoner.kuzey.util;

import com.nexoner.kuzey.block.entity.entityType.IEnergyHandlingBlockEntity;
import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.networking.packet.EnergySyncPacket;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {
    private final BlockEntity  blockEntity;

    public CustomEnergyStorage(BlockEntity blockEntity, int capacity) {
        super(capacity);
        this.blockEntity = blockEntity;
    }
    public CustomEnergyStorage(BlockEntity blockEntity, int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.blockEntity = blockEntity;
    }
    public CustomEnergyStorage(BlockEntity blockEntity, int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.blockEntity = blockEntity;
    }
    public CustomEnergyStorage(BlockEntity blockEntity, int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
        this.blockEntity = blockEntity;
    }

    public void removeEnergy(int toRemove){
        setEnergy(getEnergyStored() - toRemove);
        onEnergyChanged();
    }

    public void addEnergy(int toAdd){
        setEnergy(getEnergyStored() + toAdd);
        onEnergyChanged();
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        this.blockEntity.setChanged();
        onEnergyChanged();
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        this.blockEntity.setChanged();
        onEnergyChanged();
        return super.receiveEnergy(maxReceive, simulate);
    }

    public int getSpace(){
        return getMaxEnergyStored() - getEnergyStored();
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0,Math.min(energy, this.capacity));
    }

    public void onEnergyChanged(){

    }
}
