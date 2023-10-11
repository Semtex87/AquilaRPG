package net.vaex.aquilarpg.entity.layers;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.vaex.aquilarpg.AquilaRPG;

public class RPGModelLayers {

    public static final ModelLayerLocation ORB_OF_LIFE_LAYER = new ModelLayerLocation(
            new ResourceLocation(AquilaRPG.MOD_ID, "orb_of_life_layer"), "orb_of_life_layer");

    public static final ModelLayerLocation BULLET = new ModelLayerLocation(
            new ResourceLocation(AquilaRPG.MOD_ID, "bullet_layer"), "bullet_layer");


}
