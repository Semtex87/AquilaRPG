package net.vaex.aquilarpg.enchantment;



import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.vaex.aquilarpg.util.RPGEnchantmentUtil;
import org.jetbrains.annotations.NotNull;

import static net.vaex.aquilarpg.enchantment.RPGEnchantments.PARALYSIS;


public class EnchantmentParalysis extends Enchantment {
    public EnchantmentParalysis(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public  void doPostHurt(@NotNull LivingEntity player, @NotNull Entity target, int level) {
            if (RPGEnchantmentUtil.hasEnchant(player, PARALYSIS.get())) {
                ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 5));
                ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 5));
                ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.JUMP, 200, -5));
            }
    }
}

