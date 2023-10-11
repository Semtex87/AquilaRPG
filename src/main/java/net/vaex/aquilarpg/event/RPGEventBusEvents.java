package net.vaex.aquilarpg.event;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.entity.item.renderer.BulletModel;
import net.vaex.aquilarpg.entity.item.renderer.OrbOfLifeModel;
import net.vaex.aquilarpg.entity.layers.RPGModelLayers;

@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public  class RPGEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(RPGModelLayers.ORB_OF_LIFE_LAYER, OrbOfLifeModel::createBodyLayer);
        event.registerLayerDefinition(RPGModelLayers.BULLET, BulletModel::createBodyLayer);
    }
}
