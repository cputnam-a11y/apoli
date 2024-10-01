package io.github.apace100.apoli.recipe;

import io.github.apace100.apoli.Apoli;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ApoliRecipeSerializers {

    public static final RecipeSerializer<PowerCraftingRecipe> POWER_CRAFTING = register("power_crafting", new PowerCraftingRecipe.Serializer());
    public static final RecipeSerializer<ModifiedCraftingRecipe> MODIFIED_CRAFTING = register("modified_crafting", new ModifiedCraftingRecipe.Serializer());

    public static void register() {

    }

    public static <R extends Recipe<?>, S extends RecipeSerializer<R>> S register(String path, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, Apoli.identifier(path), serializer);
    }

}