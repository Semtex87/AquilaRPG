package net.vaex.aquilarpg.item.custom;


import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
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
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;
import net.vaex.aquilarpg.util.RPGTierInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;


public class RPGRepairItem extends RPGToolItem {
    public RPGRepairItem(RPGTierInterface pTier, Item.Properties pProperties) {
        super(pTier, pProperties.tab(RPGCreativeModeTab.RPG_TOOLS));
        this.durability = pTier.getUses();
        this.materialType = pTier.getMaterialName();

    }

    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack itemStack) {
        final ItemStack copy = itemStack.copy();
        if (copy.hurt(1, new Random(), null)) {
            calcDamage(itemStack);
            return ItemStack.EMPTY;
        } else {
            return copy;
        }
    }

    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        int damageValueToRepair = pSlot.getItem().getDamageValue();
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer) && pSlot.getItem().isDamaged() && !(pSlot.getItem().getItem() instanceof RPGRepairItem) &&
                !(pSlot.getItem().getItem() instanceof RPGBasicTwoHandMeleeWeapon twoHandMeleeWeapon && twoHandMeleeWeapon.mythicalTwohand || pSlot.getItem().getItem() instanceof RPGBasicMeleeWeapon meleeWeapon && meleeWeapon.mythicalOneHand)) {
            pPlayer.playSound(SoundEvents.ANVIL_USE,1.0f,0);
            pSlot.getItem().hurt(damageValueToRepair * (-1), new Random(), null);
            pStack.hurt(damageValueToRepair, new Random(), null);
            calcDamage(pStack);
            return true;
        } else  {
            return false;
        }
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
        }else {
            if (player != null) {
                playToolSound(player);
            }
        }
    }

    private void playToolSound(Entity entity) {
        entity.playSound(SoundEvents.ANVIL_USE, 0.8F, 1.0f);
    }
    private void playBreakSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0f );
    }


    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            components.add(new TextComponent("Repair: Drag this Item and right click on the Item u want to repair").withStyle(ChatFormatting.YELLOW).append(String.valueOf(ChatFormatting.UNDERLINE)));
        } else {
            components.add(new TextComponent("A Tool to Repair Items or some Crafting Actions").withStyle(ChatFormatting.BLUE).append(String.valueOf(ChatFormatting.UNDERLINE)));
            components.add(new TranslatableComponent("tooltip.aquilairpg.shift"));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}

