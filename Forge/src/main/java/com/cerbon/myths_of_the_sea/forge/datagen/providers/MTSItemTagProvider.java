package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MTSItemTagProvider extends ItemTagsProvider {

    public MTSItemTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> completableFuture2, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, completableFuture2, MythsOfTheSea.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(MTSItems.HIPPOCAMPUS_FOOD)
                .add(Items.COD)
                .add(Items.SALMON)
                .add(Items.TROPICAL_FISH);

        this.tag(MTSItems.HIPPOCAMPUS_FOOD_MATING)
                .add(Items.COOKED_COD)
                .add(Items.COOKED_SALMON);
    }
}
