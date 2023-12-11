package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.common.ToolActions;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicTwoHandMeleeWeapon;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import net.vaex.aquilarpg.util.RPGAttributes;
import net.vaex.aquilarpg.util.RPGCombatUtils;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jline.utils.Log;

import java.util.Random;


public class RPGTwoHandAxeWeapon extends RPGBasicTwoHandMeleeWeapon {
    public RPGTwoHandAxeWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        this.armorPiercing = 2; //TWOHAND SWORD
        //this.rangeByType = 4.0d + weaponSubType; //TWOHAND SWORD
        this.damageByType = 12.0f; //TWOHAND SWORD
        this.speedByType = 1.2f; //TWOHAND SWORD
        this.itemWeight = 7.0f + pTier.getWeight(); //TWOHAND SWORD
        this.knockback = 1; //TWOHAND SWORD
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getMaterialHardness() / pTier.getWeight());
        this.actualAttackDamage = attackDamage - 1.0f;
        this.actualAttackSpeed = attackSpeed - 4.0f;
        ;
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND){
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(RPGAttributeUUID.ATTACK_KNOCKBACK_UUID, "Tool modifier", knockback, AttributeModifier.Operation.ADDITION));
            //builder.put(RPGAttributes.ARMOR_PIERCING.get(), new AttributeModifier(RPGAttributeUUID.ARMOR_PIERCING_UUID, "Tool modifier", armorPiercing, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }
    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction);
    }


    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        float toughness = (float) pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offHand = pAttacker.getItemBySlot(EquipmentSlot.OFFHAND);
        ItemStack mainHandTarget = pTarget.getMainHandItem();
        ItemStack offHandTarget = pTarget.getOffhandItem();
        ItemStack head = pTarget.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = pTarget.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = pTarget.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = pTarget.getItemBySlot(EquipmentSlot.FEET);
        if (!pTarget.level.isClientSide() && !pTarget.isBlocking()) {
            //here we do some Debug Stuff on Dummy target
            if (pTarget instanceof ArmorStand) { //only for debug logging
                float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pStack + "-------------------");
                Log.info(pStack + " actualAttackDamage " + actualAttackDamage);
                Log.info(pStack + " attackDamage " + attackDamage);
                Log.info(pStack + " damageByType " + damageByType);
                Log.info(pStack + "-------------------");
                Log.info(pStack + " actualAttackSpeed " + actualAttackSpeed);
                Log.info(pStack + " attackSpeed " + attackSpeed);
                Log.info(pStack + " speedByType " + speedByType);
                Log.info(pStack + "-------------------");
                Log.info(pStack + "  weapon weight " + itemWeight);
                Log.info(pStack + " weapon piercing " + armorPiercing);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
            }
            if (pTarget instanceof Player) {
                //here we check if player has few armor pieces with overall less than 5.0f Def-Rating
                if (pTarget.getArmorValue() <= 5.0F) { //no armor or only some pieces
                    if (new Random().nextInt(10) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                        pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        Log.info(pTarget + " is bleeding");
                    }
                    float finalDamage = armorPiercing + CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                    pTarget.hurt(DamageSource.GENERIC, finalDamage);
                    for (ItemStack itemstack : pTarget.getArmorSlots()) {
                        itemstack.hurtAndBreak(10, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                    }
                    Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                    Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                }
                //here we check if player has light armor-rating (5-9)
                if (pTarget.getArmorValue() <= 9.0F && pTarget.getArmorValue() > 5.0F) { //light armor
                    if (new Random().nextInt(25) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                        pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        Log.info(pTarget + " is bleeding");
                    }
                    if (new Random().nextInt(25) == 1) {
                        Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                        pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                        pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                    }
                    float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                    pTarget.hurt(DamageSource.GENERIC, finalDamage);
                    Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                    Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                }
                //here we check if player has medium armor-rating (9-17)
                if (pTarget.getArmorValue() <= 17.0F && pTarget.getArmorValue() > 9.0F) { //medium armor
                    if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(75) == 1) { //here calc some damage if player has toughness rating
                        Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                        pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                        pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                        if (new Random().nextInt(3) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is bleeding");
                        }
                    } else {
                        if (new Random().nextInt(50) == 1) {//here calc some damage if player has no toughness
                            Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                            pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                            pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                            if (new Random().nextInt(2) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                                pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                                pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                                Log.info(pTarget + " is bleeding");
                            }
                        }
                    }
                    float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                    mainHand.hurtAndBreak(1, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    pTarget.hurt(DamageSource.GENERIC, finalDamage);
                    Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                    ;
                    Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                }
                //here we check if player has heavy armor-rating (20+)
                if (pTarget.getArmorValue() > 17.0F) { //heavy armor
                    if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(100) == 1) {
                        Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                        pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                        pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                        if (new Random().nextInt(3) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is bleeding");
                        }
                    } else {
                        if (new Random().nextInt(75) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is bleeding");
                        }
                    }
                    float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                    mainHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    pTarget.hurt(DamageSource.GENERIC, finalDamage);
                    Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                    Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                }
                head.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.HEAD));
                chest.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.CHEST));
                legs.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.LEGS));
                feet.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.FEET));

            if (pTarget.getOffhandItem().getItem() instanceof ShieldItem && pTarget.isBlocking()) {
                ItemStack blockingStack = pTarget.getOffhandItem();
                blockingStack.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            }
            if (pTarget.getMainHandItem().getItem() instanceof RPGBasicTwoHandMeleeWeapon && pTarget.isBlocking()) {
                ItemStack blockingStack = pTarget.getMainHandItem();
                blockingStack.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            }
            if (offHandTarget.getItem() instanceof ShieldItem && new Random().nextInt(30) == 1) {
                int damageAmount = 20;
                offHandTarget.hurtAndBreak(damageAmount, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                Log.info(pAttacker + " damaged shield of " + pTarget + "with " + damageAmount);
                pTarget.spawnAtLocation(offHandTarget.getItem());
                pTarget.getOffhandItem().shrink(1);
            }
            }
        }
        getCustomDrops(pAttacker, pTarget);
        Log.info(pTarget + "Maxlife: " + pTarget.getMaxHealth());
        Log.info(pTarget + "current Life: " + pTarget.getHealth());
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private void getCustomDrops(LivingEntity pAttacker, LivingEntity pTarget) {
        RPGCombatUtils.getCustomDropsOnHit(pTarget);
        if (pTarget.getHealth() <= actualAttackDamage && new Random().nextInt(10) == 1) {
            pTarget.kill();
            RPGCombatUtils.getGuillotineEffect(pTarget);
            Log.info(pAttacker + " executed " + pTarget);
        }
    }
}


