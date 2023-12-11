package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.entity.item.DaggerEntity;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import java.util.Random;


public class RPGDaggerWeapon extends RPGBasicMeleeWeapon {
    public RPGDaggerWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        //this.rangeByType = 1.5d; //DAGGER
        this.damageByType = 2.0f; //DAGGER
        this.speedByType = 2.0f; //DAGGER
        this.itemWeight = itemWeight + 0.5f;
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getWeight() /pTier.getMaterialHardness());
        this.actualAttackDamage = attackDamage;
        this.actualAttackSpeed = attackSpeed - 4.0f; //- 4.0f basic attack speed from UUID
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage - 1.0f, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override //right click on enemy in melee range (stab)
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack pStack, @NotNull Player pAttacker, @NotNull LivingEntity pTarget, @NotNull InteractionHand pHand) {
        if (pTarget.isAlive()) {
            if (!pTarget.getLevel().isClientSide()) {
                if ((pAttacker.getMainHandItem().getItem() instanceof RPGDaggerWeapon)) {
                    if (!(pTarget.getMobType().equals(MobType.UNDEAD))) {
                        if ((pAttacker.isShiftKeyDown())) {
                            if (!isLookingAtMe(pAttacker,pTarget) ||!pTarget.hasLineOfSight(pAttacker)) {
                                pTarget.hurt(DamageSource.GENERIC, actualAttackDamage * 2);
                                pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            }else {
                                pTarget.hurt(DamageSource.GENERIC, actualAttackDamage);
                            }
                        }
                        pStack.hurtAndBreak(1, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    }else {
                        pTarget.hurt(DamageSource.GENERIC, actualAttackDamage);
                    }
                }
                return InteractionResultHolder.consume(pStack).getResult();
            }
            return InteractionResult.sidedSuccess(true);
        }
        return InteractionResult.PASS;
    }

    boolean isLookingAtMe(Player pPlayer, Entity target) {
            Vec3 vec3 = pPlayer.getViewVector(1.0F).normalize();
            Vec3 vec31 = new Vec3(target.getX() - pPlayer.getX(), target.getEyeY() - pPlayer.getEyeY(), target.getZ() - pPlayer.getZ());
            double d0 = vec31.length();
            vec31 = vec31.normalize();
            double d1 = vec3.dot(vec31);
            return d1 > 1.0D - 0.025D / d0 && pPlayer.hasLineOfSight(target);

    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pstack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 600;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pPlayer.isShiftKeyDown()) {
            return InteractionResultHolder.fail(itemstack);
        }
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override //right click on range to throw (like trident)
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player && player.isShiftKeyDown()) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                pLevel.playSound(player, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.5F);
                if (!pLevel.isClientSide) {
                    DaggerEntity daggerEntity = new DaggerEntity(pLevel, player, pStack);
                    float throwingValue = 4.0f;
                    daggerEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, throwingValue, 0.5F);
                    if (player.getAbilities().instabuild) {
                        daggerEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }
                    pLevel.addFreshEntity(daggerEntity);
                    Log.info(player + "throwed dagger with speed of: " + throwingValue);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    pStack.shrink(1);
                }
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Level pLevel = pAttacker.getLevel();
        float thoughness = (float) pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offHand = pAttacker.getItemBySlot(EquipmentSlot.OFFHAND);
        ItemStack mainHandTarget = pTarget.getMainHandItem();
        ItemStack offHandTarget = pTarget.getOffhandItem();
        int maxDurability = pStack.getMaxDamage();
        int currentDamage = (maxDurability - 1) - pStack.getDamageValue();
        String itmdamage = "" + currentDamage;
        if (!pStack.hasTag()) pStack.setTag(new CompoundTag());
        pStack.getTag().putString("damage", itmdamage);
        if (!pTarget.level.isClientSide() && !pTarget.isBlocking()) {
            if (!pTarget.hasLineOfSight(pAttacker) && pAttacker.isShiftKeyDown() ){
                pTarget.kill();
                Log.info(pTarget + " killed ");
            }
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
                if (new Random().nextInt(25) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
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
                if (new Random().nextInt(75) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                    pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                    pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                    Log.info(pTarget + " is bleeding");
                }
                if (new Random().nextInt(50) == 1){
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
                    if (new Random().nextInt(125) == 1){
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
                mainHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                if (pAttacker.getOffhandItem().getItem() instanceof RPGDaggerWeapon) {
                    offHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);;
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + thoughness + " toughness ");
            }
            //here we check if player has heavy armor-rating (20+)
            if (pTarget.getArmorValue() > 17.0F) { //heavy armor
                if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(200) == 1){
                    Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPiercing);
                    pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                    pTarget.hurt(DamageSource.GENERIC, armorPiercing);
                    if (new Random().nextInt(3) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                        pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        Log.info(pTarget + " is bleeding");
                    }
                }else {
                    if (new Random().nextInt(150) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                        pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        Log.info(pTarget + " is bleeding");
                    }
                }
                float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), thoughness);
                mainHand.hurtAndBreak(3, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                if (pAttacker.getOffhandItem().getItem() instanceof RPGDaggerWeapon) {
                    offHand.hurtAndBreak(3, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + thoughness + " toughness ");
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

}


