package net.vaex.aquilarpg.event;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.entity.armor.*;
import net.vaex.aquilarpg.entity.item.renderer.BulletModel;
import net.vaex.aquilarpg.entity.item.renderer.OrbOfLifeModel;
import net.vaex.aquilarpg.entity.layers.RPGModelLayers;
import net.vaex.aquilarpg.item.custom.armor.cape.BlueCapeWithStarsItem;
import net.vaex.aquilarpg.item.custom.armor.head.*;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public  class RPGEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(RPGModelLayers.ORB_OF_LIFE_LAYER, OrbOfLifeModel::createBodyLayer);
        event.registerLayerDefinition(RPGModelLayers.BULLET, BulletModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(ArabianHelmetArmorItem.class, ArabianHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(StrawhatHelmetArmorItem.class, StrawhatHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(BlueCapeWithStarsItem.class, BlueCapeWithStarsRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(AskirLegionnaireCenturioHelmetArmorItem.class, AskirLegionnaireCenturioHelmetArmorRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(AskirLegionnaireHelmetArmorItem.class, AskirLegionnaireHelmetArmorRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(CenturionHelmetArmorItem.class, CenturionHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(LegionnaireHelmetArmorItem.class, LegionnaireHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(PlagueDoctorHelmetArmorItem.class, PlagueDoctorHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(FlowerCrownHelmetArmorItem.class, FlowerCrownHelmetRenderer::new);
    }
}
