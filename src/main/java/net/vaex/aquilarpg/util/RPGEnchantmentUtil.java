package net.vaex.aquilarpg.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class RPGEnchantmentUtil {
    public static boolean hasEnchant(ItemStack stack, Enchantment enchantment){
        return enchantment != null && EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) > 0;
    }

    public static boolean hasEnchant(LivingEntity entity, Enchantment enchantment){
        return enchantment != null && EnchantmentHelper.getEnchantmentLevel(enchantment,entity) > 0;
    }
}
