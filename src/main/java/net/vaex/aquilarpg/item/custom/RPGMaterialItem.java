package net.vaex.aquilarpg.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import net.vaex.aquilarpg.util.RPGTierInterface;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RPGMaterialItem extends TieredItem {

    int itemWeight;
    String materialType;

    public RPGMaterialItem(RPGTierInterface pTier, Properties pProperties) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_MISC));
        this.materialType = pTier.getMaterialName();

    }

    public String getMaterialType() { return this.materialType; }

    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("Material: " + this.getMaterialType() + " ").withStyle(ChatFormatting.BLUE));
        } else {
            pTooltip.add(new TranslatableComponent("tooltip.aquilarpg.shift"));
        }
    }
}
