package net.vaex.aquilarpg.entity.curio.shield;

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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class RenderShieldOnBack implements ICurioRenderer {
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,SlotContext slotContext,PoseStack matrixStack,
    RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity livingEntity = slotContext.entity();
        float rotation = -95;
        ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
        ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);
        matrixStack.translate(-0.2F, 0.5F, 0.8F);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(rotation));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0f));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(195.0f));
        Minecraft.getInstance().getItemRenderer()
                .renderStatic(stack, ItemTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY,
                        matrixStack, renderTypeBuffer, 0);
    }
}


