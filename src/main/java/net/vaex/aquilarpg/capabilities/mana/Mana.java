package net.vaex.aquilarpg.capabilities.mana;

import net.minecraft.nbt.CompoundTag;

public class Mana {
    private int mana;
    private static final int minMana = 0;
    private static final int maxManaPool = 100;

    private static final int maxMana = 100;


    private static int actualMaxMana = maxMana;


    public static int getPlayerMaxMana() {
        return actualMaxMana;
    }

    public int getMana() {
        return mana;
    }

    public int getMinMana() {
        return minMana;
    }
    public int getDefaultMana() {
        return maxManaPool;
    }
    public int getActualMaxMana() {
        return actualMaxMana;
    }

    public void addMana(int add) {
        this.mana = Math.min(mana + add, actualMaxMana);
    }

    public void setDefaultMaxMana() {
        actualMaxMana = maxMana;
    }
    public void addMaxMana(int add) {
        actualMaxMana = maxMana + add;
    }
    public void subMaxMana(int add) {
        actualMaxMana = maxMana + add;
    }
    public void subMana(int sub) {
        this.mana = Math.max(mana - sub, minMana);
    }

    public void copyFrom(Mana source) {
        this.mana = source.mana;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("mana", mana);
    }

    public void loadNBTData(CompoundTag nbt) {
        mana = nbt.getInt("mana");
    }
}

