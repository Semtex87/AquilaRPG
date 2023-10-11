package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EncumberedEffect extends MobEffect {


    public EncumberedEffect() {
        super(MobEffectCategory.HARMFUL, 5926017);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "9a3df641-47be-4a04-8bf0-003df570a033", -0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
