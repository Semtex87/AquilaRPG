package net.vaex.aquilarpg.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RPGBlockScannerItem extends Item {
    public RPGBlockScannerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            BlockPos blockPos = context.getClickedPos();
            String blockName = "Name: " + context.getLevel().getBlockState(context.getClickedPos()).getBlock().getName().getString();
            String blockPosStr = "x: " + blockPos.getX() + " y: " + blockPos.getY() + " z: " + blockPos.getZ();

            ItemStack itemStack = context.getItemInHand();
            if (!itemStack.hasTag()) itemStack.setTag(new CompoundTag());

            itemStack.getTag().putString("lastBlockName", blockName);
            itemStack.getTag().putString("lastBlockPosStr", blockPosStr);

            context.getPlayer().sendMessage(new TextComponent(blockName), context.getPlayer().getUUID());
            context.getPlayer().sendMessage(new TextComponent(blockPosStr), context.getPlayer().getUUID());

            context.getLevel().playSound(context.getPlayer(), blockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        if (player.getLevel().isClientSide()) {
            String blockName = player.getLevel().getBlockState(pos).getBlock().getName().getString();
            player.sendMessage(new TextComponent(blockName + "don't hurt me"), player.getUUID());
        }
        itemstack.hurtAndBreak(8, player, (p_43076_) -> {
            p_43076_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            String blockName;
            String blockPosStr = "";
            if (!itemStack.hasTag()) {
                blockName = "NO BLOCK SCANNED";
            } else {
                blockName = itemStack.getTag().getString("lastBlockName");
                blockPosStr = itemStack.getTag().getString("lastBlockPosStr");
            }
            components.add(new TextComponent("Last scanned block").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
            components.add(new TextComponent(blockName));
            components.add(new TextComponent(blockPosStr));

        } else {
            components.add(new TextComponent("Scans the current target block and position").withStyle(ChatFormatting.BLUE).append(String.valueOf(ChatFormatting.UNDERLINE)));
            components.add(new TranslatableComponent("tooltip.aquilairpg.shift"));
        }

        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }
}
