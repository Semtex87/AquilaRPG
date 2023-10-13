package net.vaex.aquilarpg.item.custom.weapon.unique;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.weapon.RPGTwoHandSwordWeapon;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class UniqueSwordIcebladeOfTheMonarch extends RPGTwoHandSwordWeapon {
    protected final Random random = new Random();
    private final int frostDamage = 5;
    public boolean isInPowderSnow;

    public UniqueSwordIcebladeOfTheMonarch(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
   }
  @Override
  public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
      super.inventoryTick(itemstack, world, entity, slot, selected);
      if (selected) {
          addParticlesAroundSelf(entity, world);
          if (entity instanceof Player player) {
              if (new Random().nextInt(1000) == 1 && player.getMainHandItem().is(RPGItems.ICEBLADEOFTHEMONARCH.get())) {
                  isInPowderSnow = true;
              }
          }
      }
      if (!selected) {
          isInPowderSnow = false;
      }
      entity.setIsInPowderSnow(isInPowderSnow);
  }

    private void addParticlesAroundSelf(Entity entity, Level level) {
        for(int i = 0; i < 5; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            level.addParticle(ParticleTypes.SNOWFLAKE, entity.getRandomX(0.5D), entity.getRandomY() + 0.5D, entity.getRandomZ(0.5D), d0, d1, d2);
        }

    }













    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@NotNull ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!pTarget.level.isClientSide()) {
            pTarget.hurt(DamageSource.MAGIC, frostDamage);
            pTarget.isFreezing();
            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.FREEZE.get(), 200, 1, true, true, true));
            pAttacker.level.playSound(null, pAttacker.blockPosition(), RPGSoundEvents.FREEZE_EFFECT.get(), SoundSource.PLAYERS, 1, 1);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("The Sword freezes all who feel its edge, and goes from Owner to Owner, never settling for long...").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("+ " + frostDamage + " Frost Damage").withStyle(ChatFormatting.AQUA));
            pTooltip.add(new TextComponent("+ Freezing Targets").withStyle(ChatFormatting.AQUA));
            pTooltip.add(new TextComponent("+ Freezing Damage over Time").withStyle(ChatFormatting.AQUA));
            pTooltip.add(new TextComponent("+ Random gets cold in the hands").withStyle(ChatFormatting.DARK_AQUA));
            pTooltip.add(new TextComponent("Two Hand Weapon").withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + this.getWeight() + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("Block Damage" + getMaxBlockDamage() + " ").withStyle(ChatFormatting.BLUE));
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
