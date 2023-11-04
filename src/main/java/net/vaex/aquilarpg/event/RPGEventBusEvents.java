package net.vaex.aquilarpg.event;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.entity.armor.*;
import net.vaex.aquilarpg.entity.item.renderer.BulletModel;
import net.vaex.aquilarpg.entity.item.renderer.OrbOfLifeModel;
import net.vaex.aquilarpg.entity.layers.RPGModelLayers;
import net.vaex.aquilarpg.item.custom.armor.cape.BlueCapeWithStarsItem;
import net.vaex.aquilarpg.item.custom.armor.head.*;
import net.vaex.aquilarpg.recipe.TinkerTableAnvilRecipe;
import net.vaex.aquilarpg.recipe.TinkerTableCommonRecipe;
import net.vaex.aquilarpg.recipe.TinkerTableRecipe;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.client.render.CuriosLayer;

@Mod.EventBusSubscriber(modid = AquilaRPG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public  class RPGEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(RPGModelLayers.ORB_OF_LIFE_LAYER, OrbOfLifeModel::createBodyLayer);
        event.registerLayerDefinition(RPGModelLayers.BULLET, BulletModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, TinkerTableRecipe.Type.ID, TinkerTableRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, TinkerTableCommonRecipe.Type.ID, TinkerTableCommonRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, TinkerTableAnvilRecipe.Type.ID, TinkerTableAnvilRecipe.Type.INSTANCE);
    }



    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(ArabianHelmetArmorItem.class, ArabianHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(StrawhatHelmetArmorItem.class, StrawhatHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(BlueCapeWithStarsItem.class, BlueCapeWithStarsRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(AskirImperialGuardHelmetArmorItem.class, AskirImperialGuardHelmetArmorRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(AskirLegionnaireHelmetArmorItem.class, AskirLegionnaireHelmetArmorRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(CenturionHelmetArmorItem.class, CenturionHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(LegionnaireHelmetArmorItem.class, LegionnaireHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(PlagueDoctorHelmetArmorItem.class, PlagueDoctorHelmetRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(FlowerCrownHelmetArmorItem.class, FlowerCrownHelmetRenderer::new);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers evt) {
        addPlayerLayer(evt, "default");
        addPlayerLayer(evt, "slim");
        CuriosRendererRegistry.load();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addPlayerLayer(EntityRenderersEvent.AddLayers evt, String skin) {
        EntityRenderer<? extends Player> renderer = evt.getSkin(skin);

        if (renderer instanceof LivingEntityRenderer livingRenderer) {
            livingRenderer.addLayer(new CuriosLayer<>(livingRenderer));
        }
    }

}
