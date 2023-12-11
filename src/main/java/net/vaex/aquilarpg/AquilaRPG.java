package net.vaex.aquilarpg;

import com.mojang.logging.LogUtils;
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
import net.vaex.aquilarpg.effects.RPGEffectManager;
import net.vaex.aquilarpg.effects.RPGPotionManager;
import net.vaex.aquilarpg.effects.RPGSimpleBrewingRecipe;
import net.vaex.aquilarpg.enchantment.RPGEnchantments;
import net.vaex.aquilarpg.entity.RPGModEntities;
import net.vaex.aquilarpg.entity.curio.RenderBackItem;
import net.vaex.aquilarpg.entity.curio.RenderHipItem;
import net.vaex.aquilarpg.entity.item.renderer.*;
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
        CuriosRendererRegistry.register(RPGItems.LARGE_KNIGHT_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.BLACK_GUARD_TOWER_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.BLACK_SUN_TOWER_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.BLACK_SUN_GOTHIC_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.DWARVEN_SMALL_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.DWARVEN_TOWER_SHIELD.get(), RenderBackItem::new);
        //GOTHIC Shields
        CuriosRendererRegistry.register(RPGItems.TEMPLAR_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.SMALL_KNIGHT_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_BLACK_YELLOW_02_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_GREEN_WHITE_02_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_02_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_WHITE_02_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_WHITE_03_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_WHITE_04_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_RED_WHITE_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLACK_01_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLACK_02_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLACK_03_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLUE_02_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_WHITE_BLUE_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GOTHIC_YELLOW_DEER_SHIELD.get(), RenderBackItem::new);

        CuriosRendererRegistry.register(RPGItems.KITESHIELD_BLACK_01.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_BLACK_WHITE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_CRUSADER_01.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_CRUSADER_02.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_GOLD_BLACK.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_GOLD_BLUE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_SILVER_BLUE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_WOOD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.YELLOW_KITESHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ASKIR_SCUTUM.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KITESHIELD_GOLD_BLACK.get(), RenderBackItem::new);
        //Round Shields
        CuriosRendererRegistry.register(RPGItems.BIG_ROUND_WHIRL_GREEN_WHITE_SHIELD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_RUNE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_TRISKELE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_BIRD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_ORNATE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_CARVED.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ROUND_SHIELD_TWISTED.get(), RenderBackItem::new);
        //BackWeapons twohand
        CuriosRendererRegistry.register(RPGItems.DAEDRIC_BOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.BONE_LONGBOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.CRIMSON_BOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.DEMONIC_EMBRACE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GLASS_BOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.NORDIC_BOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.SCORPION_STING.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.SILVER_BOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_BOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_LONG_WARBOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.WINDFORCE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.WOOD_BOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.DAEDRIC_CROSSBOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.CRIMSON_CROSSBOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GLASS_CROSSBOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_CROSSBOW.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.BIPOLARBLADE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.BATTLESTAFF.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.BLACK_CLEAVER.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.BLACKGUARD_CLAYMORE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.BRONZE_CLAYMORE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.CHRYSAMERE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.CRIMSON_HARVESTER.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.DAEDRIC_GREATAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.DECORATED_SPEAR.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.DRAGONBONE_GREATAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.DRAGONBONE_GREATSWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.DWARVEN_GREATAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.EBONY_GREATSWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.FIRE_SWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GIMLIS_GREATAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GLAMDRING.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GLASS_CLAYMORE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.GLASS_GREATSWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.HAMBURGER_RICHTSCHWERT.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ICEBLADEOFTHEMONARCH.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ICH_RICHTE_RICHTSCHWERT.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_GREATAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_GREATHAMMER.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_GREATSWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_HALBERD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_PICKAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KRUSH_IRMAK.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.KRUSH_TARACH.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.MARAUDER_GREATSWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.NARSIL_REFORGED.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.NORDIC_TWOHANDER.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.OBSIDIAN_GREATSWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.ONEMANSLAND_STEEL_SWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.RUSTED_PICKAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.SCYTHE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.SPINALREAPER.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_CLAYMORE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_GREATAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_GREATSWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_WARHAMMER.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_WARSCYTHE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_CLAYMORE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_CRESCENT_LARGE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_GREAT_TACHI.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_GREATAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_GREATHAMMER.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_GREATSWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_HALBERD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_PICKAXE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.STREICHERS_SWORD.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.TWOHAND_MACE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.TWOHAND_WOODEN_MACE.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.URUK_HAI_HEADHUNTER.get(), RenderBackItem::new);
        CuriosRendererRegistry.register(RPGItems.VIKING_DOUBLEAXE.get(), RenderBackItem::new);

        //SideWeapons BeltSlot
        CuriosRendererRegistry.register(RPGItems.BLADE_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.BROADAXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.BLOODTHORN.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.BLADE_OF_WOE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.ADAMANTIUM_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.ASSASSIN_POISONDAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.ASSASSINS_POISONBLADE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.BIFURS_MACE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.BONE_AXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.BONE_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.BONE_PICKAXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.BOROMIRS_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.BRONZE_PICKAXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.COPPER_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.CRIMSON_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.CRIMSON_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.DAEDRIC_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.DUSK_FANG.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.DOOMBRINGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.DWARVEN_AXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.ELVEN_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.FANCY_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.FANCY_KRIS.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.GIMLIS_AXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.GLASS_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.GLASS_LONGSWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.GLASS_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.HALADLE_KNIFE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.HUNTING_KNIVE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_AXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_SHORTSWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_LONGSWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_SPEAR.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.IRON_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.KRUSH_AGASH.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.KRUSH_PACH.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.KRUSH_TUROK.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.LEVIATAN.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.MARAUDER_FALCHION.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.MESSER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.MITHRIL_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.NAILED_CLUB.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.NARSIL_BROKEN.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.NORDIC_AXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.NORDIC_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.OBLIVION.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.OLD_CLEAVER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.OLD_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.RAVENBEAK.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.RAZORTOOTH.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.RUSTED_AXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.RUSTED_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.RUSTED_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.SHOTEL.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_AXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_DAGGER.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_KATANA.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_LONGSWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_SPEAR.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STAHLRIM_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_CRESCENT.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_FLAIL.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_KATANA.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_LONGSWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_MACE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_SPEAR.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STEEL_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.STING.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.URUK_HAI_SWORD.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.VIKING_BARTAXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.VIKING_BROADAXE.get(), RenderHipItem::new);
        CuriosRendererRegistry.register(RPGItems.WOODEN_CLUB.get(), RenderHipItem::new);


    }

    private void blockRenderer() {
        ItemBlockRenderTypes.setRenderLayer(RPGBlocks.COBALT_BLASTER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RPGBlocks.TINKER_TABLE.get(), RenderType.cutout());
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