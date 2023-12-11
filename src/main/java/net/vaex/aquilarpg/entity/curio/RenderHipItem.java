
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.vaex.aquilarpg.item.custom.weapon.*;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class RenderHipItem implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (stack.getItem() instanceof RPGSwordWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.3F, 1.0F, 0.3F);
            matrixStack.scale(0.6F, 0.6F, 0.6F);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(-270.0F));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGAxeWeapon || stack.getItem() instanceof RPGBluntWeapon || stack.getItem() instanceof RPGFlailWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.3F, 0.6F, 0.1F);
            matrixStack.scale(0.6F, 0.6F, 0.6F);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(-270.0F));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(0.0F));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGSpearWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.1F, 0.4F, 0.2F);
            matrixStack.scale(0.8F, 0.68F, 1.0F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(80.0f));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
        if (stack.getItem() instanceof RPGKrisWeapon || stack.getItem() instanceof RPGDaggerWeapon) {
            matrixStack.pushPose();
            LivingEntity entity = slotContext.entity();
            ICurioRenderer.translateIfSneaking(matrixStack, entity);
            ICurioRenderer.rotateIfSneaking(matrixStack, entity);
            matrixStack.translate(0.3F, 1.0F, 0.3F);
            matrixStack.scale(0.6F, 0.6F, 0.6F);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(-270.0F));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY,
                    matrixStack, renderTypeBuffer, 0);
            matrixStack.popPose();
        }
    }
}

