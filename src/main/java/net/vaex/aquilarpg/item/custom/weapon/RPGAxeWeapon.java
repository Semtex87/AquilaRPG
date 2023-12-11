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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolActions;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.entity.item.AxeEntity;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.item.custom.RPGBasicTwoHandMeleeWeapon;
import net.vaex.aquilarpg.network.ManaSyncS2CPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import net.vaex.aquilarpg.util.RPGCombatUtils;
import net.vaex.aquilarpg.util.RPGSoundEvents;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;
import org.lwjgl.system.CallbackI;

import java.util.Random;

public class RPGAxeWeapon extends RPGBasicMeleeWeapon {
    float actualAttackDamage;
    float actualAttackSpeed;


    public RPGAxeWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        //this.rangeByType = 2.5d; //AXE
        this.damageByType = 4.5f; //AXE
        this.speedByType = 1.2f; //AXE
        this.itemWeight = 2.0f + pTier.getWeight(); //AXE
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getWeight() / pTier.getMaterialHardness());
        this.actualAttackDamage = getActualAttackDamage() + damageByType;
        this.actualAttackSpeed = getActualAttackSpeed() + speedByType; //- 4.0f basic attack speed from UUID
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage - 1.0f, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed , AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }


    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        boolean malus = false;
        ItemStack mainHandItem = null;
        ItemStack offHandItem = null;
        if (isHeavyWeapon) {
            if (pEntity instanceof LivingEntity entity && !entity.hasEffect((MobEffects.DAMAGE_BOOST)) && entity.getOffhandItem().sameItem(entity.getMainHandItem())) {
                if (actualAttackDamage != decreasedAttackDamage) {
                    actualAttackDamage = decreasedAttackDamage;
                    malus = true;
                }

                if (actualAttackSpeed != decreasedAttackSpeed) {
                    actualAttackSpeed = decreasedAttackSpeed - itemWeight;
                    malus = true;
                }
            } else {
                if (actualAttackDamage != attackDamage) {
                    actualAttackDamage = attackDamage;
                    malus = true;
                }

                if (actualAttackSpeed != attackSpeed) {
                    actualAttackSpeed = attackSpeed - itemWeight;
                    malus = true;

                }
            }
            if (malus) {

                if (pEntity instanceof LivingEntity) {
                    mainHandItem = ((LivingEntity) pEntity).getMainHandItem();
                    offHandItem = ((LivingEntity) pEntity).getMainHandItem();
                }
                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    if (pEntity instanceof LivingEntity) {
                    ((LivingEntity) pEntity).getAttributes().removeAttributeModifiers(mainHandItem.getAttributeModifiers(equipmentSlot));
                    ((LivingEntity) pEntity).getAttributes().addTransientAttributeModifiers(mainHandItem.getAttributeModifiers(equipmentSlot));
                    ((LivingEntity) pEntity).getAttributes().removeAttributeModifiers(offHandItem.getAttributeModifiers(equipmentSlot));
                    ((LivingEntity) pEntity).getAttributes().addTransientAttributeModifiers(offHandItem.getAttributeModifiers(equipmentSlot));
                }
                }
            }

        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }


    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pstack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 1800;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction) && ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack mainHand = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
        if (!pPlayer.isShiftKeyDown()) {
            return InteractionResultHolder.fail(mainHand);
        }
        if (mainHand.getDamageValue() >= mainHand.getMaxDamage()) {
            return InteractionResultHolder.fail(mainHand);
        } else {
            pPlayer.startUsingItem(InteractionHand.MAIN_HAND);
            pPlayer.startUsingItem(InteractionHand.OFF_HAND);
            return InteractionResultHolder.consume(mainHand);
        }
    }

    @Override //right click on range to throw (like trident)
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player && player.isShiftKeyDown()) {
            ItemStack mainHand = player.getItemBySlot(EquipmentSlot.MAINHAND);
            ItemStack offHand = player.getItemBySlot(EquipmentSlot.OFFHAND);
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                pLevel.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 0.4F);
                if (!pLevel.isClientSide) {
                    if (mainHand.getItem() instanceof RPGAxeWeapon && (offHand.getItem() instanceof RPGAxeWeapon)) {
                        AxeEntity axeEntity = new AxeEntity(pLevel, player, mainHand);
                        AxeEntity axeEntity2 = new AxeEntity(pLevel, player, offHand);
                        float throwingValue = 1.5f;
                        axeEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, throwingValue, 0.5F);
                        axeEntity2.shootFromRotation(player, player.getXRot() - 0.3f, player.getYRot(), 0.0F, throwingValue, 0.4F);
                        if (player.getAbilities().instabuild) {
                            axeEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            axeEntity2.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }
                        pLevel.addFreshEntity(axeEntity);
                        pLevel.addFreshEntity(axeEntity2);
                        player.awardStat(Stats.ITEM_USED.get(this));
                        if (!player.getAbilities().instabuild) {
                            mainHand.shrink(1);
                            offHand.shrink(1);
                        }
                        Log.info(player + "throw " + axeEntity + "with speed of: " + throwingValue + "and " + axeEntity2 + "with speed of: " + throwingValue);
                    } else if (mainHand.getItem() instanceof RPGAxeWeapon) {
                        AxeEntity axeEntity = new AxeEntity(pLevel, player, pStack);
                        float throwingValue = 1.5f;
                        axeEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, throwingValue, 0.5F);
                        if (player.getAbilities().instabuild) {
                            axeEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }
                        pLevel.addFreshEntity(axeEntity);
                        Log.info(player + "throw " + axeEntity + "with speed of: " + throwingValue);
                        player.awardStat(Stats.ITEM_USED.get(this));
                        if (!player.getAbilities().instabuild) {
                            mainHand.shrink(1);
                        }
                    }
                }
            }
        }
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
                Log.info(pStack + " weapon penetration " + armorPenetration);
                Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
            }
            //here we check if player has few armor pieces with overall less than 5.0f Def-Rating
            if (pTarget instanceof Player) {
                if (pTarget.getArmorValue() <= 5.0F) { //no armor or only some pieces
                    if (new Random().nextInt(10) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                        pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                        pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                        Log.info(pTarget + " is bleeding");
                    }
                    float finalDamage = armorPenetration + CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
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
                        Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPenetration);
                        pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                        pTarget.hurt(DamageSource.GENERIC, armorPenetration);
                    }
                    float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                    pTarget.hurt(DamageSource.GENERIC, finalDamage);
                    Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                    Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                }
                //here we check if player has medium armor-rating (9-17)
                if (pTarget.getArmorValue() <= 17.0F && pTarget.getArmorValue() > 9.0F) { //medium armor
                    if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(75) == 1) { //here calc some damage if player has toughness rating
                        Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPenetration);
                        pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                        pTarget.hurt(DamageSource.GENERIC, armorPenetration);
                        if (new Random().nextInt(3) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                            pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                            pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                            Log.info(pTarget + " is bleeding");
                        }
                    } else {
                        if (new Random().nextInt(50) == 1) {//here calc some damage if player has no toughness
                            Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPenetration);
                            pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                            pTarget.hurt(DamageSource.GENERIC, this.getArmorPenetration());
                            if (new Random().nextInt(2) == 1 && (!pTarget.hasEffect(RPGEffectManager.BLEEDING.get()))) {
                                pTarget.addEffect(new MobEffectInstance(RPGEffectManager.BLEEDING.get(), 100, 0, true, true, false));
                                pTarget.playSound(RPGSoundEvents.BLEEDING_EFFECT.get(), 1.5F, 1.0F);
                                Log.info(pTarget + " is bleeding");
                            }
                        }
                    }
                    float finalDamage = CombatRules.getDamageAfterAbsorb(actualAttackDamage, (float) pTarget.getArmorValue(), toughness);
                    mainHand.hurtAndBreak(1, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                    if (pAttacker.getOffhandItem().getItem() instanceof RPGAxeWeapon) {
                        offHand.hurtAndBreak(1, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                    }
                    pTarget.hurt(DamageSource.GENERIC, finalDamage);
                    Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                    ;
                    Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                }
                //here we check if player has heavy armor-rating (20+)
                if (pTarget.getArmorValue() > 17.0F) { //heavy armor
                    if (pTarget.getAttributeValue(Attributes.ARMOR_TOUGHNESS) > 0.0f && new Random().nextInt(100) == 1) {
                        Log.info(pAttacker + " hit " + pTarget + "through armor with pierced damage: " + armorPenetration);
                        pTarget.playSound(RPGSoundEvents.SWORD_IMPACT.get(), 1.5F, 1.0F);
                        pTarget.hurt(DamageSource.GENERIC, armorPenetration);
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
                    if (pAttacker.getOffhandItem().getItem() instanceof RPGAxeWeapon) {
                        offHand.hurtAndBreak(2, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                    }
                    pTarget.hurt(DamageSource.GENERIC, finalDamage);
                    Log.info(pAttacker + " hit " + pTarget + "with" + finalDamage);
                    Log.info(pTarget + " has " + pTarget.getArmorValue() + " armor " + "and " + toughness + " toughness ");
                }
                head.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.HEAD));
                chest.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.CHEST));
                legs.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.LEGS));
                feet.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.FEET));

            if (pTarget.getOffhandItem().getItem() instanceof ShieldItem && pTarget.isBlocking()) {
                ItemStack blockingStack = pTarget.getOffhandItem();
                blockingStack.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            }
            if (pTarget.getMainHandItem().getItem() instanceof RPGBasicTwoHandMeleeWeapon && pTarget.isBlocking()) {
                ItemStack blockingStack = pTarget.getMainHandItem();
                blockingStack.hurtAndBreak((int) getArmorPenetration(), pTarget, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            }
            }
            /*
            if (offHandTarget.getItem() instanceof ShieldItem) {
                int damageAmount = 20;
                offHandTarget.hurtAndBreak(damageAmount, pAttacker, (var) -> var.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                Log.info(pAttacker + " damaged shield of " + pTarget + "with " + damageAmount);
                pTarget.spawnAtLocation(offHandTarget.getItem());
                pTarget.getOffhandItem().shrink(1);
            }*/
        }
        getCustomDrops(pTarget);
        Log.info( pTarget + "Maxlife: " + pTarget.getMaxHealth());
        Log.info( pTarget + "current Life: " + pTarget.getHealth());
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private void getCustomDrops( LivingEntity pTarget) {
        RPGCombatUtils.getCustomDropsOnHit(pTarget);
    }
}


