package net.vaex.aquilarpg.item.custom;


import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.entity.item.BottleEntity;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.weapon.RPGBluntWeapon;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import javax.annotation.Nullable;
import java.util.List;


public class RPGBrokenBottleItem extends RPGBluntWeapon {

    public RPGBrokenBottleItem(RPGMaterialTiers pTier, Item.Properties pProperties) {
        super(pTier, pProperties);
    }


    public void fillItemCategory(CreativeModeTab pGroup, NonNullList<ItemStack> pItems) {
        if (this.allowdedIn(pGroup)) {
            for(Potion potion : Registry.POTION) {
                if (!potion.getEffects().isEmpty()) {
                    pItems.add(PotionUtils.setPotion(new ItemStack(this), potion));
                }
            }
        }
    }


    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        PotionUtils.addPotionTooltip(pStack, pTooltip, 0.125F);
    }

    public @NotNull String getDescriptionId(ItemStack pStack) {
        return PotionUtils.getPotion(pStack).getName(this.getDescriptionId() + ".effect.");
    }
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pstack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 600;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pPlayer.isShiftKeyDown()) {
            return InteractionResultHolder.fail(itemstack);
        }
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override //right click on range to throw (like trident)
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player && player.isShiftKeyDown()) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                pLevel.playSound(player, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.5F);
                if (!pLevel.isClientSide) {
                    BottleEntity bottleEntity = new BottleEntity(pLevel, player, pStack);
                    float throwingValue = 2.0f;
                    bottleEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, throwingValue, 0.3F);
                    if (player.getAbilities().instabuild) {
                        bottleEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }
                    pLevel.addFreshEntity(bottleEntity);
                    Log.info(player + "throwed dagger with speed of: " + throwingValue);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    pStack.shrink(1);
                }
            }
        }
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
            if (!pAttacker.getLevel().isClientSide) {
                for(MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(pStack)) {
                    if (mobeffectinstance.getEffect().isInstantenous()) {
                        mobeffectinstance.getEffect().applyInstantenousEffect(pAttacker, pAttacker, pTarget, mobeffectinstance.getAmplifier(), 1.0D);
                    } else {
                        pTarget.addEffect(new MobEffectInstance(mobeffectinstance));

                    }
                }
                if (pAttacker instanceof Player player) {
                    player.awardStat(Stats.ITEM_USED.get(this));
                    if (!player.getAbilities().instabuild) {
                        pStack.hurtAndBreak(pStack.getMaxDamage(), pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    }
                }
                pTarget.playSound(SoundEvents.GLASS_BREAK, 1.5F, 1.0F);
            }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}



