package net.vaex.aquilarpg.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID)
public class LacerationEffect extends MobEffect {
    @Override
    public @NotNull String getDescriptionId() {
        return "laceration";
    }
    public LacerationEffect() {
        super(MobEffectCategory.HARMFUL, 5926017);
        addAttributeModifier(Attributes.MAX_HEALTH, "690998fd-0af4-4031-b54e-2fb9a89b2597", -3.0D, AttributeModifier.Operation.ADDITION);
    }
}




