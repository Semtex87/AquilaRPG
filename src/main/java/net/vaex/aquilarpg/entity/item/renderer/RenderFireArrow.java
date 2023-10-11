package net.vaex.aquilarpg.entity.item.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.entity.item.FireArrowEntity;

public class RenderFireArrow extends ArrowRenderer <FireArrowEntity> {
    public static final ResourceLocation FIREARROW = new ResourceLocation(AquilaRPG.MOD_ID, "textures/item/weapon/projectile/fire_arrow_shoot.png");

    public RenderFireArrow(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public ResourceLocation getTextureLocation(FireArrowEntity pEntity) {
        return FIREARROW;
    }

}
