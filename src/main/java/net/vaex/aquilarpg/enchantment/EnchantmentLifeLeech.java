package net.vaex.aquilarpg.enchantment;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.util.RPGEnchantmentUtil;

import static net.vaex.aquilarpg.enchantment.RPGEnchantments.LIFELEECH;


@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID)
public class EnchantmentLifeLeech extends Enchantment {
    public EnchantmentLifeLeech(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
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


    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            if (RPGEnchantmentUtil.hasEnchant(player, LIFELEECH.get())) {
                LivingEntity entity = event.getEntityLiving();
                float damage = event.getAmount();
                entity.hurt(DamageSource.MAGIC, damage * 1.5f);
                player.heal(damage);
            }
        }
    }
}

