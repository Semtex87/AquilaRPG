package net.vaex.aquilarpg.item.custom.weapon.offhand;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.RPGBookItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class BookOfDoom extends RPGBookItem {
    public BookOfDoom(RPGMaterialTiers pTier, EquipmentSlot pSlot, Properties pProperties) {
        super(pTier, pSlot, pProperties);

    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        if (entity instanceof ServerPlayer player) {
            if(player.hasItemInSlot(EquipmentSlot.OFFHAND)) {
               player.addEffect(new MobEffectInstance(RPGEffectManager.UNHOLY_STRENGTH.get()));
               player.addEffect(new MobEffectInstance(RPGEffectManager.HEALTHINCREASE.get()));
            }
        }
        super.inventoryTick(itemstack, world, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("This magic Book restores Mana while holding").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent(slot).withStyle(ChatFormatting.WHITE));
        } else {
            pTooltip.add(new TranslatableComponent("tooltip.aquilarpg.shift"));
        }
        pTooltip.add(new TextComponent("Material: " + material + " ").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(itemStack, level, pTooltip, pFlag);
    }
}
