package net.vaex.aquilarpg.item.custom.weapon.unique;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
import net.vaex.aquilarpg.item.custom.weapon.RPGSwordWeapon;
import net.vaex.aquilarpg.util.RPGCustomUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UniqueSwordSting extends RPGSwordWeapon {
    public UniqueSwordSting(RPGMaterialTiers pTier, Properties pProperties) {
        super(pTier, pProperties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected) {
            if (entity instanceof Player player) {
                RPGCustomUtils.setUndeadDetect(world, entity.getX(), entity.getY(), entity.getZ(), entity);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int var;
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("Sting was an elvish sword created by unknown elvish smiths. The Blade was found in a trolls cave by Gandalf the Grey").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("It glows when Undead (no Orcs) are nearby. ").withStyle(ChatFormatting.AQUA));
            pTooltip.add(new TextComponent("One Hand Weapon").withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + this.getWeight() + " ").append(new TranslatableComponent("item_weapon_weight")).withStyle(ChatFormatting.BLUE));
            pTooltip.add(new TextComponent("" + this.getRange() + " ").append(new TranslatableComponent("item_weapon_range")).withStyle(ChatFormatting.BLUE));
            if (this.armorPiercing != 0.0F)
                pTooltip.add(new TextComponent("" + this.getArmorPiercing() + " ").append(new TranslatableComponent("item_weapon_piercing")).withStyle(ChatFormatting.BLUE));
        } else {
            pTooltip.add(new TranslatableComponent("item_tooltip_shift"));
        }
        if (!itemStack.isDamaged()) {
            pTooltip.add(new TextComponent(durability + " / " + durability).withStyle(ChatFormatting.GREEN));
        } else {
            var = Integer.parseInt(itemStack.getTag().getString("damage"));
            if (var >= (durability * 90) / 100) {
                pTooltip.add(new TextComponent(var + " / " + durability).withStyle(ChatFormatting.GREEN));
            }
            if (var < (durability * 90) / 100 && var >= (durability * 20) / 100) {
                pTooltip.add(new TextComponent(var + " / " + durability).withStyle(ChatFormatting.YELLOW));
            }
            if (var <= (durability * 20) / 100) {
                pTooltip.add(new TextComponent(var + " / " + durability).withStyle(ChatFormatting.RED));
            }
        }
        pTooltip.add(new TextComponent("Material: " + materialType + " ").withStyle(ChatFormatting.BLUE));
    }
}

