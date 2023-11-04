package net.vaex.aquilarpg.item.custom.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBasicMeleeWeapon;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import net.vaex.aquilarpg.util.RPGCustomUtils;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import java.util.Random;

import static net.minecraft.world.entity.EquipmentSlot.MAINHAND;


public class RPGLanceWeapon extends RPGBasicMeleeWeapon {

    private boolean isRaised;

    EquipmentSlot slot;

    public RPGLanceWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        this.damageByType = 2.5f; //LANCE
        this.speedByType = 0.5f; //LANCE
        this.itemWeight = 3.4f + pTier.getWeight(); //LANCE
        this.knockback = 1; //LANCE
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getMaterialHardness() / pTier.getWeight());
        this.actualAttackDamage = attackDamage - 1.0f;
        this.actualAttackSpeed = attackSpeed - 4.0f;
        this.slot = MAINHAND;

    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(RPGAttributeUUID.ATTACK_KNOCKBACK_UUID, "Knockback modifier", knockback, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player && pIsSelected) {
            if (player.isPassenger()) {
                if (!isRaised(pStack)) {
                    RPGCustomUtils.killonSight(pLevel, pEntity.getX(), pEntity.getY(), pEntity.getZ(), pEntity);
                }
            }
            if (!player.isPassenger()) {
                if (isRaised(pStack)) {
                    setRaised(pStack, false);
                }
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }


    public static boolean isRaised(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("raised");
    }

    public static void setRaised(ItemStack pStack, boolean pIsCharged) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        compoundtag.putBoolean("raised", pIsCharged);
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.NONE;
    }

    public boolean getRaised() {
        return isRaised;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        return InteractionResult.PASS;
    }
    public void setRiseAfterHit(Player player, boolean raised) {
        isRaised = raised;
        if (player != null) {
            ItemStack pStack = player.getItemBySlot(MAINHAND);
            setRaised(pStack, isRaised);
        }
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level,Player player, @NotNull InteractionHand hand) {
        ItemStack mainHand = player.getItemInHand(hand);
        ItemStack shieldHand = player.getOffhandItem();
        if (!player.isPassenger()) {
            setRaised(mainHand, false);
            isRaised = false;
            return InteractionResultHolder.fail(mainHand);
        } else {
            if (shieldHand.getItem() instanceof ShieldItem) {
                setRaised(mainHand, false);
                isRaised = false;
                return InteractionResultHolder.fail(mainHand);
            } else {
                setRaised(mainHand, false);
                isRaised = false;
                return InteractionResultHolder.consume(mainHand);
            }
        }
    }



    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (!isRaised) {
            ItemStack mainHand = pAttacker.getItemBySlot(MAINHAND);
            ItemStack offHand = pAttacker.getItemBySlot(EquipmentSlot.OFFHAND);
            ItemStack mainHandTarget = pTarget.getMainHandItem();
            ItemStack offHandTarget = pTarget.getOffhandItem();
            if (!pTarget.level.isClientSide()) {
                if (pTarget.isPassenger() && !(pAttacker.isPassenger()) && new Random().nextInt(10) == 1) {
                    if (mainHand.getItem() == this) {
                        if (pAttacker instanceof Player player) {
                            pTarget.hurt(DamageSource.GENERIC, actualAttackDamage + 4.0f);
                            pTarget.knockback(knockback, knockback, 0);
                            if (new Random().nextInt(50) == 1) ;
                            pStack.hurtAndBreak(pStack.getMaxDamage(), pAttacker,
                                    p -> p.broadcastBreakEvent(pAttacker.getUsedItemHand()));
                        }
                    }
                }
                if (pAttacker.isPassenger()) {
                    if (mainHand.getItem() == this) {
                        if (pAttacker instanceof Player player) {
                            pTarget.hurt(DamageSource.GENERIC, actualAttackDamage);

                            if (new Random().nextInt(3) == 1 && !pTarget.isBlocking()) {
                                pTarget.hurt(DamageSource.GENERIC, actualAttackDamage);
                            }
                            if (new Random().nextInt(3) == 1) {
                                pTarget.stopRiding();
                                Log.info(pTarget + " stopped riding");
                            }


                            //TODO DISARM TESTING
                            if (offHandTarget.getItem() instanceof ShieldItem) {
                                Log.info(pAttacker + " disarmed " + pTarget + "dropped: " + offHandTarget);
                                pTarget.spawnAtLocation(offHandTarget.getItem());
                                pTarget.getOffhandItem().shrink(1);
                                if (!isRaised(mainHand)) {
                                    player.spawnAtLocation(Items.STICK);
                                    mainHand.hurtAndBreak(mainHand.getMaxDamage(), pAttacker, (var) -> var.broadcastBreakEvent(MAINHAND));
                                    if (pAttacker.getRootVehicle().hasImpulse) {
                                        pTarget.hurt(DamageSource.GENERIC, actualAttackDamage);
                                    }
                                }
                            }


                            pTarget.knockback(knockback, knockback, 0);
                            pTarget.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 1, true, true, true));
                            if (new Random().nextInt(80) == 1) ;
                            pStack.hurtAndBreak(pStack.getMaxDamage(), pAttacker,
                                    p -> p.broadcastBreakEvent(pAttacker.getUsedItemHand()));
                        }
                    }
                }
            }
        }
        Log.info(pTarget + "Maxlife: " + pTarget.getMaxHealth());
        Log.info(pTarget + "current Life: " + pTarget.getHealth());
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

}



