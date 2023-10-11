package net.vaex.aquilarpg.entity.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.entity.item.AxeEntity;
import org.jetbrains.annotations.NotNull;

public class RenderAxe extends EntityRenderer<AxeEntity> {
    public RenderAxe(EntityRendererProvider.Context context) {
        super(context);
    }

    public static final ResourceLocation AXE = new ResourceLocation(AquilaRPG.MOD_ID, "axe");

    @Override
    public void render(AxeEntity axeEntity, float yaw, float partialTicks, PoseStack matrix, @NotNull MultiBufferSource buffer, int light) {
        matrix.pushPose();
        matrix.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, axeEntity.yRotO, axeEntity.getYRot()) - 90.0F));
        matrix.scale(0.7f, 0.7f, 1.0f);
        if (!axeEntity.GetHasImpacted()) {
            matrix.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, axeEntity.xRotO, axeEntity.getXRot()) - 5 + (float) axeEntity.GetSpinTicks() * -20f));
        } else {
            matrix.mulPose(Vector3f.ZP.rotationDegrees(axeEntity.getXRot() - 5));
        }

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        renderer.renderStatic(axeEntity.axeItem, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, matrix, buffer, axeEntity.getId());
        matrix.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(AxeEntity pEntity) {
        return AXE;
    }
}
