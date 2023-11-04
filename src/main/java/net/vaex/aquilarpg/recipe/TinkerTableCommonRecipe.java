package net.vaex.aquilarpg.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.vaex.aquilarpg.AquilaRPG;
import org.jetbrains.annotations.Nullable;


public class TinkerTableCommonRecipe extends SingleItemRecipe {

    public TinkerTableCommonRecipe(ResourceLocation id, String string, Ingredient recipeItems, ItemStack output) {
        super(Type.INSTANCE, RPGRecipes.TINKER_TABLE_SERIALIZER.get(), id, string, recipeItems, output);

    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0)) && this.ingredient.test(container.getItem(1)) && this.ingredient.test(container.getItem(2));
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(Container p_44004_) {
        return super.getRemainingItems(p_44004_);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack getToastSymbol() {
        return super.getToastSymbol();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public boolean isIncomplete() {
        return super.isIncomplete();
    }

    public static class Type implements RecipeType<TinkerTableCommonRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "tinker_table";
    }

    public static class Serializer implements RecipeSerializer<TinkerTableCommonRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(AquilaRPG.MOD_ID, "tinker_table_common");

        @Override
        public TinkerTableCommonRecipe fromJson(ResourceLocation id, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");

            Ingredient ingredient;
            if (GsonHelper.isArrayNode(json, "ingredient")) {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(json, "ingredient"));
            } else {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            }

            String s1 = GsonHelper.getAsString(json, "result");
            int i = GsonHelper.getAsInt(json, "count");
            ItemStack itemstack = new ItemStack(Registry.ITEM.get(new ResourceLocation(s1)), i);

            return new TinkerTableCommonRecipe(id, group , ingredient, itemstack);
        }

        @org.jetbrains.annotations.Nullable
        @Override
        public TinkerTableCommonRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            String group = buf.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            ItemStack itemstack = buf.readItem();

            return new TinkerTableCommonRecipe(id, group, ingredient, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, TinkerTableCommonRecipe recipe) {
            buf.writeUtf(recipe.group);
            recipe.ingredient.toNetwork(buf);
            buf.writeItem(recipe.result);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        private static <O> Class<O> castClass(Class<?> cls) {
            return (Class<O>)cls;
        }
    }
}
