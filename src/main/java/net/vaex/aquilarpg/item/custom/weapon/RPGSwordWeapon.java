package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jline.utils.Log;

import java.util.Random;


public class RPGSwordWeapon extends RPGBasicMeleeWeapon {
    public RPGSwordWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        this.armorPiercing = 0; //SWORD
        //this.rangeByType = 2.8d; //SWORD OR SWORD + LONGWORD
        this.damageByType = 4.0f; //SWORD
        this.speedByType = 1.6f; //SWORD
        this.itemWeight = itemWeight + 1.4f;
        this.knockback = 0; //SWORD
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getWeight() / pTier.getMaterialHardness());
        this.actualAttackDamage = getActualAttackDamage() + damageByType;
        this.actualAttackSpeed = getActualAttackSpeed() + speedByType; //- 4.0f basic attack speed from UUID
    }

    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage - 1.0f, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed, AttributeModifier.Operation.ADDITION));
            //builder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(AttributeUUID.ATK_RNG_UUID, "Attack Reach modifier", 0.0d, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }



    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Level pLevel = pAttacker.getLevel();
        float toughness = (float) pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offHand = pAttacker.getItemBySlot(EquipmentSlot.OFFHAND);
        ItemStack mainHandTarget = pTarget.getMainHandItem();
        ItemStack offHandTarget = pTarget.getOffhandItem();
        if (!pTarget.level.isClientSide() && !pTarget.isBlocking()) {
            //here we check if player has few armor pieces with overall less than 5.0f Def-Rating
            if (pTarget.getArmorValue() <= 5.0F) { //no armor or only some pieces
                if (new Random().nextInt(20) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                    pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                    pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                    Log.info(pTarget + " is bleeding");
                }
                float finalDamage = armorPiercing + CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness) ;
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + toughness + " toughness ");
            }
            //here we check if player has light armor-rating (5-9)
            if (pTarget.getArmorValue() <= 9.0F && pTarget.getArmorValue() > 5.0F) { //light armor
                if (new Random().nextInt(50) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                    pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                    pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                    Log.info(pTarget + " is bleeding");
                }
                if (new Random().nextInt(50) == 1){
                    Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                    pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                    pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                }
                float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + toughness + " toughness ");
            }
            //here we check if player has medium armor-rating (9-17)
            if (pTarget.getArmorValue() <= 17.0F && pTarget.getArmorValue() > 9.0F ) { //medium armor
                if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(100) == 1){ //here calc some damage if player has toughness rating
                    Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                    pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                    pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                    if (new Random().nextInt(3) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                        pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        Log.info(pTarget + " is bleeding");
                    }
                }else {
                    if (new Random().nextInt(125) == 1){//here calc some damage if player has no toughness
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
                if (pAttacker.getOffhandItem().getItem() instanceof RPGSwordWeapon) {
                    offHand.hurtAndBreak(1, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);;
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + toughness + " toughness ");
            }
            //here we check if player has heavy armor-rating (20+)
            if (pTarget.getArmorValue() > 17.0F) { //heavy armor
                if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(150) == 1){
                    Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                    pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                    pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                    if (new Random().nextInt(3) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                        pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        Log.info(pTarget + " is bleeding");
                    }
                }else {
                    if (new Random().nextInt(75) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                        pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        Log.info(pTarget + " is bleeding");
                    }
                }
                float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                mainHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                if (pAttacker.getOffhandItem().getItem() instanceof RPGSwordWeapon) {
                    offHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }

                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + toughness + " toughness ");
            }
        }
        //Log.info( pTarget + "Maxlife: " + pTarget.getMaxHealth());
        //Log.info( pTarget + "current Life: " + pTarget.getHealth());
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}

