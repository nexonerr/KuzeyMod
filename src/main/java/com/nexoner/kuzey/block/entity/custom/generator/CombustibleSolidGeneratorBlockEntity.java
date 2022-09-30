package com.nexoner.kuzey.block.entity.custom.generator;

import com.nexoner.kuzey.block.entity.ModBlockEntities;
import com.nexoner.kuzey.block.entity.template.AbstractGeneratorEntity;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.networking.ModPackets;
import com.nexoner.kuzey.networking.packet.EnergySyncPacket;
import com.nexoner.kuzey.screen.CombustibleSolidGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

public class CombustibleSolidGeneratorBlockEntity extends AbstractGeneratorEntity {
    
    public int progress;
    public int maxProgress;

    protected final ContainerData data;
    
    public CombustibleSolidGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COMBUSTIBLE_SOLID_GENERATOR_BLOCK_ENTITY.get(), pPos, pBlockState, new TranslatableComponent("block.kuzey.combustible_solid_generator"), 
                KuzeyCommonConfigs.combustibleSolidGeneratorCapacity.get(), KuzeyCommonConfigs.combustibleSolidGeneratorMaxExtracted.get(), KuzeyCommonConfigs.combustibleSolidGeneratorProduced.get(), 1);

        data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return progress;
                    case 1: return maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0: progress = pValue;break;
                    case 1: maxProgress = pValue;break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        ModPackets.sendToClients(new EnergySyncPacket(energyStorage.getEnergyStored(),worldPosition));
        return new CombustibleSolidGeneratorMenu(pContainerId,pPlayerInventory,this,data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("progress",progress);
        pTag.putInt("maxProgress",maxProgress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        progress = pTag.getInt("progress");
        maxProgress = pTag.getInt("maxProgress");
    }

    @Override
    public void tickCustom(AbstractGeneratorEntity entity) {
        if (!level.isClientSide) {
            if (maxProgress <= 0){
                ItemStack toProcess = new ItemStack(itemHandler.getStackInSlot(0).getItem(), 1);
                int burnTime = ForgeHooks.getBurnTime(toProcess, null);
                if (burnTime > 0 && !toProcess.hasContainerItem()){
                    maxProgress = burnTime / 4;
                    itemHandler.extractItem(0,1,false);
                }
            }
                if (maxProgress > 0){
                    if (progress >= maxProgress){
                        maxProgress = 0;
                        progress = 0;
                    } else if (canInsertEnergy(entity)){
                        energyStorage.addEnergy(produced);
                        progress++;
                    }
                }
            }
        }
    }
