package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.extensions.IForgeItem;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.item.custom.RPGBasicTwoHandMeleeWeapon;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import javax.annotation.Nonnull;
import java.util.Random;


public class RPGTFlailWeapon extends RPGBasicMeleeWeapon implements IForgeItem {
    float actualAttackDamage;
    float actualAttackSpeed;
    float decreasedAttackSpeed = 0.0f;
    public boolean swinging;
    public int swingTime;
    public InteractionHand swingingArm;
    private LivingEntity entityLiving;

    public RPGTFlailWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        this.armorPiercing = pTier.getMaterialHardness();
        //this.rangeByType = 3.0d; //FLAIL
        this.damageByType = 10.0f; //FLAIL
        this.speedByType = 0.7f; //FLAIL
        this.itemWeight = 2.0f + pTier.getWeight(); //FLAIL
        this.knockback = 1; //FLAIL
        //////////////////////////////////////////
        this.actualAttackDamage = getActualAttackDamage() + damageByType;
        this.actualAttackSpeed = getActualAttackSpeed() + speedByType; //- 4.0f basic attack speed from UUID
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed - decreasedAttackSpeed, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(RPGAttributeUUID.ATTACK_KNOCKBACK_UUID, "Knockback modifier", knockback, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player && pIsSelected) {
             coolDownCheck(player);
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }


    public static boolean isSwinging(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("swing");
    }

    public static void  setSwinging(ItemStack pStack, boolean pIsCharged) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        compoundtag.putBoolean("swing", pIsCharged);
    }

       public @NotNull AABB getSweepHitBox(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull Entity target){
        return target.getBoundingBox().inflate(2.0D, 0.25D, 2.0D);
    }

    private void coolDownCheck(Player player){
        if (player.getCooldowns().isOnCooldown(player.getItemInHand(InteractionHand.MAIN_HAND).getItem())) {
            decreasedAttackSpeed = 5.0f;
        }else
            decreasedAttackSpeed = 0;
    }

    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        Log.info(player + " hurt this" + entity + " with " + stack +" swing == " + isSwinging(stack));
        return super.onLeftClickEntity(stack, player, entity);
    }
    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        float thoughness = (float) pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offHand = pAttacker.getItemBySlot(EquipmentSlot.OFFHAND);
        ItemStack mainHandTarget = pTarget.getMainHandItem();
        ItemStack offHandTarget = pTarget.getOffhandItem();
        if (!pTarget.level.isClientSide() && !pTarget.isBlocking()) {
            //here we do some Debug Stuff on Dummy target
            if (pTarget instanceof ArmorStand) { //only for debug logging
                float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), thoughness);
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pStack + " has " + actualAttackDamage + " weapon damage ");
                Log.info(pStack + " has " + actualAttackSpeed + " weapon speed ");
                Log.info(pStack + " has " + itemWeight + " weapon weight ");
                Log.info(pStack + " has " + armorPiercing + " weapon piercing");
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + thoughness + " toughness ");
            }
            //here we check if player has few armor pieces with overall less than 5.0f Def-Rating
            if (pTarget.getArmorValue() <= 5.0F) { //no armor or only some pieces
                if (new Random().nextInt(10) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                    pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                    pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                    Log.info(pTarget + " is bleeding");
                }
                float finalDamage = armorPiercing + CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), thoughness) ;
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + thoughness + " toughness ");
            }
            //here we check if player has light armor-rating (5-9)
            if (pTarget.getArmorValue() <= 9.0F && pTarget.getArmorValue() > 5.0F) { //light armor
                if (new Random().nextInt(25) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                    pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                    pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                    Log.info(pTarget + " is bleeding");
                }
                if (new Random().nextInt(25) == 1){
                    Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                    pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                    pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                }
                float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), thoughness);
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + thoughness + " toughness ");
            }
            //here we check if player has medium armor-rating (9-17)
            if (pTarget.getArmorValue() <= 17.0F && pTarget.getArmorValue() > 9.0F ) { //medium armor
                if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(75) == 1){ //here calc some damage if player has toughness rating
                    Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                    pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                    pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                    if (new Random().nextInt(3) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                        pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        Log.info(pTarget + " is bleeding");
                    }
                }else {
                    if (new Random().nextInt(50) == 1){//here calc some damage if player has no toughness
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
                float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), thoughness);
                mainHand.hurtAndBreak(1, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                if (pAttacker.getOffhandItem().getItem() instanceof RPGTFlailWeapon) {
                    offHand.hurtAndBreak(1, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);;
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + thoughness + " toughness ");
            }
            //here we check if player has heavy armor-rating (20+)
            if (pTarget.getArmorValue() > 17.0F) { //heavy armor
                if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(100) == 1){
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
                float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), thoughness);
                mainHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                if (pAttacker.getOffhandItem().getItem() instanceof RPGTFlailWeapon) {
                    offHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + thoughness + " toughness ");
            }

            if (offHandTarget.getItem() instanceof ShieldItem) {
                int damageAmount = 20;
                offHandTarget.hurtAndBreak(damageAmount, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                Log.info(pAttacker + " damaged shield of " + pTarget + "with " + damageAmount);
                pAttacker.getMainHandItem().canDisableShield(mainHandTarget,pTarget,pAttacker);
                //pTarget.spawnAtLocation(offHandTarget.getItem());
                //pTarget.getOffhandItem().shrink(1);
            }
            if (mainHandTarget.getItem() instanceof RPGBasicTwoHandMeleeWeapon && pTarget.isBlocking()) {
                pAttacker.getMainHandItem().canDisableShield(mainHandTarget,pTarget,pAttacker);
                int damageAmount = 20;
                mainHandTarget.hurtAndBreak(damageAmount, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                Log.info(pAttacker + " damaged shield of " + pTarget + "with " + damageAmount);
                //pTarget.spawnAtLocation(offHandTarget.getItem());
                //pTarget.getOffhandItem().shrink(1);
            }

        }
        Log.info( pTarget + "Maxlife: " + pTarget.getMaxHealth());
        Log.info( pTarget + "current Life: " + pTarget.getHealth());

        if (pAttacker instanceof Player player) {
            setSwinging(mainHand,false);
        player.getCooldowns().addCooldown(player.getItemInHand(InteractionHand.MAIN_HAND).getItem(), 40);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}