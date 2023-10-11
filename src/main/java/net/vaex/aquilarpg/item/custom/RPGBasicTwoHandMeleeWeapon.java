package net.vaex.aquilarpg.item.custom;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;


public class RPGBasicTwoHandMeleeWeapon extends SwordItem {
    public int durability;
    public String materialType;

    public float itemWeight;
    //public double rangeByType;
    public float speedByType;
    public float decreasedAttackDamage;
    public float decreasedAttackSpeed;
    public float increadedAttackDamage;
    public float increasedAttackSpeed;
    public float actualAttackDamage;
    public float actualAttackSpeed;
    public float attackDamage;
    public float attackSpeed;

    public float damageByType;
    public int isLance = 0;
    public float armorPiercing;
    public float armorPenetration;
    public float shieldPenetration;

    public int knockback;
    public int isBlockweapon = 0;
    public boolean canBlock = false;
    public float maxBlockDamage;
    public boolean isFoil;
    public boolean isPoison;
    public boolean isSpiked;
    public boolean fantasyWeapon;
    public boolean isHeavyWeapon;
    public boolean mythicalTwohand;
    boolean isPoleWeapon;
    double weaponSubType;
    public boolean isMaterialSilver = false;
    public float silverDamageModifier;
    public float enhancementValue;
    Ingredient repairMaterial;

    public RPGBasicTwoHandMeleeWeapon(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, 0, 0, pProperties.tab(RPGCreativeModeTab.RPG_WEAPON));
        this.durability = pTier.getUses();
        this.materialType = pTier.getMaterialName();
        this.enhancementValue = pTier.getEnchantmentValue();
        this.armorPiercing = pTier.getMaterialHardness();                                                               //todo aktuell 1.5 wegen MaterialHardness -> Waffen Lanze Axt ggf Schwert?
        this.armorPenetration = pTier.getWeight();
        this.shieldPenetration = pTier.getWeight() + pTier.getMaterialHardness();
        this.repairMaterial = pTier.getRepairIngredient();
        //this.rangeByType = 1.0d; //CLAYMORE
        this.damageByType = 0.0f; //DUMMY
        this.speedByType = 0.0f; //DUMMY
        this.itemWeight = 0.0f + pTier.getWeight(); //DUMMY
        this.knockback = 0; //DUMMY
        this.maxBlockDamage =  (int)(pTier.getWeight() * pTier.getMaterialHardness()); //DUMMY
        //////////////////////////////////////////
        this.attackDamage = damageByType + pTier.getMaterialHardness();
        this.attackSpeed = speedByType + (pTier.getWeight() / pTier.getMaterialHardness());
        this.actualAttackDamage = attackDamage;
        this.actualAttackSpeed = attackSpeed - 4.0f;
        this.decreasedAttackDamage = pTier.getWeight();
        this.decreasedAttackSpeed = pTier.getWeight();

    }


    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        boolean malus = false;
        ItemStack mainHandItem = null;
        ItemStack offHandItem = null;
        if (isHeavyWeapon) {
            if (pEntity instanceof LivingEntity entity && !(entity.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof AirItem)) {
                entity.addEffect(new MobEffectInstance(RPGEffectManager.ENCUMBERED.get()));
                if (actualAttackDamage != decreasedAttackDamage) {
                    actualAttackDamage = decreasedAttackDamage;
                    malus = true;
                }

                if (actualAttackSpeed != decreasedAttackSpeed) {
                    actualAttackSpeed = decreasedAttackSpeed - itemWeight;
                    malus = true;
                }
            }
            if (pEntity instanceof LivingEntity entity && entity.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof RPGBasicTwoHandMeleeWeapon) {
                entity.addEffect(new MobEffectInstance(RPGEffectManager.OVERLOADED.get()));
                if (actualAttackDamage != decreasedAttackDamage) {
                    actualAttackDamage = 0;
                    malus = true;
                }

                if (actualAttackSpeed != decreasedAttackSpeed) {
                    actualAttackSpeed = 0;
                    malus = true;
                }
            }
            if (pEntity instanceof LivingEntity entity && !entity.hasEffect((MobEffects.DAMAGE_BOOST)) && mythicalTwohand) {
                entity.addEffect(new MobEffectInstance(RPGEffectManager.OVERLOADED.get()));
                if (actualAttackDamage != decreasedAttackDamage) {
                    actualAttackDamage = 0;
                    malus = true;
                }

                if (actualAttackSpeed != decreasedAttackSpeed) {
                    actualAttackSpeed = decreasedAttackSpeed - itemWeight;
                    malus = true;
                }
            } else {
                if (actualAttackDamage != attackDamage) {
                    actualAttackDamage = decreasedAttackDamage;
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
                    }
                    if (pEntity instanceof LivingEntity entity && entity.hasItemInSlot(EquipmentSlot.OFFHAND)) {
                        ((LivingEntity) pEntity).getAttributes().removeAttributeModifiers(offHandItem.getAttributeModifiers(equipmentSlot));
                        ((LivingEntity) pEntity).getAttributes().addTransientAttributeModifiers(offHandItem.getAttributeModifiers(equipmentSlot));
                    }
                }
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(RPGAttributeUUID.ATTACK_KNOCKBACK_UUID, "Tool modifier", knockback, AttributeModifier.Operation.ADDITION));
            //builder.put(IRPGAttributes.ARMOR_PIERCING.get(), new AttributeModifier(AttributeUUID.ARMOR_PIERCING_UUID, "Tool modifier", armorPiercing, AttributeModifier.Operation.ADDITION));
            //builder.put(IRPGAttributes.SHIELD_PENETRATION.get(), new AttributeModifier(AttributeUUID.SHIELD_PENETRATION_UUID, "Tool modifier", shieldPenetration, AttributeModifier.Operation.ADDITION));
            //builder.put(IRPGAttributes.ARMOR_PENETRATION.get(), new AttributeModifier(AttributeUUID.ARMOR_PENETRATION_UUID, "Tool modifier", armorPenetration, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }


    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        ItemStack mainHand = pAttacker.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!pTarget.level.isClientSide()) {
            if (this.isMaterialSilver && pTarget.getMobType().equals(MobType.UNDEAD)) {
                pTarget.hurt(DamageSource.MAGIC, this.getActualAttackDamage() + getSilverAttackDamage());
            }
            if (isPoison && new Random().nextInt(5) == 1) {
                if (mainHand.getItem() == this) {
                    if (pAttacker instanceof Player player) {
                        pTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0));
                        pTarget.playSound(SoundEvents.PUFFER_FISH_STING, 1.0F, 1.0F);
                        pTarget.hurt(DamageSource.GENERIC, this.getActualAttackDamage());
                    }
                }
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
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

    public String getMaterialType() {
        return this.materialType;
    }

    public float getArmorPiercing() {
        return this.armorPiercing;
    }

    public float getArmorPenetration() {
        return this.armorPenetration;
    }

    public float getKnockback() {
        return this.knockback;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return this.repairMaterial.test(pRepair);
    }

    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return Mob.getEquipmentSlotForItem(stack) == slot;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    public float getSilverAttackDamage() {
        if (getTier().equals(RPGMaterialTiers.SILVER) || getTier().equals(RPGMaterialTiers.ASTRALSILVER)) {
            isMaterialSilver = true;
            silverDamageModifier = attackDamage + 4.0F;
        }
        return silverDamageModifier;
    }

    public RPGBasicTwoHandMeleeWeapon setFantasy(boolean pFantasy) {
        fantasyWeapon = pFantasy;
        return this;
    }

    public RPGBasicTwoHandMeleeWeapon setMythicalTwoHand(boolean mythicalTwoHand) {//for unique/mythical/god weapons
        mythicalTwohand = mythicalTwoHand;
        return this;
    }

    public RPGBasicTwoHandMeleeWeapon setHeavyTwoHand(boolean heavyOne) {//for huge weapons
        isHeavyWeapon = heavyOne;

        return this;
    }

    public RPGBasicTwoHandMeleeWeapon setPoleWeapon(boolean pLong) {
        boolean isPoleWeapon = pLong;
        weaponSubType = weaponSubType + 0.5d;
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Blockstuff
    public RPGBasicTwoHandMeleeWeapon setBlockWeapon(int blockWeapon) {
        isBlockweapon = blockWeapon;
        this.canBlock = true;
        return this;
    }

    public boolean canBlock() {
        return this.canBlock;
    }

    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
        return this.canBlock();
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 20000;
    }

    public float getMaxBlockDamage() {
        return this.maxBlockDamage;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (this.canBlock()) {
            if (player.getMainHandItem().getItem() instanceof RPGBasicTwoHandMeleeWeapon twoHandMeleeWeapon && twoHandMeleeWeapon.canBlock() && player.getItemBySlot(EquipmentSlot.OFFHAND).is(Items.AIR)) {
                ItemStack itemstack = player.getItemInHand(hand);
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(itemstack);
            }
        }
        return super.use(level, player, hand);
    }

    public void blockSequence(ItemStack stack, float damage, Player player, DamageSource source) {
        float piercingValue = 0;
        if (source.getEntity() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) source.getEntity();
            if (attacker.getMainHandItem().getItem() instanceof RPGBasicTwoHandMeleeWeapon) {
                piercingValue = piercingValue + ((RPGBasicTwoHandMeleeWeapon) attacker.getMainHandItem().getItem()).getArmorPiercing();
            }
        }
        if (new Random().nextInt(60) == 1) {
            player.sendMessage(new TextComponent("Parry !!"), player.getUUID());
            if (source.getEntity() instanceof LivingEntity) {
                source.getEntity().hurt(DamageSource.MAGIC, this.getMaxBlockDamage());
            }
        }

        if (damage > this.getMaxBlockDamage()) {
            stack.hurtAndBreak((int) (piercingValue + getMaxBlockDamage()), player, (var) ->
                    var.broadcastBreakEvent(EquipmentSlot.MAINHAND));

            float damageCalc = damage - this.getMaxBlockDamage();
            float finalDamage = CombatRules.getDamageAfterAbsorb(damageCalc, (float) player.getArmorValue(), (float) player.getAttributeValue(Attributes.ARMOR_TOUGHNESS));

            player.hurt(DamageSource.GENERIC, finalDamage);
            return;
        }
        stack.hurtAndBreak((int) (piercingValue + damage), player, (var) ->
                var.broadcastBreakEvent(EquipmentSlot.MAINHAND));

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TextBox
    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("Two Hand Weapon").withStyle(ChatFormatting.BLUE));
            if (this.mythicalTwohand) {
                pTooltip.add(new TextComponent("Mythic").withStyle(ChatFormatting.BLUE));
            }
            if (this.knockback != 0.0F)
                pTooltip.add(new TextComponent("" + this.getKnockback() + " ").append(new TranslatableComponent("item_weapon_knockback")).withStyle(ChatFormatting.BLUE));
            if (this.isPoleWeapon)
                pTooltip.add(new TextComponent("" + this.getArmorPiercing() + " ").append(new TranslatableComponent("item_weapon_piercing")).withStyle(ChatFormatting.BLUE));
            if (this.isMaterialSilver) {
                pTooltip.add((new TextComponent("+" + this.getSilverAttackDamage() + " ")).append(new TranslatableComponent("item_weapon_silver_damage_undead")).withStyle(ChatFormatting.WHITE));
            }
            if (this.canBlock) {
                pTooltip.add(new TextComponent( String.format("%.1f", getMaxBlockDamage()) + " ").append(new TranslatableComponent("item_weapon_block_damage")).withStyle(ChatFormatting.BLUE));
            }
            if (this.fantasyWeapon) {
                pTooltip.add(new TextComponent("#Fantasy").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
            }
            if (!this.fantasyWeapon){
                pTooltip.add(new TextComponent("#Realistic").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.ITALIC));
            }
            pTooltip.add(new TextComponent(this.getWeight() + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
        } else {
            pTooltip.add(new TranslatableComponent("item_tooltip_shift"));
        }
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
        pTooltip.add(new TextComponent("Material: " + materialType + " ").withStyle(ChatFormatting.BLUE));
    }
}


