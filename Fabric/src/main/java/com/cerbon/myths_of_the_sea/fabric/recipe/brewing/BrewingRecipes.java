package com.cerbon.myths_of_the_sea.fabric.recipe.brewing;

import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.potion.MTSPotions;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

public class BrewingRecipes {

    public static void register() {
        FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(MTSItems.HIPPOCAMPUS_EYE.get()), MTSPotions.VERY_LONG_NIGHT_VISION.get());
    }
}
