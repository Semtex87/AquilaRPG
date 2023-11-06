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
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.armor.jewelry.UniqueNeckPendantOfBloodSacrifice;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;


public class RPGBasicJewelry extends TieredItem implements ICurioItem {
    final int durability;
    final String materialType;
    boolean isFoil;
    int ticks;

    public RPGBasicJewelry(RPGMaterialTiers pTier, SlotTypePreset pSlot, Properties pProperties ) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_ARMOR));
        this.materialType = pTier.getMaterialName();
        this.durability = pTier.getUses();
    }

@Override
public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
    int damageValue = itemStack.getDamageValue();
    int maxDurability = itemStack.getMaxDamage();
    int currentDamage = maxDurability - damageValue;
    if (Screen.hasShiftDown()) {
        pTooltip.add(new TextComponent("Someone will be interested on it..").withStyle(ChatFormatting.ITALIC));
    } else {
        pTooltip.add(new TranslatableComponent("tooltip.aquilarpg.shift"));
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
    super.appendHoverText(itemStack, level, pTooltip, pFlag);
}

    public RPGBasicJewelry setFoil(boolean foil)
    {
        isFoil = foil;
        return this;
    }
    @Override
    public boolean isFoil(@NotNull ItemStack itemstack) {
        if (isFoil)
        {
            return true;
        }else{
            return false;
        }
    }
}