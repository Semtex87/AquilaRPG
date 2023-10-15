package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.ArabianHelmetArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ArabianHelmetArmorModel  extends AnimatedGeoModel<ArabianHelmetArmorItem> {
    @Override
    public ResourceLocation getModelLocation(ArabianHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/arabian_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ArabianHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/arabian_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ArabianHelmetArmorItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}