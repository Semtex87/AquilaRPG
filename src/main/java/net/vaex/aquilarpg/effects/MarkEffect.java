package net.vaex.aquilarpg.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.vaex.aquilarpg.AquilaRPG;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class MarkEffect extends MobEffect {
    public MarkEffect(MobEffectCategory category, Color color) {
        super(category, color.getRed());
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "Mark";
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
            BlockPos playerPos = pLivingEntity.blockPosition();
            String pos = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")";
            pLivingEntity.getPersistentData().putIntArray(AquilaRPG.MOD_ID + "homepos",
                    new int[]{playerPos.getX(), playerPos.getY(), playerPos.getZ()});

            pLivingEntity.sendMessage(new TextComponent("Set Home at " + pos), pLivingEntity.getUUID());
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }





    @Override
    public boolean isInstantenous() {
        return true;
    }


}
