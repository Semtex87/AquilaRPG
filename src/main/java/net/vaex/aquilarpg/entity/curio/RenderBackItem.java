package net.vaex.aquilarpg.entity.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.vaex.aquilarpg.item.custom.RPGBasicBowWeapon;
import net.vaex.aquilarpg.item.custom.RPGBasicCrossbowWeapon;
import net.vaex.aquilarpg.item.custom.RPGBasicShield;
import net.vaex.aquilarpg.item.custom.weapon.*;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class RenderBackItem implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,SlotContext slotContext,PoseStack matrixStack,
    RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (stack.getItem() instanceof RPGTwoHandSwordWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.1F, 0.4F, 0.2F);
            matrixStack.scale(0.8F, 0.68F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(105.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGTwoHandAxeWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.1F, 0.4F, 0.2F);
            matrixStack.scale(0.8F, 0.68F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(105.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGTwoHandScytheWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.1F, 0.4F, 0.2F);
            matrixStack.scale(0.8F, 0.68F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(105.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGTwoHandBluntWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.1F, 0.4F, 0.2F);
            matrixStack.scale(0.8F, 0.68F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(105.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGTwoHandSpearWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.1F, 0.4F, 0.2F);
            matrixStack.scale(0.8F, 0.68F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(105.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGTwoHandStaffWeapon || stack.getItem() instanceof RPGTwoHandSpearWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.1F, 0.4F, 0.2F);
            matrixStack.scale(0.8F, 0.68F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(105.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGBasicShield) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.0F, 0.4F, 0.3F);
            matrixStack.scale(0.5F, 0.5F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(0.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGBasicBowWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.0F, 0.4F, 0.2F);
            matrixStack.scale(0.8F, 0.8F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(0.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGBasicCrossbowWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.0F, 0.4F, 0.3F);
            matrixStack.scale(0.6F, 0.6F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(0.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
    }
}


