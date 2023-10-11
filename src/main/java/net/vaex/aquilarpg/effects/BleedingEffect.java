package net.vaex.aquilarpg.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class BleedingEffect extends MobEffect {

    protected BleedingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public @NotNull String getDescriptionId() {
        return "Bleed";
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int color) {
        entity.hurt(DamageSource.GENERIC, 1.0F);
        super.applyEffectTick(entity, color);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
