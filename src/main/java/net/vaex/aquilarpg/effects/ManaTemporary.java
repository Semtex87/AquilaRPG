package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.jetbrains.annotations.NotNull;

public class ManaTemporary extends MobEffect {

    protected ManaTemporary(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public @NotNull String getDescriptionId() {
        return "manatemp";
    }



    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
