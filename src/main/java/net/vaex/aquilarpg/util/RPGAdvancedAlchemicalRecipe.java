package net.vaex.aquilarpg.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import org.jetbrains.annotations.NotNull;

public class RPGAdvancedAlchemicalRecipe implements IBrewingRecipe {
    private final Item materialOne;
    private final Item ingredientItem;
    private final Item output;

    public RPGAdvancedAlchemicalRecipe(Item materialOne, Item outputItem, Item materialTwo) {
        this.materialOne = materialOne;
        this.output = outputItem;
        this.ingredientItem = materialTwo;
    }


    public boolean isInput(ItemStack material) {
        return material.getItem() == materialOne;
    }


    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == ingredientItem;
    }

    public @NotNull ItemStack getOutput(@NotNull ItemStack input, @NotNull ItemStack ingredient) {
        if (isInput(input) && isIngredient(ingredient)) {

            return output.getDefaultInstance();
        }
        return ItemStack.EMPTY;
    }


}
