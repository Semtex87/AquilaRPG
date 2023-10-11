package net.vaex.aquilarpg.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SanguineRose extends MobEffect {

    public SanguineRose() {
        super(MobEffectCategory.NEUTRAL, 6666666);
    }
    @Override
    public void applyEffectTick (LivingEntity entity,int color){
        entity.hurt(DamageSource.MAGIC, entity.getMaxHealth());
        super.applyEffectTick(entity, color);
    }
    public @NotNull String getDescriptionId() {
        return "instant_death";
    }
    @Override
    public boolean isDurationEffectTick ( int p_19455_, int p_19456_){
        return true;
    }

}
