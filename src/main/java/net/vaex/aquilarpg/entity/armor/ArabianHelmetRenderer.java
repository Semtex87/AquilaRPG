package net.vaex.aquilarpg.entity.armor;

import net.vaex.aquilarpg.item.custom.armor.head.ArabianHelmetArmorItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ArabianHelmetRenderer  extends GeoArmorRenderer<ArabianHelmetArmorItem> {
    public ArabianHelmetRenderer() {
        super(new ArabianHelmetArmorModel());

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
