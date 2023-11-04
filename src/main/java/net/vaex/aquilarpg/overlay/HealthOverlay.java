package net.vaex.aquilarpg.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.util.RPGAttributes;

public class HealthOverlay implements IIngameOverlay {
    private static final ResourceLocation HEALTH_BAR_FULL = new ResourceLocation(AquilaRPG.MOD_ID, "textures/gui/health/health_bar_full.png");
    private static final ResourceLocation HEALTH_BAR_WITHER = new ResourceLocation(AquilaRPG.MOD_ID, "textures/gui/health/health_bar_wither.png");
    private static final ResourceLocation HEALTH_BAR_POISON = new ResourceLocation(AquilaRPG.MOD_ID, "textures/gui/health/health_bar_poison.png");
    private static final ResourceLocation HEALTH_BAR_FROZEN = new ResourceLocation(AquilaRPG.MOD_ID, "textures/gui/health/health_bar_freeze.png");
    private static final int IMAGE_WIDTH = 14/2;
    private static final int IMAGE_HEIGHT = 100/2;
    private int x;
    private int y;

    @SubscribeEvent //stops rendering vanilla Health Bar
    public static void stopRenderHealthBar(RenderGameOverlayEvent event) {
        IIngameOverlay overlay = ForgeIngameGui.PLAYER_HEALTH_ELEMENT;
        OverlayRegistry.enableOverlay(overlay, false);//true = visible
    }

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        this.x = (width - IMAGE_WIDTH);
        this.y = (height - IMAGE_HEIGHT);
        Player player = (Player) Minecraft.getInstance().cameraEntity;
        Font font = gui.getFont();
        if (player != null) {
            if(player.isCreative()) return;
            float maxHealth =  player.getMaxHealth();
            float currentHealth =  player.getHealth();
            float oneUnit =  (IMAGE_HEIGHT / player.getMaxHealth());
            float currentHeight =  (oneUnit * player.getHealth());

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            if (player.hasEffect(MobEffects.WITHER)) {
                RenderSystem.setShaderTexture(0, HEALTH_BAR_WITHER);
            } else if (player.hasEffect(MobEffects.POISON)) {
                RenderSystem.setShaderTexture(0, HEALTH_BAR_POISON);
            } else if (player.isFullyFrozen()) {
                RenderSystem.setShaderTexture(0, HEALTH_BAR_FROZEN);
            } else {
                RenderSystem.setShaderTexture(0, HEALTH_BAR_FULL);
            }

            poseStack.pushPose();
            GuiComponent.blit(poseStack, x - 7, (int) ((y + 24) - currentHeight), (float) 0, (float) 0, IMAGE_WIDTH, (int) currentHeight, IMAGE_WIDTH, IMAGE_HEIGHT);
            poseStack.popPose();
            if (player.isShiftKeyDown()) {
                Minecraft.getInstance().font.draw(poseStack, Component.nullToEmpty("Health: " + currentHealth + "/" + maxHealth), 20, 20, -2);
            }
        }
    }
}
