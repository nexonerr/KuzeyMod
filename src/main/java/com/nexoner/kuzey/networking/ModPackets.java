package com.nexoner.kuzey.networking;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.networking.packet.EnergySyncPacket;
import com.nexoner.kuzey.networking.packet.FluidSyncPacket;
import com.nexoner.kuzey.networking.packet.ItemStackSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }
    private static final String PROTOCOL_VERSION = "0.2";

    public static void register(){
        SimpleChannel net = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(KuzeyMod.MOD_ID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
    INSTANCE = net;

    net.messageBuilder(FluidSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
            .decoder(FluidSyncPacket::new)
            .encoder(FluidSyncPacket::toBytes)
            .consumer(FluidSyncPacket::handle)
            .add();

        net.messageBuilder(ItemStackSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackSyncPacket::new)
                .encoder(ItemStackSyncPacket::toBytes)
                .consumer(ItemStackSyncPacket::handle)
                .add();

        net.messageBuilder(EnergySyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergySyncPacket::new)
                .encoder(EnergySyncPacket::toBytes)
                .consumer(EnergySyncPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }


}
