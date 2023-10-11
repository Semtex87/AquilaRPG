package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolActions;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicTwoHandMeleeWeapon;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import net.vaex.aquilarpg.util.RPGAttributes;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jline.utils.Log;

import java.util.Random;

public class RPGTwoHandBluntWeapon extends RPGBasicTwoHandMeleeWeapon {
    public RPGTwoHandBluntWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        this.armorPenetration = pTier.getWeight() *pTier.getWeight(); //TWOHAND SWORD
        this.shieldPenetration = pTier.getWeight()* pTier.getWeight(); //TWOHAND SWORD
        //this.rangeByType = 4.0d + weaponSubType; //TWOHAND SWORD
        this.damageByType = 7.0f; //TWOHAND SWORD
        this.speedByType = 1.1f; //TWOHAND SWORD
        this.itemWeight = 8.0f + pTier.getWeight(); //TWOHAND SWORD
        this.knockback = (int) pTier.getWeight(); //TWOHAND SWORD
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getWeight();
        this.attackSpeed = speedByType + (pTier.getMaterialHardness() / pTier.getWeight());
        this.actualAttackDamage = attackDamage - 1.0f;
        this.actualAttackSpeed = attackSpeed - 4.0f;

    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND){
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(RPGAttributeUUID.ATTACK_KNOCKBACK_UUID, "Tool modifier", knockback, AttributeModifier.Operation.ADDITION));
            //builder.put(RPGAttributes.ARMOR_PENETRATION.get(), new AttributeModifier(RPGAttributeUUID.ARMOR_PENETRATION_UUID, "Tool modifier", armorPenetration, AttributeModifier.Operation.ADDITION));
            //builder.put(RPGAttributes.SHIELD_PENETRATION.get(), new AttributeModifier(RPGAttributeUUID.SHIELD_PENETRATION_UUID, "Tool modifier", shieldPenetration, AttributeModifier.Operation.ADDITION));
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
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!pTarget.level.isClientSide()) {
            calcDamageHeavyArmor(pStack,pTarget,pAttacker);



            Log.info(pAttacker + " hit " + pTarget);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private void onHitTarget(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Player player = Minecraft.getInstance().player;
    }
    private void onHitBlockingTarget(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Player player = Minecraft.getInstance().player;
    }




    private void calcDamageHeavyArmor(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        float toughness = (float) pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offHand = pAttacker.getItemBySlot(EquipmentSlot.OFFHAND);
        ItemStack head = pTarget.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = pTarget.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = pTarget.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = pTarget.getItemBySlot(EquipmentSlot.FEET);
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

            head.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.HEAD));
            chest.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.CHEST));
            legs.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.LEGS));
            feet.hurtAndBreak((int) armorPiercing, pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.FEET));

            pTarget.hurt(DamageSource.GENERIC, finalDamage);
            Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
            Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
        }
    }
    private void calcDamageMediumArmor(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Player player = Minecraft.getInstance().player;
    }
    private void calcDamageLightArmor(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Player player = Minecraft.getInstance().player;
    }
    private void calcDamageNoArmor(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Player player = Minecraft.getInstance().player;
    }














}
