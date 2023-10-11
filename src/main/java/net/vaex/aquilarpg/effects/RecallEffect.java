package net.vaex.aquilarpg.effects;


import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.vaex.aquilarpg.AquilaRPG;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class RecallEffect extends MobEffect {
    public RecallEffect(MobEffectCategory category, Color color) {
        super(category, color.getBlue());
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "Recall";
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
            boolean hasHomepos = pLivingEntity.getPersistentData().getIntArray(AquilaRPG.MOD_ID + "homepos").length != 0;
            if(hasHomepos) {
                int[] playerPos = pLivingEntity.getPersistentData().getIntArray(AquilaRPG.MOD_ID + "homepos");
                pLivingEntity.teleportTo(playerPos[0], playerPos[1], playerPos[2]);

                pLivingEntity.sendMessage(new TextComponent("Returned Home!"), pLivingEntity.getUUID());

            } else {
                pLivingEntity.sendMessage(new TextComponent("There is no Destination"), pLivingEntity.getUUID());

            }
        super.applyEffectTick(pLivingEntity, pAmplifier);

    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

}
