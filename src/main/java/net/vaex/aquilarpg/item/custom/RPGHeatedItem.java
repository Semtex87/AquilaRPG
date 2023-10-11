package net.vaex.aquilarpg.item.custom;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.util.RPGTierInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class RPGHeatedItem extends Item {

    int heat;
    public RPGHeatedItem(RPGTierInterface ptier, Properties pProperties) {
        super(pProperties.durability(ptier.getForgingHeatpoint()));
        this.heat = ptier.getForgingHeatpoint();
        pProperties.durability(heat);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        if (new Random().nextInt(60) == 1) {
            stack.hurt((heat / 20), null, null);
        }
        if (entity instanceof Player player) {
            if (!selected && !(player.getOffhandItem().is(RPGItems.TONGS.get()) || (player.getOffhandItem().is(RPGItems.CRUCIBLE_TONGS.get())))) {
                player.hurt(DamageSource.ON_FIRE, 1.0F);
            }
            if (stack.getDamageValue() >= stack.getMaxDamage()) {
                checkValidItem(stack, player);
                stack.shrink(1);
            }
        }

    }

    private void checkValidItem(ItemStack stack, Player player) {
       /* if (stack.is(RPGItems.HOT_COBALT_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.COBALT_INGOT.get()));
        if (stack.is(RPGItems.HOT_STEEL_INGOT.get()))
            player.addItem(new ItemStack(RPGItems.STEEL_INGOT.get()));
        /////////////////////////////////////////////////////////////////
        if (stack.is(RPGItems.CRUCIBLE_MOLTEN_ASTRALSILVER.get()))
            player.addItem(new ItemStack(RPGItems.CRUCIBLE_SILVER_ZINC_MIX.get()));
*/
    }


    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(Blocks.WATER_CAULDRON)) {
            if (!level.isClientSide) {
                /*
                if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_COBALT_INGOT.get())) {
                    pContext.getPlayer().addItem(new ItemStack(RPGItems.COBALT_INGOT.get()));
                }
                if (pContext.getPlayer().getMainHandItem().is(RPGItems.HOT_STEEL_INGOT.get())) {
                    pContext.getPlayer().addItem(new ItemStack(RPGItems.STEEL_INGOT.get()));
                }*/
                pContext.getPlayer().getMainHandItem().shrink(1);
                return InteractionResult.SUCCESS;
            }
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        return InteractionResult.PASS;
    }
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        int var = itemStack.getMaxDamage() - itemStack.getDamageValue();
        if (Screen.hasShiftDown()) {
            pTooltip.add(new TextComponent("Caution! Very hot").withStyle(ChatFormatting.ITALIC));
            pTooltip.add(new TextComponent("Use Tongs(Offhand) to avoid damage").withStyle(ChatFormatting.ITALIC));
        } else {
            pTooltip.add(new TranslatableComponent("item_tooltip_shift"));
        }
        pTooltip.add(new TextComponent("heat " + var).withStyle(ChatFormatting.RED));
        super.appendHoverText(itemStack, level, pTooltip, pFlag);
    }

}




