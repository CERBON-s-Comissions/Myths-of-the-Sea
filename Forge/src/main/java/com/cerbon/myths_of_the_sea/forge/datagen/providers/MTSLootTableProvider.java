package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class MTSLootTableProvider implements LootTableSubProvider {

    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(MTSLootTableProvider::new, LootContextParamSets.ENTITY)
        ));
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> output) {
        add(MTSEntities.BAKE_KUJIRA, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(MTSItems.BAKE_KUJIRA_BONE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))))
                ),
                output);


        add(MTSEntities.LEVIATHAN, LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(MTSItems.LEVIATHAN_HEART.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 1f))))
                        ),
                output);

        add(MTSEntities.KRAKEN, LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(MTSItems.KRAKEN_TENTACLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 1f))))
                        ),
                output);
    }

    public <T extends Entity> void add(RegistryEntry<EntityType<T>> entity, LootTable.Builder lootTable, @NotNull BiConsumer<ResourceLocation, LootTable.Builder> output){
        output.accept(new ResourceLocation(MythsOfTheSea.MOD_ID, "entities/"+ entity.getId().getPath()) , lootTable);
    }
}
