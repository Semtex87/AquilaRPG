package net.vaex.aquilarpg.effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.capabilities.mana.ClientManaData;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.network.ManaSyncS2CPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID)
public class ManaShieldEffect extends MobEffect {
    protected final Random random = new Random();
    int ticks;

    protected ManaShieldEffect(MobEffectCategory category) {
        super(category, 0);

    }

    @Override
    public @NotNull String getDescriptionId() {
        return "Mana_shield";
    }

        @Override
        public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
            Level level = entity.getLevel();
            if (entity instanceof Player player) {
                if (player.hasEffect(RPGEffectManager.MANA_SHIELD.get())) {
                    player.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                        if (mana.getMana() > 0) {
                            ticks++;
                            if (ticks == 80) {
                                level.playSound(null, player.getX(), player.getY(), player.getZ(),RPGSoundEvents.FORCE_FIELD.get(), SoundSource.PLAYERS, 1.5F, 1F);
                                ticks = 0;
                            }
                        }
                    });
                }
            }
            super.applyEffectTick(entity, amplifier);
        }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        Level level = event.getEntity().getLevel();
        LivingEntity entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        float damage = event.getAmount();
        LivingAttackEvent event1 = new LivingAttackEvent(entity, source, damage);
        if (!level.isClientSide()) {
            if (entity instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.hasEffect(RPGEffectManager.MANA_SHIELD.get())) {
                    serverPlayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                        if (mana.getMana() > damage) {
                            mana.subMana((int) damage);
                            event.setCanceled(true);
                            event1.setCanceled(true);
                            NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverPlayer);
                            serverPlayer.level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), RPGSoundEvents.FORCE_FIELD_HIT.get(), SoundSource.PLAYERS, 4.0F, 1.0f);
                        }
                        if (mana.getMana() < damage) {
                            float remaining = damage - mana.getMana();
                            float shieldDamage = mana.getMana() - remaining;
                            mana.subMana((int) shieldDamage);
                            event.setCanceled(false);
                            event1.setCanceled(false);
                            serverPlayer.hurt(source, remaining);
                            NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverPlayer);
                        }
                    });
                }

            }
        }
    }

/*
    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        Level level = event.getEntity().getLevel();
        LivingEntity entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity attacker = event.getSource().getEntity();
        float damage = event.getAmount();
        if (!entity.level.isClientSide()) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.hasEffect(RPGEffectManager.MANA_SHIELD.get())) {
                LivingAttackEvent event1 = new LivingAttackEvent(entity,source,damage);
                int currentMana = ClientManaData.getPlayerMana();
                if (currentMana > damage) {
                    event.setCanceled(true);
                    event1.setCanceled(true);
                    serverPlayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                        mana.subMana((int) damage);
                        NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverPlayer);
                    });
                    serverPlayer.level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), RPGSoundEvents.FORCE_FIELD_HIT.get(), SoundSource.PLAYERS, 4.0F,1.0f);
                }
                if (currentMana < damage ) {
                    float remaining = damage - currentMana;
                    serverPlayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                        mana.subMana((int) currentMana);
                        NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverPlayer);
                    });
                    serverPlayer.hurt(source, remaining);
                }
            }
        }
    }
    }
*/


}
