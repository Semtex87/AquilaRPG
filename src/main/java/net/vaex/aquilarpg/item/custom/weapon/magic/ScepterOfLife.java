package net.vaex.aquilarpg.item.custom.weapon.magic;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.capabilities.mana.ClientManaData;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.entity.item.OrbOfLifeEntity;
import net.vaex.aquilarpg.network.ManaC2SPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ScepterOfLife extends Item {
private int manaConsume = 50;
private int lifeconsume = 2;



    public ScepterOfLife(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        if (entity instanceof ServerPlayer player) {
            if (!selected) { //auto recharge
                if (new Random().nextInt(320) == 1) {
                    itemstack.setDamageValue(itemstack.getDamageValue() + (-1));
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

        }else {

            pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F);

        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
    }
    @Override //right click on range to throw (like trident)
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                pLevel.playSound(null, player.getX(), player.getY(), player.getZ(),  SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS,1.5F, 1F);
                if (!pLevel.isClientSide) {
                    OrbOfLifeEntity orbOfLife = new OrbOfLifeEntity(pLevel, player);
                    orbOfLife.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.25F);
                    pLevel.addFreshEntity(orbOfLife);
                }
                //ItemStack stack = player.getItemInHand(hand);
                //stack.setDamageValue(stack.getDamageValue() + 1);
                player.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.subMana(manaConsume);
                    NetworkHandler.sendPacketToServer((new ManaC2SPacket()));
                });
                player.hurt(DamageSource.MAGIC, lifeconsume);
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    pStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(new TextComponent("this Scepter looks like a root and regenerate itself"));
        tooltip.add(new TextComponent("It drains life of the holder to create..."));
        tooltip.add(new TextComponent("Mana consume: " + manaConsume + " / cast"));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
