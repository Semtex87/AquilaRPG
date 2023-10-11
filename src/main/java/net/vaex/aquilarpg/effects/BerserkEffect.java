package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BerserkEffect extends MobEffect {
    public BerserkEffect() {
        super(MobEffectCategory.HARMFUL, 5926017);
    }
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,200,0));
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,0));
            addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "9a3df641-47be-4a04-8bf0-003df570a033", 5.0F, AttributeModifier.Operation.ADDITION);
            addAttributeModifier(Attributes.MAX_HEALTH, "9a3df641-47be-4a04-8bf0-003df570a033", 8.0F, AttributeModifier.Operation.ADDITION);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }
    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
