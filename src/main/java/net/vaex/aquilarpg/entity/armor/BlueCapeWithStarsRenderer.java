package net.vaex.aquilarpg.entity.armor;

import net.vaex.aquilarpg.item.custom.armor.cape.BlueCapeWithStarsItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BlueCapeWithStarsRenderer extends GeoArmorRenderer<BlueCapeWithStarsItem> {
    public BlueCapeWithStarsRenderer() {
        super(new BlueCapeWithStarsArmorModel());

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
