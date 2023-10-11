package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import java.util.Random;


public class RPGKrisWeapon extends RPGBasicMeleeWeapon {
    public RPGKrisWeapon(RPGMaterialTiers pTier, Properties pProperties) {
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
            //builder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(AttributeUUID.ATK_RNG_UUID, "Attack Reach modifier", 0.0d, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return null;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 20;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                pLevel.playSound(player, player.getX(), player.getY(), player.getZ(),
                        RPGSoundEvents.BLEEDING_EFFECT.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
                if (!pLevel.isClientSide) {
                    if (new Random().nextInt(25) == 1 && (!player.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        player.kill();
                        Log.info(player + " suicided");
                    }else {
                        player.hurt(DamageSource.MAGIC, actualAttackDamage);
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
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
                mainHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                if (pAttacker.getOffhandItem().getItem() instanceof RPGKrisWeapon) {
                    offHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                }
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}


