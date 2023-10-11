package net.vaex.aquilarpg.effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.network.ManaSyncS2CPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import org.jetbrains.annotations.NotNull;

public class ManaRegen extends MobEffect {
    int manaBonus= 2;
    int ticks;
    protected ManaRegen(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public @NotNull String getDescriptionId() {
        return "mana_regeneration_effect";
    }


    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof ServerPlayer serverplayer) {
            ticks++;
            if(ticks == 100) {
                serverplayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.addMana(manaBonus * pAmplifier);
                    NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverplayer);
                });
                ticks = 0;
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
