package com.nexoner.kuzey.networking.packet;

import com.nexoner.kuzey.block.entity.entityType.IEnergyHandlingBlockEntity;
import com.nexoner.kuzey.block.entity.entityType.IFluidHandlingBlockEntity;
import com.nexoner.kuzey.screen.menutype.IEnergyMenu;
import com.nexoner.kuzey.screen.menutype.IFluidMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncPacket {
    private final int energy;
    private final BlockPos pos;

    public EnergySyncPacket(int energy, BlockPos pos){
        this.energy = energy;
        this.pos = pos;
    }

    public EnergySyncPacket(FriendlyByteBuf buf){
       this.energy = buf.readInt();
       this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle (Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //HERE WE ARE ON CLIENT
            if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof IEnergyHandlingBlockEntity blockEntity) {
                blockEntity.setEnergy(energy);

                if(Minecraft.getInstance().player.containerMenu instanceof IEnergyMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    blockEntity.setEnergy(energy);
                }
            }

        });
        return true;
    }
}
