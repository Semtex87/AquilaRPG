package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.awt.*;

public class SunburnEffect extends MobEffect {

    protected SunburnEffect(MobEffectCategory pCategory, Color pColor) {
        super(pCategory,pColor.getRed());
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {
            pLivingEntity.isOnFire();
            pLivingEntity.setSecondsOnFire(20);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
