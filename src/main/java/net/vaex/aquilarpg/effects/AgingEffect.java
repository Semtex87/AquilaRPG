package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class AgingEffect extends MobEffect {
    int manaBonus= 2;
    int ticks;
    protected AgingEffect(MobEffectCategory category, int color) {
        super(category,color);
    }


    @Override
    public void applyEffectTick(@NotNull LivingEntity pEntity, int pColor) {
        super.applyEffectTick(pEntity, pColor);
    }


    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            if (player.hasEffect(RPGEffectManager.LIFELEECH.get())) {
                LivingEntity entity = event.getEntityLiving();
                if(entity instanceof AgeableMob livingHitEntity) {
                    livingHitEntity.ageUp(2000);
                }
            }
        }
    }
    public @NotNull String getDescriptionId() {
        return "aging";
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
