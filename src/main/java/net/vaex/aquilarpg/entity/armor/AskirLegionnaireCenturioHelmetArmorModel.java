package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.AskirLegionnaireCenturioHelmetArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AskirLegionnaireCenturioHelmetArmorModel extends AnimatedGeoModel<AskirLegionnaireCenturioHelmetArmorItem> {
    @Override
    public ResourceLocation getModelLocation(AskirLegionnaireCenturioHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/askir_legionnaire_centurio_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AskirLegionnaireCenturioHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/askir_legionnaire_centurio_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AskirLegionnaireCenturioHelmetArmorItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}