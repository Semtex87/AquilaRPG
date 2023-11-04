package net.vaex.aquilarpg.capabilities.mana;


public class ClientManaData {
    private static int playerMana;
    public static void set(int mana) {
        ClientManaData.playerMana = mana;
    }
    public static void setMaxMana(int maxMana) {
        ClientManaData.playerMana = maxMana;
    }
    public static int getPlayerMana() {
        return playerMana;
    }

}
