package net.vaex.aquilarpg.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.vaex.aquilarpg.AquilaRPG;

public class RPGTags {

    public static class Blocks {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // blocktags here
        //public static final Tag.Named<Block> CROP_BLOCKS = (Tag.Named<Block>) tag("crop_blocks");


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(AquilaRPG.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //itemtags here
        //meltables, uncrafting etc
        public static final TagKey<Item>SALVAGE_IRON_ITEMS_ONE_INGOT = tag("salvage_iron_items_one_ingot");//todo fill Tag.json <>
        public static final TagKey<Item>SALVAGE_IRON_ITEMS_TWO_INGOT = tag("salvage_iron_items_two_ingots");//todo fill Tag.json <>
        public static final TagKey<Item>IRPG_BONE_ITEMS_RESULT_TWO_BONE_MEAL = tag("irpg_bone_items_result_two");//done
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //tool
        //hammer
        public static final TagKey<Item> RPG_TOOL_HAMMER = tag("rpg_hammer_tool");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //weapon
        //public static final TagKey<Item> TWOHAND_WEAPON = tag("weapon_twohand");//todo fill Tag.json <>
        //public static final TagKey<Item> JOUSTING_WEAPON = tag("weapon_jousting");//todo fill Tag.json <>
        //public static final TagKey<Item> AQUILA_WEAPONS = tag("aquila_weapons");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //shield
        //public static final TagKey<Item> SHIELD_LIST = tag("shield_list");//todo fill Tag.json <>

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //armor
        public static final TagKey<Item> RPG_FULLHELM_HIDE_HAT = tag("rpg_fullhelm_hide_hat");
        //public static final TagKey<Item> ARMOR_LIGHT = tag("armor_light_list");//todo fill Tag.json <>
        //public static final TagKey<Item> ARMOR_MEDIUM = tag("armor_medium_list");//todo fill Tag.json <>
        //public static final TagKey<Item> ARMOR_HEAVY = tag("armor_heavy_list");//todo fill Tag.json <>
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //food
        //public static final TagKey<Item> DRINKS = tag("drinkables");//todo fill Tag.json <>
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //melting

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //alloying
       // public static final TagKey<Item> ASTRALSILVER = tag("astralsilver");//todo fill Tag.json <>
        //public static final TagKey<Item> BRASS = tag("brass");//todo fill Tag.json <>
        //public static final TagKey<Item> BRONZE = tag("bronze");//todo fill Tag.json <>
        //public static final TagKey<Item> CRIMSON_STEEL = tag("crimson_steel");//todo fill Tag.json <>
        //public static final TagKey<Item> DWARVEN_DARKSTEEL = tag("dwarven_darksteel");//todo fill Tag.json <>
        //public static final TagKey<Item> ELVEN_STEEL = tag("elven_steel");//todo fill Tag.json <>
        //public static final TagKey<Item> ORICALCUM = tag("oricalcum");//todo fill Tag.json <>
        //public static final TagKey<Item> VOID_STEEL = tag("void_steel");//todo fill Tag.json <>
        //public static final TagKey<Item> STEEL = tag("steel");//todo fill Tag.json <>
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //dummy

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //jewellery

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //edibles
        public static final TagKey<Item> RPG_FLOUR_ITEMS = tag("rpg_flour_items");//todo fill Tag.json <>
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //ingredient
        //public static final TagKey<Item> INGREDIENTS = tag("ingredients");//todo fill Tag.json <>
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //ammo
        //public static final TagKey<Item> IRPG_RANGE_AMMO = tag("irpg_range_ammo"); //todo fill Tag.json <>
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //valid upgrades
        //public static final TagKey<Item> VALID_TWO_HAND_ENCHANTS = tag("valid_two_hand_enchants"); //todo set Tag <>
        //public static final TagKey<Item> VALID_ONE_HAND_ENCHANTS = tag("valid_two_hand_enchants"); //todo set Tag <>
        //public static final TagKey<Item> VALID_TWO_HAND_UPGRADES = tag("valid_two_hand_upgrades"); //todo set Tag <>
        //public static final TagKey<Item> VALID_ONE_HAND_UPGRADES = tag("valid_two_hand_upgrades"); //todo set Tag <>
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //public static final TagKey<Item> ITEM_UNIQUE = tag("item_unique");//todo fill Tag.json <>




        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(AquilaRPG.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }


    }
}
