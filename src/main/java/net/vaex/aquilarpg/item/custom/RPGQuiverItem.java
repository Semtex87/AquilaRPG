package net.vaex.aquilarpg.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.List;

public class RPGQuiverItem extends RPGBasicJewelry {


    public RPGQuiverItem(RPGMaterialTiers pTier, SlotTypePreset pSlot, Properties pProperties) {
        super(pTier, pSlot, pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(new TextComponent("Better Way to store your Arrows").withStyle(ChatFormatting.YELLOW).append(String.valueOf(ChatFormatting.UNDERLINE)));
        } else {
            components.add(new TextComponent("currently WIP").withStyle(ChatFormatting.BLUE).append(String.valueOf(ChatFormatting.UNDERLINE)));
            components.add(new TranslatableComponent("tooltip.aquilairpg.shift"));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
