package net.vaex.aquilarpg.entity.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.entity.item.BulletEntity;
import net.vaex.aquilarpg.entity.layers.RPGModelLayers;

public class RenderBullet extends EntityRenderer<BulletEntity> {
    private  static final ResourceLocation BULLET = new ResourceLocation(AquilaRPG.MOD_ID, "textures/entity/bullet.png");
    protected BulletModel model;

    public RenderBullet(EntityRendererProvider.Context pContext) {
        super(pContext);
        model = new BulletModel(pContext.bakeLayer(RPGModelLayers.BULLET));
        this.shadowRadius = 0.5f;
    }
    @Override
    public void render(BulletEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.yRotO) - 90.0F));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.xRotO) + 90.0F));
        VertexConsumer ivertexbuilder = net.minecraft.client.renderer.entity.ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, false);
        this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
    @Override
    public ResourceLocation getTextureLocation(BulletEntity pEntity) {
        return BULLET;
    }

}





