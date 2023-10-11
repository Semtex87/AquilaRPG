package net.vaex.aquilarpg.item;


import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.vaex.aquilarpg.AquilaRPG;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum RPGArmorMaterial implements ArmorMaterial {

    DWARVEN_FIELDPLATE("dwarven_fieldplate", "Heavy Armor", SoundEvents.ARMOR_EQUIP_NETHERITE, 6, 0,
           0, 1,20, new int[]{4, 6, 8, 4},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.METALSCRAP.get())),
    MARAUDER("marauder", "Heavy Armor", SoundEvents.ARMOR_EQUIP_IRON, 6, 0,
            0, 1,20, new int[]{3, 5, 6, 4},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.IRON_PLATE.get())),
    TOWNGUARD_CHAINMAIL_BLACK_WHITE_01("townguard_chainmail_black_white_01", "medium armor", SoundEvents.ARMOR_EQUIP_CHAIN, 6, 0,
            0, 1,20, new int[]{2, 4, 5, 2},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.CHAINPARTS_IRON.get())),
    TOWNGUARD_CHAINMAIL_RED_WHITE_01("townguard_chainmail_red_white_01", "medium armor", SoundEvents.ARMOR_EQUIP_CHAIN, 6, 0,
            0, 1,20, new int[]{2, 4, 5, 2},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.CHAINPARTS_IRON.get())),
    TOWNGUARD_CHAINMAIL_YELLOW_WHITE_01("townguard_chainmail_yellow_white_01", "medium armor", SoundEvents.ARMOR_EQUIP_CHAIN, 6, 0,
            0, 1,20, new int[]{2, 4, 5, 2},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.CHAINPARTS_IRON.get())),
    TOWNGUARD_CHAINMAIL_WHITE("townguard_chainmail_white", "medium armor", SoundEvents.ARMOR_EQUIP_CHAIN, 6, 0,
            0, 1,20, new int[]{2, 4, 5, 2},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.CHAINPARTS_IRON.get())),
    TOWNGUARD_CHAINMAIL_BLUE_WHITE_01("townguard_chainmail_blue_white_01", "medium armor", SoundEvents.ARMOR_EQUIP_CHAIN, 6, 0,
            0, 1,20, new int[]{2, 4, 5, 2},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.CHAINPARTS_IRON.get())),

    BROTHERHOOD("brotherhood", "light armor", SoundEvents.ARMOR_EQUIP_LEATHER, 6, 0,
            0, 1,20, new int[]{2, 3, 4, 2},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.LEATHERSTRIPES.get())),

    TEMPLAR_CHAINMAIL("templar_chainmail", "medium armor", SoundEvents.ARMOR_EQUIP_CHAIN, 6, 0,
            0, 1,20, new int[]{2, 4, 5, 2},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.CHAINPARTS_IRON.get())),

    KINGSTONE_CHAINMAIL("kingstone", "medium armor", SoundEvents.ARMOR_EQUIP_CHAIN, 6, 0,
            0, 1,20, new int[]{2, 4, 5, 2},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.CHAINPARTS_IRON.get())),

    BLACK_LEGION("blacklegion", "Heavy armor", SoundEvents.ARMOR_EQUIP_NETHERITE, 6, 0,
            0, 1,20, new int[]{3, 5, 8, 3},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.STEEL_INGOT.get())),









    //TODO IMPLEMENTATION
    STAHLRIM_HEAVY("stahlrim_heavy", "medium armor", SoundEvents.ARMOR_EQUIP_NETHERITE, 6, 0,
            0, 1,20, new int[]{3, 5, 8, 3},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.METALSCRAP.get())),

    STAHLRIM_LIGHT("stahlrim_light", "medium armor", SoundEvents.ARMOR_EQUIP_LEATHER, 6, 0,
            0, 1,20, new int[]{3, 5, 8, 3},0.5f, new int[]{2, 8, 10, 4},() -> Ingredient.of(RPGItems.METALSCRAP.get())),

    DWARVENCROWN("dwarvencrown", "light armor", SoundEvents.ARMOR_EQUIP_GOLD, 6, 0,
            0, 1,20, new int[]{3, 0, 0, 0},0.5f, new int[]{2, 0, 0, 0},() -> Ingredient.of(RPGItems.METALSCRAP.get())),

    ;
    //TODO FIXING Class
    private final String name;
    private final String armorclass;
    private int lightArmorVar = 0;
    private int mediumArmorVar = 0;
    private int heavyArmorVar = 0;

    private int basicArmorValue = 0;
    private final SoundEvent equipSound;
    private final int durability;
    private final int enchantability;
    private final int[] damageReductionAmountArray;
    private final float toughness;
    private final int[] weightArray;

    private final LazyLoadedValue<Ingredient> repairIngredient;

     RPGArmorMaterial(String name, String armorclass, SoundEvent equipSound, int durability, int lightArmorVar, int mediumArmorVar, int heavyArmorVar, int enchantability, int[] damageReductionAmountArray, float toughness, int[] weightArray, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.armorclass = armorclass;
        this.equipSound = equipSound;
        this.durability = durability;
        this.lightArmorVar = lightArmorVar;
        this.mediumArmorVar = mediumArmorVar;
        this.heavyArmorVar = heavyArmorVar;
        this.enchantability = enchantability;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.toughness = toughness;
        this.weightArray = weightArray;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    public int getLightArmorVar() {
        return this.lightArmorVar;
    }
    public int getMediumArmorVar() {
        return this.lightArmorVar;
    }
    public int getHeavyArmorVar() {
        return this.lightArmorVar;
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
    public int getEnchantmentValue() {
        return this.enchantability;
    }
    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipSound;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();

    }
    @Override
    public String getName() {
        return AquilaRPG.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }
    public String getArmorclass() {
        return armorclass;
    }

    public int getWeight(EquipmentSlot equipmentSlot) {
        return this.weightArray[equipmentSlot.getIndex()];
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}

