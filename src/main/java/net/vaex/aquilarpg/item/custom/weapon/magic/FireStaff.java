package net.vaex.aquilarpg.item.custom.weapon.magic;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import net.vaex.aquilarpg.network.ManaC2SPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class FireStaff extends BowItem {
    private int manaConsume = 10;
    private int manaConsumeCharged = 25;

    public FireStaff(Properties properties) {
        super(properties);
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pstack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 7200;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        int currentMana = ClientManaData.getPlayerMana(pPlayer);
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (manaConsume <= currentMana) {
            if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
                return InteractionResultHolder.fail(itemstack);
            } else {
                pPlayer.startUsingItem(pHand);
                pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.PORTAL_AMBIENT, SoundSource.PLAYERS, 1.0F, 1.0F);
                pLevel.addParticle(ParticleTypes.PORTAL, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), 0.5, 0.5, 0.5);
                return InteractionResultHolder.consume(itemstack);
            }

        } else {
            pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.5F, 1F);
                if (!pLevel.isClientSide) {
                    CustomSmallFireball customSmallFireball = new CustomSmallFireball(pLevel, player);
                    customSmallFireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0.25F);
                    pLevel.addFreshEntity(customSmallFireball);
                    player.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                        mana.subMana(manaConsume);
                        NetworkHandler.sendPacketToServer((new ManaC2SPacket()));
                    });
                }
            }
            if (i >= 20) {
                pLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1.5F, 1F);
                if (!pLevel.isClientSide) {
                    CustomLargeFireball customLargeFireball = new CustomLargeFireball(pLevel, player, 2);
                    customLargeFireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0.25F);
                    pLevel.addFreshEntity(customLargeFireball);
                    player.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                        mana.subMana(manaConsumeCharged);
                        NetworkHandler.sendPacketToServer((new ManaC2SPacket()));
                    });
                }
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                pStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(new TextComponent("Pyromancer's best Friend"));
        tooltip.add(new TextComponent("Small Fireball: " + manaConsume + " / cast"));
        tooltip.add(new TextComponent("Large Fireball: " + manaConsumeCharged + " / cast"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
