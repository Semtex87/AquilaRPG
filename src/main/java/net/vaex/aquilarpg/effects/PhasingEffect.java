package net.vaex.aquilarpg.effects;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PhasingEffect extends MobEffect {

    protected PhasingEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pEntity, int pColor) {
        if (!pEntity.getLevel().isClientSide()) {
            if (Math.random() < 0.08) {
                Vec3 pos = pEntity.position();
                double randomX = (Math.random() - 0.5) * 2 * 5;
                double randomY = (Math.random() - 0.5) * 2 * 5;
                double randomZ = (Math.random() - 0.5) * 2 * 5;
                pEntity.randomTeleport(pos.x + randomX, pos.y + randomY, pos.z + randomZ, true);
                pEntity.level.playSound(null, pEntity.blockPosition(),SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1, 1);
                }
        }
        super.applyEffectTick(pEntity, pColor);

    }
    public @NotNull String getDescriptionId() {
        return "phasing";
    }
    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }

}
