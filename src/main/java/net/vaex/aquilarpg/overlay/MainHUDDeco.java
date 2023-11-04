package net.vaex.aquilarpg.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
public class MainHUDDeco implements IIngameOverlay {
    private static final ResourceLocation MAIN_HUD = new ResourceLocation(AquilaRPG.MOD_ID, "textures/gui/cr_hud_bg.png");
    private static final int IMAGE_WIDTH = 160/2;
    private static final int IMAGE_HEIGHT = 170/2;
    private int x;
    private int y;


    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        Player player = (Player) Minecraft.getInstance().cameraEntity;
        if (player == null) return;
        if(player.isCreative()) return;
        this.x = (width - IMAGE_WIDTH);
        this.y = (height - IMAGE_HEIGHT);
        RenderSystem.setShaderTexture(0, MAIN_HUD);
        poseStack.pushPose();
        GuiComponent.blit(poseStack, x , y,0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
        poseStack.popPose();
    }
}
