package com.cerbon.myths_of_the_sea.forge.recipe.brewing;

import com.cerbon.myths_of_the_sea.forge.recipe.brewing.custom.HippocampusEyeBrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class BrewingRecipes {

    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new HippocampusEyeBrewingRecipe()));
    }
}
