package net.vaex.aquilarpg.entity.armor;

import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.custom.armor.head.PlagueDoctorHelmetArmorItem;
import net.vaex.aquilarpg.item.custom.armor.head.StrawhatHelmetArmorItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PlagueDoctorHelmetArmorModel extends AnimatedGeoModel<PlagueDoctorHelmetArmorItem> {
    @Override
    public ResourceLocation getModelLocation(PlagueDoctorHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "geo/plague_doctor_mask.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PlagueDoctorHelmetArmorItem object) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "textures/models/armor/plague_doctor_mask.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PlagueDoctorHelmetArmorItem animatable) {
        return new ResourceLocation(AquilaRPG.MOD_ID, "animations/armor_animation.json");
    }
}