package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.AskirLegionnaireHelmetArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AskirLegionnaireHelmetArmorModel extends AnimatedGeoModel<AskirLegionnaireHelmetArmorItem> {
    @Override
    public ResourceLocation getModelLocation(AskirLegionnaireHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/askir_legionnaire_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AskirLegionnaireHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/askir_legionnaire_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AskirLegionnaireHelmetArmorItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}