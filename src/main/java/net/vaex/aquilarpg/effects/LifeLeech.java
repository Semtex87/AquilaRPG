package net.vaex.aquilarpg.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import org.jetbrains.annotations.NotNull;


@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID)
public class LifeLeech extends MobEffect {

    public LifeLeech() {
        super(MobEffectCategory.NEUTRAL, 5456737);
    }
    @Override
    public @NotNull String getDescriptionId() {
        return "lifeleech";
    }

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            if (player.hasEffect(RPGEffectManager.LIFELEECH.get())) {
                LivingEntity entity = event.getEntityLiving();
                float damage = event.getAmount();
                entity.hurt(DamageSource.MAGIC, damage * 1.5f);
                player.heal(damage / 2);
            }
        }
    }
}

