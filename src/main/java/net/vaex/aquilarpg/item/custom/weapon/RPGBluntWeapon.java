package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolActions;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.item.custom.RPGBasicTwoHandMeleeWeapon;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jline.utils.Log;

import java.util.List;
import java.util.Random;


public class RPGBluntWeapon extends RPGBasicMeleeWeapon {
    float increasedAttackDamage = 0.0f;
    float decreasedAttackSpeed = 0.0f;
    float increasedWeight = 0.0f;
    boolean isBalanced = false;

    public RPGBluntWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        //this.rangeByType = 2.3d; //MACE
        this.damageByType = 4.0f; //MACE
        this.speedByType = 1.1f; //MACE
        this.itemWeight = 1.3f + pTier.getWeight() + increasedWeight; //MACE
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getWeight() / pTier.getMaterialHardness());
        this.actualAttackDamage = getActualAttackDamage() + damageByType;
        this.actualAttackSpeed = getActualAttackSpeed() + speedByType; //- 4.0f basic attack speed from UUID
        ;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", (actualAttackDamage + increasedAttackDamage), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", (actualAttackSpeed - decreasedAttackSpeed), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(RPGAttributeUUID.ATTACK_KNOCKBACK_UUID, "Knockback modifier", knockback, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }


    public void setBalanced(boolean balanced) {
        isBalanced = balanced;
        increasedAttackDamage = 1.0f;
        decreasedAttackSpeed = 0.5f;
        increasedWeight = 0.5f;
    }
    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }


    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        CompoundTag nbt;
        String var1 = String.valueOf(pStack.getTag().getInt("sharpened_effect"));
        float toughness = (float) pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offHand = pAttacker.getItemBySlot(EquipmentSlot.OFFHAND);
        ItemStack mainHandTarget = pTarget.getMainHandItem();
        ItemStack offHandTarget = pTarget.getOffhandItem();
        ItemStack head = pTarget.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = pTarget.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = pTarget.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = pTarget.getItemBySlot(EquipmentSlot.FEET);
        if (pAttacker instanceof Player player) {
            if (pStack.hasTag()) {
                nbt = pStack.getTag();
            } else {
                nbt = new CompoundTag();
            }
            if (nbt.contains("sharpened_effect") || nbt.contains("hits")) {
                nbt.putInt("hits", nbt.getInt("hits") + 1);
            } else {
                nbt.putInt("hits", 1);
            }


            if (pStack.getItem() instanceof RPGBasicMeleeWeapon weapon) {
                if (pStack.getTag().getInt("hits") == weapon.getEnchantmentValue()) {
                    pAttacker.level.playSound(null, pAttacker.blockPosition(), RPGSoundEvents.FULL_CHARGE.get(), SoundSource.PLAYERS, 1, 1);
                    pStack.getTag().remove("hits");
                    pStack.getTag().remove("sharpened_effect");
                    pStack.getEnchantmentTags().clear();
                }


                if (!pTarget.level.isClientSide() && !pTarget.isBlocking()) {
                    //here we check if player has few armor pieces with overall less than 5.0f Def-Rating
                    if (pTarget.getArmorValue() <= 5.0F) { //no armor or only some pieces
                        if (new Random().nextInt(10) == 1 && (!pTarget.hasEffect(RPGEffectManager.LACERATION.get()))) {
                            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.LACERATION.get(), 36000, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " has laceration");
                        }
                        if (new Random().nextInt(15) == 1 && (!pTarget.hasEffect(RPGEffectManager.PARALYSIS.get()))) {
                            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.PARALYSIS.get(), 200, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is stunned");
                        }
                        if (new Random().nextInt(20) == 1 && (!pTarget.hasEffect(MobEffects.BLINDNESS))) {
                            pTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is blind");
                        }
                        float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                        pTarget.hurt(DamageSource.GENERIC, finalDamage);
                        Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                        Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                    }
                    //here we check if player has light armor-rating (5-9)
                    if (pTarget.getArmorValue() <= 9.0F && pTarget.getArmorValue() > 5.0F) { //light armor
                        if (new Random().nextInt(20) == 1 && (!pTarget.hasEffect(RPGEffectManager.LACERATION.get()))) {
                            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.LACERATION.get(), 36000, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " has laceration");
                        }
                        if (new Random().nextInt(25) == 1 && (!pTarget.hasEffect(RPGEffectManager.PARALYSIS.get()))) {
                            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.PARALYSIS.get(), 200, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is stunned");
                        }
                        if (new Random().nextInt(30) == 1 && (!pTarget.hasEffect(MobEffects.BLINDNESS))) {
                            pTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is blind");
                        }
                        float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                        pTarget.hurt(DamageSource.GENERIC, finalDamage);
                        Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                        Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                    }
                    //here we check if player has medium armor-rating (9-17)
                    if (pTarget.getArmorValue() <= 17.0F && pTarget.getArmorValue() > 9.0F) { //medium armor
                        if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(75) == 1) { //here calc some damage if player has toughness rating
                            head.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.HEAD));
                            chest.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.CHEST));
                            legs.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.LEGS));
                            feet.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.FEET));
                            float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                            pTarget.hurt(DamageSource.GENERIC, finalDamage);
                            Log.info(pAttacker + " hit armor" + pTarget + "with damage: " + armorPenetration);
                        } else {
                            if (new Random().nextInt(50) == 1) {//here calc some damage if player has no toughness
                                if (new Random().nextInt(40) == 1 && (!pTarget.hasEffect(RPGEffectManager.LACERATION.get()))) {
                                    pTarget.addEffect(new MobEffectInstance(RPGEffectManager.LACERATION.get(), 36000, 0, true, true, false));
                                    pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                                    Log.info(pTarget + " has laceration");
                                }
                                if (new Random().nextInt(50) == 1 && (!pTarget.hasEffect(RPGEffectManager.PARALYSIS.get()))) {
                                    pTarget.addEffect(new MobEffectInstance(RPGEffectManager.PARALYSIS.get(), 200, 0, true, true, false));
                                    pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                                    Log.info(pTarget + " is stunned");
                                }
                                if (new Random().nextInt(60) == 1 && (!pTarget.hasEffect(MobEffects.BLINDNESS))) {
                                    pTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, true, true, false));
                                    pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                                    Log.info(pTarget + " is blind");
                                }
                            }
                            float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                            pTarget.hurt(DamageSource.GENERIC, finalDamage);
                            Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                            Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                        }
                        mainHand.hurtAndBreak(1, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        if (pAttacker.getOffhandItem().getItem() instanceof RPGBluntWeapon) {
                            offHand.hurtAndBreak(1, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                        }
                    }
                    //here we check if player has heavy armor-rating (20+)
                    if (pTarget.getArmorValue() > 17.0F) { //heavy armor
                        if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(100) == 1) {
                            head.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.HEAD));
                            chest.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.CHEST));
                            legs.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.LEGS));
                            feet.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.FEET));
                            float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                            pTarget.hurt(DamageSource.GENERIC, finalDamage);
                            Log.info(pAttacker + " hit armor" + pTarget + "with damage: " + armorPenetration);
                        }
                    } else {
                        head.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.HEAD));
                        chest.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.CHEST));
                        legs.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.LEGS));
                        feet.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.FEET));
                        float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                        pTarget.hurt(DamageSource.GENERIC, finalDamage);
                        Log.info(pAttacker + " hit armor" + pTarget + "with damage: " + armorPenetration);
                        if (new Random().nextInt(75) == 1 && (!pTarget.hasEffect(RPGEffectManager.PARALYSIS.get()))) {
                            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.PARALYSIS.get(), 200, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is stunned");
                        }
                        if (new Random().nextInt(90) == 1 && (!pTarget.hasEffect(MobEffects.BLINDNESS))) {
                            pTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is blind");
                        }
                    }
                    float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                    mainHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    if (pAttacker.getOffhandItem().getItem() instanceof RPGBluntWeapon) {
                        offHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                    }
                    pTarget.hurt(DamageSource.GENERIC, finalDamage);
                    Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                    Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                }

                if (pTarget.getOffhandItem().getItem() instanceof ShieldItem && pTarget.isBlocking()) {
                    ItemStack blockingStack = pTarget.getOffhandItem();
                    blockingStack.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }
                if (pTarget.getMainHandItem().getItem() instanceof RPGBasicTwoHandMeleeWeapon && pTarget.isBlocking()) {
                    ItemStack blockingStack = pTarget.getMainHandItem();
                    blockingStack.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }

                if (offHandTarget.getItem() instanceof ShieldItem) {
                    int damageAmount = 20;
                    offHandTarget.hurtAndBreak(damageAmount, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                    Log.info(pAttacker + " damaged shield of " + pTarget + "with " + damageAmount);
                    pTarget.spawnAtLocation(offHandTarget.getItem());
                    pTarget.getOffhandItem().shrink(1);
                }
            }
        }
        Log.info(pTarget + "Maxlife: " + pTarget.getMaxHealth());
        Log.info(pTarget + "current Life: " + pTarget.getHealth());
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        int var;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("One Hand Weapon").withStyle(ChatFormatting.BLUE));
            if (!itemStack.isEnchanted()) {
                pTooltip.add(new TextComponent("Enchant Capacity: " + this.getEnchantmentValue()).withStyle(ChatFormatting.BLUE));
            }
            if (itemStack.isEnchanted() && itemStack.getItem() instanceof RPGBasicMeleeWeapon weapon) { //todo
                weapon.getEnchantmentValue();
                var = itemStack.getTag().getInt("hits");
                pTooltip.add(new TextComponent("Enchantment Uses"+ (getEnhancementValue() - var) + "/" + getEnhancementValue()).withStyle(ChatFormatting.DARK_PURPLE));
            }
            if (itemStack.getTag() != null && itemStack.getTag().contains("Enchantment")) {
                pTooltip.add(new TextComponent("Tag: " + getEnhancementValue()).withStyle(ChatFormatting.BLUE));
            }
            if (itemStack.getTag() == null) {
                pTooltip.add(new TextComponent("No Tags: ").withStyle(ChatFormatting.BLUE));
            }
            pTooltip.add(new TextComponent("" + this.getWeight() + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
            if (this.throwWeapon)
                pTooltip.add(new TextComponent("Throwable").withStyle(ChatFormatting.BLUE));
            if (this.isSilver)
                pTooltip.add((new TextComponent("+" + silverDamageModifier + " ")).append(new TranslatableComponent("item_weapon_silver_damage_undead")).withStyle(ChatFormatting.BLUE));
            //pTooltip.add(new TextComponent("" + this.getRange()+  " ").append(new TranslatableComponent("item_weapon_range")).withStyle(ChatFormatting.BLUE));
            if (this.knockback != 0.0F)
                pTooltip.add(new TextComponent("" + this.getKnockback() + " ").append(new TranslatableComponent("item_weapon_knockback")).withStyle(ChatFormatting.BLUE));
            if (this.isPoison)
                pTooltip.add(new TextComponent("Poison").withStyle(ChatFormatting.DARK_GREEN));
            if (this.armorPiercing != 0.0F)
                pTooltip.add(new TextComponent("" + this.getArmorPiercing() + " ").append(new TranslatableComponent("item_weapon_piercing")).withStyle(ChatFormatting.BLUE));
            if (this.fantasyWeapon) {
                pTooltip.add(new TextComponent("#Fantasy").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
            } else {
                pTooltip.add(new TextComponent("#Realistic").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
            }
        } else {
            pTooltip.add(new TranslatableComponent("tooltip.aquilarpg.shift"));
        }
        if (!itemStack.isDamaged()) {
            pTooltip.add(new TextComponent(durability + " / " + durability).withStyle(ChatFormatting.GREEN));
        } else {
            if (!itemStack.isDamaged()) {
                pTooltip.add(new TextComponent(currentDamage + " / " + maxDurability).withStyle(ChatFormatting.GREEN));
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
        }
        pTooltip.add(new TextComponent("Material: " + materialType + " ").withStyle(ChatFormatting.BLUE));
    }


}











