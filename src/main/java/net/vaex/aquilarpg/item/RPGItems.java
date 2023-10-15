package net.vaex.aquilarpg.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.*;
import net.vaex.aquilarpg.item.custom.armor.cape.BlueCapeWithStarsItem;
import net.vaex.aquilarpg.item.custom.armor.head.*;
import net.vaex.aquilarpg.item.custom.armor.jewelry.UniqueNeckPendantOfBloodSacrifice;
import net.vaex.aquilarpg.item.custom.weapon.*;
import net.vaex.aquilarpg.item.custom.weapon.gun.BlowGun;
import net.vaex.aquilarpg.item.custom.weapon.gun.Blunderbuss;
import net.vaex.aquilarpg.item.custom.weapon.gun.FlintlockPistol;
import net.vaex.aquilarpg.item.custom.weapon.gun.MusketGun;
import net.vaex.aquilarpg.item.custom.weapon.magic.FireStaff;
import net.vaex.aquilarpg.item.custom.weapon.magic.ScepterOfLife;
import net.vaex.aquilarpg.item.custom.weapon.magic.TeleportStaff;
import net.vaex.aquilarpg.item.custom.weapon.offhand.BookOfDoom;
import net.vaex.aquilarpg.item.custom.weapon.offhand.TomeOfDefense;
import net.vaex.aquilarpg.item.custom.weapon.offhand.TomeOfManaflow;
import net.vaex.aquilarpg.item.custom.weapon.projectile.Bullet;
import net.vaex.aquilarpg.item.custom.weapon.projectile.FireArrowItem;
import net.vaex.aquilarpg.item.custom.weapon.projectile.PoisonDart;
import net.vaex.aquilarpg.item.custom.weapon.unique.*;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import net.vaex.aquilarpg.util.RPGRarity;
import top.theillusivec4.curios.api.SlotTypePreset;

import static net.minecraft.world.item.ArmorMaterials.IRON;

public class RPGItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AquilaRPG.MOD_ID);
    public static final Item.Properties basicStackableItem = new Item.Properties()
            .tab(RPGCreativeModeTab.RPG_MISC)
            .stacksTo(64);

    public static final Item.Properties smallstackableItem = new Item.Properties()
            .tab(RPGCreativeModeTab.RPG_MISC)
            .stacksTo(16);

    public static final Item.Properties basicFoodProps = new Item.Properties()
            .tab(RPGCreativeModeTab.RPG_MISC)
            .stacksTo(16);

    public static final Item.Properties refinedFoodProps = new Item.Properties()
            .tab(RPGCreativeModeTab.RPG_MISC)
            .stacksTo(1);

    public static final Item.Properties jewelleryItems = new Item.Properties()
            .tab(RPGCreativeModeTab.RPG_ARMOR)
            .stacksTo(1);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //ICON
    public static final RegistryObject<Item> MANA = ITEMS.register("mana",
            () -> new Item(new Item.Properties().tab(null)));
    public static final RegistryObject<Item> NATURE_SPELL = ITEMS.register("nature_spell_icon",
            () -> new Item(new Item.Properties().tab(null)));
    public static final RegistryObject<Item> FIRE_SPELL = ITEMS.register("pyro_spell_icon",
            () -> new Item(new Item.Properties().tab(null)));
    public static final RegistryObject<Item> LIGHTNING_SPELL = ITEMS.register("lightning_spell_icon",
            () -> new Item(new Item.Properties().tab(null)));
    public static final RegistryObject<Item> DARK_SPELL = ITEMS.register("dark_spell_icon",
            () -> new Item(new Item.Properties().tab(null)));
    //TOOLS
    public static final RegistryObject<Item> CLAY_CRUCIBLE = ITEMS.register("clay_crucible",
            () -> new Item( new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TONGS = ITEMS.register("tongs",
            () -> new RPGToolItem(RPGMaterialTiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> CRUCIBLE_TONGS = ITEMS.register("crucible_tongs",
            () -> new RPGToolItem(RPGMaterialTiers.STEEL, new Item.Properties()));
    public static final RegistryObject<Item> BOWSAW = ITEMS.register("bowsaw",
            () -> new RPGToolItem(RPGMaterialTiers.WOOD, new Item.Properties()));
    public static final RegistryObject<Item> CHISEL = ITEMS.register("chisel",
            () -> new RPGToolItem(RPGMaterialTiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> FILE = ITEMS.register("file",
            () -> new RPGToolItem(RPGMaterialTiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> HANDSAW = ITEMS.register("handsaw",
            () -> new RPGToolItem(RPGMaterialTiers.WOOD, new Item.Properties()));
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.IRON, new Item.Properties()).setThrowable(true));
    public static final RegistryObject<Item> LOCKPICK = ITEMS.register("lockpick",
            () -> new RPGToolItem(RPGMaterialTiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle",
            () -> new RPGToolItem(RPGMaterialTiers.STONE, new Item.Properties()));
    public static final RegistryObject<Item> SPATULA = ITEMS.register("spatula",
            () -> new RPGToolItem(RPGMaterialTiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> PEELING_IRON = ITEMS.register("peeling_iron",
            () -> new RPGPeelingIron(RPGMaterialTiers.IRON, new Item.Properties().durability(RPGMaterialTiers.IRON.getUses())));
    public static final RegistryObject<Item> INGOT_MOLD = ITEMS.register("ingot_mold",
            () -> new RPGToolItem(RPGMaterialTiers.CLAY, new Item.Properties()));
    public static final RegistryObject<Item> WHETSTONE = ITEMS.register("whetstone",
            () -> new RPGWetStone(RPGMaterialTiers.STONE, new Item.Properties().durability(10)));
    public static final RegistryObject<Item> PAN = ITEMS.register("pan",
            () -> new RPGToolItem(RPGMaterialTiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> BLOCK_SCANNER_ITEM = ITEMS.register("magnifying_glass",
            () -> new RPGBlockScannerItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS)
                    .durability(32)));
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench",
            () -> new RPGDisassemblerItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS)
                    .durability(64)));



    public static final RegistryObject<Item> FORGE_HAMMER_IRON = ITEMS.register("forge_hammer_iron",
            () -> new RPGRepairItem(RPGMaterialTiers.IRON, new Item.Properties()));
    public static final RegistryObject<Item> FORGE_HAMMER_STEEL = ITEMS.register("forge_hammer_steel",
            () -> new RPGRepairItem(RPGMaterialTiers.STEEL, new Item.Properties()));
    public static final RegistryObject<Item> FORGE_HAMMER_NETHERITE = ITEMS.register("forge_hammer_netherite",
            () -> new RPGRepairItem(RPGMaterialTiers.NETHERITE, new Item.Properties().durability(RPGMaterialTiers.NETHERITE.getUses() / 2)));
    public static final RegistryObject<Item> FORGE_HAMMER_MITHRIL = ITEMS.register("forge_hammer_mithril",
            () -> new RPGRepairItem(RPGMaterialTiers.MITHRIL, new Item.Properties().rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> FORGE_HAMMER_ELEMENTIUM = ITEMS.register("forge_hammer_elementium",
            () -> new RPGRepairItem(RPGMaterialTiers.ELEMENTIUM, new Item.Properties().rarity(RPGRarity.RPG_EPIC)).setFoil(true));

    public static final RegistryObject<Item> BONE_PICKAXE = ITEMS.register("bone_pickaxe",
            () -> new PickaxeItem(RPGMaterialTiers.BONE, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS).durability(RPGMaterialTiers.BONE.getUses()).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe",
            () -> new PickaxeItem(RPGMaterialTiers.BRONZE, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS).durability(RPGMaterialTiers.BRONZE.getUses()).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> IRON_PICKAXE = ITEMS.register("iron_pickaxe",
            () -> new PickaxeItem(RPGMaterialTiers.IRON, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS).durability(RPGMaterialTiers.IRON.getUses()).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> RUSTED_PICKAXE = ITEMS.register("rusted_pickaxe",
            () -> new PickaxeItem(RPGMaterialTiers.RUSTY, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS).durability(RPGMaterialTiers.RUSTY.getUses()).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> RUSTED_SHOVEL = ITEMS.register("rusted_shovel",
            () -> new ShovelItem(RPGMaterialTiers.RUSTY, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS).durability(RPGMaterialTiers.RUSTY.getUses()).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe",
            () -> new PickaxeItem(RPGMaterialTiers.STEEL, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS).durability(RPGMaterialTiers.STEEL.getUses()).rarity(RPGRarity.RPG_COMMON)));



    //MANA
    public static final RegistryObject<Item> MANA_SHARD = ITEMS.register("mana_shard",
            () -> new RPGManCrystalItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).food(RPGFoodItem.MANA), 10));
    public static final RegistryObject<Item> MANA_STONE = ITEMS.register("mana_stone",
            () -> new RPGManCrystalItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).food(RPGFoodItem.MANA), 15));
    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal",
            () -> new RPGManCrystalItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).food(RPGFoodItem.MANA), 25));
    public static final RegistryObject<Item> SMALL_MANA_POTION = ITEMS.register("small_mana_potion",
            () -> new RPGManaPotionItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).food(RPGFoodItem.MANA), 20));
    public static final RegistryObject<Item> MEDIUM_MANA_POTION = ITEMS.register("medium_mana_potion",
            () -> new RPGManaPotionItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).food(RPGFoodItem.MANA), 30));
    public static final RegistryObject<Item> LARGE_MANA_POTION = ITEMS.register("large_mana_potion",
            () -> new RPGManaPotionItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).food(RPGFoodItem.MANA), 75));
    public static final RegistryObject<Item> SCROLL_OF_MANA = ITEMS.register("scroll_of_mana",
            () -> new RPGManaScrolltem(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).rarity(RPGRarity.RPG_UNCOMMON),100));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //BASIC COOKING INGREDIENT
    public static final RegistryObject<Item> YEAST = ITEMS.register("yast", () -> new Item(basicFoodProps));
    public static final RegistryObject<Item> HOP = ITEMS.register("hop", () -> new Item(basicFoodProps));
    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour", () -> new Item(basicFoodProps));
    public static final RegistryObject<Item> SPICE = ITEMS.register("spice", () -> new Item(refinedFoodProps));
    public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(basicFoodProps));
    public static final RegistryObject<Item> FRESH_WATER = ITEMS.register("fresh_water", () -> new Item(refinedFoodProps));
    public static final RegistryObject<Item> MILK = ITEMS.register("milk", () -> new Item(refinedFoodProps));
    public static final RegistryObject<Item> CUP_CLEAN_WATER = ITEMS.register("cup_clean_water", () -> new Item(refinedFoodProps));
    public static final RegistryObject<Item> CUP_COCONUT_MILK = ITEMS.register("cup_coconut_milk", () -> new Item(refinedFoodProps));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //SEEDS AND PLANTS


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Fruits etc
    public static final RegistryObject<Item> GRAPES = ITEMS.register("grapes", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> SPINACH = ITEMS.register("spinach", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> ASPARAGUS = ITEMS.register("asparagus", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> AVOCADO = ITEMS.register("avocado", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> COCONUT_HALF = ITEMS.register("coconut_half", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> AUBERGINE = ITEMS.register("aubergine", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> BELLPEPPER = ITEMS.register("bellpepper", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> COTTON = ITEMS.register("cotton", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> OAT = ITEMS.register("oat", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> ONION = ITEMS.register("onion", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> PEANUT = ITEMS.register("peanut", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> PEPPER = ITEMS.register("pepper", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> RADISH = ITEMS.register("radish", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> RYE = ITEMS.register("rye", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> SORGHUM = ITEMS.register("sorghum", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> SWEET_POTATO = ITEMS.register("sweet_potato", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(basicFoodProps.food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> COCONUT = ITEMS.register("coconut", () -> new Item(basicFoodProps.stacksTo(1)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //COMMON FEAST
    public static final RegistryObject<Item> HAM = ITEMS.register("ham", () -> new Item(basicFoodProps.food(RPGFoodItem.COMMON_FEAST)));
    public static final RegistryObject<Item> BAGUETTE = ITEMS.register("baguette", () -> new Item(basicFoodProps.food(RPGFoodItem.COMMON_FEAST)));
    public static final RegistryObject<Item> BLUEBERRIES = ITEMS.register("blueberries", () -> new Item(basicFoodProps.food(RPGFoodItem.COMMON_FEAST)));
    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> OLIVES = ITEMS.register("olives", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> PEPPERBOWL = ITEMS.register("pepperbowl", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> CHOCO_ICE = ITEMS.register("choco_ice", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> DRIED_FRUITS = ITEMS.register("dried_fruits", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> FRIED_EGGS = ITEMS.register("fried_eggs", () -> new RPGPanItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(RPGItems.PAN.get())));
    public static final RegistryObject<Item> INNERS = ITEMS.register("inners", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> JELLY = ITEMS.register("jelly", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> SLICED_TOMATO = ITEMS.register("sliced_tomato", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> TOMATOES = ITEMS.register("tomatoes", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> STRAWBERRIES = ITEMS.register("strawberries", () -> new BowlFoodItem(basicFoodProps.food(RPGFoodItem.COMMON_FEAST).craftRemainder(Items.BOWL)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //HEARTY FEAST
    public static final RegistryObject<Item> BEEFSTEW = ITEMS.register("beefstew", () -> new BowlFoodItem(refinedFoodProps.food(RPGFoodItem.HEARTY_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> CHILI = ITEMS.register("chili", () -> new RPGPanItem(refinedFoodProps.food(RPGFoodItem.HEARTY_FEAST).craftRemainder(RPGItems.PAN.get())));
    public static final RegistryObject<Item> FISHSOUP = ITEMS.register("fishsoup", () -> new BowlFoodItem(refinedFoodProps.food(RPGFoodItem.HEARTY_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> FONDUE = ITEMS.register("fondue", () -> new BowlFoodItem(refinedFoodProps.food(RPGFoodItem.HEARTY_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> MUTTONSTEW = ITEMS.register("muttonstew", () -> new BowlFoodItem(refinedFoodProps.food(RPGFoodItem.HEARTY_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> ONIONSOUP = ITEMS.register("onionsoup", () -> new BowlFoodItem(refinedFoodProps.food(RPGFoodItem.HEARTY_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> RICE = ITEMS.register("rice", () -> new BowlFoodItem(refinedFoodProps.food(RPGFoodItem.HEARTY_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> SLIMESOUP = ITEMS.register("slimesoup", () -> new BowlFoodItem(refinedFoodProps.food(RPGFoodItem.HEARTY_FEAST).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> SPIDEREYESOUP = ITEMS.register("spidereyesoup", () -> new BowlFoodItem(refinedFoodProps.food(RPGFoodItem.HEARTY_FEAST).craftRemainder(Items.BOWL)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //SPECIAL
    public static final RegistryObject<Item> ENDERSOUP = ITEMS.register("endersoup", () -> new BowlFoodItem(refinedFoodProps.food(RPGFoodItem.ENDERSOUP).craftRemainder(Items.BOWL).rarity(RPGRarity.RPG_ARTIFACT)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //DRINKS
    public static final RegistryObject<Item> BEERRBOTTLE = ITEMS.register("beerbottle", () -> new Item(refinedFoodProps.food(RPGFoodItem.ALC_LIGHT).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> RUMBOTTLE = ITEMS.register("rumbottle", () -> new Item(refinedFoodProps.food(RPGFoodItem.ALC_MEDIUM).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> WINEBOTTLE = ITEMS.register("winebottle", () -> new Item(refinedFoodProps.food(RPGFoodItem.ALC_STRONG).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> ABSYNTH = ITEMS.register("absynth", () -> new Item(refinedFoodProps.food(RPGFoodItem.ALC_STRONG).craftRemainder(Items.GLASS_BOTTLE).rarity(RPGRarity.RPG_UNCOMMON)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //INGREDIENT
    public static final RegistryObject<Item> DISTILLED_WATER = ITEMS.register("distilled_water",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    //BASIC ALCHEMIC INGREDIENT
    public static final RegistryObject<Item> ASH_FROM_A_FOREST_FIRE = ITEMS.register("ash_from_a_forest_fire",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON), 1F, RPGIngedientTiers.Tier2,"blindness","restore_heal","",""));
    public static final RegistryObject<Item> CAVE_SPIDER_POISON = ITEMS.register("cave_spider_poison",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.6F, RPGIngedientTiers.Tier1,"poison","","",""));
    public static final RegistryObject<Item> BEHOLDERS_EYE = ITEMS.register("beholders_eye",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_EPIC), 1.2F, RPGIngedientTiers.Tier4,"blindness","harm","poison","infravision"));
    public static final RegistryObject<Item> AQUA_REGIA = ITEMS.register("aqua_regia",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_EPIC), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> ASKIR_CAVE_CATFISH = ITEMS.register("askir_cave_catfish",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 2.5F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> BEAST_TONGUE = ITEMS.register("beast_tongue",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> BEAST_TOOTH = ITEMS.register("beast_tooth",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> BLACK_PEARL = ITEMS.register("black_pearl",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.2F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> CREEPER_BLOOD = ITEMS.register("creeper_blood",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> DRAGON_SPINE = ITEMS.register("dragon_spine",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_RARE), 2.0F, RPGIngedientTiers.Tier1,"","","",""));

    public static final RegistryObject<Item> FISH_BONE = ITEMS.register("fish_bone",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.4F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> FROST_SALT = ITEMS.register("frost_salt",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_RARE), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> HOLY_WATER = ITEMS.register("holy_water",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_RARE), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> HUMAN_BLOOD = ITEMS.register("human_blood",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> HUMAN_EYE = ITEMS.register("human_eye",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.2F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> HYDROCHLORIC_ACID = ITEMS.register("hydrochloric_acid",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.5F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> LIQUID_DEATH = ITEMS.register("liquid_death",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_EPIC), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> MAPLE_ROOT = ITEMS.register("maple_root",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 0.4F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> MISTLETOE = ITEMS.register("mistletoe",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> NITRIC_ACID = ITEMS.register("nitric_acid",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> PIXIE_TEARS = ITEMS.register("pixie_tears",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> PURE_WATER = ITEMS.register("pure_water",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> SALTPETER = ITEMS.register("saltpeter",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.5F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> SULFUR_DUST = ITEMS.register("sulfur_dust",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.5F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> SULFURIC_ACID = ITEMS.register("sulfuric_acid",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_COMMON), 1.0F, RPGIngedientTiers.Tier1,"","","",""));
    public static final RegistryObject<Item> WINESTONE = ITEMS.register("winestone",
            () -> new RPGIngredientDiscovered(new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(50).setNoRepair(), 0.5F, RPGIngedientTiers.Tier1,"","","",""));





    //MATERIAL
    public static final RegistryObject<Item> RAW_CLAY_CRUCIBLE = ITEMS.register("raw_clay_crucible", () -> new RPGMaterialItem(RPGMaterialTiers.CLAY, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RAW_CLAY_INGOT_MOLD = ITEMS.register("raw_clay_ingot_mold", () -> new RPGMaterialItem(RPGMaterialTiers.CLAY, new Item.Properties().stacksTo(1)));

    //Parts
    public static final RegistryObject<Item> HAMMER_HEAD_STEEL = ITEMS.register("hammer_head_steel", () -> new RPGMaterialItem(RPGMaterialTiers.STEEL, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HAMMER_HEAD_NETHERITE = ITEMS.register("hammer_head_netherite", () -> new RPGMaterialItem(RPGMaterialTiers.NETHERITE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HAMMER_HEAD_IRON = ITEMS.register("hammer_head_iron", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TOOL_STICK = ITEMS.register("tool_stick", () -> new RPGMaterialItem(RPGMaterialTiers.WOOD, new Item.Properties().stacksTo(1)));

    //leather,cloth...
    public static final RegistryObject<Item> WOLVES_PELT = ITEMS.register("wolves_pelt", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> THREAD = ITEMS.register("thread", () -> new Item(basicStackableItem));

    public static final RegistryObject<Item> SKULL = ITEMS.register("skull", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> SILK = ITEMS.register("silk", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> STEEL_PLATE = ITEMS.register("steel_plate", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> INDIGO = ITEMS.register("indigo", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> LEATHER = ITEMS.register("leather", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> HIDE = ITEMS.register("hide", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> BEAR_PELT = ITEMS.register("bear_pelt", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> RUNIC_SOLUTION = ITEMS.register("runic_solution",  () -> new Item(new Item.Properties().stacksTo(1).rarity(RPGRarity.RPG_RARE).tab(RPGCreativeModeTab.RPG_TOOLS)));

    public static final RegistryObject<Item> SILVER_KEY = ITEMS.register("silver_key", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> STEEL_KEY = ITEMS.register("steel_key", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> IRON_KEY = ITEMS.register("iron_key", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> GOLD_KEY = ITEMS.register("gold_key", () -> new Item(basicStackableItem));

    public static final RegistryObject<Item> SCROLL = ITEMS.register("scroll", () -> new Item(basicStackableItem));




    public static final RegistryObject<Item> BANDAGE = ITEMS.register("bandage", () -> new RPGBandageItem(basicStackableItem));
    public static final RegistryObject<Item> BOWLIMBS = ITEMS.register("bowlimbs", () -> new RPGMaterialItem(RPGMaterialTiers.WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BOWSTRING = ITEMS.register("bowstring", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> LEATHERSTRIPES = ITEMS.register("leatherstripes", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> LINEN = ITEMS.register("linen", () -> new RPGBandageItem(basicStackableItem));
    public static final RegistryObject<Item> MAGIC_DUST = ITEMS.register("magic_dust", () -> new Item(basicStackableItem.rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> RUNE_DUST = ITEMS.register("rune_dust", () -> new Item(basicStackableItem.rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> IRON_SCRAP = ITEMS.register("iron_scrap", () -> new Item(basicStackableItem));
    public static final RegistryObject<Item> RAW_BLADE_HOT = ITEMS.register("raw_blade_hot", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BASIC_AXE_HEAD = ITEMS.register("basic_axe_head", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BASIC_BLADE = ITEMS.register("basic_blade", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BASIC_CLAYMORE_BLADE = ITEMS.register("basic_claymore_blade", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BASIC_GRIP = ITEMS.register("basic_grip", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BASIC_HANDGUARD = ITEMS.register("basic_handguard", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(1)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Materials
    //ingots
    public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> GLASS_INGOT = ITEMS.register("glass_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> MITHRIL_INGOT = ITEMS.register("mithril_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> ADAMANT_INGOT = ITEMS.register("adamant_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> DARKIRON_INGOT = ITEMS.register("darkiron_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> EBONY_INGOT = ITEMS.register("ebony_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> HELSTONE_INGOT = ITEMS.register("helstone_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> HEXXIN_INGOT = ITEMS.register("hexxin_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", () ->  new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> STAHLRIM_INGOT = ITEMS.register("stahlrim_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> ZINC_INGOT = ITEMS.register("zinc_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //heated ingots (no inventory tab)
    public static final RegistryObject<Item> HOT_IRON_INGOT = ITEMS.register("hot_iron_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_COBALT_INGOT = ITEMS.register("hot_cobalt_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_COPPER_INGOT = ITEMS.register("hot_copper_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_ADAMANT_INGOT = ITEMS.register("hot_adamant_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_DARKIRON_INGOT = ITEMS.register("hot_darkiron_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_EBONY_INGOT = ITEMS.register("hot_ebony_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_GLASS_INGOT = ITEMS.register("hot_glass_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_GOLD_INGOT = ITEMS.register("hot_gold_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_HELSTONE_INGOT = ITEMS.register("hot_helstone_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_HEXXIN_INGOT = ITEMS.register("hot_hexxin_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_LEAD_INGOT = ITEMS.register("hot_lead_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_MITHRIL_INGOT = ITEMS.register("hot_mithril_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_SILVER_INGOT = ITEMS.register("hot_silver_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_STAHLRIM_INGOT = ITEMS.register("hot_stahlrim_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_TIN_INGOT = ITEMS.register("hot_tin_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_ZINC_INGOT = ITEMS.register("hot_zinc_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //alloys
    public static final RegistryObject<Item> ELEMENTIUM_INGOT = ITEMS.register("elementium_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> ORICALCUM_INGOT = ITEMS.register("oricalcum_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> VOIDSTEEL_INGOT = ITEMS.register("voidsteel_ingot", () -> new Item(basicStackableItem.rarity(RPGRarity.RPG_EPIC)));
    public static final RegistryObject<Item> ASTRALSILVER_INGOT = ITEMS.register("astralsilver_ingot", () -> new Item(basicStackableItem.rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> BRASS_INGOT = ITEMS.register("brass_ingot", () -> new Item(basicStackableItem.rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(basicStackableItem.rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> CRIMSON_STEEL_INGOT = ITEMS.register("crimson_steel_ingot", () -> new Item(basicStackableItem.rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> DWARVEN_DARKSTEEL_INGOT = ITEMS.register("dwarven_darksteel_ingot", () -> new Item(basicStackableItem.rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> ELVEN_STEEL_INGOT = ITEMS.register("elven_steel_ingot", () -> new Item(basicStackableItem.rarity(RPGRarity.RPG_UNCOMMON)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //heated alloys (no inventory tab)
    public static final RegistryObject<Item> HOT_STEEL_INGOT = ITEMS.register("hot_steel_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_ASTRALSILVER_INGOT = ITEMS.register("hot_astralsilver_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_BRASS_INGOT = ITEMS.register("hot_brass_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_BRONZE_INGOT = ITEMS.register("hot_bronze_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_CRIMSON_STEEL_INGOT = ITEMS.register("hot_crimson_steel_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_DWARVEN_DARKSTEEL_INGOT = ITEMS.register("hot_dwarven_darksteel_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_ELEMENTIUM_INGOT = ITEMS.register("hot_elementium_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_ELVEN_STEEL_INGOT = ITEMS.register("hot_elven_steel_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_ORICALCUM_INGOT = ITEMS.register("hot_oricalcum_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    public static final RegistryObject<Item> HOT_VOIDSTEEL_INGOT = ITEMS.register("hot_voidsteel_ingot", () -> new RPGHeatedItem(RPGMaterialTiers.STEEL, basicStackableItem.rarity(RPGRarity.RPG_COMMON).tab(null)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //nugget
    public static final RegistryObject<Item> COBALT_NUGGET = ITEMS.register("cobalt_nugget", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //raw
    public static final RegistryObject<Item> GLASS_SHARD = ITEMS.register("glass_shard", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_ADAMANT = ITEMS.register("raw_adamant", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_DARKIRON = ITEMS.register("raw_darkiron", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_EBONY = ITEMS.register("raw_ebony", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_HEXXIN = ITEMS.register("raw_hexxin", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_STAHLRIM = ITEMS.register("raw_stahlrim", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_ZINC = ITEMS.register("raw_zinc", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_COPPER = ITEMS.register("raw_copper", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_IRON = ITEMS.register("raw_iron", () ->new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_GOLD = ITEMS.register("raw_gold", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_HELSTONE = ITEMS.register("raw_helstone", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    public static final RegistryObject<Item> RAW_MITHRIL = ITEMS.register("raw_mithril", () -> new Item(new Item.Properties().stacksTo(64).rarity(RPGRarity.RPG_COMMON).tab(RPGCreativeModeTab.RPG_MISC)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //crucible filled
    public static final RegistryObject<Item> CRUCIBLE_SILVER_ZINC_MIX = ITEMS.register("crucible_silver_zinc_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));
    public static final RegistryObject<Item> CRUCIBLE_COAL_IRON_MIX = ITEMS.register("crucible_coal_iron_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));
    public static final RegistryObject<Item> CRUCIBLE_COPPER_ZINC_MIX = ITEMS.register("crucible_copper_zinc_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));
    public static final RegistryObject<Item> CRUCIBLE_TIN_COPPER_MIX = ITEMS.register("crucible_tin_copper_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));
    public static final RegistryObject<Item> CRUCIBLE_ZINC_COBALT_MIX = ITEMS.register("crucible_zinc_cobalt_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));
    public static final RegistryObject<Item> CRUCIBLE_HELSTONE_IRON_COAL_MIX = ITEMS.register("crucible_helstone_iron_coal_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));
    public static final RegistryObject<Item> CRUCIBLE_COAL_ZINC_DARKIRON_MIX = ITEMS.register("crucible_coal_zinc_darkiron_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));
    public static final RegistryObject<Item> CRUCIBLE_COAL_ZINC_GOLD_MIX = ITEMS.register("crucible_coal_zinc_gold_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));
    public static final RegistryObject<Item> CRUCIBLE_STAHLRIM_ADAMANT_EBONY_MIX = ITEMS.register("crucible_stahlrim_adamant_ebony_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));
    public static final RegistryObject<Item> CRUCIBLE_STAHLRIM_ELEMENTIUM_RUNE_DUST_MIX = ITEMS.register("crucible_stahlrim_elementium_rune_dust_mix", () -> new Item(new Item.Properties().tab(null).stacksTo(1)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //crucible filled hot
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_ASTRALSILVER = ITEMS.register("crucible_molten_astralsilver", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_STEEL = ITEMS.register("crucible_molten_steel", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_BRASS = ITEMS.register("crucible_molten_brass", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_BRONZE = ITEMS.register("crucible_molten_bronze", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_CRIMSON_STEEL = ITEMS.register("crucible_molten_crimson_steel", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_DWARVEN_DARKSTEEL = ITEMS.register("crucible_molten_dwarven_darksteel", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_ELEMENTIUM = ITEMS.register("crucible_molten_elementium", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_ELVEN_STEEL = ITEMS.register("crucible_molten_elven_steel", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_ORICALCUM = ITEMS.register("crucible_molten_oricalcum", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));
    public static final RegistryObject<Item> CRUCIBLE_MOLTEN_VOID_STEEL = ITEMS.register("crucible_molten_void_steel", () -> new RPGHeatedItem(RPGMaterialTiers.COBALT,new Item.Properties().craftRemainder(RPGItems.CLAY_CRUCIBLE.get()).stacksTo(1).tab(null)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // parts
    public static final RegistryObject<Item> CHAINPARTS_IRON = ITEMS.register("chainparts_iron", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> IRON_FITTINGS = ITEMS.register("iron_fittings", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> IRON_RIVETS = ITEMS.register("iron_rivets", () -> new RPGMaterialItem(RPGMaterialTiers.IRON, new Item.Properties().stacksTo(16)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Fuel
    public static final RegistryObject<Item> COAL_COKE = ITEMS.register("coal_coke",
            () -> new RPGFuelItem(3200, 2200,new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).stacksTo(64)));
    public static final RegistryObject<Item> PEAT_Brick = ITEMS.register("peat_brick",
            () -> new RPGFuelItem(160, 300,new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).stacksTo(64)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Gem
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).stacksTo(64)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //RANGE
    //bow
    public static final RegistryObject<Item> WOOD_BOW = ITEMS.register("wood_bow",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())));
    public static final RegistryObject<Item> SILVER_BOW = ITEMS.register("silver_bow",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.SILVER, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.SILVER.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //longbow

    public static final RegistryObject<Item> GLASS_BOW = ITEMS.register("glass_bow",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.GLASS, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.GLASS.getUses())));
    public static final RegistryObject<Item> BONE_LONGBOW = ITEMS.register("bone_longbow",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.BONE, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.BONE.getUses())));
    public static final RegistryObject<Item> CRIMSON_BOW = ITEMS.register("crimson_bow",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.CRIMSON, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.CRIMSON.getUses())));
    public static final RegistryObject<Item> DAEDRIC_BOW = ITEMS.register("daedric_bow",                                                                                        //TODO Rendertest
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.EBONY, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.EBONY.getUses())));
    public static final RegistryObject<Item> DEMONIC_EMBRACE = ITEMS.register("demonic_embrace",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.ELEMENTIUM, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.ELEMENTIUM.getUses())));
    public static final RegistryObject<Item> NORDIC_BOW = ITEMS.register("nordic_bow",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.SILVER.getUses())));
    public static final RegistryObject<Item> STEEL_LONG_WARBOW = ITEMS.register("steel_long_warbow",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> WINDFORCE = ITEMS.register("windforce",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.ASTRALSILVER, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.ASTRALSILVER.getUses())));
    public static final RegistryObject<Item> SCORPION_STING = ITEMS.register("scorpion_sting",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.DRAGONBONE, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.DRAGONBONE.getUses())));
    public static final RegistryObject<Item> STAHLRIM_BOW = ITEMS.register("stahlrim_bow",
            () -> new RPGBasicBowWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //crossbow
    public static final RegistryObject<Item> GLASS_CROSSBOW = ITEMS.register("glass_crossbow",
            () -> new RPGBasicCrossbowWeapon(RPGMaterialTiers.GLASS, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.GLASS.getUses())));
    public static final RegistryObject<Item> DAEDRIC_CROSSBOW = ITEMS.register("daedric_crossbow",                                                                               //TODO Rendertest
            () -> new RPGBasicCrossbowWeapon(RPGMaterialTiers.EBONY, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.EBONY.getUses())));
    public static final RegistryObject<Item> CRIMSON_CROSSBOW = ITEMS.register("crimson_crossbow",
            () -> new RPGBasicCrossbowWeapon(RPGMaterialTiers.CRIMSON, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.CRIMSON.getUses())));
    public static final RegistryObject<Item> STAHLRIM_CROSSBOW = ITEMS.register("stahlrim_crossbow",
            () -> new RPGBasicCrossbowWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //AMMO
    public static final RegistryObject<Item> FIRE_ARROW = ITEMS.register("fire_arrow",
            () -> new FireArrowItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_WEAPON).stacksTo(64)));
    public static final RegistryObject<Item> POISON_DART = ITEMS.register("poison_dart",
            () -> new PoisonDart(new Item.Properties().tab(RPGCreativeModeTab.RPG_WEAPON).stacksTo(64)));
    public static final RegistryObject<Item> BULLET = ITEMS.register("bullet",
            () -> new Bullet(new Item.Properties().tab(RPGCreativeModeTab.RPG_WEAPON).stacksTo(64)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //wands scepters
    public static final RegistryObject<Item> SCEPTER_OF_LIFE = ITEMS.register("scepter_of_life",
            () -> new ScepterOfLife(new Item.Properties().tab(RPGCreativeModeTab.RPG_WEAPON).durability(RPGMaterialTiers.VOID.getUses()).rarity(RPGRarity.RPG_EPIC)));
    //magic staffs
    public static final RegistryObject<Item> TELEPORT_STAFF = ITEMS.register("teleport_staff",
            () -> new TeleportStaff(new Item.Properties().tab(RPGCreativeModeTab.RPG_WEAPON).durability(RPGMaterialTiers.VOID.getUses()).rarity(RPGRarity.RPG_EPIC)));
    public static final RegistryObject<Item> FIRESTAFF = ITEMS.register("firestaff",
            () -> new FireStaff(new Item.Properties().tab(RPGCreativeModeTab.RPG_WEAPON).durability(RPGMaterialTiers.VOID.getUses()).rarity(RPGRarity.RPG_EPIC)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //guns
    public static final RegistryObject<Item> BLOWGUN = ITEMS.register("blowgun",
            () -> new BlowGun(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.WOOD.getUses())));
    public static final RegistryObject<Item> FLINTLOCK = ITEMS.register("flintlock",
            () -> new FlintlockPistol(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> BLUNDERBUSS = ITEMS.register("blunderbuss",
            () -> new Blunderbuss(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> MUSKET = ITEMS.register("musket",
            () -> new MusketGun(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STEEL.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //books, orbs etc
    public static final RegistryObject<Item> STEEL_CHAKRAM = ITEMS.register("steel_chakram",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final RegistryObject<Item> STING = ITEMS.register("sting",
            () -> new UniqueSwordSting(RPGMaterialTiers.MITHRIL, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.MITHRIL.getUses())));
    public static final RegistryObject<Item> DUSK_FANG = ITEMS.register("dusk_fang",
            () -> new UniqueSwordDuskfangDawnfang(RPGMaterialTiers.MITHRIL, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.MITHRIL.getUses())));
    public static final RegistryObject<Item> GLASS_LONGSWORD = ITEMS.register("glass_longsword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.GLASS, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.GLASS.getUses())));
    public static final RegistryObject<Item> GLASS_SWORD = ITEMS.register("glass_sword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.GLASS, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.GLASS.getUses())));
    public static final RegistryObject<Item> RUBY_SWORD = ITEMS.register("ruby_sword",                                                                                          //todo REMOVE
            () -> new SwordItem(Tiers.DIAMOND, 1, 1, new Item.Properties().tab(RPGCreativeModeTab.RPG_WEAPON)));
    public static final RegistryObject<Item> BLADE_OF_WOE = ITEMS.register("blade_of_woe",
            () -> new UniqueSwordBladeOfWoe(RPGMaterialTiers.VOID, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.VOID.getUses())));
    public static final RegistryObject<Item> ASSASSINS_POISONBLADE = ITEMS.register("assassins_poisonblade",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setPoison(true));
    public static final RegistryObject<Item> BLADE_SWORD = ITEMS.register("blade_sword",                                                                                        //TODO Rendertest
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> BLOODTHORN = ITEMS.register("bloodthorn",                            //todo unique
            () -> new UniqueBloodThorn(RPGMaterialTiers.CRIMSON, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.CRIMSON.getUses())).setFantasy(true).setMythicalOneHand(true));
    public static final RegistryObject<Item> BOROMIRS_SWORD = ITEMS.register("boromirs_sword",//todo unique
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> CRIMSON_SWORD = ITEMS.register("crimson_sword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.CRIMSON, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.CRIMSON.getUses())));
    public static final RegistryObject<Item> DOOMBRINGER = ITEMS.register("doombringer",                          //todo unique
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> HAND_AND_A_HALF = ITEMS.register("hand_and_a_half",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> IRON_LONGSWORD = ITEMS.register("iron_longsword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())));
    public static final RegistryObject<Item> IRON_SHORTSWORD = ITEMS.register("iron_shortsword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())));
    public static final RegistryObject<Item> IRON_SWORD = ITEMS.register("iron_sword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())));
    public static final RegistryObject<Item> MARAUDER_FALCHION = ITEMS.register("marauder_falchion",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> MESSER = ITEMS.register("messer",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> MITHRIL_SWORD = ITEMS.register("mithril_sword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.MITHRIL, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.MITHRIL.getUses())));
    public static final RegistryObject<Item> NARSIL_BROKEN = ITEMS.register("narsil_broken",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> NORDIC_SWORD = ITEMS.register("nordic_sword",
            () -> new RPGAxeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> RAZORTOOTH = ITEMS.register("razortooth",//todo unique
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> RUSTED_SWORD = ITEMS.register("rusted_sword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.RUSTY, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.RUSTY.getUses())));
    public static final RegistryObject<Item> STAHLRIM_KATANA = ITEMS.register("stahlrim_katana",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())));
    public static final RegistryObject<Item> STAHLRIM_LONGSWORD = ITEMS.register("stahlrim_longsword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())));
    public static final RegistryObject<Item> STAHLRIM_SWORD = ITEMS.register("stahlrim_sword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())));
    public static final RegistryObject<Item> STEEL_KATANA = ITEMS.register("steel_katana",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> STEEL_LONGSWORD = ITEMS.register("steel_longsword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> URUK_HAI_SWORD = ITEMS.register("uruk_hai_sword",
            () -> new RPGSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //dagger
    public static final RegistryObject<Item> ADAMANTIUM_DAGGER = ITEMS.register("adamantium_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.ADAMANTIUM, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.ADAMANTIUM.getUses())).setThrowable(true).setFantasy(true));
    public static final RegistryObject<Item> BONE_DAGGER = ITEMS.register("bone_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.BONE, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.BONE.getUses())).setThrowable(true));
    public static final RegistryObject<Item> COPPER_DAGGER = ITEMS.register("copper_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.COPPER, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.COPPER.getUses())).setThrowable(true));
    public static final RegistryObject<Item> CRIMSON_DAGGER = ITEMS.register("crimson_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.CRIMSON, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.CRIMSON.getUses())).setThrowable(true).setFantasy(true));
    public static final RegistryObject<Item> DAEDRIC_DAGGER = ITEMS.register("daedric_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.EBONY, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.EBONY.getUses())).setThrowable(true).setFantasy(true));
    public static final RegistryObject<Item> DRAGONBONE_DAGGER = ITEMS.register("dragonbone_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.DRAGONBONE, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.DRAGONBONE.getUses())).setThrowable(true).setFantasy(true));
    public static final RegistryObject<Item> ELVEN_DAGGER = ITEMS.register("elven_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.ELVEN, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.ELVEN.getUses())).setThrowable(true).setFantasy(true));
    public static final RegistryObject<Item> FANCY_DAGGER = ITEMS.register("fancy_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.SILVER, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.SILVER.getUses())).setThrowable(true).setSilver(true));
    public static final RegistryObject<Item> GLASS_DAGGER = ITEMS.register("glass_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.GLASS, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.GLASS.getUses())).setThrowable(true).setFantasy(true));
    public static final RegistryObject<Item> OLD_DAGGER = ITEMS.register("old_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.OLD, new Item.Properties().rarity(RPGRarity.RPG_CLUTTER).durability(RPGMaterialTiers.OLD.getUses())).setThrowable(true));
    public static final RegistryObject<Item> RUSTED_DAGGER = ITEMS.register("rusted_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.RUSTY, new Item.Properties().rarity(RPGRarity.RPG_CLUTTER).durability(RPGMaterialTiers.RUSTY.getUses())).setThrowable(true));
    public static final RegistryObject<Item> STAHLRIM_DAGGER = ITEMS.register("stahlrim_dagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())).setThrowable(true).setFantasy(true));
    public static final RegistryObject<Item> BROKEN_BOTTLE = ITEMS.register("broken_bottle",
            () -> new RPGBrokenBottleItem(RPGMaterialTiers.CLUTTER, new Item.Properties().rarity(RPGRarity.RPG_CLUTTER).durability(RPGMaterialTiers.CLUTTER.getUses())).setThrowable(true));
    public static final RegistryObject<Item> ASSASSIN_POISONDAGGER = ITEMS.register("assassin_poisondagger",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())).setPoison(true).setThrowable(true));
    public static final RegistryObject<Item> FANCY_KRIS = ITEMS.register("fancy_kris",
            () -> new RPGKrisWeapon(RPGMaterialTiers.ASTRALSILVER, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.ASTRALSILVER.getUses())).setThrowable(false).setSilver(true));
    public static final RegistryObject<Item> OBLIVION = ITEMS.register("oblivion",//todo unique
            () -> new RPGKrisWeapon(RPGMaterialTiers.ELEMENTIUM, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.ELEMENTIUM.getUses())).setThrowable(false).setFantasy(true).setMythicalOneHand(true));
    public static final RegistryObject<Item> HUNTING_KNIVE = ITEMS.register("hunting_knive",
            () -> new RPGDaggerWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setThrowable(true));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //axe
    public static final RegistryObject<Item> STAHLRIM_AXE = ITEMS.register("stahlrim_axe",
            () -> new RPGAxeWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())));
    public static final RegistryObject<Item> DWARVEN_AXE = ITEMS.register("dwarven_axe",
            () -> new RPGAxeWeapon(RPGMaterialTiers.DWARVEN, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.DWARVEN.getUses())));
    public static final RegistryObject<Item> GIMLIS_AXE = ITEMS.register("gimlis_axe",//todo unique
            () -> new RPGAxeWeapon(RPGMaterialTiers.DWARVEN, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.DWARVEN.getUses())));
    public static final RegistryObject<Item> IRON_AXE = ITEMS.register("iron_axe",
            () -> new RPGAxeWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())));
    public static final RegistryObject<Item> NORDIC_AXE = ITEMS.register("nordic_axe",
            () -> new RPGAxeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> RUSTED_AXE = ITEMS.register("rusted_axe",
            () -> new RPGAxeWeapon(RPGMaterialTiers.RUSTY, new Item.Properties().rarity(RPGRarity.RPG_CLUTTER).durability(RPGMaterialTiers.RUSTY.getUses())));
    public static final RegistryObject<Item> BONE_AXE = ITEMS.register("bone_axe",
            () -> new RPGAxeWeapon(RPGMaterialTiers.BONE, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.BONE.getUses())));
    public static final RegistryObject<Item> VIKING_BARTAXE = ITEMS.register("viking_bartaxe",
            () -> new RPGAxeWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())));
    public static final RegistryObject<Item> VIKING_BROADAXE = ITEMS.register("viking_broadaxe",
            () -> new RPGAxeWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())));
    public static final RegistryObject<Item> KRUSH_TUROK = ITEMS.register("krush_turok",
            () -> new RPGAxeWeapon(RPGMaterialTiers.ORC, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.ORC.getUses())).setHeavyOneHand(true));
    public static final RegistryObject<Item> KRUSH_PACH = ITEMS.register("krush_pach",
            () -> new RPGAxeWeapon(RPGMaterialTiers.ORC, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.ORC.getUses())).setHeavyOneHand(true));
    public static final RegistryObject<Item> KRUSH_AGASH = ITEMS.register("krush_agash",
            () -> new RPGAxeWeapon(RPGMaterialTiers.ORC, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.ORC.getUses())).setHeavyOneHand(true));
    public static final RegistryObject<Item> BROADAXE = ITEMS.register("broadaxe",
            () -> new RPGAxeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> LEVIATAN = ITEMS.register("leviatan",//todo unique
            () -> new RPGAxeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.STEEL.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //mace
    public static final RegistryObject<Item> BIFURS_MACE = ITEMS.register("bifurs_mace",//todo unique
            () -> new RPGBluntWeapon(RPGMaterialTiers.DWARVEN, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.DWARVEN.getUses())));
    public static final RegistryObject<Item> RAVENBEAK = ITEMS.register("ravenbeak",
            () -> new RPGBluntWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> STEEL_MACE = ITEMS.register("steel_mace",
            () -> new RPGBluntWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    public static final RegistryObject<Item> NAILED_CLUB = ITEMS.register("nailed_club",
            () -> new RPGBluntWeapon(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())));
    public static final RegistryObject<Item> WOODEN_CLUB = ITEMS.register("wooden_club",
            () -> new RPGBluntWeapon(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //flail
    public static final RegistryObject<Item> STEEL_FLAIL = ITEMS.register("steel_flail",
            () -> new RPGTFlailWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //2handed
    public static final RegistryObject<Item> BRONZE_CLAYMORE = ITEMS.register("bronze_claymore",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.BRONZE, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.BRONZE.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> BLACKGUARD_CLAYMORE = ITEMS.register("blackguard_claymore",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> BIPOLARBLADE = ITEMS.register("bipolarblade",//todo unique
            () -> new UniqueSwordBipolarblade(RPGMaterialTiers.VOID, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.VOID.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> GLASS_CLAYMORE = ITEMS.register("glass_claymore",//todo unique
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.GLASS, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.GLASS.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> GLASS_GREATSWORD = ITEMS.register("glass_greatsword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.GLASS, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.GLASS.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> CHRYSAMERE = ITEMS.register("chrysamere",//todo unique
            () -> new UniqueSwordChrysamere(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1).setMythicalTwoHand(true).setFantasy(true));
    public static final RegistryObject<Item> DRAGONBONE_GREATSWORD = ITEMS.register("dragonbone_greatsword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.DRAGONBONE, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> EBONY_GREATSWORD = ITEMS.register("ebony_greatsword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.EBONY, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> FIRE_SWORD = ITEMS.register("fire_sword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> GLAMDRING = ITEMS.register("glamdring",//todo unique
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> HAMBURGER_RICHTSCHWERT = ITEMS.register("hamburger_richtschwert",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> ICEBLADEOFTHEMONARCH = ITEMS.register("icebladeofthemonarch",//todo unique
            () -> new UniqueSwordIcebladeOfTheMonarch(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> ICH_RICHTE_RICHTSCHWERT = ITEMS.register("ich_richte_richtschwert",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> IRON_GREATSWORD = ITEMS.register("iron_greatsword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> MARAUDER_GREATSWORD = ITEMS.register("marauder_greatsword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> NARSIL_REFORGED = ITEMS.register("narsil_reforged",//todo unique
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> NORDIC_TWOHANDER = ITEMS.register("nordic_twohander",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> OBSIDIAN_GREATSWORD = ITEMS.register("obsidian_sword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.DARKIRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> ONEMANSLAND_STEEL_SWORD = ITEMS.register("onemansland_steel_sword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> STAHLRIM_CLAYMORE = ITEMS.register("stahlrim_claymore",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> STAHLRIM_GREATSWORD = ITEMS.register("stahlrim_greatsword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> STEEL_CLAYMORE = ITEMS.register("steel_claymore",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> STEEL_GREAT_TACHI = ITEMS.register("steel_great_tachi",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> STEEL_GREATSWORD = ITEMS.register("steel_greatsword",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> STREICHERS_SWORD = ITEMS.register("streichers_sword",//todo unique
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(1));
    public static final RegistryObject<Item> URUK_HAI_HEADHUNTER = ITEMS.register("uruk_hai_headhunter",
            () -> new RPGTwoHandSwordWeapon(RPGMaterialTiers.ORC, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.ORC.getUses())));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //jousting
    public static final RegistryObject<Item> JOUSTING_LANCE_BLACK = ITEMS.register("jousting_lance_black",
            () -> new RPGLanceWeapon(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())));
    public static final RegistryObject<Item> JOUSTING_LANCE_BLUE = ITEMS.register("jousting_lance_blue",
            () -> new RPGLanceWeapon(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())));
    public static final RegistryObject<Item> JOUSTING_LANCE_GREEN = ITEMS.register("jousting_lance_green",
            () -> new RPGLanceWeapon(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())));
    public static final RegistryObject<Item> JOUSTING_LANCE_RED = ITEMS.register("jousting_lance_red",
            () -> new RPGLanceWeapon(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())));
    public static final RegistryObject<Item> JOUSTING_LANCE_YELLOW = ITEMS.register("jousting_lance_yellow",
            () -> new RPGLanceWeapon(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())));
    public static final RegistryObject<Item> KRUSH_IRMAK = ITEMS.register("krush_irmak",
            () -> new RPGLanceWeapon(RPGMaterialTiers.ORC, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.ORC.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //spear
    public static final RegistryObject<Item> STAHLRIM_SPEAR = ITEMS.register("stahlrim_spear",
            () -> new RPGSpearWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())));
    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new RPGSpearWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())));
    public static final RegistryObject<Item> STEEL_SPEAR = ITEMS.register("steel_spear",
            () -> new RPGSpearWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //twohandaxe
    public static final RegistryObject<Item> BLACK_CLEAVER = ITEMS.register("black_cleaver",
            () -> new RPGTwoHandAxeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> DAEDRIC_GREATAXE = ITEMS.register("daedric_greataxe",
            () -> new RPGTwoHandAxeWeapon(RPGMaterialTiers.VOID, new Item.Properties().rarity(RPGRarity.RPG_EPIC).durability(RPGMaterialTiers.VOID.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> DRAGONBONE_GREATAXE = ITEMS.register("dragonbone_greataxe",
            () -> new RPGTwoHandAxeWeapon(RPGMaterialTiers.DRAGONBONE, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.DRAGONBONE.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> GIMLIS_GREATAXE = ITEMS.register("gimlis_greataxe",//todo unique
            () -> new RPGTwoHandAxeWeapon(RPGMaterialTiers.DWARVEN, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY).durability(RPGMaterialTiers.DWARVEN.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> IRON_GREATAXE = ITEMS.register("iron_greataxe",
            () -> new RPGTwoHandAxeWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> STAHLRIM_GREATAXE = ITEMS.register("stahlrim_greataxe",
            () -> new RPGTwoHandAxeWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> STEEL_GREATAXE = ITEMS.register("steel_greataxe",
            () -> new RPGTwoHandAxeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> VIKING_DOUBLEAXE = ITEMS.register("viking_doubleaxe",
            () -> new RPGTwoHandAxeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(0));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //twohandmace
    public static final RegistryObject<Item> BATTLESTAFF = ITEMS.register("battlestaff",
            () -> new RPGTwoHandBluntWeapon(RPGMaterialTiers.WOOD, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> IRON_GREATHAMMER = ITEMS.register("iron_greathammer",
            () -> new RPGTwoHandBluntWeapon(RPGMaterialTiers.IRON, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> POLEMACE = ITEMS.register("polemace",
            () -> new RPGTwoHandBluntWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> TWOHAND_MACE = ITEMS.register("twohand_mace",
            () -> new RPGTwoHandBluntWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> STEEL_GREATHAMMER = ITEMS.register("steel_greathammer",
            () -> new RPGTwoHandBluntWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> STAHLRIM_WARHAMMER = ITEMS.register("stahlrim_warhammer",
            () -> new RPGTwoHandBluntWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())).setBlockWeapon(0));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //twohandpole
    public static final RegistryObject<Item> KRUSH_TARACH = ITEMS.register("krush_tarach",
            () -> new RPGBasicTwoHandMeleeWeapon(RPGMaterialTiers.ORC, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.ORC.getUses())).setBlockWeapon(0));
    public static final RegistryObject<Item> POLEAXE = ITEMS.register("poleaxe",
            () -> new RPGBasicTwoHandMeleeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(0));;
    public static final RegistryObject<Item> STEEL_HALBERD = ITEMS.register("steel_halberd",
            () -> new RPGBasicTwoHandMeleeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setBlockWeapon(0));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //sickle
    public static final RegistryObject<Item> STEEL_CRESCENT = ITEMS.register("steel_crescent",
            () -> new RPGTwoHandScytheWeapon(RPGMaterialTiers.STEEL, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STEEL.getUses())));
    //twohandscythe
    public static final RegistryObject<Item> STAHLRIM_WARSCYTHE = ITEMS.register("stahlrim_warscythe",
            () -> new RPGTwoHandScytheWeapon(RPGMaterialTiers.STAHLRIM, new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.STAHLRIM.getUses())));
    public static final RegistryObject<Item> SCYTHE = ITEMS.register("scythe",
            () -> new RPGTwoHandScytheWeapon(RPGMaterialTiers.IRON, new Item.Properties().durability(RPGMaterialTiers.IRON.getUses()).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> SPINALREAPER = ITEMS.register("spinalreaper",//todo unique
            () -> new RPGTwoHandScytheWeapon(RPGMaterialTiers.BONE, new Item.Properties().durability(RPGMaterialTiers.BONE.getUses()).rarity(RPGRarity.RPG_LEGENDARY)));
    public static final RegistryObject<Item> CRIMSON_HARVESTER = ITEMS.register("crimson_harvester",
            () -> new RPGTwoHandScytheWeapon(RPGMaterialTiers.CRIMSON, new Item.Properties().durability(RPGMaterialTiers.CRIMSON.getUses()).rarity(RPGRarity.RPG_RARE)));





    //TODO NEW
    public static final RegistryObject<Item> DECORATED_SPEAR = ITEMS.register("decorated_spear",
            () -> new RPGBasicTwoHandMeleeWeapon(RPGMaterialTiers.BRONZE, new Item.Properties().durability(RPGMaterialTiers.BRONZE.getUses()).rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> DWARVEN_GREATAXE = ITEMS.register("dwarven_greataxe",
            () -> new RPGBasicTwoHandMeleeWeapon(RPGMaterialTiers.DWARVEN, new Item.Properties().durability(RPGMaterialTiers.DWARVEN.getUses()).rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> HALADLE_KNIFE = ITEMS.register("haladle_knife",
            () -> new RPGBasicMeleeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().durability(RPGMaterialTiers.STEEL.getUses()).rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> OLD_CLEAVER = ITEMS.register("old_cleaver",
            () -> new RPGBasicMeleeWeapon(RPGMaterialTiers.IRON, new Item.Properties().durability(RPGMaterialTiers.IRON.getUses()).rarity(RPGRarity.RPG_CLUTTER)));
    public static final RegistryObject<Item> SHOTEL = ITEMS.register("shotel",
            () -> new RPGBasicMeleeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().durability(RPGMaterialTiers.STEEL.getUses()).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> STEEL_CRESCENT_LARGE = ITEMS.register("steel_crescent_large",
            () -> new RPGBasicTwoHandMeleeWeapon(RPGMaterialTiers.STEEL, new Item.Properties().durability(RPGMaterialTiers.STEEL.getUses()).rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> TWOHAND_WOODEN_MACE = ITEMS.register("twohand_wooden_mace",
            () -> new RPGBasicTwoHandMeleeWeapon(RPGMaterialTiers.WOOD, new Item.Properties().durability(RPGMaterialTiers.WOOD.getUses()).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> IRON_HALBERD = ITEMS.register("iron_halberd",
            () -> new RPGBasicTwoHandMeleeWeapon(RPGMaterialTiers.IRON, new Item.Properties().durability(RPGMaterialTiers.IRON.getUses()).rarity(RPGRarity.RPG_COMMON)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //----------------------------------------------------------------
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Shields

    public static final RegistryObject<Item> LARGE_KNIGHT_SHIELD = ITEMS.register("large_knight_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> TEMPLAR_SHIELD = ITEMS.register("templar_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> BIG_ROUND_WHIRL_GREEN_WHITE_SHIELD = ITEMS.register("big_round_whirl_green_white_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> BLACK_GUARD_TOWER_SHIELD = ITEMS.register("black_guard_tower_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.DARKIRON,new Item.Properties().rarity(RPGRarity.RPG_RARE).durability(RPGMaterialTiers.DARKIRON.getUses())).setShieldType("heavy"));
    public static final RegistryObject<Item> BLACK_SUN_GOTHIC_SHIELD = ITEMS.register("black_sun_gothic_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.STEEL,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> BLACK_SUN_TOWER_SHIELD = ITEMS.register("black_sun_tower_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.STEEL,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setShieldType("heavy"));
    public static final RegistryObject<Item> DWARVEN_SMALL_SHIELD = ITEMS.register("dwarven_small_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.DWARVEN,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.DWARVEN.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> DWARVEN_TOWER_SHIELD = ITEMS.register("dwarven_towershield",
            () -> new RPGBasicShield(RPGMaterialTiers.DWARVEN,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.DWARVEN.getUses())).setShieldType("heavy"));
    public static final RegistryObject<Item> GOTHIC_BLACK_YELLOW_02_SHIELD = ITEMS.register("gothic_black_yellow_02_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_GREEN_WHITE_02_SHIELD = ITEMS.register("gothic_green_white_01_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_RED_02_SHIELD = ITEMS.register("gothic_red_02_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_RED_WHITE_02_SHIELD = ITEMS.register("gothic_red_white_02_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_RED_WHITE_03_SHIELD = ITEMS.register("gothic_red_white_03_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_RED_WHITE_04_SHIELD = ITEMS.register("gothic_red_white_04_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_RED_WHITE_SHIELD = ITEMS.register("gothic_red_white_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_WHITE_BLACK_01_SHIELD = ITEMS.register("gothic_white_black_01_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_WHITE_BLACK_02_SHIELD = ITEMS.register("gothic_white_black_02_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_WHITE_BLACK_03_SHIELD = ITEMS.register("gothic_white_black_03_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_WHITE_BLUE_02_SHIELD = ITEMS.register("gothic_white_blue_02_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_WHITE_BLUE_SHIELD = ITEMS.register("gothic_white_blue_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> GOTHIC_YELLOW_DEER_SHIELD = ITEMS.register("gothic_yellow_deer_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));
    public static final RegistryObject<Item> KITESHIELD_BLACK_01 = ITEMS.register("kiteshield_black_01",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> KITESHIELD_BLACK_WHITE = ITEMS.register("kiteshield_black_white",
            () -> new RPGBasicShield(RPGMaterialTiers.STEEL,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> KITESHIELD_CRUSADER_01 = ITEMS.register("kiteshield_crusader_01",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> KITESHIELD_CRUSADER_02 = ITEMS.register("kiteshield_crusader_02",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> KITESHIELD_GOLD_BLACK = ITEMS.register("kiteshield_gold_black",
            () -> new RPGBasicShield(RPGMaterialTiers.STEEL,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> KITESHIELD_GOLD_BLUE = ITEMS.register("kiteshield_gold_blue",
            () -> new RPGBasicShield(RPGMaterialTiers.STEEL,new Item.Properties().rarity(RPGRarity.RPG_COMMON).durability(RPGMaterialTiers.STEEL.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> KITESHIELD_SILVER_BLUE = ITEMS.register("kiteshield_silver_blue",
            () -> new RPGBasicShield(RPGMaterialTiers.STEEL,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> KITESHIELD_WOOD = ITEMS.register("kiteshield_wood",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> ROUND_SHIELD_BIRD = ITEMS.register("round_shield_bird",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> ROUND_SHIELD_CARVED = ITEMS.register("round_shield_carved",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> ROUND_SHIELD_ORNATE = ITEMS.register("round_shield_ornate",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> ROUND_SHIELD_RUNE = ITEMS.register("round_shield_rune",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> ROUND_SHIELD_TRISKELE = ITEMS.register("round_shield_triskele",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> ROUND_SHIELD_TWISTED = ITEMS.register("round_shield_twisted",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> YELLOW_KITESHIELD = ITEMS.register("yellow_kiteshield",
            () -> new RPGBasicShield(RPGMaterialTiers.WOOD,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.WOOD.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> ASKIR_SCUTUM = ITEMS.register("askir_scutum",
            () -> new RPGBasicShield(RPGMaterialTiers.BRONZE,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.STEEL.getUses())).setShieldType("medium"));
    public static final RegistryObject<Item> SMALL_KNIGHT_SHIELD = ITEMS.register("small_knight_shield",
            () -> new RPGBasicShield(RPGMaterialTiers.IRON,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON).durability(RPGMaterialTiers.IRON.getUses())).setShieldType("small"));

    //----------------------------------------------------------------

    //Armor
    //sets
//ARMOR
    public static final RegistryObject<Item> DWARVEN_FIELDPLATE_BOOTS = ITEMS.register("dwarven_fieldplate_boots",
            () -> new ArmorItem(RPGArmorMaterial.DWARVEN_FIELDPLATE, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> DWARVEN_FIELDPLATE_CHESTPLATE = ITEMS.register("dwarven_fieldplate_chestplate",
            () -> new ArmorItem(RPGArmorMaterial.DWARVEN_FIELDPLATE, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> DWARVEN_FIELDPLATE_LEGGINGS = ITEMS.register("dwarven_fieldplate_leggings",
            () -> new ArmorItem(RPGArmorMaterial.DWARVEN_FIELDPLATE, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> DWARVEN_FIELDPLATE_HELMET = ITEMS.register("dwarven_fieldplate_helmet",
            () -> new ArmorItem(RPGArmorMaterial.DWARVEN_FIELDPLATE, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_RARE)));

    public static final RegistryObject<Item> MARAUDER_BOOTS = ITEMS.register("marauder_boots",
            () -> new ArmorItem(RPGArmorMaterial.MARAUDER, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> MARAUDER_CHESTPLATE = ITEMS.register("marauder_breastplate",
            () -> new ArmorItem(RPGArmorMaterial.MARAUDER, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> MARAUDER_LEGPLATES = ITEMS.register("marauder_legplates",
            () -> new ArmorItem(RPGArmorMaterial.MARAUDER, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> MARAUDER_FULLHELM = ITEMS.register("marauder_fullhelm",
            () -> new ArmorItem(RPGArmorMaterial.MARAUDER, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    //todo brotherhood
    public static final RegistryObject<Item> BROTHERHOOD_BOOTS = ITEMS.register("brotherhood_boots",
            () -> new ArmorItem(RPGArmorMaterial.BROTHERHOOD, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> BROTHERHOOD_CHESTPLATE = ITEMS.register("brotherhood_chestplate",
            () -> new ArmorItem(RPGArmorMaterial.BROTHERHOOD, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> BROTHERHOOD_LEGGINGS = ITEMS.register("brotherhood_leggings",
            () -> new ArmorItem(RPGArmorMaterial.BROTHERHOOD, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> BROTHERHOOD_HELMET = ITEMS.register("brotherhood_helmet",
            () -> new ArmorItem(RPGArmorMaterial.BROTHERHOOD, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    //todo templar
    public static final RegistryObject<Item> TEMPLAR_BOOTS = ITEMS.register("templar_boots",
            () -> new ArmorItem(RPGArmorMaterial.TEMPLAR_CHAINMAIL, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> TEMPLAR_BREASTPLATE = ITEMS.register("templar_breastplate",
            () -> new ArmorItem(RPGArmorMaterial.TEMPLAR_CHAINMAIL, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> TEMPLAR_LEGGINGS = ITEMS.register("templar_leggings",
            () -> new ArmorItem(RPGArmorMaterial.TEMPLAR_CHAINMAIL, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> TEMPLAR_HELMET = ITEMS.register("templar_helmet",
            () -> new ArmorItem(RPGArmorMaterial.TEMPLAR_CHAINMAIL, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));

    public static final RegistryObject<Item> KINGSTONE_BOOTS = ITEMS.register("kingstone_boots",
            () -> new ArmorItem(RPGArmorMaterial.KINGSTONE_CHAINMAIL, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> KINGSTONE_CHESTPLATE = ITEMS.register("kingstone_chestplate",
            () -> new ArmorItem(RPGArmorMaterial.KINGSTONE_CHAINMAIL, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> KINGSTONE_LEGGINGS = ITEMS.register("kingstone_leggings",
            () -> new ArmorItem(RPGArmorMaterial.KINGSTONE_CHAINMAIL, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> KINGSTONE_HELMET_02 = ITEMS.register("kingstone_helmet_02",
            () -> new ArmorItem(RPGArmorMaterial.KINGSTONE_CHAINMAIL, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_COMMON)));

    public static final RegistryObject<Item> BLACKLEGION_BOOTS = ITEMS.register("blacklegion_boots",
            () -> new ArmorItem(RPGArmorMaterial.BLACK_LEGION, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> BLACKLEGION_CHESTPLATE = ITEMS.register("blacklegion_chestplate",
            () -> new ArmorItem(RPGArmorMaterial.BLACK_LEGION, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> BLACKLEGION_LEGGINGS = ITEMS.register("blacklegion_leggings",
            () -> new ArmorItem(RPGArmorMaterial.BLACK_LEGION, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> BLACKLEGION_HELMETS = ITEMS.register("blacklegion_helmet",
            () -> new ArmorItem(RPGArmorMaterial.BLACK_LEGION, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_RARE)));

    //todo stahlrim light
    //todo stahlrim heavy



    //TOWNGUARD-ARMORS
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_BLUE_WHITE_01_BOOTS = ITEMS.register("townguard_chainmail_blue_white_01_boots",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_BLUE_WHITE_01, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_BLUE_WHITE_01_CHESTPLATE = ITEMS.register("townguard_chainmail_blue_white_01_chestplate",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_BLUE_WHITE_01, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_BLUE_WHITE_01_LEGGINGS = ITEMS.register("townguard_chainmail_blue_white_01_leggings",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_BLUE_WHITE_01, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_BLUE_WHITE_01_HELMET = ITEMS.register("townguard_chainmail_blue_white_01_helmet",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_BLUE_WHITE_01, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));

    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_BLACK_WHITE_01_BOOTS = ITEMS.register("townguard_chainmail_black_white_01_boots",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_BLACK_WHITE_01, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_BLACK_WHITE_01_CHESTPLATE = ITEMS.register("townguard_chainmail_black_white_01_chestplate",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_BLACK_WHITE_01, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_BLACK_WHITE_01_LEGGINGS = ITEMS.register("townguard_chainmail_black_white_01_leggings",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_BLACK_WHITE_01, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_BLACK_WHITE_01_HELMET = ITEMS.register("townguard_chainmail_black_white_01_helmet",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_BLACK_WHITE_01, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));

    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_RED_WHITE_01_BOOTS = ITEMS.register("townguard_chainmail_red_white_01_boots",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_RED_WHITE_01, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_RED_WHITE_01_CHESTPLATE = ITEMS.register("townguard_chainmail_red_white_01_chestplate",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_RED_WHITE_01, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_RED_WHITE_01_LEGGINGS = ITEMS.register("townguard_chainmail_red_white_01_leggings",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_RED_WHITE_01, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_RED_WHITE_01_HELMET = ITEMS.register("townguard_chainmail_red_white_01_helmet",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_RED_WHITE_01, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));

    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_YELLOW_WHITE_01_BOOTS = ITEMS.register("townguard_chainmail_yellow_white_01_boots",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_YELLOW_WHITE_01, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_YELLOW_WHITE_01_CHESTPLATE = ITEMS.register("townguard_chainmail_yellow_white_01_chestplate",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_YELLOW_WHITE_01, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_YELLOW_WHITE_01_LEGGINGS = ITEMS.register("townguard_chainmail_yellow_white_01_leggings",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_YELLOW_WHITE_01, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_YELLOW_WHITE_01_HELMET = ITEMS.register("townguard_chainmail_yellow_white_01_helmet",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_YELLOW_WHITE_01, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));

    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_WHITE_BOOTS = ITEMS.register("townguard_chainmail_white_boots",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_WHITE, EquipmentSlot.FEET,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_WHITE_CHESTPLATE = ITEMS.register("townguard_chainmail_white_chestplate",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_WHITE, EquipmentSlot.CHEST,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item>TOWNGUARD_CHAINMAIL_WHITE_LEGGINGS = ITEMS.register("townguard_chainmail_white_leggings",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_WHITE, EquipmentSlot.LEGS,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> TOWNGUARD_CHAINMAIL_WHITE_HELMET = ITEMS.register("townguard_chainmail_white_helmet",
            () -> new ArmorItem(RPGArmorMaterial.TOWNGUARD_CHAINMAIL_WHITE, EquipmentSlot.HEAD,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));




    //ASKIR



    //non sets
    //HEAD
    public static final RegistryObject<Item> ARABIAN_HELMET = ITEMS.register("arabian_helmet",
            () -> new ArabianHelmetArmorItem(RPGArmorTiers.CLOTH, new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> STRAWHAT_HELMET = ITEMS.register("strawhat_helmet",
            () -> new StrawhatHelmetArmorItem(RPGArmorTiers.THATCH,new Item.Properties()));
    public static final RegistryObject<Item> ASKIR_LEGIONNAIRE_CENTURIO_HELMET = ITEMS.register("askir_legionnaire_centurio_helmet",
            () -> new AskirLegionnaireCenturioHelmetArmorItem(RPGArmorTiers.STEEL_PLATE, new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> ASKIR_LEGIONNAIRE_HELMET = ITEMS.register("askir_legionnaire_helmet",
            () -> new AskirLegionnaireHelmetArmorItem(RPGArmorTiers.STEEL_PLATE,new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR).rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> CENTURION_HELMET = ITEMS.register("centurion_helmet",
            () -> new CenturionHelmetArmorItem(RPGArmorTiers.STEEL_PLATE, new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> LEGIONNAIRE_HELMET = ITEMS.register("legionnaire_helmet",
            () -> new LegionnaireHelmetArmorItem(RPGArmorTiers.STEEL_PLATE, new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> PLAGUE_DOCTOR_HELMET = ITEMS.register("plague_doctor_helmet",
            () -> new PlagueDoctorHelmetArmorItem(RPGArmorTiers.CLOTH, new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    public static final RegistryObject<Item> FLOWER_CROWN_HELMET = ITEMS.register("flower_crown_helmet",
            () -> new FlowerCrownHelmetArmorItem(RPGArmorTiers.THATCH, new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));
    //CHEST
    //LEGGINGS
    //BOOTS

    //CAPE
    public static final RegistryObject<Item> BLUE_CAPE_WITH_STARS = ITEMS.register("blue_cape_with_stars",          //TODO FIXING
            () -> new BlueCapeWithStarsItem(IRON, EquipmentSlot.CHEST, SlotTypePreset.BACK,
                    new Item.Properties().tab(RPGCreativeModeTab.RPG_ARMOR)));




    //jewellery
    //amulet

    public static final RegistryObject<Item> ANKH_SILVER = ITEMS.register("ankh_silver",
            () -> new RPGBasicJewelry(RPGMaterialTiers.SILVER, SlotTypePreset.NECKLACE, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> BONE_AMULET = ITEMS.register("bone_amulet",
            () -> new RPGBasicJewelry(RPGMaterialTiers.BONE,  SlotTypePreset.NECKLACE,new Item.Properties().rarity(RPGRarity.RPG_COMMON)));
    public static final RegistryObject<Item> PENDANT_OF_BLOOD_SACRIFICE = ITEMS.register("pendant_of_blood_sacrifice",
            () -> new UniqueNeckPendantOfBloodSacrifice(RPGMaterialTiers.ADAMANTIUM,  new Item.Properties().rarity(RPGRarity.RPG_ARTIFACT)).setFoil(true));
    public static final RegistryObject<Item> EXPENSIVE_AMULET = ITEMS.register("expensive_amulet",
            () -> new RPGBasicJewelry(RPGMaterialTiers.GOLD, SlotTypePreset.NECKLACE, new Item.Properties().rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> PENDANT_OF_MANAFLOW = ITEMS.register("pendant_of_manaflow",
            () -> new RPGBasicJewelry(RPGMaterialTiers.ASTRALSILVER, SlotTypePreset.NECKLACE, new Item.Properties().rarity(RPGRarity.RPG_EPIC)));
    public static final RegistryObject<Item> AMULET_OF_CLARITY = ITEMS.register("amulet_of_clarity",
            () -> new RPGBasicJewelry(RPGMaterialTiers.GOLD, SlotTypePreset.NECKLACE, new Item.Properties().rarity(RPGRarity.RPG_LEGENDARY)));
    //ring
    public static final RegistryObject<Item> AQUAMARINE_GOLD_RING = ITEMS.register("aquamarine_gold_ring",
            () -> new RPGBasicJewelry(RPGMaterialTiers.GOLD,  SlotTypePreset.RING,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> RUBY_GOLD_RING = ITEMS.register("ruby_gold_ring",
            () -> new RPGBasicJewelry(RPGMaterialTiers.GOLD,  SlotTypePreset.RING,new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON)));
    public static final RegistryObject<Item> BAND_OF_THORNS = ITEMS.register("band_of_thorns",
            () -> new RPGBasicJewelry(RPGMaterialTiers.BONE,  SlotTypePreset.RING,new Item.Properties().rarity(RPGRarity.RPG_EPIC)));
    public static final RegistryObject<Item> COPPER_RIG = ITEMS.register("copper_ring",
            () -> new RPGBasicJewelry(RPGMaterialTiers.COPPER,  SlotTypePreset.RING,new Item.Properties().rarity(RPGRarity.RPG_COMMON)));



    //belt

    //arm
    public static final RegistryObject<Item> BRONZE_BRACELET = ITEMS.register("bronze_bracelet",
            () -> new RPGBasicJewelry(RPGMaterialTiers.COPPER,  SlotTypePreset.RING,new Item.Properties().rarity(RPGRarity.RPG_COMMON)));
    //back
    public static final RegistryObject<Item> BRONZE_QUIVER = ITEMS.register("bronze_quiver",
            () -> new RPGBasicJewelry(RPGMaterialTiers.BRONZE, SlotTypePreset.BACK, new Item.Properties().rarity(RPGRarity.RPG_UNCOMMON)));
    //charm
    public static final RegistryObject<Item> BOOK_OF_DOOM = ITEMS.register("book_of_doom",
            () -> new BookOfDoom(RPGMaterialTiers.CLUTTER, EquipmentSlot.OFFHAND, new Item.Properties().rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> TOME_OF_DEFENSE = ITEMS.register("tome_of_defense",
            () -> new TomeOfDefense(RPGMaterialTiers.CLUTTER,EquipmentSlot.OFFHAND, new Item.Properties().rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> TOME_OF_MANA = ITEMS.register("tome_of_mana",
            () -> new TomeOfManaflow(RPGMaterialTiers.CLUTTER, EquipmentSlot.OFFHAND, new Item.Properties().rarity(RPGRarity.RPG_RARE)));
    public static final RegistryObject<Item> DARK_RITUAL = ITEMS.register("dark_ritual",
            () -> new RPGBookItem(RPGMaterialTiers.CLUTTER, EquipmentSlot.OFFHAND, new Item.Properties().rarity(RPGRarity.RPG_RARE)));




















    //TEST
    public static final RegistryObject<Item> WATER_BOTTLE = ITEMS.register("water_bottle",
            () -> new Item(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).food(RPGFoodItem.BASIC_FOOD)));
    public static final RegistryObject<Item> MANA_FLASK = ITEMS.register("mana_flask",
            () -> new RPGManaPotionItem(new Item.Properties().tab(RPGCreativeModeTab.RPG_MISC).food(RPGFoodItem.MANA), 10));
    public static final RegistryObject<Item> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS)));
    public static final RegistryObject<Item> RUBY_SHOVEL = ITEMS.register("ruby_shovel",
            () -> new ShovelItem(Tiers.DIAMOND, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS)));
    public static final RegistryObject<Item> RUBY_HOE = ITEMS.register("ruby_hoe",
            () -> new HoeItem(Tiers.DIAMOND, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS)));
    public static final RegistryObject<Item> RUBY_AXE = ITEMS.register("ruby_axe",
            () -> new AxeItem(Tiers.DIAMOND, 1,1,new Item.Properties().tab(RPGCreativeModeTab.RPG_TOOLS)));

    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
