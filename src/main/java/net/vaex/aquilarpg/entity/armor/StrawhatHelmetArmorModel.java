package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.StrawhatHelmetArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StrawhatHelmetArmorModel extends AnimatedGeoModel<StrawhatHelmetArmorItem> {
    @Override
    public ResourceLocation getModelLocation(StrawhatHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/strawhat_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(StrawhatHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/strawhat_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(StrawhatHelmetArmorItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}