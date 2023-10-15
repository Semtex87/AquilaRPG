package net.vaex.aquilarpg.entity.armor;

import net.vaex.aquilarpg.item.custom.armor.head.CenturionHelmetArmorItem;
import net.vaex.aquilarpg.item.custom.armor.head.StrawhatHelmetArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class CenturionHelmetRenderer extends GeoArmorRenderer<CenturionHelmetArmorItem> {
    public CenturionHelmetRenderer() {
        super(new CenturionHelmetArmorModel());

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
