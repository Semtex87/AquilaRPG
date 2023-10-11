package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.vaex.aquilarpg.enchantment.RPGEnchantments;
import net.vaex.aquilarpg.util.RPGEnchantmentUtil;


import static net.vaex.aquilarpg.effects.RPGEffectManager.BLESSED;

public class BlessedEffect extends MobEffect {

    protected BlessedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            if (RPGEnchantmentUtil.hasEnchant(player, RPGEnchantments.BLESSED.get())) {
                LivingEntity entity = event.getEntityLiving();
                entity.isOnFire();
                entity.setSecondsOnFire(10);
            }
        }
    }
}
