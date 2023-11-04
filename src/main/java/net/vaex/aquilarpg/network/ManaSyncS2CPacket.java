package net.vaex.aquilarpg.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.vaex.aquilarpg.capabilities.mana.ClientManaData;

import java.util.function.Supplier;

public class ManaSyncS2CPacket {
    private final int mana;

    public ManaSyncS2CPacket(int mana) {
        this.mana = mana;
    }

    public ManaSyncS2CPacket(FriendlyByteBuf buf) {
        this.mana = buf.readInt();
    }



    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(mana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientManaData.set(mana);
        });
        return true;
    }


}

