package net.vaex.aquilarpg.item.custom.weapon.unique;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.weapon.RPGSwordWeapon;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class UniqueSwordBladeOfWoe extends RPGSwordWeapon {
    public UniqueSwordBladeOfWoe(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            if (entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(RPGEffectManager.LIFELEECH.get()));
                if (new Random().nextInt(1000) == 1) {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, true, true, false));
                    entity.level.playSound(null, entity.blockPosition(), RPGSoundEvents.BROTHERHOOD_HOWL.get(), SoundSource.PLAYERS, 1, 1);
                }
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (!pTarget.level.isClientSide()) {
            pAttacker.level.playSound(null, pAttacker.blockPosition(), RPGSoundEvents.ENCH_WEAPON_HIT.get(), SoundSource.PLAYERS, 1, 1);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("also known as the Night Mother's Kiss, is a ritualistic dagger associated with Sithis and the Dark Brotherhood").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("It is said that anyone slain with the dagger has their soul sent straight to the Void").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("+ Lifeleech").withStyle(ChatFormatting.RED));
            pTooltip.add(new TextComponent("+ Random howls from void").withStyle(ChatFormatting.DARK_RED));
            pTooltip.add(new TextComponent("One Hand Weapon").withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + this.getWeight() + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + this.getRange() + " ").append(new TranslatableComponent("item_weapon_range")).withStyle(ChatFormatting.BLUE));
            if (this.armorPiercing != 0.0F)
                pTooltip.add(new TextComponent("" + this.getArmorPiercing() + " ").append(new TranslatableComponent("item_weapon_piercing")).withStyle(ChatFormatting.BLUE));
        } else {
            pTooltip.add(new TranslatableComponent("item_tooltip_shift"));
        }
        if (!itemStack.isDamaged()) {
            pTooltip.add(new TextComponent(durability + " / " + durability).withStyle(ChatFormatting.GREEN));
        } else {
            if (currentDamage >= (maxDurability * 90) / 100) {
                pTooltip.add(new TextComponent(currentDamage + " / " + maxDurability).withStyle(ChatFormatting.GREEN));
            }
            if (currentDamage < (maxDurability * 90) / 100 && currentDamage >= (maxDurability * 20) / 100) {
                pTooltip.add(new TextComponent(currentDamage + " / " + maxDurability).withStyle(ChatFormatting.YELLOW));
            }
            if (currentDamage <= (maxDurability * 20) / 100) {
                pTooltip.add(new TextComponent(currentDamage + " / " + maxDurability).withStyle(ChatFormatting.RED));
            }
        }
        pTooltip.add(new TextComponent("Material: " + materialType + " ").withStyle(ChatFormatting.BLUE));
    }
}

