package net.vaex.aquilarpg.entity.armor;

import net.vaex.aquilarpg.item.custom.armor.head.PlagueDoctorHelmetArmorItem;
import net.vaex.aquilarpg.item.custom.armor.head.StrawhatHelmetArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PlagueDoctorHelmetRenderer extends GeoArmorRenderer<PlagueDoctorHelmetArmorItem> {
    public PlagueDoctorHelmetRenderer() {
        super(new PlagueDoctorHelmetArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorLeftLeg";
        this.leftLegBone = "armorRightLeg";
        this.rightBootBone = "armorLeftBoot";
        this.leftBootBone = "armorRightBoot";
    }
}
