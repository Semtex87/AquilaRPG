package net.vaex.aquilarpg.item;


import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.RPGIngredientDiscovered;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import net.vaex.aquilarpg.util.RPGRarity;

public class RPGIngredientItems {

    public static final Item.Properties drinkIngredient = new Item.Properties()
            .tab(RPGCreativeModeTab.RPG_MISC)
            .stacksTo(64);

    public static final Item.Properties eatIngredient = new Item.Properties()
            .tab(RPGCreativeModeTab.RPG_MISC)
            .stacksTo(64);

    public static final Item.Properties basicStackableItem = new Item.Properties()
            .tab(RPGCreativeModeTab.RPG_MISC)
            .stacksTo(64);

    public static final DeferredRegister<Item> INGREDIENT_ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AquilaRPG.MOD_ID);


    //BASIC ALCHEMIC INGREDIENT
    public static final RegistryObject<Item> ASH_FROM_A_FOREST_FIRE = INGREDIENT_ITEMS.register("ash_from_a_forest_fire",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON), 1F, RPGIngedientTiers.Tier2,"blindness","restore_heal","",""));
    public static final RegistryObject<Item> CAVE_SPIDER_POISON = INGREDIENT_ITEMS.register("cave_spider_poison",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.6F, RPGIngedientTiers.Tier1,"poison","","",""));
    public static final RegistryObject<Item> BEHOLDERS_EYE = INGREDIENT_ITEMS.register("beholders_eye",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_EPIC), 1.2F, RPGIngedientTiers.Tier4,"blindness","harm","poison","infravision"));
    public static final RegistryObject<Item> AQUA_REGIA = INGREDIENT_ITEMS.register("aqua_regia",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_EPIC), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> ASKIR_CAVE_CATFISH = INGREDIENT_ITEMS.register("askir_cave_catfish",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 2.5F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> BEAST_TONGUE = INGREDIENT_ITEMS.register("beast_tongue",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> BEAST_TOOTH = INGREDIENT_ITEMS.register("beast_tooth",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> BLACK_PEARL = INGREDIENT_ITEMS.register("black_pearl",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.2F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> CREEPER_BLOOD = INGREDIENT_ITEMS.register("creeper_blood",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> DRAGON_SPINE = INGREDIENT_ITEMS.register("dragon_spine",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_RARE), 2.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> DISTILLED_WATER = INGREDIENT_ITEMS.register("distilled_water",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> FISH_BONE = INGREDIENT_ITEMS.register("fish_bone",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.4F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> FROST_SALT = INGREDIENT_ITEMS.register("frost_salt",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_RARE), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> HOLY_WATER = INGREDIENT_ITEMS.register("holy_water",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_RARE), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> HUMAN_BLOOD = INGREDIENT_ITEMS.register("human_blood",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> HUMAN_EYE = INGREDIENT_ITEMS.register("human_eye",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.2F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> HYDROCHLORIC_ACID = INGREDIENT_ITEMS.register("hydrochloric_acid",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.5F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> LIQUID_DEATH = INGREDIENT_ITEMS.register("liquid_death",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_EPIC), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> MAPLE_ROOT = INGREDIENT_ITEMS.register("maple_root",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.4F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> MISTLETOE = INGREDIENT_ITEMS.register("mistletoe",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> NITRIC_ACID = INGREDIENT_ITEMS.register("nitric_acid",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> PIXIE_TEARS = INGREDIENT_ITEMS.register("pixie_tears",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> PURE_WATER = INGREDIENT_ITEMS.register("pure_water",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> SALTPETER = INGREDIENT_ITEMS.register("saltpeter",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.5F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> SULFUR_DUST = INGREDIENT_ITEMS.register("sulfur_dust",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.5F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> SULFURIC_ACID = INGREDIENT_ITEMS.register("sulfuric_acid",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> WINESTONE = INGREDIENT_ITEMS.register("winestone",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(50).setNoRepair(), 0.5F, RPGIngedientTiers.Tier1,"","","",""));





    public static void register(IEventBus eventBus) {
        INGREDIENT_ITEMS.register(eventBus);
    }
}
