package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.cape.BlueCapeWithStarsItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlueCapeWithStarsArmorModel extends AnimatedGeoModel<BlueCapeWithStarsItem> {
    @Override
    public ResourceLocation getModelLocation(BlueCapeWithStarsItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/blue_cape_with_stars.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BlueCapeWithStarsItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/blue_cape_with_stars.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BlueCapeWithStarsItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}