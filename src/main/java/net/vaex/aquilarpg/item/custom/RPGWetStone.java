package net.vaex.aquilarpg.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.custom.weapon.*;
import net.vaex.aquilarpg.util.RPGTierInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class RPGWetStone extends RPGToolItem {

    Enchantment enchantment;

    public RPGWetStone(RPGTierInterface pTier, Item.Properties pProperties) {
        super(pTier, pProperties);
        this.enchantment = Enchantments.SHARPNESS;

    }

    private void calcDamage(@Nonnull ItemStack tool) {
        Player player = Minecraft.getInstance().player;
        int damageValueTool = tool.getDamageValue();
        int maxDurabilityTool = tool.getMaxDamage();
        if (damageValueTool >= maxDurabilityTool) {
            tool.shrink(1);
            if (player != null) {
                playBreakSound(player);
            }
        } else {
            if (player != null) {
                playToolSound(player);
            }
        }
    }


    private void playToolSound(Entity entity) {
        entity.playSound(SoundEvents.ANVIL_USE, 0.8F, 1.0f);
    }

    private void playBreakSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0f);
    }


    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        String sharpen ="SHARPEN";
        int num = 3;
        Random rand = new Random();
        int ran = rand.nextInt(num) + 1;
        int damageValueTool = pStack.getDamageValue();
        int maxDurabilityTool = pStack.getMaxDamage();
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer) && !pSlot.getItem().isEnchanted() && (pSlot.getItem().getItem() instanceof RPGSwordWeapon ||
                pSlot.getItem().getItem() instanceof RPGDaggerWeapon || pSlot.getItem().getItem() instanceof RPGAxeWeapon || pSlot.getItem().getItem() instanceof RPGTwoHandSwordWeapon ||
                pSlot.getItem().getItem() instanceof RPGSpearWeapon || pSlot.getItem().getItem() instanceof RPGKrisWeapon || pSlot.getItem().getItem() instanceof RPGTwoHandScytheWeapon ||
                pSlot.getItem().getItem() instanceof RPGTwoHandAxeWeapon)) {
            if (!pStack.hasTag()) pStack.setTag(new CompoundTag());
            if (pStack.getTag() != null) {
                pStack.getTag().putString("sharpened_effect", sharpen);
            }
            pSlot.getItem().enchant(Enchantments.SHARPNESS, ran);
            pStack.hurt(ran,new Random(),null);
            if (damageValueTool >= maxDurabilityTool) {
                pStack.shrink(1);
                    playBreakSound(pPlayer);
            } else {
                    playToolSound(pPlayer);
            }
            Log.info(pSlot.getItem().getItem() + " string is:" + pStack.getTag().getString("sharpened_effect"));
            Log.info(pSlot.getItem().getItem() + " has Keys " + pStack.getTag().getAllKeys());

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(new TextComponent("Sharpen: Drag this Item and right click on the Item u want to sharpen").withStyle(ChatFormatting.ITALIC));
            components.add(new TextComponent("This works not on Blunt Weapons!").withStyle(ChatFormatting.DARK_RED).append(String.valueOf(ChatFormatting.UNDERLINE)));
        } else {
            components.add(new TextComponent("A Tool to Sharp Weapons for your next Combat!").withStyle(ChatFormatting.BLUE));
            components.add(new TranslatableComponent("tooltip.aquilairpg.shift"));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

}
