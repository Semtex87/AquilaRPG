package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.FlowerCrownHelmetArmorItem;
import net.vaex.aquilarpg.item.custom.armor.head.LegionnaireHelmetArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FlowerCrownHelmetArmorModel extends AnimatedGeoModel<FlowerCrownHelmetArmorItem> {
    @Override
    public ResourceLocation getModelLocation(FlowerCrownHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/flower_crown.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FlowerCrownHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/flower_crown.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FlowerCrownHelmetArmorItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}