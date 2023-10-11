package net.vaex.aquilarpg.item.custom.weapon.offhand;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.capabilities.mana.ClientManaData;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBookItem;
import net.vaex.aquilarpg.util.RPGAttributeUUID;
import net.vaex.aquilarpg.util.RPGAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;


public class TomeOfManaflow extends RPGBookItem {

    int manaValue = 10;
        public TomeOfManaflow(RPGMaterialTiers pTier, EquipmentSlot pSlot, Properties pProperties) {
        super(pTier, pSlot, pProperties);

    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        final Random random = new Random();
        if (entity instanceof Player player) {
            if (player.getItemInHand(InteractionHand.OFF_HAND).is(itemstack.getItem())) {
                int currentMana = ClientManaData.getPlayerMana(player);
                if (currentMana > 0) {
                    player.addEffect(new MobEffectInstance(RPGEffectManager.MANA_SHIELD.get(), 99999999, 0, true, true));
                    world.addParticle(ParticleTypes.ENCHANT, entity.getRandomX(0.5D), entity.getRandomY() - 0.25D, entity.getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2.0D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2.0D);

                } else {
                    player.removeEffect(RPGEffectManager.MANA_SHIELD.get());
                }
            } else {
                player.removeEffect(RPGEffectManager.MANA_SHIELD.get());
            }
        }
        super.inventoryTick(itemstack, world, entity, slot, selected);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.OFFHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            //builder.put(RPGAttributes.MANA.get(), new AttributeModifier(RPGAttributeUUID.MANA_UUID,"Tool modifier", 2.0f, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot, stack);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("This magic Book restores Mana while holding").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent(slot).withStyle(ChatFormatting.WHITE));
        } else {
            pTooltip.add(new TranslatableComponent("item_tooltip_shift"));
        }
        pTooltip.add(new TextComponent("Material: " + material + " ").withStyle(ChatFormatting.BLUE));
        pTooltip.add(new TextComponent("Offhand: " + "Mana Regeneration ").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(itemStack, level, pTooltip, pFlag);
    }
}
