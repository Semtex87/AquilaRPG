package net.vaex.aquilarpg.item.custom.weapon.magic;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.capabilities.mana.ClientManaData;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.entity.item.CustomLargeFireball;
import net.vaex.aquilarpg.entity.item.CustomSmallFireball;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.weapon.RPGMagicWeapon;
import net.vaex.aquilarpg.network.ManaC2SPacket;
import net.vaex.aquilarpg.network.ManaSyncS2CPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class FireStaff extends RPGMagicWeapon {
    protected final Random random = new Random();
    private int manaConsume = 10;

    public FireStaff(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_WEAPON));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        if (entity instanceof Player pPlayer) {
            if (selected) {
                if (new Random().nextInt(100) == 1) {
                    addParticlesAroundSelf(entity, world);
                }
            }
        }
        super.inventoryTick(itemstack, world, entity, slot, selected);
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pstack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 7200;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    private void addParticlesAroundSelf(Entity entity, Level level) {
        for (int i = 0; i < 5; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            level.addParticle(getParticle(), entity.getRandomX(0.5D), entity.getRandomY() + 0.5D, entity.getRandomZ(0.5D), d0, d1, d2);
        }
    }

    private ParticleOptions getParticle() {
        return ParticleTypes.LAVA;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        if (!pLevel.isClientSide) {
            if (pEntityLiving instanceof ServerPlayer serverplayer && !serverplayer.isCreative()) {
                InteractionHand hand = serverplayer.getUsedItemHand();
                serverplayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    int i = this.getUseDuration(pStack) - pTimeLeft;
                    if (i > 10) {
                        if (mana.getMana() > manaConsume) {
                            mana.subMana(manaConsume);
                            Log.info(manaConsume);
                            pLevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.5F, 1F);
                            CustomSmallFireball customSmallFireball = new CustomSmallFireball(pLevel, serverplayer);
                            customSmallFireball.shootFromRotation(serverplayer, serverplayer.getXRot(), serverplayer.getYRot(), 0.0F, 2.5F, 0.25F);
                            pLevel.addFreshEntity(customSmallFireball);
                            NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverplayer);
                            for (int j = 0; j < 4; ++j) {
                                addParticlesAroundSelf(serverplayer, pLevel);
                            }
                        } else {
                            serverplayer.sendMessage(new TextComponent("not enough mana"), serverplayer.getUUID());
                            pLevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);

                        }
                    }
                 if (i > 20) {
                        if (mana.getMana() > manaConsume *2) {
                            mana.subMana(manaConsume * 2);
                            Log.info(manaConsume * 2);
                            pLevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1.5F, 1F);
                            CustomLargeFireball customLargeFireball = new CustomLargeFireball(pLevel, serverplayer, 2);
                            customLargeFireball.shootFromRotation(serverplayer, serverplayer.getXRot(), serverplayer.getYRot(), 0.0F, 2.5F, 0.25F);
                            pLevel.addFreshEntity(customLargeFireball);
                            NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverplayer);
                            for (int j = 0; j < 8; ++j) {
                                addParticlesAroundSelf(serverplayer, pLevel);
                            }

                        } else {
                            serverplayer.sendMessage(new TextComponent("not enough mana"), serverplayer.getUUID());
                            pLevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);

                        }

                    }
                });
                serverplayer.awardStat(Stats.ITEM_USED.get(this));
                if (!serverplayer.getAbilities().instabuild) {
                    pStack.hurtAndBreak(1, serverplayer, p -> p.broadcastBreakEvent(hand));
                }
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(new TextComponent("Pyromancer's best Friend"));
        tooltip.add(new TextComponent("Small Fireball: " + manaConsume + " / cast"));
        tooltip.add(new TextComponent("Large Fireball: " + manaConsume * 2 + " / cast"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
