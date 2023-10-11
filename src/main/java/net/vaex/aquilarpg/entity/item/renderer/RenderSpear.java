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
import net.vaex.aquilarpg.entity.item.SpearEntity;
import org.jetbrains.annotations.NotNull;

public class RenderSpear extends EntityRenderer<SpearEntity>
{
    public static final ResourceLocation SPEAR = new ResourceLocation(AquilaRPG.MOD_ID, "spear");
    public RenderSpear(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(SpearEntity pEntity, float yaw, float pPartialTicks, PoseStack pMatrixStack, @NotNull MultiBufferSource buffer, int light)
    {
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) - 45));
        pMatrixStack.translate(0,-0.3f,0);
        pMatrixStack.scale(1.0f,1.0f,1.0f);

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        renderer.renderStatic(pEntity.spearItem, ItemTransforms.TransformType.FIXED, light, OverlayTexture.NO_OVERLAY, pMatrixStack, buffer, pEntity.getId());
        pMatrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(SpearEntity pEntity) {
        return SPEAR;
    }

}