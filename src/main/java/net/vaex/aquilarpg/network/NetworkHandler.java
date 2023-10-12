package net.vaex.aquilarpg.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation("aquila", "network")).clientAcceptedVersions("1"::equals).serverAcceptedVersions("1"::equals).networkProtocolVersion(() -> {
        return "1";
    }).simpleChannel();
    public static int nextPacketID = 0;

    public static void register() {
        INSTANCE.messageBuilder(ManaC2SPacket.class, getNextPacketID(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ManaC2SPacket::new)
                .encoder(ManaC2SPacket::toBytes)
                .consumer(ManaC2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(ManaSyncS2CPacket.class, getNextPacketID(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaSyncS2CPacket::new)
                .encoder(ManaSyncS2CPacket::toBytes)
                .consumer(ManaSyncS2CPacket::handle)
                .add();
    }

    public static int getNextPacketID() {
        int id = nextPacketID++;
        return id;
    }

    public static void sendPacketTo(Object packet, ServerPlayer player) {
        if (!(player instanceof FakePlayer)) {
            INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }

    }

    public static void sendPacketToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}

