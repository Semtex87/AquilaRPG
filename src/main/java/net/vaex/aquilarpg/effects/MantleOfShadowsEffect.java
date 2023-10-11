package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class MantleOfShadowsEffect extends MobEffect {
    @Override
    public @NotNull String getDescriptionId() {
        return "Mantle of Shadows";
    }
    protected MantleOfShadowsEffect() {
        super(MobEffectCategory.BENEFICIAL, 5900017);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "54c5432c-5141-457a-9ff6-c71f3ae0cea5", +2.0D,AttributeModifier.Operation.ADDITION);
        addAttributeModifier(Attributes.ATTACK_SPEED, "06634ce7-ee3e-4d08-b8d9-3909385bb70f", +2.0D,AttributeModifier.Operation.ADDITION);
    }
}
