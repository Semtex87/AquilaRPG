package net.vaex.aquilarpg.item.custom.weapon.offhand;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBookItem;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class TomeOfDefense extends RPGBookItem {


    float defense;
    public TomeOfDefense(RPGMaterialTiers pTier, EquipmentSlot pSlot, Properties pProperties) {
        super(pTier, pSlot, pProperties);
        this.defense =4.0f;

    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.OFFHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ARMOR, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", defense, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(RPGAttributeUUID.MAX_HEALTH_UUID,"Tool modifier", 2.0f, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("This magic Book gives you Defense").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent(slot).withStyle(ChatFormatting.WHITE));
        } else {
            pTooltip.add(new TranslatableComponent("item_tooltip_shift"));
        }
        pTooltip.add(new TextComponent("Material: " + material + " ").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(itemStack, level, pTooltip, pFlag);
    }
}
