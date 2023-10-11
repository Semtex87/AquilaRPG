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
import net.vaex.aquilarpg.entity.item.OrbOfLifeEntity;
import net.vaex.aquilarpg.entity.layers.RPGModelLayers;

public class RenderOrbOfLife extends EntityRenderer<OrbOfLifeEntity> {
    private  static final ResourceLocation ORB_OF_LIFE = new ResourceLocation(AquilaRPG.MOD_ID, "textures/entity/orb_of_life.png");
    protected OrbOfLifeModel model;

    public RenderOrbOfLife(EntityRendererProvider.Context pContext) {
        super(pContext);
        model = new OrbOfLifeModel(pContext.bakeLayer(RPGModelLayers.ORB_OF_LIFE_LAYER));
        this.shadowRadius = 0.5f;
    }
    @Override
    public void render(OrbOfLifeEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.yRotO) - 90.0F));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.xRotO) + 90.0F));
        VertexConsumer ivertexbuilder = net.minecraft.client.renderer.entity.ItemRenderer.getFoilBufferDirect(bufferIn, this.model.renderType(this.getTextureLocation(entityIn)), false, false);
        this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
    @Override
    public ResourceLocation getTextureLocation(OrbOfLifeEntity pEntity) {
        return ORB_OF_LIFE;
    }

}




