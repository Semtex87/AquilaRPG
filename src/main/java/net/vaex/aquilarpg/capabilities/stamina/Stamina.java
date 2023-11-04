package net.vaex.aquilarpg.capabilities.stamina;

import net.minecraft.nbt.CompoundTag;

public class Stamina {
    private int stamina;
    private static final int minStamina = 0;
    private static final int maxStaminaPool = 100;

    private static final int maxStamina = 100;


    private static int actualMaxStamina = maxStamina;


    public static int getPlayerMaxStamina() {
        return actualMaxStamina;
    }

    public int getStamina() {
        return stamina;
    }

    public int getMinMana() {
        return minStamina;
    }
    public int getDefaultStamina() {
        return maxStaminaPool;
    }
    public int getActualMaxStamina() {
        return actualMaxStamina;
    }

    public void addStamina(int add) {
        this.stamina = Math.min(stamina + add, actualMaxStamina);
    }

    public void setDefaultMaxStamina() {
        actualMaxStamina = maxStamina;
    }
    public void addMaxStamina(int add) {
        actualMaxStamina = maxStamina + add;
    }
    public void subMaxStamina(int add) {
        actualMaxStamina = maxStamina + add;
    }
    public void subStamina(int sub) {
        this.stamina = Math.max(stamina - sub, minStamina);
    }

    public void copyFrom(Stamina source) {
        this.stamina = source.stamina;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("stamina", stamina);
    }

    public void loadNBTData(CompoundTag nbt) {
        stamina = nbt.getInt("stamina");
    }
}

