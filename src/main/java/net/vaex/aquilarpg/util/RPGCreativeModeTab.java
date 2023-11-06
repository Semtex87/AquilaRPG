package net.vaex.aquilarpg.util;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.vaex.aquilarpg.block.RPGBlocks;
import net.vaex.aquilarpg.item.RPGItems;

public class RPGCreativeModeTab {

    public static final CreativeModeTab RPG_TOOLS = new CreativeModeTab("aquilarpg_tools") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGItems.WRENCH.get());
        }
    };

    public static final CreativeModeTab RPG_WEAPON = new CreativeModeTab("aquilarpg_weapon") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGItems.STEEL_SWORD.get());
        }
    };

    public static final CreativeModeTab RPG_ARMOR= new CreativeModeTab("aquilarpg_armor") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGItems.ASKIR_IMPERIAL_GUARD_HELMET.get());
        }
    };


    //MISC
    public static final CreativeModeTab RPG_BLOCKS = new CreativeModeTab("aquilarpg_blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGBlocks.COBALT_BLASTER.get());
        }
    };
    //BLOCKS
    public static final CreativeModeTab RPG_MISC = new CreativeModeTab("aquilarpg_misc") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGItems.SCROLL.get());
        }
    };
}