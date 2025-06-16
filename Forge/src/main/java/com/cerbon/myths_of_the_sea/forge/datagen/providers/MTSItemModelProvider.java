package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class MTSItemModelProvider extends ItemModelProvider {

    public MTSItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MythsOfTheSea.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(MTSItems.BAKE_KUJIRA_BONE.get());
        basicItem(MTSItems.BUNYIP_FANG.get());
        basicItem(MTSItems.HIPPOCAMPUS_EYE.get());
        basicItem(MTSItems.LEVIATHAN_HEART.get());

        eggItem(MTSItems.ABAIA_SPAWN_EGG.get());

        MTSItems.BAKE_KUJIRA_ARMOR_SET.values().forEach(armorItem -> basicItem(armorItem.get()));
    }

    protected void eggItem(SpawnEggItem eggItem) {
        getBuilder(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(eggItem)).toString()).parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }
}
