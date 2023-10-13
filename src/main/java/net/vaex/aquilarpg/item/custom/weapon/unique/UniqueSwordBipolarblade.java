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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.weapon.RPGTwoHandSwordWeapon;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UniqueSwordBipolarblade extends RPGTwoHandSwordWeapon {
    private boolean fullCharged = false;


    public UniqueSwordBipolarblade(RPGMaterialTiers pTier, Properties pProperties) {

        super(pTier, pProperties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            if (entity instanceof Player player) {
                if (itemstack.getTag().contains("charged") && isDay(itemstack)){
                    player.addEffect(new MobEffectInstance(RPGEffectManager.BLESSED.get()));
                }
                if (itemstack.getTag().contains("charged") && isNight(itemstack)){
                    setNight(itemstack, true);
                    player.addEffect(new MobEffectInstance(RPGEffectManager.UNHOLY_STRENGTH.get()));
                }
            }
        }
    }


    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        CompoundTag nbt;
        String var1;
        String var3 = "FULL CHARGED";
        int polarityCounter = 0;
        if (pAttacker instanceof Player) {
                if (pStack.hasTag()) {
                    nbt = pStack.getTag();
                } else {
                    nbt = new CompoundTag();
                }
            if (!nbt.contains("charged")) {
                if (nbt.contains("hits")) {
                    nbt.putInt("hits", nbt.getInt("hits") + polarityCounter);
                } else {
                    nbt.putInt("hits", polarityCounter);
                }
            }
            var1 = String.valueOf(pStack.getTag().getInt("hits"));

            fullCharged = !nbt.contains("charged") && (pStack.getTag().getInt("hits") == 99 || pStack.getTag().getInt("hits") == -99);

            if (!nbt.contains("charged") && pTarget.isDeadOrDying() && pTarget.getMobType().equals(MobType.UNDEAD)) {
                nbt.putInt("hits", nbt.getInt("hits") + 1);
                if (pStack.getTag().getInt("hits") == 100) {
                    pStack.getTag().putString("charged", var3);
                    setDay(pStack, true);
                }
            }
            if (!nbt.contains("charged") && pTarget.isDeadOrDying() && !pTarget.getMobType().equals(MobType.UNDEAD)) {
                nbt.putInt("hits", nbt.getInt("hits") - 1);
                if (pStack.getTag().getInt("hits") == -100) {
                    pStack.getTag().putString("charged", var3);
                    setNight(pStack, true);
                }
            }
            if (!nbt.contains("charged") && fullCharged){
                pAttacker.level.playSound(null, pAttacker.blockPosition(), RPGSoundEvents.FULL_CHARGE.get(), SoundSource.PLAYERS, 1, 1);
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }


    public static boolean isDay(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("day");
    }

    public static void setDay(ItemStack pStack, boolean pIsCharged) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        compoundtag.putBoolean("day", pIsCharged);
    }

    public static boolean isNight(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("night");
    }

    public static void setNight(ItemStack pStack, boolean pIsCharged) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        compoundtag.putBoolean("night", pIsCharged);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        String var2;
        String var3;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("Two smiths were not given enough instruction, and created halves with Materials that completely negated each other.").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("The sword is enchanted with the ability to collect souls from Living or Undead.").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("These conflicting Materials make the blade practically useless...until u find out its real Power").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("Two Hand Weapon").withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + this.getWeight() + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
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
        if ((!itemStack.getTag().contains("charged"))) {
            var2 = String.valueOf(itemStack.getTag().getInt("hits"));
            if (itemStack.getTag().getInt("hits") > 0) {
                pTooltip.add(new TextComponent("Hunted Souls of Undead: " + var2 + " ").withStyle(ChatFormatting.GOLD));
            }
            if (itemStack.getTag().getInt("hits") < 0) {
                pTooltip.add(new TextComponent("Hunted Souls of Living: " + var2 + " ").withStyle(ChatFormatting.DARK_GRAY));
            }
        } else {
            var3 = itemStack.getTag().getString("charged");
            if ((itemStack.getTag().getInt("hits") <= 101) || (itemStack.getTag().getInt("hits") >= -101)) {
                pTooltip.add(new TextComponent("" + var3 + " ").withStyle(ChatFormatting.DARK_PURPLE));
            }
        }


        pTooltip.add(new TextComponent("Material: " + materialType + " ").withStyle(ChatFormatting.BLUE));
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

