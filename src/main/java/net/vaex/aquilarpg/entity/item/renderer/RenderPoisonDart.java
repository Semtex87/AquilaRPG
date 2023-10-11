package net.vaex.aquilarpg.entity.item.renderer;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.entity.item.PoisonDartEntity;

public class RenderPoisonDart extends ArrowRenderer <PoisonDartEntity> {
    public static final ResourceLocation POISON_DART = new ResourceLocation(AquilaRPG.MOD_ID, "textures/item/weapon/projectile/poison_dart_shoot.png");

    public RenderPoisonDart(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public ResourceLocation getTextureLocation(PoisonDartEntity pEntity) {
        return POISON_DART;
    }

}
