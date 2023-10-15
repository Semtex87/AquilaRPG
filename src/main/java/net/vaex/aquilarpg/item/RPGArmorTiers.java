package net.vaex.aquilarpg.item;


import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.vaex.aquilarpg.util.ElementaryResist;
import net.vaex.aquilarpg.util.RPGTierInterface;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum RPGArmorTiers implements ArmorMaterial, RPGTierInterface {

    CLOTH("Cloth","Light",0, 80,0, new int[] {1, 1, 1, 1}, 60,0, SoundEvents.WOOL_PLACE, new float[] {1.0F, 1.0F, 1.0F, 1.0F}, () -> Ingredient.of(RPGItems.LINEN.get()), new int[] {5, 5, 0, 0}),
    THATCH("Thatch","Light",0, 50,0, new int[] {1, 1, 1, 1}, 20,0, SoundEvents.GRASS_HIT, new float[] {0.2F, 0.4F, 0.3F, 0.2F}, () -> Ingredient.of(Items.WHEAT), new int[] {5, 5, 1, 0}),

    //QUILT("quilt", 800, new int[] {2, 2, 2, 2}, 25, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, () -> Ingredient.of(Items.LEATHER)),


    //BRONZE("bronze", 800, new int[] {2, 2, 2, 2}, 25, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, () -> Ingredient.of(Items.IRON_INGOT)),


    //RUSTY("rusty",  800, new int[] {2, 2, 2, 2}, 25,SoundEvents.ARMOR_EQUIP_IRON, 0.0f, () -> Ingredient.of(Items.IRON_INGOT)),



    //STEEL("rusty",  800, new int[] {2, 2, 2, 2}, 25,SoundEvents.ARMOR_EQUIP_IRON, 0.0f, () -> Ingredient.of(RPGItems.STEEL_INGOT.get()));
    STEEL_PLATE("Steel Plate","Heavy",4, 900,1, new int[] {3, 8, 3, 3}, 300,1000, SoundEvents.ARMOR_EQUIP_IRON, new float[] {1.2F, 4.0F, 3.0F, 1.4F}, () -> Ingredient.of(RPGItems.STEEL_INGOT.get()), new int[] {20, 10, 30, 10}),

    ;
    private final String name;
    private final SoundEvent equipSound;
    private final String armorclass;
    private final int durability;
    private final int enchantability;
    private final int[] damageReductionAmountArray;
    public final LazyLoadedValue<Ingredient> repairIngredient;

    public final int level;
    public int baseDurability;
    public  float speed;
    public  float materialHardness;

    public  int forgingHeatpoint;
    public  int meltingPoint;
    public  int enchantmentValue;
    public  int[] elementaryResist; // [AERO,AQUA,TERRA,PYRO]
    private float[] weightArray;

    RPGArmorTiers(String name, String armorClass, int level, int durability, float hardness, int[] damageReductionAmountArray, int enchantability, int meltingPoint, SoundEvent equipSound, float[] weightArray, Supplier<Ingredient> repairIngredient, int[] elementaryResist) {
        this.name = name;
        this.armorclass = armorClass;
        this.level = getLevel();
        this.durability = durability;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
        this.weightArray = weightArray;
        this.meltingPoint = meltingPoint;
        this.baseDurability = durability;
        this.materialHardness = hardness;
        this.enchantmentValue = enchantability;
        this.elementaryResist = elementaryResist;
    }






    public String getArmorclass() {
        return armorclass;
    }


    @Override
    public int getDurabilityForSlot(@NotNull EquipmentSlot equipmentSlotType) {
        return this.durability;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot equipmentSlotType) {
        return this.damageReductionAmountArray[equipmentSlotType.getIndex()];
    }

    @Override
    public int getUses() {
        return 0;
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public float getAttackDamageBonus() {
        return 0;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipSound;
    }

    public float getWeightForSlot(EquipmentSlot equipmentSlot) {
        return this.weightArray[equipmentSlot.getIndex()];
    }
    public int getElementaryResist(ElementaryResist resist) {
        return elementaryResist[resist.getIndex()];
    }
    public String getElementaryResistName(ElementaryResist name) {
        return name.getName();
    }
    @Override
    public float getWeight() {
        return 0;
    }

    @Override
    public int getForgingHeatpoint() {
        return 0;
    }

    @Override
    public int getMeltingpoint() {
        return 0;
    }

    @Override
    public int getDurability() {
        return 0;
    }

    @Override
    public float getMaterialHardness() {
        return 0;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getMaterialName() {
        return null;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.materialHardness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}

