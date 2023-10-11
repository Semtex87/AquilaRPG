package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class ReadyToFightEffect extends MobEffect {


    public ReadyToFightEffect() {
        super(MobEffectCategory.BENEFICIAL, 5900017);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, "9a3df641-47be-4a04-8bf0-003df570a033", +2.0F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ARMOR, "9a3df641-47be-4a04-8bf0-003df570a033", +1.0F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    @Override
    public @NotNull String getDescriptionId() {
        return "call to arms";
    }
    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
