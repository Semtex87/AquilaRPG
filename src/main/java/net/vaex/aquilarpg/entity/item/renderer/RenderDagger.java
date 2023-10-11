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
import net.vaex.aquilarpg.entity.item.DaggerEntity;
import org.jetbrains.annotations.NotNull;

public class RenderDagger extends EntityRenderer<DaggerEntity> {
    public static final ResourceLocation DAGGER = new ResourceLocation(AquilaRPG.MOD_ID, "dagger");

    public RenderDagger(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(DaggerEntity daggerEntity, float yaw, float partialTicks, PoseStack matrix, @NotNull MultiBufferSource buffer, int light) {
        matrix.pushPose();
        matrix.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, daggerEntity.yRotO, daggerEntity.getYRot()) - 90.0F));
        matrix.scale(0.5f, 0.5f, 1.0f);
        if (!daggerEntity.GetHasImpacted()) {
            matrix.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, daggerEntity.xRotO, daggerEntity.getXRot()) - 135 + (float) daggerEntity.GetSpinTicks() * -50f));
        } else {
            matrix.mulPose(Vector3f.ZP.rotationDegrees(daggerEntity.getXRot() - 135));
        }
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        renderer.renderStatic(daggerEntity.daggerItem, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, matrix, buffer, daggerEntity.getId());
        matrix.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DaggerEntity entity) {
        return DAGGER;
    }
}
