package net.vaex.aquilarpg.util;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;

public class RPGAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, AquilaRPG.MOD_ID);
    //ATTRIBUTES
    //BASIC
    public static final RegistryObject<Attribute> STRENGTH = ATTRIBUTES.register("strength", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_Strength", 0.0D, 0.0D, 1024d));
    public static final RegistryObject<Attribute> DEXTERITY = ATTRIBUTES.register("dexterity", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_Dexterity", 0.0D, 0.0D, 1024d));
    public static final RegistryObject<Attribute> VITALITY = ATTRIBUTES.register("vitality", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_Vitality", 0.0D, 0.0D, 1024d));
    public static final RegistryObject<Attribute> WISDOM = ATTRIBUTES.register("wisdom", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_Wisdom", 0.0D, 0.0D, 1024d));
    public static final RegistryObject<Attribute> STAMINA = ATTRIBUTES.register("stamina", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_Stamina", 20.0D, 0.0D, 1024d).setSyncable(true));
    public static final RegistryObject<Attribute> MANA = ATTRIBUTES.register("mana", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_Mana", 20.0D, 0.5d, 1024d).setSyncable(true));
    public static final RegistryObject<Attribute> WEIGHT = ATTRIBUTES.register("weight", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_Weight", 0.0D, 0.0d, 1024d));
    public static final RegistryObject<Attribute> MAX_HEALTH = ATTRIBUTES.register("max_health", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_Max_Health", 0.0D, 0.0d, 1024d));

    //MELEE & ATTACK
    public static final RegistryObject<Attribute> ARMOR_PIERCING = ATTRIBUTES.register("armor_piercing", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_armor_piercing", 0.0D, 0.0d, 1024d));
    public static final RegistryObject<Attribute> ARMOR_PENETRATION = ATTRIBUTES.register("armor_penetration", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_armor_penetration", 0.0D, 0.0d, 1024d));
    public static final RegistryObject<Attribute> SHIELD_PENETRATION = ATTRIBUTES.register("shield_penetration", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_shield_penetration", 0.0D, 0.0d, 1024d));

    //DEFENSE
    public static final RegistryObject<Attribute> DENSITY = ATTRIBUTES.register("density", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_armor_density", 0.0D, 0.0d, 1024d));




    //RESISTS
    public static final RegistryObject<Attribute> RESIST_ALL = ATTRIBUTES.register("resist_all", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_resist_all", 0.0D, 0.0D, 1024d).setSyncable(true));
    public static final RegistryObject<Attribute> ELEMENTAL_PYRO_RESIST = ATTRIBUTES.register("resist_pyro", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_resist_pyro", 0.0D, 0.0D, 1024d).setSyncable(true));
    public static final RegistryObject<Attribute> ELEMENTAL_AERO_RESIST = ATTRIBUTES.register("resist_aero", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_resist_aero", 0.0D, 0.0D, 1024d).setSyncable(true));
    public static final RegistryObject<Attribute> ELEMENTAL_TERRA_RESIST = ATTRIBUTES.register("resist_terra", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_resist_terra", 0.0D, 0.0D, 1024d).setSyncable(true));
    public static final RegistryObject<Attribute> ELEMENTAL_AQUA_RESIST = ATTRIBUTES.register("resist_aqua", () -> new RangedAttribute(AquilaRPG.MOD_ID + ".player_resist_aqua", 0.0D, 0.0D, 1024d).setSyncable(true));


    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

}
