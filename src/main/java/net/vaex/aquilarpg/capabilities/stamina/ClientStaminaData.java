package net.vaex.aquilarpg.capabilities.stamina;

import net.minecraft.world.entity.player.Player;

public class ClientStaminaData {
    private static int playerStamina;


    public static void set(int stamina) {
        ClientStaminaData.playerStamina = stamina;
    }

    public static int getPlayerStamina(Player entity) {
        return playerStamina;
    }

}
