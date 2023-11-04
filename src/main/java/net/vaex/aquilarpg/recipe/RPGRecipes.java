package net.vaex.aquilarpg.recipe;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vaex.aquilarpg.AquilaRPG;

public class RPGRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AquilaRPG.MOD_ID);

    public static final RegistryObject<RecipeSerializer<TinkerTableRecipe>> TINKER_TABLE_SERIALIZER =
            SERIALIZERS.register("tinker_table", () -> TinkerTableRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<TinkerTableCommonRecipe>> TINKER_TABLE_COMMON_SERIALIZER =
            SERIALIZERS.register("tinker_table_common", () -> TinkerTableCommonRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<TinkerTableAnvilRecipe>> TINKER_TABLE_ANVIL_SERIALIZER =
            SERIALIZERS.register("tinker_table_anvil", () -> TinkerTableAnvilRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
