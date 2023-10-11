package net.vaex.aquilarpg.effects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class RPGSimpleBrewingRecipe implements IBrewingRecipe {
    private final Potion inputPotion;
    private final Potion outputPotion;
    private final Item ingredientItem;
    public RPGSimpleBrewingRecipe(Potion inputPotion, Potion outputPotion, Item ingredientItem) {
        this.inputPotion = inputPotion;
        this.outputPotion = outputPotion;
        this.ingredientItem = ingredientItem;
    }

    @Override
    public boolean isInput(ItemStack input) {
        return PotionUtils.getPotion(input) == inputPotion;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == ingredientItem;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (isInput(input) && isIngredient(ingredient)) {
            ItemStack outputPotionItem = new ItemStack(input.getItem());
            outputPotionItem.setTag(new CompoundTag());
            PotionUtils.setPotion(outputPotionItem, outputPotion);

            return outputPotionItem;
        }
        return ItemStack.EMPTY;
    }
}
