package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SharpenEffect extends MobEffect {
    public SharpenEffect() {
        super(null, 0);

    }

    @Override
    public void applyEffectTick (LivingEntity entity, int color){
        super.applyEffectTick(entity, color);
    }
    public @NotNull String getDescriptionId() {
        return "sharpen_effect";
    }

    @Override
    public boolean isDurationEffectTick ( int p_19455_, int p_19456_){
        return true;
    }

}
