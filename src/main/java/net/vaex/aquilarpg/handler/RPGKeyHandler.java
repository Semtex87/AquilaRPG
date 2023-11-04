package net.vaex.aquilarpg.handler;


import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.network.ManaC2SPacket;
import net.vaex.aquilarpg.network.ManaSyncS2CPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jline.utils.Log;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Optional;

import static net.vaex.aquilarpg.util.RPGKeyBindings.gatherManaKeyMapping;
import static net.vaex.aquilarpg.util.RPGKeyBindings.swapBackSlot;

public class RPGKeyHandler{

    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player playerEntity = minecraft.player;
        if (gatherManaKeyMapping.consumeClick()) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.sendMessage(new TextComponent("Pressed Mana Key!"),playerEntity.getUUID());
                Log.info("pressed gathering Mana key" + gatherManaKeyMapping);
                playerEntity.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.subMana(50);
                    NetworkHandler.sendPacketToServer((new ManaC2SPacket()));
                });            }
        }
        /*
        if (gatherWaterKeyMapping.consumeClick()) {
            Minecraft.getInstance().player.sendMessage(new TextComponent("Pressed Water Key!"),playerEntity.getUUID()); ;
            NetworkHandler.sendPacketToServer(new ProspectWaterC2SPacket());
            Log.info("pressed gathering Water key" + gatherWaterKeyMapping);
        }*/

        /*
        if (gatherWaterKeyMapping.consumeClick()) {
            Minecraft.getInstance().player.sendMessage(new TextComponent("Pressed Water Key!"),playerEntity.getUUID()); ;
            NetworkHandler.sendPacketToServer(new ProspectWaterC2SPacket());
            Log.info("pressed gathering Water key" + gatherWaterKeyMapping);
        }
    }*/
}
}