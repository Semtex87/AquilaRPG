package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.AskirImperialGuardHelmetArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AskirImperialGuardHelmetArmorModel extends AnimatedGeoModel<AskirImperialGuardHelmetArmorItem> {
    @Override
    public ResourceLocation getModelLocation(AskirImperialGuardHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/askir_imperial_guard_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AskirImperialGuardHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/askir_imperial_guard_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AskirImperialGuardHelmetArmorItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}