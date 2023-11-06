package net.vaex.aquilarpg.item.custom;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import java.util.List;


public class RPGBasicShield extends ShieldItem {
    private final RPGMaterialTiers material;
    private float baseWeight;
    private float baseBlockDamage;
    private float actualShieldDefense;
    private float actualShieldWeight;
    private float actualShieldToughness;
    private float maxBlockDamage;
    private int durability;

    private float resistMagic;
    boolean isFoil;
    String materialType;
    String shieldType;

    public RPGBasicShield(RPGMaterialTiers pTier, Properties pProperties ) {
        super(pProperties.tab(RPGCreativeModeTab.RPG_ARMOR));
        this.durability = pTier.getUses();
        this.materialType = pTier.getMaterialName();
        this.material =pTier;
        this.actualShieldWeight = baseWeight + pTier.getWeight();
        this.actualShieldDefense =  Math.round(baseWeight + (Math.PI * pTier.getMaterialHardness()));
        this.actualShieldToughness = pTier.getMaterialHardness();
        this.maxBlockDamage = Math.round (baseBlockDamage + (actualShieldWeight * actualShieldDefense));
    }

    public RPGBasicShield setShieldType(String handed) {
        shieldType = handed;
        if (shieldType.contains("small")) {
            baseWeight = 3.0f;
            baseBlockDamage = 8.0f;
        }
        if (shieldType.contains("medium")) {
            baseWeight = 5.0f;
            baseBlockDamage = 14.0f;
        }
        if (shieldType.contains("heavy")) {
            baseWeight = 9.0f;
            baseBlockDamage = 20.0f;
        }
        return this;
    }


    public void blockSequence(ItemStack stack, float damage, Player player, DamageSource source) {
        float piercingValue = 0;
        int maxDurability = stack.getMaxDamage();
        int currentDamage = (maxDurability - 1) - stack.getDamageValue();
        String itmdamage = "" + currentDamage;
        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        stack.getTag().putString("damage", itmdamage);
        if (source.getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) source.getEntity();
            if (attacker.getMainHandItem().getItem() instanceof RPGBasicMeleeWeapon) {
                piercingValue = piercingValue + ((RPGBasicMeleeWeapon) attacker.getMainHandItem().getItem()).getArmorPiercing();
            }
            if (attacker.getMainHandItem().getItem() instanceof RPGBasicTwoHandMeleeWeapon) {
                piercingValue = piercingValue + ((RPGBasicTwoHandMeleeWeapon) attacker.getMainHandItem().getItem()).getArmorPiercing();
            }
            if (attacker instanceof Zombie) {
                piercingValue = piercingValue + 2;
                damage = damage + 3;
            }
            if (attacker instanceof Skeleton) {
                piercingValue = piercingValue + 1;

            }
        }

        if (damage > this.getMaxBlockDamage()) {
            stack.hurtAndBreak((int) (damage + piercingValue), player, (var) ->
                    var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            float damageCalc = damage - this.getMaxBlockDamage();
            float finalDamage = CombatRules.getDamageAfterAbsorb(damageCalc, (float) player.getArmorValue(), (float) player.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
            player.hurt(DamageSource.GENERIC, finalDamage);
            return;
        }

        stack.hurtAndBreak((int) (damage + piercingValue), player, (var) ->
                var.broadcastBreakEvent(EquipmentSlot.MAINHAND));

        Log.info(player + "blocked damage: " + damage  + "blocked pierced damage: " + piercingValue);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        // if (pEntity instanceof LivingEntity && ((LivingEntity) pEntity).getMainHandItem().is(AquilaTags.Items.TWOHAND_WEAPON) && (((LivingEntity) pEntity).getOffhandItem().is(AquilaTags.Items.HEAVY_SHIELD) || ((LivingEntity) pEntity).getOffhandItem().is(AquilaTags.Items.LARGE_SHIELD))) {
        //    ((LivingEntity) pEntity).addEffect(new MobEffectInstance(AquilaEffectManager.ENCUMBERED.get(), 1,1,true,true, true));
        // }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 72000;
    }

    public float getShieldType() {
        return this.actualShieldWeight;
    }
    public float getShieldToughness() {
        return this.actualShieldToughness;
    }
    public float getMaxBlockDamage() {
        return this.maxBlockDamage;
    }
    public float getShieldDefense() {
        return this.actualShieldDefense;
    }
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    public float getDurability() {
        return this.durability;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        if (Screen.hasShiftDown()) {
            if (shieldType.contains("small"))
                pTooltip.add((new TranslatableComponent("item_shield_type_small")).withStyle(ChatFormatting.ITALIC));
            if (shieldType.contains("medium"))
                pTooltip.add((new TranslatableComponent("item_shield_type_medium")).withStyle(ChatFormatting.ITALIC));
            if (shieldType.contains("heavy"))
                pTooltip.add((new TranslatableComponent("item_shield_type_large")).withStyle(ChatFormatting.ITALIC));
            //pTooltip.add(new TextComponent("" + shieldType + " ").append(new TranslatableComponent("item_shield_weight")).withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + getShieldDefense() + " ").append(new TranslatableComponent("item_shield_defense")).withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + getShieldToughness() + " ").append(new TranslatableComponent("item_shield_toughness")).withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + getMaxBlockDamage() + " ").append(new TranslatableComponent("item_maxBlockDamage")).withStyle(ChatFormatting.BLUE));
        } else {
            pTooltip.add(new TranslatableComponent("tooltip.aquilarpg.shift"));
        }
        if (!itemStack.isDamaged()) {
            pTooltip.add(new TextComponent( durability + " / " + durability).withStyle(ChatFormatting.GREEN));
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
    @Override
    public boolean isFoil(@NotNull ItemStack itemstack) {
        if (isFoil)
        {
            return true;
        }else{
            return false;
        }
    }
}






















