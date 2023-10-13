package net.vaex.aquilarpg.item.custom.weapon.unique;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.weapon.RPGSwordWeapon;
import net.vaex.aquilarpg.network.ManaC2SPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UniqueSwordDuskfangDawnfang extends RPGSwordWeapon {

    private int fireDMG = 5;
    private int frostDMG = 5;
    public boolean isInPowderSnow;
    private boolean isNight = false;

    private boolean fullCharged = false;

    public UniqueSwordDuskfangDawnfang(RPGMaterialTiers pTier, Properties pProperties) {

        super(pTier, pProperties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (!world.isClientSide) {
            if (selected) {
                if (entity instanceof Player player) {
                    if (world.isNight()) {
                        setDay(itemstack, false);
                        setNight(itemstack, true);
                        isNight = true;

                    }
                    if (!world.isNight()) {
                        setDay(itemstack, true);
                        setNight(itemstack, false);
                        isNight = false;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        CompoundTag nbt;
        int chargeCounter = 0;
        int totalKills = 0;
        Level level = pAttacker.getLevel();
        if (pAttacker instanceof Player) {
            if (!level.isNight()) {
                pTarget.hurt(DamageSource.ON_FIRE, this.fireDMG);
                pAttacker.heal(this.fireDMG);

            }
            if (level.isNight()) {
                pTarget.hurt(DamageSource.FREEZE, this.frostDMG);
                pAttacker.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                    mana.addMana(this.frostDMG);
                    NetworkHandler.sendPacketToServer((new ManaC2SPacket()));
                });
            }

            if (pStack.hasTag()) {
                nbt = pStack.getTag();
            } else {
                nbt = new CompoundTag();
            }
            if (nbt.contains("totalkills")) {
                nbt.putInt("kills", nbt.getInt("kills") + chargeCounter);
                nbt.putInt("totalkills", nbt.getInt("totalkills") + totalKills);
            } else {
                nbt.putInt("kills", chargeCounter);
                nbt.putInt("totalkills", totalKills);
            }

            if (pTarget.isDeadOrDying()) {
                nbt.putInt("kills", nbt.getInt("kills") + 1);
                nbt.putInt("totalkills", nbt.getInt("totalkills") + 1);
            }
            fullCharged = pStack.getTag().getInt("kills") == 12;

            if (pStack.getTag().getInt("kills") == 12 && level.isDay()) {
                pTarget.isOnFire();
                pTarget.setSecondsOnFire(10);
            }

            if (pStack.getTag().getInt("kills") == 12 && level.isNight()) {
                pTarget.addEffect(new MobEffectInstance(RPGEffectManager.FREEZE.get(), 200));
                pTarget.isFreezing();

            }
            if (pStack.getTag().getInt("kills") > 12) {
                pStack.removeTagKey("kills");
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
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("This powerful Sword cycles between Day and Night").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("At Day Dusk Fang absorbs leeches Life to generate Health and deals 5 Fire Damage").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("At Night  Dawn Fang leeches Life to generate Mana and deals 5 Frost Damage").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("Dawnfang/Duskfang keeps track of enemies killed every 13th Kill will be critical").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("One Hand Weapon").withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + this.getWeight() + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + this.getRange() + " ").append(new TranslatableComponent("item_weapon_range")).withStyle(ChatFormatting.BLUE));
            if (isNight) {
                pTooltip.add(new TextComponent("DAWN FANG").withStyle(ChatFormatting.DARK_AQUA));
            } else {
                pTooltip.add(new TextComponent("DUSK FANG").withStyle(ChatFormatting.DARK_RED));
            }
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
        var2 = String.valueOf(itemStack.getTag().getInt("kills"));
        if (itemStack.getTag().getInt("kills") < 13) {
            pTooltip.add(new TextComponent("Kills" + var2 + " ").withStyle(ChatFormatting.DARK_PURPLE));
            pTooltip.remove(new TextComponent("CHARGED").withStyle(ChatFormatting.DARK_PURPLE));
        }
        if (itemStack.getTag().getInt("kills") == 13) {
            pTooltip.remove(new TextComponent("Kills" + var2 + " ").withStyle(ChatFormatting.DARK_PURPLE));
            pTooltip.add(new TextComponent("CHARGED").withStyle(ChatFormatting.DARK_PURPLE));
        }
        pTooltip.add(new TextComponent("Material: " + materialType + " ").withStyle(ChatFormatting.BLUE));
    }


    @Override
    public boolean isFoil(@NotNull ItemStack itemstack) {
        if (fullCharged) {
            return true;
        } else {
            return false;
        }
    }

}

