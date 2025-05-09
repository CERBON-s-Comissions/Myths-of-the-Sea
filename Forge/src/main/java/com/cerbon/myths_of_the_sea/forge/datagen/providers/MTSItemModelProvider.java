package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MTSItemModelProvider extends ItemModelProvider {

    public MTSItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MythsOfTheSea.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(MTSItems.HIPPOCAMPUS_EYE.get());
        basicItem(MTSItems.LEVIATHAN_HEART.get());
    }
}
