package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class UnholyStrengthEffect extends MobEffect {

    public UnholyStrengthEffect(MobEffectCategory neutral, int rgb) {
        super(neutral, rgb);
    }

    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {
            addAttributeModifier(Attributes.ATTACK_DAMAGE, "9a3df641-47be-4a04-8bf0-003df570a033", 8.0F, AttributeModifier.Operation.ADDITION);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);

    }
}
