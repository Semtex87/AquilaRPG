package net.vaex.aquilarpg.item.custom;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;

import java.util.List;
import java.util.Random;


public class RPGBasicMeleeWeapon extends SwordItem {
    public int durability;
    public String materialType;


    public float itemWeight;
    public double rangeByType;
    public float speedByType;
    public float attackDamage;
    public float attackSpeed;
    public float decreasedAttackDamage;
    public float decreasedAttackSpeed;
    public float increadedAttackDamage;
    public float increasedAttackSpeed;
    public float actualAttackDamage;
    public float actualAttackSpeed;
    public float armorPiercing;
    public float armorPenetration;
    public float shieldPenetration;
    public float damageByType;
    public int knockback;
    public int enhancementValue;
    boolean isFoil;
    public boolean isSilver;
    public boolean mythicalOneHand;
    public float silverDamageModifier = 2.0f;
    public boolean isPoison;
    public boolean isSpiked;
    public boolean isHeavyWeapon;
    public boolean fantasyWeapon;

    public float maxBlockDamage;
    public boolean throwWeapon;
    Ingredient repairMaterial;

    List<Integer> list = Lists.newArrayList();
    int enchantmentPoolSize = 0;


    public RPGBasicMeleeWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, 0, 0, pProperties.tab(RPGCreativeModeTab.RPG_WEAPON));
        this.durability = pTier.getUses();
        this.materialType = pTier.getMaterialName();
        this.armorPiercing = pTier.getMaterialHardness();
        this.armorPenetration = pTier.getWeight();
        this.shieldPenetration = pTier.getWeight() + pTier.getMaterialHardness();
        this.repairMaterial = pTier.getRepairIngredient();
        this.rangeByType = 0.0d; //DUMMY
        this.damageByType = 0.0f; //DUMMY
        this.speedByType = 0.0f; //DUMMY
        this.itemWeight = 0.0f + pTier.getWeight(); //DUMMY
        this.knockback = 0; //DUMMY
        this.enhancementValue = pTier.getEnchantmentValue();
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getWeight() / pTier.getMaterialHardness());
        this.actualAttackDamage = attackDamage;
        this.actualAttackSpeed = attackSpeed - 4.0f;
        this.decreasedAttackDamage =  pTier.getWeight();
        this.decreasedAttackSpeed =  pTier.getWeight();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND)
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(RPGAttributeUUID.ATTACK_KNOCKBACK_UUID, "Knockback modifier", knockback, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    public RPGBasicMeleeWeapon setThrowable(boolean pThrowable) {
        throwWeapon = pThrowable;
        return this;
    }
    public RPGBasicMeleeWeapon setPoison(boolean pPoison) {
        isPoison = pPoison;
        return this;
    }

    public RPGBasicMeleeWeapon setHeavyOneHand(boolean heavyOne) {//for orkweapons
        isHeavyWeapon = heavyOne;

        return this;
    }
    public RPGBasicMeleeWeapon setSilver(boolean pSilver) {
        isSilver = pSilver;
        return this;
    }
    public RPGBasicMeleeWeapon setMythicalOneHand(boolean mythical) { //for unique/mythical/god weapons
        mythicalOneHand = mythical;
        return this;
    }
    public RPGBasicMeleeWeapon setFantasy(boolean pFantasy) {
        fantasyWeapon = pFantasy;
        return this;
    }
    public float getActualAttackSpeed() {
        return actualAttackSpeed;
    }
    public float getActualAttackDamage() {
        return this.actualAttackDamage;
    }
    public float getAttackSpeed() {
        return attackSpeed;
    }
    public float getAttackDamage() {
        return this.attackDamage;
    }
    public float getDurability() {
        return this.durability;
    }
    public float getWeight() {
        return this.itemWeight;
    }
    public int getEnhancementValue() {
        return this.enhancementValue;
    }
    public String getMaterialType() {
        return this.materialType;
    }

    public float getArmorPiercing() {
        return this.armorPiercing;
    }

    public float getArmorPenetration() {
        return this.armorPenetration;
    }

    public double getRange() {
        return this.rangeByType;
    }

    public float getKnockback() {
        return this.knockback;
    }


    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 72000;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!pTarget.level.isClientSide()) {
            if (isSilver && pTarget.getMobType().equals(MobType.UNDEAD)) {
                pTarget.hurt(DamageSource.MAGIC, actualAttackDamage + silverDamageModifier);
            }
            if (isPoison && new Random().nextInt(5) == 1) {
                if (mainHand.getItem() == this) {
                    if (pAttacker instanceof Player) {
                        pTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
                        pTarget.playSound(SoundEvents.PUFFER_FISH_STING, 1.0F, 1.0F);
                        pTarget.hurt(DamageSource.GENERIC, this.getActualAttackDamage());
                    }
                }
            }
        }
        checkEnchantments(pStack, pTarget.getLevel());
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return mythicalOneHand ?  this.repairMaterial.test(pRepair): pRepair.is(RPGItems.MAGIC_DUST.get());
    }

    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return Mob.getEquipmentSlotForItem(stack) == slot;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book){
        return true;
    }

    private void checkEnchantments(ItemStack stack, Level level) {
        if (!level.isClientSide()) {
            if (stack.isEnchanted()) {
                enhancementValue = stack.getEnchantmentTags().size();
                Log.info(enhancementValue);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("One Hand Weapon").withStyle(ChatFormatting.BLUE));
            if (!itemStack.isEnchanted()) {
                pTooltip.add(new TextComponent("Enchant Capacity: " + this.getEnchantmentValue()).withStyle(ChatFormatting.BLUE));
            }
            if (itemStack.isEnchanted()) { //todo
                pTooltip.add(new TextComponent("todo....").withStyle(ChatFormatting.BLUE));
            }
            if (itemStack.getTag() != null && itemStack.getTag().contains("Enchantment")) {
                pTooltip.add(new TextComponent("Tag: " + getEnhancementValue()).withStyle(ChatFormatting.BLUE));
            }
            if (itemStack.hasTag()) {
                pTooltip.add(new TextComponent("No Tags: " + itemStack.getTag().getAllKeys()).withStyle(ChatFormatting.BLUE));
                if (itemStack.getTag().getBoolean("sharpened_effect")) {
                    pTooltip.add(new TextComponent("Boolean is: " + itemStack.getTag().getBoolean("sharpened_effect")).withStyle(ChatFormatting.BLUE));
                }
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
            }
            if (!this.fantasyWeapon){
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



    @Override
    public boolean isFoil(@NotNull ItemStack itemstack) {
        return (itemstack.isEnchanted()) || isFoil;
    }
}




