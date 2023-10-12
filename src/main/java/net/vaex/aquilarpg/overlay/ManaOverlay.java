package net.vaex.aquilarpg.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.capabilities.mana.ClientManaData;
import net.vaex.aquilarpg.capabilities.mana.Mana;

public class ManaOverlay implements IIngameOverlay {
    private static final ResourceLocation MANA_BAR = new ResourceLocation(AquilaRPG.MOD_ID, "textures/gui/mana/mana_bar_full.png");
    private static final int IMAGE_WIDTH = 12 / 2;
    private static final int IMAGE_HEIGHT = 80 / 2;
    private int x;
    private int y;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        this.x = (width - IMAGE_WIDTH);
        this.y = (height - IMAGE_HEIGHT);
        Player player = (Player) Minecraft.getInstance().cameraEntity;
        if (player != null) {
            float maxMana = Mana.getPlayerMaxMana();
            float currentMana = ClientManaData.getPlayerMana(player);
            float oneUnit = (float) IMAGE_HEIGHT / maxMana;
            float currentHeight = (int) (oneUnit * currentMana);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.setShaderTexture(0, MANA_BAR);

            poseStack.pushPose();
            GuiComponent.blit(poseStack, x - 18, (int) ((y + 14) - currentHeight), (float) 0, (float) 0, IMAGE_WIDTH, (int) currentHeight, IMAGE_WIDTH, IMAGE_HEIGHT);
            poseStack.pushPose();
            if (player.isShiftKeyDown()) {
                Minecraft.getInstance().font.draw(poseStack, Component.nullToEmpty("Mana: " + currentMana + "/" + maxMana), 20, 30, -1);
            }
        }
    }
}


/*
    //note old/alternative overlay
    private static final ResourceLocation FILLED_MANA= new ResourceLocation(AquilaImmersiveRPG.MOD_ID,
            "textures/gui/mana/filled_mana.png");
    private static final ResourceLocation EMPTY_MANA= new ResourceLocation(AquilaImmersiveRPG.MOD_ID,
            "textures/gui/mana/empty_mana.png");


    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        int x = width / 2;
        int y = height;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_MANA);
        for (int i = 0; i < 10; i++) {
            GuiComponent.blit(poseStack, x + 10 + (i * 9), y - 64, 0, 0, 12, 12,
                    12, 12);
        }

        RenderSystem.setShaderTexture(0, FILLED_MANA);
        for (int i = 0; i < 10; i++) {
            if (ClientManaData.getPlayerMana() > i) {
                GuiComponent.blit(poseStack, x + 10 + (i * 9), y - 64, 0, 0, 12, 12,
                        12, 12);
            } else {
                break;
            }
        }
    }*/



