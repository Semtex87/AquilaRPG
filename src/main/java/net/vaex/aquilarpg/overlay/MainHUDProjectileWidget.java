package net.vaex.aquilarpg.overlay;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.item.custom.weapon.gun.BlowGun;
import net.vaex.aquilarpg.item.custom.weapon.gun.Blunderbuss;
import net.vaex.aquilarpg.item.custom.weapon.gun.FlintlockPistol;
import net.vaex.aquilarpg.item.custom.weapon.gun.MusketGun;
import net.vaex.aquilarpg.item.custom.weapon.magic.FireStaff;
import net.vaex.aquilarpg.item.custom.weapon.magic.ScepterOfLife;
import net.vaex.aquilarpg.item.custom.weapon.magic.TeleportStaff;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainHUDProjectileWidget implements IIngameOverlay {

    private static final ResourceLocation ARROW_WIDGET = new ResourceLocation(AquilaRPG.MOD_ID, "textures/gui/widget/arrow_widget.png");
    private static final int IMAGE_WIDTH = 68 / 2;
    private static final int IMAGE_HEIGHT = 54 / 2;
    private int x;
    private int y;
    String infinitySymbol = null;
    ItemStack arrowStack = null;
    int arrowCount = 0;
    ArrayList<Item> projectilesList = new ArrayList<>();
    ArrayList<Item> magicList = new ArrayList<>();

    private void addProjectiles() {
        projectilesList.add(Items.ARROW);
        projectilesList.add(Items.TIPPED_ARROW);
        projectilesList.add(Items.SPECTRAL_ARROW);
        projectilesList.add(RPGItems.FIRE_ARROW.get());
        projectilesList.add(RPGItems.BULLET.get());
        projectilesList.add(RPGItems.POISON_DART.get());
        projectilesList.add(Items.FIRE_CHARGE);
    }
    private void addmagicProjectile() {
        projectilesList.add(RPGItems.MANA.get());
        projectilesList.add(RPGItems.FIRE_SPELL.get());
        projectilesList.add(RPGItems.LIGHTNING_SPELL.get());
        projectilesList.add(RPGItems.NATURE_SPELL.get());

    }


    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        addProjectiles();
        addmagicProjectile();
        final float scale = 0.5f;
        PoseStack poseStack1 = new PoseStack();
        poseStack1.scale(scale, scale, scale);
        this.x = (width - IMAGE_WIDTH);
        this.y = (height - IMAGE_HEIGHT);
        Player player = (Player) Minecraft.getInstance().cameraEntity;
        if (player == null) return;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        //CREATIVE
        if (player.isCreative()) {
            RenderSystem.setShaderTexture(0, ARROW_WIDGET);
            GuiComponent.blit(poseStack, x - 70, y, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
            infinitySymbol = new String(Character.toString('\u221E').getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            this.arrowStack = projectilesList.get(0).getDefaultInstance();
            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 62, y + 11, -1);
            Minecraft.getInstance().font.draw(poseStack1, Component.nullToEmpty("" + infinitySymbol), (x * 2) - 105, (y * 2) + 40, -1);
            return;
        }
        //MAGIC
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof FireStaff) {
            RenderSystem.setShaderTexture(0, ARROW_WIDGET);
            GuiComponent.blit(poseStack, x - 70, y, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
            this.arrowStack = RPGItems.FIRE_SPELL.get().getDefaultInstance();
            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 61, y + 11, -1);
            return;
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof TeleportStaff) {
            RenderSystem.setShaderTexture(0, ARROW_WIDGET);
            GuiComponent.blit(poseStack, x - 70, y, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
            this.arrowStack = RPGItems.DARK_SPELL.get().getDefaultInstance();
            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 60, y + 11, -1);
            return;
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof ScepterOfLife) {
            RenderSystem.setShaderTexture(0, ARROW_WIDGET);
            GuiComponent.blit(poseStack, x - 70, y, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
            this.arrowStack = RPGItems.NATURE_SPELL.get().getDefaultInstance();
            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 60, y + 11, -1);
            return;
        }
        //BOW
        if ((player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BowItem)) {
            RenderSystem.setShaderTexture(0, ARROW_WIDGET);
            GuiComponent.blit(poseStack, x - 70, y, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
            getArrowProjectile(player, poseStack1);
        }
        //CROSSBOW
        if ((player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof CrossbowItem)) {
            RenderSystem.setShaderTexture(0, ARROW_WIDGET);
            GuiComponent.blit(poseStack, x - 70, y, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
            if ((player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BlowGun)) {
                for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemstack = player.getInventory().getItem(i);
                    if (itemstack.getItem().equals(projectilesList.get(5))) {
                        this.arrowCount = player.getInventory().countItem(projectilesList.get(5));
                        this.arrowStack = projectilesList.get(5).getDefaultInstance();
                        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 62, y + 11, -1);
                        Minecraft.getInstance().font.draw(poseStack1, Component.nullToEmpty("" + arrowCount), (x * 2) - 105, (y * 2) + 30, -1);
                        return;
                    }
                }
            }
            if ((player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof MusketGun || player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Blunderbuss || player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof FlintlockPistol)) {
                for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemstack = player.getInventory().getItem(i);
                    if (itemstack.getItem().equals(projectilesList.get(4))) {
                        this.arrowCount = player.getInventory().countItem(projectilesList.get(4));
                        this.arrowStack = projectilesList.get(4).getDefaultInstance();
                        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 62, y + 11, -1);
                        Minecraft.getInstance().font.draw(poseStack1, Component.nullToEmpty("" + arrowCount), (x * 2) - 105, (y * 2) + 30, -1);
                        return;
                    }
                }
            } else {
                getArrowProjectile(player, poseStack1);
            }
        }
    }

    private void getArrowProjectile(Player player, PoseStack poseStack1){
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);
            if (itemstack.getItem().equals(projectilesList.get(0))) {
                this.arrowCount = player.getInventory().countItem(projectilesList.get(0));
                this.arrowStack = projectilesList.get(0).getDefaultInstance();
                Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 62, y + 11, -1);
                Minecraft.getInstance().font.draw(poseStack1, Component.nullToEmpty("" + arrowCount), (x * 2) - 105, (y * 2) + 40, -1);
                //Log.info("ARROW  >>>>>" + player.getInventory().countItem(projectiles.get(0)));
                return;
            }
            if (itemstack.getItem().equals(projectilesList.get(1))) {
                this.arrowCount = player.getInventory().countItem(projectilesList.get(1));
                this.arrowStack = projectilesList.get(1).getDefaultInstance();
                Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 62, y + 11, -1);
                Minecraft.getInstance().font.draw(poseStack1, Component.nullToEmpty("" + arrowCount), (x * 2) - 105, (y * 2) + 40, -1);
                return;
            }
            if (itemstack.getItem().equals(projectilesList.get(2))) {
                this.arrowCount = player.getInventory().countItem(projectilesList.get(2));
                this.arrowStack = projectilesList.get(2).getDefaultInstance();
                Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 62, y + 11, -1);
                Minecraft.getInstance().font.draw(poseStack1, Component.nullToEmpty("" + arrowCount), (x * 2) - 105, (y * 2) + 40, -1);
                return;
            }
            if (itemstack.getItem().equals(projectilesList.get(3))) {
                this.arrowCount = player.getInventory().countItem(projectilesList.get(3));
                this.arrowStack = projectilesList.get(3).getDefaultInstance();
                Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 62, y + 11, -1);
                Minecraft.getInstance().font.draw(poseStack1, Component.nullToEmpty("" + arrowCount), (x * 2) - 105, (y * 2) + 30, -1);
                return;
            }
            if (itemstack.getItem().equals(projectilesList.get(6))) {
                this.arrowCount = player.getInventory().countItem(projectilesList.get(6));
                this.arrowStack = projectilesList.get(6).getDefaultInstance();
                Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x - 62, y + 11, -1);
                Minecraft.getInstance().font.draw(poseStack1, Component.nullToEmpty("" + arrowCount), (x * 2) - 105, (y * 2) + 30, -1);
                return;
            }
        }
    }
}






