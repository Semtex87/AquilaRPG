package net.vaex.aquilarpg.util;



import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;


public interface RPGTierInterface extends Tier {

    float getWeight();

    int getForgingHeatpoint();

    int getMeltingpoint();


    int getDurability();

    float getMaterialHardness();

    Ingredient getRepairIngredient();

    String getMaterialName();
}

    //SoundEvent getSound();


