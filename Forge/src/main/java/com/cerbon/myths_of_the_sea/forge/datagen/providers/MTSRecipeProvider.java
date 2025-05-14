package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.myths_of_the_sea.item.MTSItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ArmorItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MTSRecipeProvider extends RecipeProvider {

    public MTSRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MTSItems.BAKE_KUJIRA_ARMOR_SET.get(ArmorItem.Type.HELMET).get())
                .pattern("###")
                .pattern("# #")
                .define('#', MTSItems.BAKE_KUJIRA_BONE.get())
                .unlockedBy("has_bake_kujira_bone", has(MTSItems.BAKE_KUJIRA_BONE.get()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MTSItems.BAKE_KUJIRA_ARMOR_SET.get(ArmorItem.Type.CHESTPLATE).get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', MTSItems.BAKE_KUJIRA_BONE.get())
                .unlockedBy("has_bake_kujira_bone", has(MTSItems.BAKE_KUJIRA_BONE.get()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MTSItems.BAKE_KUJIRA_ARMOR_SET.get(ArmorItem.Type.LEGGINGS).get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', MTSItems.BAKE_KUJIRA_BONE.get())
                .unlockedBy("has_bake_kujira_bone", has(MTSItems.BAKE_KUJIRA_BONE.get()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MTSItems.BAKE_KUJIRA_ARMOR_SET.get(ArmorItem.Type.BOOTS).get())
                .pattern("# #")
                .pattern("# #")
                .define('#', MTSItems.BAKE_KUJIRA_BONE.get())
                .unlockedBy("has_bake_kujira_bone", has(MTSItems.BAKE_KUJIRA_BONE.get()))
                .save(writer);

        //TODO: Add Bunyip Claw recipe
    }
}
