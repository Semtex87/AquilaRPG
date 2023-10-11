package net.vaex.aquilarpg.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import net.vaex.aquilarpg.util.RPGTierInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class RPGToolItem extends TieredItem {
     int durability;
     int itemWeight;
     boolean isFoil = false;
     String materialType;
    public RPGToolItem(RPGTierInterface pTier, Properties pProperties) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_TOOLS).setNoRepair());
        this.durability = pTier.getUses();
        this.materialType = pTier.getMaterialName();

    }

    public boolean isValidRepairItem(ItemStack pStack, ItemStack pRepairCandidate) {
        return false;
    }

    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack itemStack) {
        final ItemStack copy = itemStack.copy();
        if (copy.hurt(1, new Random(), null)) {
            return ItemStack.EMPTY;
        } else {
            return copy;
        }
    }
    public RPGToolItem setFoil(boolean foil)
    {
        isFoil = foil;
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@NotNull ItemStack itemstack) {
        return isFoil;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }
    public float getDurability() { return this.durability; }

    public String getMaterialType() { return this.materialType; }

    @Override
    @SubscribeEvent
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        int damageValue = itemStack.getDamageValue();
        int maxDurability = itemStack.getMaxDamage();
        int currentDamage = maxDurability - damageValue;
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
        super.appendHoverText(itemStack, level, pTooltip, pFlag);
    }
}
