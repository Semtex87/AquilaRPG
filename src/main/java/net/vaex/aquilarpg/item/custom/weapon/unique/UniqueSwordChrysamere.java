package net.vaex.aquilarpg.item.custom.weapon.unique;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.weapon.RPGTwoHandSwordWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UniqueSwordChrysamere extends RPGTwoHandSwordWeapon {
    @NotNull Item repairMaterial;
    public UniqueSwordChrysamere(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
        this.repairMaterial = RPGItems.MAGIC_DUST.get();
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            if (entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(RPGEffectManager.HEALTHINCREASE.get(), 200, 1,true,true,true));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE));
                if (player.isBlocking()){
                        player.addEffect(new MobEffectInstance(RPGEffectManager.REFLECTION.get()));
                }
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            //builder.put(IRPGAttributes.STRENGTH.get(), new AttributeModifier(AttributeUUID.STRENGTH_UUID, "Tool modifier", 2, AttributeModifier.Operation.ADDITION));
            //builder.put(IRPGAttributes.WISDOM.get(), new AttributeModifier(AttributeUUID.WISDOM_UUID, "Tool modifier", 1, AttributeModifier.Operation.ADDITION));
            //builder.put(IRPGAttributes.VITALITY.get(), new AttributeModifier(AttributeUUID.VITALITY_UUID, "Tool modifier", 5, AttributeModifier.Operation.ADDITION));
            //builder.put(IRPGAttributes.ELEMENTAL_PYRO_RESIST.get(), new AttributeModifier(AttributeUUID.ELEMENTAL_PYRO_RESIST_UUID, "Tool modifier", 10, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", actualAttackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", actualAttackSpeed, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }


    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.is(RPGItems.MAGIC_DUST.get());
    }

    public boolean canEquip(ItemStack stack, EquipmentSlot slot, Entity entity) {
        return Mob.getEquipmentSlotForItem(stack) == slot;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book){
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@NotNull ItemStack itemstack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("an ancient claymore with offensive capabilities surpassed only by its own defenses.").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("lends the wielder health, protects him or her from fire, and reflects any spells cast against the wielder back to the caster.").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("-" + "Yagrum Bagarn").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GOLD));
            pTooltip.add(new TextComponent("Two Hand Weapon").withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("+ Fire Resist").withStyle(ChatFormatting.AQUA));
            pTooltip.add(new TextComponent("+ 2 Health").withStyle(ChatFormatting.AQUA));
            pTooltip.add(new TextComponent("+ Projectile Reflection when blocking").withStyle(ChatFormatting.AQUA));
            pTooltip.add(new TextComponent("" + this.getWeight() + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("Block Damage" + getMaxBlockDamage() + " ").withStyle(ChatFormatting.BLUE));
        } else {
            pTooltip.add(new TranslatableComponent("item_tooltip_shift"));
        }
        if (!itemStack.isDamaged()) {
            pTooltip.add(new TextComponent(durability + " / " + durability).withStyle(ChatFormatting.GREEN));
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
