package net.vaex.aquilarpg;

import com.mojang.logging.LogUtils;
import mezz.jei.common.config.KeyBindings;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vaex.aquilarpg.block.RPGBlocks;
import net.vaex.aquilarpg.block.entity.RPGBlockEntities;
import net.vaex.aquilarpg.capabilities.mana.ManaEvents;
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.effects.RPGPotionManager;
import net.vaex.aquilarpg.effects.RPGSimpleBrewingRecipe;
import net.vaex.aquilarpg.enchantment.RPGEnchantments;
import net.vaex.aquilarpg.entity.RPGModEntities;
import net.vaex.aquilarpg.entity.curio.shield.RenderShieldOnBack;
import net.vaex.aquilarpg.entity.item.renderer.*;
import net.vaex.aquilarpg.event.PlayerEvents;
import net.vaex.aquilarpg.item.*;
import net.vaex.aquilarpg.network.NetworkHandler;
import net.vaex.aquilarpg.overlay.Overlays;
import net.vaex.aquilarpg.recipe.RPGRecipes;
import net.vaex.aquilarpg.screen.RPGMenuTypes;
import net.vaex.aquilarpg.screen.TinkerTableScreen;
import net.vaex.aquilarpg.util.*;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AquilaRPG.MOD_ID)
public class AquilaRPG {
    public static final String MOD_ID = "aquilarpg";
    private static final Logger LOGGER = LogUtils.getLogger();


    public AquilaRPG() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RPGBlocks.register(modEventBus);
        RPGItems.register(modEventBus);
        RPGBlockEntities.register(modEventBus);
        RPGModEntities.register(modEventBus);
        RPGEffectManager.register(modEventBus);
        RPGEnchantments.register(modEventBus);
        RPGPotionManager.register(modEventBus);
        RPGSoundEvents.register(modEventBus);
        RPGAttributes.register(modEventBus);
        RPGMenuTypes.register(modEventBus);
        RPGRecipes.register(modEventBus);
        // Register the setup method for modloading

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::enqueue);
        // Register the enqueueIMC method for modloading
        // modEventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        //modEventBus.addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetworkHandler.register();
            BrewingRecipeRegistry.addRecipe(new RPGSimpleBrewingRecipe(Potions.AWKWARD, RPGPotionManager.RANDOM_TP.get(), Items.ENDER_PEARL));
            BrewingRecipeRegistry.addRecipe(new RPGSimpleBrewingRecipe(Potions.AWKWARD, RPGPotionManager.BLEEDING.get(), Items.CACTUS));
            BrewingRecipeRegistry.addRecipe(new RPGSimpleBrewingRecipe(Potions.AWKWARD, RPGPotionManager.INFRAVISION.get(), RPGIngredientItems.BEHOLDERS_EYE.get()));
            //todo = ash_from_a_forest_fire
            //todo = askir_cave_catfish
            //todo = beast_tongue
            //todo = beast_tooth
            //todo = black_pearl
            //todo = cave_spider_poison
            //todo = creeper_blood
            //todo = fish_bone
            //todo = frost_salt
            //todo = human_blood
            //todo = human_eye
            //todo = maple_root
            //todo = mistletoe
            // mana recipes
            BrewingRecipeRegistry.addRecipe(new RPGAdvancedAlchemicalRecipe(RPGItems.MANA_SHARD.get(), RPGItems.SMALL_MANA_POTION.get(),  RPGItems.DISTILLED_WATER.get()));
            BrewingRecipeRegistry.addRecipe(new RPGAdvancedAlchemicalRecipe(RPGItems.MANA_STONE.get(), RPGItems.MEDIUM_MANA_POTION.get(),  RPGItems.PURE_WATER.get()));
            BrewingRecipeRegistry.addRecipe(new RPGAdvancedAlchemicalRecipe(RPGItems.MANA_CRYSTAL.get(), RPGItems.LARGE_MANA_POTION.get(),  RPGItems.HOLY_WATER.get()));

            //Brewing items (no Potions)
            // basic recipes
            BrewingRecipeRegistry.addRecipe(new RPGSimpleAlchemicalRecipe(Potions.WATER, RPGItems.SULFURIC_ACID.get(),  RPGItems.SULFUR_DUST.get()));
            BrewingRecipeRegistry.addRecipe(new RPGSimpleAlchemicalRecipe(Potions.WATER, RPGItems.NITRIC_ACID.get(),  RPGItems.SALTPETER.get()));
            BrewingRecipeRegistry.addRecipe(new RPGSimpleAlchemicalRecipe(Potions.WATER, RPGItems.HYDROCHLORIC_ACID.get(),  RPGItems.SALT.get()));
            BrewingRecipeRegistry.addRecipe(new RPGSimpleAlchemicalRecipe(Potions.WATER, RPGItems.CAVE_SPIDER_POISON.get(),  Items.SPIDER_EYE));

            // advanced recipes
            BrewingRecipeRegistry.addRecipe(new RPGAdvancedAlchemicalRecipe(RPGItems.HYDROCHLORIC_ACID.get(), RPGItems.AQUA_REGIA.get(),  RPGItems.NITRIC_ACID.get()));
            BrewingRecipeRegistry.addRecipe(new RPGAdvancedAlchemicalRecipe(RPGItems.AQUA_REGIA.get(), RPGItems.RUNIC_SOLUTION.get(),  RPGItems.RUNE_DUST.get()));
            BrewingRecipeRegistry.addRecipe(new RPGAdvancedAlchemicalRecipe(RPGItems.PURE_WATER.get(), RPGItems.LIQUID_DEATH.get(),  RPGItems.WINESTONE.get()));
            BrewingRecipeRegistry.addRecipe(new RPGAdvancedAlchemicalRecipe(RPGItems.DISTILLED_WATER.get(), RPGItems.HOLY_WATER.get(),  RPGItems.PIXIE_TEARS.get()));
            BrewingRecipeRegistry.addRecipe(new RPGAdvancedAlchemicalRecipe(RPGItems.DISTILLED_WATER.get(), RPGItems.PURE_WATER.get(),  RPGItems.HOLY_WATER.get()));
        });


        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
    private void clientSetup(final FMLClientSetupEvent event) {
        Overlays.registerOverlays();
        RPGKeyBindings.register();
        RPGItemProperties.addCustomItemProperties();
        MenuScreens.register(RPGMenuTypes.TINKER_TABLE_MENU.get(),TinkerTableScreen::new);
        entityRegister();
        registerCurioRenderItems();
        blockRenderer();

    }
    private void enqueue(final InterModEnqueueEvent evt) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.CHARM.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BACK.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.RING.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BRACELET.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BODY.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HANDS.getMessageBuilder().build());
    }
    private void entityRegister() {
        EntityRenderers.register(RPGModEntities.DAGGER.get(), RenderDagger::new);
        EntityRenderers.register(RPGModEntities.BOTTLE.get(), RenderBottle::new);
        EntityRenderers.register(RPGModEntities.SPEAR.get(), RenderSpear::new);
        EntityRenderers.register(RPGModEntities.AXE.get(), RenderAxe::new);
        EntityRenderers.register(RPGModEntities.FIRE_ARROW.get(), RenderFireArrow::new);
        EntityRenderers.register(RPGModEntities.ORB_OF_LIFE.get(), RenderOrbOfLife::new);
        EntityRenderers.register(RPGModEntities.BULLET.get(), RenderBullet::new);
        EntityRenderers.register(RPGModEntities.POISON_DART.get(), RenderPoisonDart::new);
    }

    private void registerCurioRenderItems() {
        //BackShields Pos 1 Backslot
        CuriosRendererRegistry.register(RPGItems.LARGE_KNIGHT_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.BLACK_GUARD_TOWER_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.BLACK_SUN_TOWER_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.BLACK_SUN_GOTHIC_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.DWARVEN_SMALL_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.DWARVEN_TOWER_SHIELD.get(), RenderShieldOnBack::new);

        //GOTHIC Shields
        CuriosRendererRegistry.register(RPGItems.TEMPLAR_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.SMALL_KNIGHT_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_BLACK_YELLOW_02_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_GREEN_WHITE_02_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_02_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_WHITE_02_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_WHITE_03_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_WHITE_04_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_WHITE_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLACK_01_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLACK_02_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLACK_03_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLUE_02_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLUE_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_YELLOW_DEER_SHIELD.get(), RenderShieldOnBack::new);

        CuriosRendererRegistry.register(RPGItems.KITESHIELD_BLACK_01.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_BLACK_WHITE.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_CRUSADER_01.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_CRUSADER_02.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_GOLD_BLACK.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_GOLD_BLUE.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_SILVER_BLUE.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_WOOD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.YELLOW_KITESHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.ASKIR_SCUTUM.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_GOLD_BLACK.get(), RenderShieldOnBack::new);

        //BackShields Pos 2 Backslot
        //Round Shields
        CuriosRendererRegistry.register(RPGItems.BIG_ROUND_WHIRL_GREEN_WHITE_SHIELD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_RUNE.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_TRISKELE.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_BIRD.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_ORNATE.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_CARVED.get(), RenderShieldOnBack::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_TWISTED.get(), RenderShieldOnBack::new);

        //BackWeapons x Slot
        CuriosRendererRegistry.register(RPGItems.DAEDRIC_BOW.get(), RenderShieldOnBack::new);                    //TODO TESTING
        CuriosRendererRegistry.register(RPGItems.DAEDRIC_CROSSBOW.get(), RenderShieldOnBack::new);               //TODO TESTING

        //SideWeapons BeltSlot
        CuriosRendererRegistry.register(RPGItems.BLADE_SWORD.get(), RenderShieldOnBack::new);                    //TODO TESTING
    }

    private void blockRenderer() {
        ItemBlockRenderTypes.setRenderLayer(RPGBlocks.COBALT_BLASTER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RPGBlocks.TINKER_TABLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RPGBlocks.COBALT_BLOCK.get(), RenderType.translucent());
    }


    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {

            });
        }
    }
}