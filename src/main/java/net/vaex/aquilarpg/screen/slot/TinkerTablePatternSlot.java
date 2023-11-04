package net.vaex.aquilarpg.screen.slot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.vaex.aquilarpg.item.RPGItems;

import java.util.List;
import java.util.Map;


public class TinkerTablePatternSlot extends Slot {
    public TinkerTablePatternSlot(Container itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return TinkerTablePatternSlot.isPattern(stack);
    }


    @Override
    public int getMaxStackSize(ItemStack pStack) {
        return TinkerTablePatternSlot.isPattern(pStack) ? 1 : super.getMaxStackSize(pStack);
    }

    public static boolean isPattern(ItemStack stack) {
        return stack.is(RPGItems.BLUEPRINT_BOW.get()) ||
                stack.is(RPGItems.BLUEPRINT_DAGGER.get()) ||
                stack.is(RPGItems.BLUEPRINT_AXE.get()) ||
                stack.is(RPGItems.BLUEPRINT_SWORD.get());
    }
}
