package net.vaex.aquilarpg.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.capabilities.mana.ManaProvider;
import net.vaex.aquilarpg.network.ManaC2SPacket;
import net.vaex.aquilarpg.network.NetworkHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RPGManaScrolltem extends Item {
    int manaAmount;

    public RPGManaScrolltem(Properties props, int manaAmount) {
        super(props);
        this.manaAmount = manaAmount;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        pPlayer.getCapability(ManaProvider.PLAYER_MANA).ifPresent(mana -> {
            mana.addMana(mana.getActualMaxMana());
            NetworkHandler.sendPacketToServer((new ManaC2SPacket()));
        });
        pLevel.playSound(null,pPlayer,SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS,8.0f,1.0f);
        stack.hurtAndBreak(1, pPlayer, (var) -> var.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(new TextComponent("Click Right to use the Scroll and restore full Mana Pool").withStyle(ChatFormatting.BLUE));
        super.appendHoverText(itemStack, level, pTooltip, pFlag);
    }
}

