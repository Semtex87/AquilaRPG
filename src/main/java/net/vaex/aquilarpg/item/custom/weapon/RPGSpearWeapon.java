package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.capabilities.mana.ClientManaData;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.entity.item.SpearEntity;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.network.ManaSyncS2CPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import java.util.Random;


public class RPGSpearWeapon extends RPGBasicMeleeWeapon {
    public RPGSpearWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        //this.rangeByType = 2.5d; //SPEAR
        this.damageByType = 3.0f; //SPEAR
        this.speedByType = 1.1f; //SPEAR
        this.itemWeight = 2.5f + pTier.getWeight(); //SPEAR
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getWeight() / pTier.getMaterialHardness());
        this.actualAttackDamage = attackDamage;
        this.actualAttackSpeed = attackSpeed - 4.0f; //- 4.0f basic attack speed from UUID
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
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pstack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 1200;
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
        if (pEntityLiving instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                pLevel.playSound((Player)null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!pLevel.isClientSide) {
                    if (pEntityLiving instanceof ServerPlayer serverplayer && !serverplayer.isCreative()) {
                        serverplayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
                            mana.subMana(10);
                            NetworkHandler.sendPacketTo(new ManaSyncS2CPacket(mana.getMana()), serverplayer);
                        });
                    }
                    SpearEntity spearEntity = new SpearEntity(pLevel, player, pStack);
                    float throwingValue = 2.5f;
                    spearEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, throwingValue , 0.5F);
                    if (player.getAbilities().instabuild) {
                        spearEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }
                    pLevel.addFreshEntity(spearEntity);
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
        float thoughness = (float) pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offHand = pAttacker.getItemBySlot(EquipmentSlot.OFFHAND);
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
                pTarget.hurt(DamageSource.GENERIC, finalDamage);
                Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " +  "and " + thoughness + " toughness ");
            }
        }
        Log.info( pTarget + "Maxlife: " + pTarget.getMaxHealth());
        Log.info( pTarget + "current Life: " + pTarget.getHealth());
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }





}


