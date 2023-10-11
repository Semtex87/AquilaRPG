package net.vaex.aquilarpg.util;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.vaex.aquilarpg.item.RPGItems;

public class RPGCreativeModeTab {

    public static final CreativeModeTab RPG_TOOLS = new CreativeModeTab("aquila_tools") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGItems.LIGHTNING_SPELL.get());
        }
    };

    public static final CreativeModeTab RPG_WEAPON = new CreativeModeTab("aquila_weapon") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGItems.FIRE_SPELL.get());
        }
    };

    public static final CreativeModeTab RPG_ARMOR= new CreativeModeTab("aquila_armor") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGItems.DARK_SPELL.get());
        }
    };


    //MISC
    public static final CreativeModeTab RPG_BLOCKS = new CreativeModeTab("aquila_blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGItems.NATURE_SPELL.get());
        }
    };
    //BLOCKS
    public static final CreativeModeTab RPG_MISC = new CreativeModeTab("aquila_misc") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RPGItems.MANA.get());
        }
    };
}