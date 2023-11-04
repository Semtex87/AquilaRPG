package net.vaex.aquilarpg.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;
import net.vaex.aquilarpg.item.RPGItems;
import net.vaex.aquilarpg.util.RPGCreativeModeTab;

import java.util.function.Supplier;

public class RPGBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AquilaRPG.MOD_ID);

    public static final RegistryObject<Block> COBALT_BLOCK = registerBlock("cobalt_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
            .strength(5f)
            .requiresCorrectToolForDrops()), RPGCreativeModeTab.RPG_BLOCKS);
        public static final RegistryObject<Block> COBALT_ORE = registerBlock("cobalt_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
            .strength(4f)
            .requiresCorrectToolForDrops()), RPGCreativeModeTab.RPG_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_COBALT_ORE = registerBlock("deepslate_cobalt_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
            .strength(4f)
            .requiresCorrectToolForDrops()), RPGCreativeModeTab.RPG_BLOCKS);
    public static final RegistryObject<Block> PEAT_BLOCK = registerBlock("peat_block", () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
            .sound(SoundType.GRAVEL)
            .strength(1)
            .requiresCorrectToolForDrops()), RPGCreativeModeTab.RPG_BLOCKS);



    public static final RegistryObject<Block> COBALT_BLASTER = registerBlock("smeltery",
            () -> new CobaltBlasterBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()),
            RPGCreativeModeTab.RPG_BLOCKS);
    public static final RegistryObject<Block> TINKER_TABLE = registerBlock("tinker_table",
            () -> new TinkerTableBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()),
            RPGCreativeModeTab.RPG_BLOCKS);

    public static final RegistryObject<Block> COBALT_LAMP = registerBlock("cobalt_lamp",
            () -> new CobaltLampBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2f).requiresCorrectToolForDrops()
                    .lightLevel((state) -> state.getValue(CobaltLampBlock.CLICKED) ? 15 : 0)),
            RPGCreativeModeTab.RPG_BLOCKS);


    //--------------------------REGISTRY--------------------------------------
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return RPGItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
