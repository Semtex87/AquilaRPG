package net.vaex.aquilarpg.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DurabilityInformation implements IIngameOverlay {
    private int x;
    private int y;
    @Override
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        final float scale = 0.5f;
        PoseStack poseStack1 = new PoseStack();
        final Font font = Minecraft.getInstance().font;
        poseStack1.scale(scale, scale, scale);
        this.x = (width);
        this.y = (height);
        Player player = (Player) Minecraft.getInstance().cameraEntity;
        if (player == null) return;
        ItemStack itemStackHelmSlot = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack itemStackBreastSlot = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack itemStackLegsSlot = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack itemStackBootsSlot = player.getItemBySlot(EquipmentSlot.FEET);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(itemStackHelmSlot, x-70 , y-16 , 0);//HELM ICON
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, player.getItemBySlot(EquipmentSlot.HEAD), x-70, y-28);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(itemStackBreastSlot, x-54, y-16, 0);//CHEST ICON
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, player.getItemBySlot(EquipmentSlot.CHEST), x-54, y-28);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(itemStackLegsSlot, x-38, y-16, 0);//LEGS ICON
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, player.getItemBySlot(EquipmentSlot.LEGS), x-38, y-28);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(itemStackBootsSlot, x-22, y-16, -10);//FEET ICON
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, player.getItemBySlot(EquipmentSlot.FEET), x-22, y-28);
    }
}

