package net.vaex.aquilarpg.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.item.custom.weapon.RPGLanceWeapon;
import net.vaex.aquilarpg.item.custom.weapon.RPGFlailWeapon;
import net.vaex.aquilarpg.item.custom.weapon.unique.UniqueSwordBipolarblade;
import net.vaex.aquilarpg.item.custom.weapon.unique.UniqueSwordDuskfangDawnfang;


public class RPGItemProperties {

    public static void addCustomItemProperties() {
        //bow
        makeBow(RPGItems.WOOD_BOW.get());
        makeBow(RPGItems.GLASS_BOW.get());
        makeBow(RPGItems.BONE_LONGBOW.get());
        makeBow(RPGItems.CRIMSON_BOW.get());
        makeBow(RPGItems.DAEDRIC_BOW.get());
        makeBow(RPGItems.DEMONIC_EMBRACE.get());
        makeBow(RPGItems.NORDIC_BOW.get());
        makeBow(RPGItems.SCORPION_STING.get());
        makeBow(RPGItems.SILVER_BOW.get());
        makeBow(RPGItems.STEEL_LONG_WARBOW.get());
        makeBow(RPGItems.WINDFORCE.get());
        makeBow(RPGItems.STAHLRIM_BOW.get());
        //crossbow
        makeCrossbow(RPGItems.GLASS_CROSSBOW.get());
        makeCrossbow(RPGItems.CRIMSON_CROSSBOW.get());
        makeCrossbow(RPGItems.STAHLRIM_CROSSBOW.get());
        makeCrossbow(RPGItems.DAEDRIC_CROSSBOW.get());

        //Magic
        makeBow(RPGItems.FIRESTAFF.get()); //itsa bow lol
        makeBow(RPGItems.APOSTEL_STAFF.get());



        //spear
        makeSpear(RPGItems.STAHLRIM_SPEAR.get());
        makeSpear(RPGItems.STEEL_SPEAR.get());
        makeSpear(RPGItems.IRON_SPEAR.get());
        //shield
        
        makeShield(RPGItems.LARGE_KNIGHT_SHIELD.get());
        makeShield(RPGItems.TEMPLAR_SHIELD.get());
        makeShield(RPGItems.ASKIR_SCUTUM.get());
        makeShield(RPGItems.BIG_ROUND_WHIRL_GREEN_WHITE_SHIELD.get());
        makeShield(RPGItems.BLACK_GUARD_TOWER_SHIELD.get());
        makeShield(RPGItems.BLACK_SUN_GOTHIC_SHIELD.get());
        makeShield(RPGItems.BLACK_SUN_TOWER_SHIELD.get());
        makeShield(RPGItems.DWARVEN_SMALL_SHIELD.get());
        makeShield(RPGItems.DWARVEN_TOWER_SHIELD.get());
        makeShield(RPGItems.GOTHIC_BLACK_YELLOW_02_SHIELD.get());
        makeShield(RPGItems.GOTHIC_GREEN_WHITE_02_SHIELD.get());
        makeShield(RPGItems.GOTHIC_RED_02_SHIELD.get());
        makeShield(RPGItems.GOTHIC_RED_WHITE_SHIELD.get());
        makeShield(RPGItems.GOTHIC_RED_WHITE_02_SHIELD.get());
        makeShield(RPGItems.GOTHIC_RED_WHITE_03_SHIELD.get());
        makeShield(RPGItems.GOTHIC_RED_WHITE_04_SHIELD.get());
        makeShield(RPGItems.GOTHIC_WHITE_BLACK_01_SHIELD.get());
        makeShield(RPGItems.GOTHIC_WHITE_BLACK_02_SHIELD.get());
        makeShield(RPGItems.GOTHIC_WHITE_BLACK_03_SHIELD.get());
        makeShield(RPGItems.GOTHIC_WHITE_BLUE_SHIELD.get());
        makeShield(RPGItems.GOTHIC_WHITE_BLUE_02_SHIELD.get());
        makeShield(RPGItems.GOTHIC_YELLOW_DEER_SHIELD.get());
        makeShield(RPGItems.KITESHIELD_BLACK_01.get());
        makeShield(RPGItems.KITESHIELD_BLACK_WHITE.get());
        makeShield(RPGItems.KITESHIELD_CRUSADER_01.get());
        makeShield(RPGItems.KITESHIELD_CRUSADER_02.get());
        makeShield(RPGItems.KITESHIELD_GOLD_BLACK.get());
        makeShield(RPGItems.KITESHIELD_SILVER_BLUE.get());
        makeShield(RPGItems.KITESHIELD_GOLD_BLUE.get());
        makeShield(RPGItems.YELLOW_KITESHIELD.get());
        makeShield(RPGItems.TEMPLAR_SHIELD.get());
        makeShield(RPGItems.ROUND_SHIELD_BIRD.get());
        makeShield(RPGItems.ROUND_SHIELD_CARVED.get());
        makeShield(RPGItems.ROUND_SHIELD_ORNATE.get());
        makeShield(RPGItems.ROUND_SHIELD_RUNE.get());
        makeShield(RPGItems.ROUND_SHIELD_TRISKELE.get());
        makeShield(RPGItems.ROUND_SHIELD_TWISTED.get());
        makeShield(RPGItems.SMALL_KNIGHT_SHIELD.get());

        //blockweapon
        makeBlockWeapon(RPGItems.BRONZE_CLAYMORE.get());
        makeBlockWeapon(RPGItems.BLACKGUARD_CLAYMORE.get());
        makeBlockWeapon(RPGItems.GLASS_CLAYMORE.get());
        makeBlockWeapon(RPGItems.GLASS_GREATSWORD.get());


        makeBlockWeapon(RPGItems.CHRYSAMERE.get());
        makeBlockWeapon(RPGItems.DRAGONBONE_GREATSWORD.get());
        makeBlockWeapon(RPGItems.EBONY_GREATSWORD.get());
        makeBlockWeapon(RPGItems.FIRE_SWORD.get());
        makeBlockWeapon(RPGItems.GLAMDRING.get());
        makeBlockWeapon(RPGItems.HAMBURGER_RICHTSCHWERT.get());
        makeBlockWeapon(RPGItems.ICEBLADEOFTHEMONARCH.get());
        makeBlockWeapon(RPGItems.ICH_RICHTE_RICHTSCHWERT.get());
        makeBlockWeapon(RPGItems.HAMBURGER_RICHTSCHWERT.get());
        makeBlockWeapon(RPGItems.IRON_GREATSWORD.get());
        makeBlockWeapon(RPGItems.MARAUDER_GREATSWORD.get());
        makeBlockWeapon(RPGItems.NARSIL_REFORGED.get());
        makeBlockWeapon(RPGItems.NORDIC_TWOHANDER.get());
        makeBlockWeapon(RPGItems.OBSIDIAN_GREATSWORD.get());
        makeBlockWeapon(RPGItems.ONEMANSLAND_STEEL_SWORD.get());
        makeBlockWeapon(RPGItems.STAHLRIM_CLAYMORE.get());
        makeBlockWeapon(RPGItems.STAHLRIM_GREATSWORD.get());
        makeBlockWeapon(RPGItems.STAHLRIM_CLAYMORE.get());
        makeBlockWeapon(RPGItems.STEEL_GREAT_TACHI.get());
        makeBlockWeapon(RPGItems.STEEL_GREATSWORD.get());
        makeBlockWeapon(RPGItems.STREICHERS_SWORD.get());
        makeBlockWeapon(RPGItems.STEEL_CLAYMORE.get());
        makeBlockWeapon(RPGItems.URUK_HAI_HEADHUNTER.get());

        makeRaisedLance(RPGItems.JOUSTING_LANCE_BLACK.get());
        makeRaisedLance(RPGItems.JOUSTING_LANCE_BLUE.get());
        makeRaisedLance(RPGItems.JOUSTING_LANCE_GREEN.get());
        makeRaisedLance(RPGItems.JOUSTING_LANCE_RED.get());
        makeRaisedLance(RPGItems.JOUSTING_LANCE_YELLOW.get());
        makeRaisedLance(RPGItems.KRUSH_IRMAK.get());


        //texturechanging weapon
        makeSting(RPGItems.STING.get());
        makeDuskfangDawnfang(RPGItems.DUSK_FANG.get());
        makeBipolarBlade(RPGItems.BIPOLARBLADE.get());
        makeFlail(RPGItems.STEEL_FLAIL.get());

        //List<RegistryObject<Item>> shields = RPGItems.RPGItems.getEntries().stream()
        // .filter(shield -> shield.get() instanceof IRPGBasicShield).toList();
        //  shields.forEach(shield -> makeShield(shield.get()));
    }

    public static void makeSting(Item item) {
        ItemProperties.register(item, new ResourceLocation("changing"), (stack, level, living, id) ->
        {
            if (living == null) {
                return 0.0F;
            } else {
                return living.hasItemInSlot(EquipmentSlot.MAINHAND) && living.hasEffect(MobEffects.GLOWING)? 1.0F : 0.0F;
            }
        });
    }

    public static void makeFlail(Item item) {
        ItemProperties.register(item, new ResourceLocation("swing"), (stack, level, living, id) -> {
            if (living == null) {
                return 0.0F;
            } else {
                return RPGFlailWeapon.isSwinging(stack) ? 1.0F : 0.0F;

            }
        });
    }

    public static void makeRaisedLance(Item item)
    {
        ItemProperties.register(item, new ResourceLocation("raised"), (stack, level, living, id) ->
        {
            if (living == null) {
                return 0.0F;
            } else {
                return RPGLanceWeapon.isRaised(stack) ? 1.0F : 0.0F;
            }
        });

    }


    public static void makeDuskfangDawnfang(Item item) {
        ItemProperties.register(item, new ResourceLocation("changing"), (stack, level, living, id) ->
        {
            if (living == null) {
                return 0.0F;
            } else {
                return UniqueSwordDuskfangDawnfang.isNight(stack)? 1.0F : 0.0F;
            }

        });
    }

    public static void makeBipolarBlade(Item item)
    {
        ItemProperties.register(item, new ResourceLocation("night"), (stack, level, living, id) ->
                UniqueSwordBipolarblade.isNight(stack) ? 1.0F : 0.0F);
        ItemProperties.register(item, new ResourceLocation("day"), (stack, level, living, id) ->
                UniqueSwordBipolarblade.isDay(stack) ? 1.0F : 0.0F);

        ItemProperties.register(item, new ResourceLocation("blocking"), (p_174590_, p_174591_, p_174592_, p_174593_) -> {
            return p_174592_ != null && p_174592_.isUsingItem() && p_174592_.getUseItem() == p_174590_ ? 1.0F : 0.0F;
        });
    }


    public static void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() -
                        p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }
    public static void makeCrossbow(Item item)
    {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174620_, p_174621_, p_174622_, p_174623_) -> {
            if (p_174622_ == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(p_174620_) ? 0.0F : (float)(p_174620_.getUseDuration() - p_174622_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_174620_);
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174615_, p_174616_, p_174617_, p_174618_) -> {
            return p_174617_ != null && p_174617_.isUsingItem() && p_174617_.getUseItem() == p_174615_ && !CrossbowItem.isCharged(p_174615_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("charged"), (p_174610_, p_174611_, p_174612_, p_174613_) -> {
            return p_174612_ != null && CrossbowItem.isCharged(p_174610_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("firework"), (p_174605_, p_174606_, p_174607_, p_174608_) -> {
            return p_174607_ != null && CrossbowItem.isCharged(p_174605_) && CrossbowItem.containsChargedProjectile(p_174605_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }
    public static void makeShield(Item item)
    {
        ItemProperties.register(item, new ResourceLocation("blocking"), (p_174590_, p_174591_, p_174592_, p_174593_) -> {
            return p_174592_ != null && p_174592_.isUsingItem() && p_174592_.getUseItem() == p_174590_ ? 1.0F : 0.0F;
        });
    }
    public static void makeBlockWeapon(Item item)
    {
        ItemProperties.register(item, new ResourceLocation("blocking"), (stack, level, living, id) -> {
            return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }
    public static void makeSpear(Item item)
    {
        ItemProperties.register(item, new ResourceLocation("throwing"), (p_174590_, p_174591_, p_174592_, p_174593_) -> {
            return p_174592_ != null && p_174592_.isUsingItem() && p_174592_.getUseItem() == p_174590_ ? 1.0F : 0.0F;
        });
    }



    //todo testing stuff here


    public static void makeParryWeapon(Item item)
    {
        ItemProperties.register(item, new ResourceLocation("parry"), (p_174590_, p_174591_, p_174592_, p_174593_) -> {
            return p_174592_ != null && p_174592_.isUsingItem() && p_174592_.getUseItem() == p_174590_ ? 1.0F : 0.0F;
        });
    }

    public static void makeStabWeapon(Item item){
        ItemProperties.register(item, new ResourceLocation("stab"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() -
                        p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("stabbing"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });

    }
    public static void register(){}

}





