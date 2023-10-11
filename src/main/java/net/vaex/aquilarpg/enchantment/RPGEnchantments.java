package net.vaex.aquilarpg.enchantment;


import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;

public class RPGEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, AquilaRPG.MOD_ID);
    //enchantments here
    public static RegistryObject<Enchantment> REFLECT = ENCHANTMENTS.register("reflect",
            () -> new EnchantmentReflect(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> LIFELEECH = ENCHANTMENTS.register("lifeleech",
            () -> new EnchantmentLifeLeech(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> PARALYSIS = ENCHANTMENTS.register("paralysis",
            () -> new EnchantmentParalysis(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> BLESSED = ENCHANTMENTS.register("blessed",
            () -> new EnchantmentBlessed(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));


    public static RegistryObject<Enchantment> REINFORCED = ENCHANTMENTS.register("reinforced",
            () -> new EnchantmentBlessed(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEARABLE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.CHEST));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}

