package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.CenturionHelmetArmorItem;
import net.vaex.aquilarpg.item.custom.armor.head.LegionnaireHelmetArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LegionnaireHelmetArmorModel extends AnimatedGeoModel<LegionnaireHelmetArmorItem> {
    @Override
    public ResourceLocation getModelLocation(LegionnaireHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/legionnaire_helmet.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LegionnaireHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/legionnaire_helmet.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LegionnaireHelmetArmorItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}