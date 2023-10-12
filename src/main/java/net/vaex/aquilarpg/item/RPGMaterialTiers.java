package net.vaex.aquilarpg.item;


import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.vaex.aquilarpg.util.RPGTierInterface;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RPGMaterialTiers implements Tier, RPGTierInterface {
    public String name;
    public final int level;
    public int baseDurability;
    public  float speed;
    public  float materialHardness;
    public  int forgingHeatpoint;
    public  int meltingPoint;
    public  int enchantmentValue;
    public  float weight;
    public final LazyLoadedValue<Ingredient> repairIngredient;


    //TIERS BASIC
    public static RPGMaterialTiers WOOD;
    public static RPGMaterialTiers STONE;
    public static RPGMaterialTiers IRON;
    public static RPGMaterialTiers DIAMOND;
    public static RPGMaterialTiers GOLD;
    public static RPGMaterialTiers NETHERITE;
    //TIERS EQ BASIC
    //EQ WOOD
    public static RPGMaterialTiers OLD;
    public static RPGMaterialTiers CLAY;
    public static RPGMaterialTiers RUSTY;
    //EQ STONE
    //EQ IRON
    //EQ GOLD
    public static RPGMaterialTiers COBALT;
    //EQ DIAMOND
    public static RPGMaterialTiers STEEL;
    public static RPGMaterialTiers RUBY_TIER;                                                                          //Todo this is a testTier
    //EQ NETHERITE
    public static RPGMaterialTiers GLASS;
    public static RPGMaterialTiers MITHRIL;

    //EQ NETHERITE+
    public static RPGMaterialTiers ELEMENTIUM;


    public static RPGMaterialTiers LEATHER; //ARMORMATERIAL
    public static RPGMaterialTiers BONE;

    public static RPGMaterialTiers CLUTTER;
    //EQ STONE

    public static RPGMaterialTiers FLINT;
    public static RPGMaterialTiers TIN; //JEWELERY
    public static RPGMaterialTiers LEAD; //JEWELERY

    public static RPGMaterialTiers HARDLEATHER; //ARMORMATERIAL
    //EQ IRON
    public static RPGMaterialTiers COPPER;
    public static RPGMaterialTiers BRONZE;
    public static RPGMaterialTiers CHAIN;
    public static RPGMaterialTiers DARKIRON;
    //EQ GOLD
    public static RPGMaterialTiers SILVER;
    public static RPGMaterialTiers ASTRALSILVER;
    public static RPGMaterialTiers PLATINUM;
    public static RPGMaterialTiers HELSTONE;
    //EQ DIAMOND
    public static RPGMaterialTiers ELVEN;
    public static RPGMaterialTiers DWARVEN;
    public static RPGMaterialTiers ORC;
    public static RPGMaterialTiers CRIMSON;
    public static RPGMaterialTiers ADAMANTIUM;
    public static RPGMaterialTiers HEXXIN;
    //EQ NETHERITE
    public static RPGMaterialTiers EBONY;
    public static RPGMaterialTiers DRAGONBONE;
    public static RPGMaterialTiers STAHLRIM;
    public static RPGMaterialTiers VOID; // todo check all materials armor, weapon jewellery
    //EQ NETHERITE+
    //public static RPGMaterialTiers UNIQUE_TIER1;
    //public static RPGMaterialTiers UNIQUE_TIER2;
    //public static RPGMaterialTiers UNIQUE_TIER3;







    public RPGMaterialTiers(String name, Tier tier, float hardness, float weight, Supplier<Ingredient> repairIngredient, int melting, int heating) {
        this.name = name;                                                                                               //TODO NAME = OK
        this.level = tier.getLevel();                                                                                   //TODO MININGTIER
        this.baseDurability = tier.getUses();                                                                           //TODO DURABILITY
        this.speed = tier.getSpeed();                                                                                   //TODO SPEED
        this.materialHardness = hardness;                                                                               //TODO MOODIFICATOR
        this.enchantmentValue = tier.getEnchantmentValue();                                                             //TODO ENCHANTMENT
        this.weight = weight;                                                                                           //TODO WEIGHT
        this.meltingPoint = melting;
        this.forgingHeatpoint = heating;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }
    public RPGMaterialTiers(String name, int level, int durability, float speed, float hardness, int forgingHeatpoint, int meltingPoint, int enchantability, float weight, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.level = level;
        this.baseDurability = durability;
        this.speed = speed;
        this.materialHardness = hardness;
        this.forgingHeatpoint = forgingHeatpoint;
        this.meltingPoint = meltingPoint;
        this.enchantmentValue = enchantability;
        this.weight = weight;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    static {
        //VANILLA
        WOOD = new RPGMaterialTiers("Wood", Tiers.WOOD, 0.5f, 1.0f, () -> Ingredient.of(Items.STICK), 0, 0);
        STONE = new RPGMaterialTiers("Stone", Tiers.STONE, 0.4f, 2.6f, () -> Ingredient.of(Items.STONE),0 ,0);
        IRON = new RPGMaterialTiers("Iron", Tiers.IRON, 1.0f, 1.7f, () -> Ingredient.of(Items.IRON_INGOT),1540, 950);;
        DIAMOND = new RPGMaterialTiers("Diamond", Tiers.DIAMOND, 1.5f, 1.6f, () -> Ingredient.of(Items.DIAMOND), 0, 0);
        GOLD = new RPGMaterialTiers("Gold", Tiers.GOLD, 0.3f, 1.4f, () -> Ingredient.of(Items.GOLD_INGOT),1060,700);
        NETHERITE = new RPGMaterialTiers("Netherite", Tiers.NETHERITE, 1.9f, 3.0f, () -> Ingredient.of(Items.NETHERITE_INGOT),1800,1550);
        //MOD
        //EQ WOOD
        CLAY = new RPGMaterialTiers("Clay", 0, 40, 0.0F, 0.2F, 0, 0, 10, 1.8F, Ingredient::of);
        OLD = new RPGMaterialTiers("Old", 1, 80, 0.0F, 0.6F, 0, 0, 10, 1.5F, Ingredient::of);
        CLUTTER = new RPGMaterialTiers("Clutter", 0, 20, 0.0F, 0.5F, 0, 0, 5, 1.5f, Ingredient::of);
        //EQ STONE
        RUSTY = new RPGMaterialTiers("Rusty",  2, 70, 0.0F, 0.4F, 0, 0, 10, 1.0f, Ingredient::of);
        BONE = new RPGMaterialTiers("Bone",  2, 50, 0.0F, 0.4F, 0, 0, 15, 0.7f, () -> Ingredient.of(Items.BONE));
        FLINT = new RPGMaterialTiers("Flint",  1, 60, 0.0F, 0.2F, 0, 0, 15, 1.1f, () -> Ingredient.of(Items.FLINT));
        TIN = new RPGMaterialTiers("Tin",  1, 45, 0.0F, 0.1F, 180, 232, 5, 1.8f, () -> Ingredient.of(Items.STICK));
        LEAD = new RPGMaterialTiers("Lead",  1, 55, 0.0F, 0.1F, 0, 0, 5, 2.0f, () -> Ingredient.of(Items.STICK));
        LEATHER = new RPGMaterialTiers("Leather",  0, 180, 0.0F, 0.6F, 0, 0, 25, 0.9f, Ingredient::of);
        HARDLEATHER = new RPGMaterialTiers("Hardleather",  220, 0, 0.0F, 0.8F, 0, 0, 30, 1.0f, Ingredient::of);
        //EQ GOLD
        SILVER = new RPGMaterialTiers("Silver",  2, 230, 0.0F, 0.3F, 0, 0, 35, 1.5f, () -> Ingredient.of(Items.STICK));
        COPPER = new RPGMaterialTiers("Copper",  2, 250, 0.0F, 0.9F, 0, 0, 35, 1.6f, () -> Ingredient.of(Items.STICK));
        BRONZE = new RPGMaterialTiers("Bronze",  2, 290, 0.0F, 1.5F, 600, 850, 45, 1.8f, () -> Ingredient.of(Items.STICK));
        //EQ IRON
        CHAIN = new RPGMaterialTiers("Chain",  2, 375, 0.0F, 0.8F, 0, 0, 70, 1.5f, () -> Ingredient.of(Items.STICK));
        PLATINUM = new RPGMaterialTiers("Platinum",  2, 220, 0.0F, 0.3F, 0, 0, 25, 1.3f, () -> Ingredient.of(Items.STICK));
        HELSTONE = new RPGMaterialTiers("Helstone",  3, 250, 0.0F, 0.5F, 0, 0, 20, 1.7f, () -> Ingredient.of(Items.STICK));
        COBALT = new RPGMaterialTiers("Cobalt", 3, 300, 0.0F, 0.6F, 1100, 1453, 10, 1.2f, Ingredient::of);
        //EQ DIAMOND
        STEEL = new RPGMaterialTiers("Steel", 3, 550, 0.0F, 1.8F, 1200, 1550, 50, 2.2f, Ingredient::of);
        ELVEN = new RPGMaterialTiers("Elven",  3, 500, 0.0F, 2.0F, 0, 0, 70, 1.1f, () -> Ingredient.of(Items.STICK));
        DWARVEN = new RPGMaterialTiers("Dwarven",  3, 500, 0.0F, 2.1F, 0, 0, 70, 2.0f, () -> Ingredient.of(Items.STICK));
        ORC = new RPGMaterialTiers("Orcish",  3, 500, 0.0F, 1.7F, 0, 0, 70, 2.5f, () -> Ingredient.of(Items.IRON_INGOT));
        ASTRALSILVER = new RPGMaterialTiers("Astralsilver",  2, 400, 0.0F, 1.1F, 0, 0, 80, 1.7f, () -> Ingredient.of(Items.STICK));
        DARKIRON = new RPGMaterialTiers("Darkiron",  4, 550, 0.0F, 1.3F, 0, 0, 60, 2.2f, () -> Ingredient.of(Items.STICK));
        HEXXIN = new RPGMaterialTiers("Hexxin", 3, 350, 0.0F, 0.9F, 0, 0, 50, 1.4f, () -> Ingredient.of(Items.STICK));
        RUBY_TIER = new RPGMaterialTiers("Ruby", 3, 50, 0.0F, 0.6F, 0, 0, 10, 1.2f,Ingredient::of);
        GLASS = new RPGMaterialTiers("Glass", 4, 600, 0.0F, 2.0F, 1300, 950, 90, 1.0f, Ingredient::of);
        //EQ NETHERITE
        MITHRIL = new RPGMaterialTiers("Mithril", 4, 1250, 0.0F, 2.2F, 900, 1200, 450, 1.3f, Ingredient::of);
        DRAGONBONE = new RPGMaterialTiers("Dragonbone",  4, 700, 0.0F, 1.9F, 0, 0, 350, 1.6f, () -> Ingredient.of(Items.STICK));
        ADAMANTIUM = new RPGMaterialTiers("Adamantium",  4, 1500, 0.0F, 2.1F, 1400, 1600, 400, 1.9f,() -> Ingredient.of(Items.STICK));
        EBONY = new RPGMaterialTiers("Ebony",  4, 1250, 0.0F, 2.7F, 0, 0, 350, 0.7f, () -> Ingredient.of(Items.STICK));
        CRIMSON = new RPGMaterialTiers("Crimson",  4, 1500, 0.0F, 2.3F, 0, 0, 300, 1.9f, () -> Ingredient.of(Items.STICK));
        STAHLRIM = new RPGMaterialTiers("Stahlrim",  4, 1650, 0.0F, 2.8F, 1300, 1500, 450, 1.4f, () -> Ingredient.of(Items.STICK));
        //EQ NETHERITE+
        ELEMENTIUM = new RPGMaterialTiers("Elementium", 5, 2000, 0.0F, 2.5F, 1500, 2000, 600, 3.2f, Ingredient::of);
        VOID = new RPGMaterialTiers("Void",  6, 2200, 0.0F, 4.0F, 1800, 2500, 750, 4.0f, () -> Ingredient.of(Items.STICK));
        //UNIQUE ITEMS
        //UNIQUE_TIER1 = new AquilaItemTier("unique", 4, 750, 3.0F, 2.5F, 175, 1.0F, Ingredient::of);
        //UNIQUE_TIER2 = new AquilaItemTier("legendary", 5, 850, 3.0F, 2.8F, 200, 1.0F, () -> Ingredient.of(ItemInit.MAGIC_DUST.get()));
        //UNIQUE_TIER3 = new AquilaItemTier("artifact", 6, 999, 3.0F, 3.0F, 250, 1.0F, () -> Ingredient.of(ItemInit.BAG_RUNEDUST.get()));
    }


    public float getWeight() {
        return this.weight;
    }
    public int getForgingHeatpoint() {
        return this.forgingHeatpoint;
    }

    public int getMeltingpoint() {
        return this.meltingPoint;
    }

    public int getDurability() {
        return this.baseDurability;
    }

    public float getMaterialHardness() {
        return this.materialHardness;
    }

    public String getMaterialName() {
        return this.name;
    }

    public int getUses() {
        return baseDurability;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.materialHardness;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}


