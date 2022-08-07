package com.nexoner.kuzey.networking.packet;

import com.nexoner.kuzey.block.custom.EmreEssenceExtractorBlock;
import com.nexoner.kuzey.block.entity.EntityType.IFluidHandlingBlockEntity;
import com.nexoner.kuzey.block.entity.custom.EmreEssenceExtractorBlockEntity;
import com.nexoner.kuzey.screen.menutype.IFluidMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidSyncPacket {
    private final FluidStack fluidStack;
    private final BlockPos pos;

    public FluidSyncPacket(FluidStack stack, BlockPos pos){
        this.fluidStack = stack;
        this.pos = pos;
    }

    public FluidSyncPacket(FriendlyByteBuf buf){
       this.fluidStack = buf.readFluidStack();
       this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFluidStack(fluidStack);
        buf.writeBlockPos(pos);
    }

    public boolean handle (Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //HERE WE ARE ON CLIENT
            if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof IFluidHandlingBlockEntity blockEntity) {
                blockEntity.setFluid(this.fluidStack);
            }
            if (Minecraft.getInstance().player.containerMenu instanceof IFluidMenu menu && menu.getBlockEntity().getBlockPos().equals(pos)) {
                menu.setFluid(fluidStack);
            }

        });
        return true;
    }
}
