package net.vaex.aquilarpg.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;

import java.util.function.Supplier;

public class ManaC2SPacket {
    private static final String MESSAGE_CHARGE_MANA = "message.aquilarpg.charge_mana";
    private static final String MESSAGE_NO_MANA = "message.aquilarpg.no_mana";

    public ManaC2SPacket() {

    }

    public ManaC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
                //Minecraft.getInstance().player.sendMessage(new TranslatableComponent(MESSAGE_NO_MANA).withStyle(ChatFormatting.RED),player.getUUID()); ;
                player.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                   // Minecraft.getInstance().player.sendMessage(new TranslatableComponent("Current Mana"+ mana.getMana())
                            //.withStyle(ChatFormatting.AQUA),player.getUUID()); ;
                    NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), player);
                    //NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMaxMana()), player);
                });
        });
        return true;
    }
}

