package net.vaex.aquilarpg.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;

public class RPGKeyBindings {
    public static final String KEY_CATEGORY_AQUILA = "key.category.aquilarpg.aquilarpg";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String KEY_GATHER_MANA = "key.aquilarpg.gather.mana";
    public static final String KEY_DRINK_WATER = "key.aquilarpg.drink.water";
    public static final String SWAP_BACK_SLOT_ITEM_MAIN_OFF_HAND = "key.aquilarpg_swap_backslot";
    public static KeyMapping gatherManaKeyMapping;
    public static KeyMapping gatherWaterKeyMapping;
    public static KeyMapping swapBackSlot;

    public static void register() {
        gatherManaKeyMapping = new KeyMapping(KEY_GATHER_MANA, KeyConflictContext.IN_GAME, InputConstants.getKey("key.keyboard.period"), KEY_CATEGORY_AQUILA);
        ClientRegistry.registerKeyBinding(gatherManaKeyMapping);
        //gatherWaterKeyMapping = new KeyMapping(KEY_DRINK_WATER, KeyConflictContext.IN_GAME, InputConstants.getKey("key.keyboard.z"), KEY_CATEGORY_AQUILA);
        //ClientRegistry.registerKeyBinding(gatherWaterKeyMapping);
        //swapBackSlot = new KeyMapping(SWAP_BACK_SLOT_ITEM_MAIN_OFF_HAND, KeyConflictContext.IN_GAME, InputConstants.getKey("key.keyboard.x"), KEY_CATEGORY_AQUILA);
        //ClientRegistry.registerKeyBinding(gatherWaterKeyMapping);
    }
}



