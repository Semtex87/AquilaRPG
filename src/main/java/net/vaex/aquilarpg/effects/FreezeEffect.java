package net.vaex.aquilarpg.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class FreezeEffect extends MobEffect {
    public boolean isInPowderSnow;
    public FreezeEffect(MobEffectCategory category, Color color) {
        super(category, color.getBlue());
    }
    @Override
    public @NotNull String getDescriptionId() {
        return "Freeze";
    }
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        isInPowderSnow = true;
        if (!pLivingEntity.level.isClientSide()) {
            Double x = pLivingEntity.getX();
            Double y = pLivingEntity.getY();
            Double z = pLivingEntity.getZ();
            pLivingEntity.teleportTo(x, y, z);
            pLivingEntity.setDeltaMovement(0, 0, 0);
            pLivingEntity.isFreezing();
            pLivingEntity.setIsInPowderSnow(isInPowderSnow);
            pLivingEntity.hurt(DamageSource.FREEZE, 1.0F);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }







}
