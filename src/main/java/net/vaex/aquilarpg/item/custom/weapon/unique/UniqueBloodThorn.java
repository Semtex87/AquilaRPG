package net.vaex.aquilarpg.item.custom.weapon.unique;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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

public class UniqueBloodThorn extends RPGSwordWeapon {

    private boolean fullCharged = false;

    public UniqueBloodThorn(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        CompoundTag nbt;
        String var1;
        if (pAttacker instanceof Player player) {
            if (pStack.hasTag()) {
                nbt = pStack.getTag();
            } else {
                nbt = new CompoundTag();
            }
            if (nbt.contains("hits")) {
                nbt.putInt("hits", nbt.getInt("hits") + 1);
            } else {
                nbt.putInt("hits", 1);
            }
            var1 = String.valueOf(pStack.getTag().getInt("hits"));
            //player.sendMessage(new TextComponent("hits:" + var1).withStyle(ChatFormatting.RED), pAttacker.getUUID());
            fullCharged = pStack.getTag().getInt("hits") == 100;
            if (pStack.getTag().getInt("hits") == 101) {
                pAttacker.level.playSound(null, pAttacker.blockPosition(), RPGSoundEvents.FULL_CHARGE.get(), SoundSource.PLAYERS, 1, 1);
                pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 400));
                nbt.putInt("hits", 0);
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }


    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return Mob.getEquipmentSlotForItem(stack) == slot;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book){
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        if (Screen.hasShiftDown()) {
        String var2;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("Blood Mark: Counts your hits up to 100. If this is reached your next hit causes Bleeding for 20 Seconds ").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("One Hand Weapon").withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + this.getWeight() + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
            if (this.armorPiercing != 0.0F)
                pTooltip.add(new TextComponent("" + this.getArmorPiercing() + " ").append(new TranslatableComponent("item_weapon_piercing")).withStyle(ChatFormatting.BLUE));
        } else {
            pTooltip.add(new TranslatableComponent("tooltip.aquilarpg.shift"));
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
            var2 = String.valueOf(itemStack.getTag().getInt("hits"));
            pTooltip.add(new TextComponent("Blood Mark: " + var2 + " ").withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("Material: " + materialType + " ").withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack itemstack) {
        if (fullCharged)
        {
            return true;
        }else{
            return false;
        }
    }
}

