package net.vaex.aquilarpg.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;

import java.util.ArrayList;
public class MainHUDCompassWidget implements IIngameOverlay {

    private static final ResourceLocation WIDGET_ELEMENT = new ResourceLocation(AquilaRPG.MOD_ID, "textures/gui/widget/item_widget.png");
    private static final int IMAGE_WIDTH = 36 / 2;
    private static final int IMAGE_HEIGHT = 46 / 2;
    private int x;
    private int y;
    ItemStack itemStack = null;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        ArrayList<Item> compass = new ArrayList<>();
        compass.add(Items.COMPASS);
        final float scale = 0.5f;
        PoseStack poseStack1 = new PoseStack();
        poseStack1.scale(scale, scale, scale);
        this.x = (width - IMAGE_WIDTH);
        this.y = (height - IMAGE_HEIGHT);
        Player player = (Player) Minecraft.getInstance().cameraEntity;
        if (player == null) return;

        if (!player.isCreative()) {
            for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                ItemStack itemstack = player.getInventory().getItem(i);
                if (itemstack.getItem().equals(compass.get(0))) {
                    this.itemStack = compass.get(0).getDefaultInstance();
                    RenderSystem.setShaderTexture(0, WIDGET_ELEMENT);
                    GuiComponent.blit(poseStack, x - 400, y , 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
                    Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(itemStack, x - 399, y+4, -1);
                    return;
                }
            }
        }
    }
}