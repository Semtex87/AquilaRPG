package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HealIncreaseEffect extends MobEffect {
    public HealIncreaseEffect(MobEffectCategory neutral, int rgb) {
        super(MobEffectCategory.HARMFUL, 5926017);
    }
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {
            addAttributeModifier(Attributes.MAX_HEALTH, "9a3df641-47be-4a04-8bf0-003df570a033", 4.0F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);

    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }
}
