package net.vaex.aquilarpg.item.custom;


import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.item.RPGMaterialTiers;
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
        Player player = Minecraft.getInstance().player;
        Level level = player.getLevel();
        final ItemStack copy = itemStack.copy();
        if (copy.hurt(1, new Random(), null)) {
            playBreakSound(level,player);
            return ItemStack.EMPTY;
        } else {
            playToolSound(level,player);
            return copy;
        }
    }

    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        int damageValueToRepair = pSlot.getItem().getDamageValue();
        if (!pPlayer.getLevel().isClientSide && pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer) && pSlot.getItem().isDamaged() && !(pSlot.getItem().getItem() instanceof RPGRepairItem) &&
                !(pSlot.getItem().getItem() instanceof RPGBasicTwoHandMeleeWeapon twoHandMeleeWeapon && twoHandMeleeWeapon.mythicalTwohand || pSlot.getItem().getItem() instanceof RPGBasicMeleeWeapon meleeWeapon && meleeWeapon.mythicalOneHand)) {
            pPlayer.playSound(SoundEvents.ANVIL_USE, 1.0f, 0);
            pSlot.getItem().hurt(damageValueToRepair * (-1), new Random(), null);
            pStack.hurt(damageValueToRepair, new Random(), null);
            calcDamage(pStack, pPlayer);
            return true;
        } else {
            return false;
        }
    }

    private void calcDamage(@Nonnull ItemStack tool, Player player) {
        if (player != null) {
            Level level = player.getLevel();
            int damageValueTool = tool.getDamageValue();
            int maxDurabilityTool = tool.getMaxDamage();
            if (damageValueTool >= maxDurabilityTool) {
                tool.shrink(1);
                playBreakSound(level,player);
            } else {
                playToolSound(level,player);
            }
        }
    }



    private void playToolSound(Level level, Entity entity) {
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.PLAYERS, 1.5F, 1F);
    }
    private void playBreakSound(Level level, Entity entity) {
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.5F, 1F);
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

