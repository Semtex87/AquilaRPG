package net.vaex.aquilarpg.handler;


import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.vaex.aquilarpg.item.RPGItems;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jline.utils.Log;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.Optional;

import static net.vaex.aquilarpg.util.RPGKeyBindings.swapBackSlot;

public class RPGKeyHandler implements ICurioItem {
    /*
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player playerEntity = minecraft.player;
        if (swapBackSlot.consumeClick()) {
            Optional<SlotResult> stack = CuriosApi.getCuriosHelper().findCurio(playerEntity, String.valueOf(SlotTypePreset.BACK),0);
            ItemStack itemstack = playerEntity.getItemBySlot(EquipmentSlot.OFFHAND);
            if (itemstack.isEmpty()){
                playerEntity.setItemSlot(SlotTypePreset.BACK, playerEntity.getItemBySlot(EquipmentSlot.OFFHAND));
                playerEntity.setItemSlot(EquipmentSlot.OFFHAND, itemstack);
            }

            Minecraft.getInstance().player.sendMessage(new TextComponent("Pressed Swap Key!"),playerEntity.getUUID()); ;
            Log.info("pressed gathering Mana key" + swapBackSlot);

        }*/
        /*
        if (gatherWaterKeyMapping.consumeClick()) {
            Minecraft.getInstance().player.sendMessage(new TextComponent("Pressed Water Key!"),playerEntity.getUUID()); ;
            NetworkHandler.sendPacketToServer(new ProspectWaterC2SPacket());
            Log.info("pressed gathering Water key" + gatherWaterKeyMapping);
        }
    }*/
}
