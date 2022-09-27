package com.nexoner.kuzey.block.entity.entityType;

import com.nexoner.kuzey.util.CustomEnergyStorage;

public interface IEnergyHandlingBlockEntity {
    void setEnergy(int energy);
    CustomEnergyStorage getEnergyStorage();
}
