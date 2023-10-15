package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.CenturionHelmetArmorItem;
import net.vaex.aquilarpg.item.custom.armor.head.StrawhatHelmetArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CenturionHelmetArmorModel extends AnimatedGeoModel<CenturionHelmetArmorItem> {
    @Override
    public ResourceLocation getModelLocation(CenturionHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/centurion_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CenturionHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/centurion_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CenturionHelmetArmorItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}