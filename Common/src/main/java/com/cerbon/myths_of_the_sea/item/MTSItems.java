package com.cerbon.myths_of_the_sea.item;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.item.custom.LeviathanHeartItem;
import com.cerbon.myths_of_the_sea.item.custom.MTSSpawnEggItem;
import com.cerbon.myths_of_the_sea.item.custom.armor.MTSArmorMaterials;
import com.cerbon.myths_of_the_sea.platform.Services;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class MTSItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(
            BuiltInRegistries.ITEM,
            MythsOfTheSea.MOD_ID
    );

    public static final TagKey<Item> HIPPOCAMPUS_FOOD = bind("hippocampus_food");

    public static final TagKey<Item> HIPPOCAMPUS_FOOD_MATING = bind("hippocampus_food_mating");


    public static final RegistryEntry<Item> BAKE_KUJIRA_BONE = registerItem("bake_kujira_bone");

    public static final RegistryEntry<Item> BUNYIP_FANG = registerItem("bunyip_fang");

    public static final RegistryEntry<Item> HIPPOCAMPUS_EYE = registerItem("hippocampus_eye");

    public static final RegistryEntry<Item> KRAKEN_TENTACLE = registerItem(
            Services.PLATFORM.krakenTentacleItem(new Item.Properties().stacksTo(1)),
            "kraken_tentacle"
    );

    public static final RegistryEntry<Item> BUNYIP_CLAW = registerItem(
            Services.PLATFORM.bunyipClawItem(4.0F, -2.4F, new Item.Properties().durability(250)),
            "bunyip_claw"
    );

    //TODO: Add Abaia Tail

    public static final RegistryEntry<Item> LEVIATHAN_HEART = registerItem(
            () -> new LeviathanHeartItem(new Item.Properties().food(
                    new FoodProperties.Builder()
                            .alwaysEat()
                            .nutrition(4)
                            .saturationMod(1.2f)
                            .build()
                    )
            ),
            "leviathan_heart"
    );

    public static final Map<ArmorItem.Type, RegistryEntry<ArmorItem>> BAKE_KUJIRA_ARMOR_SET = registerFullArmorSet(MTSArmorMaterials.BAKE_KUJIRA);

    // Spawn Eggs
    public static final RegistryEntry<SpawnEggItem> ABAIA_SPAWN_EGG = registerEgg(
            MTSEntities.ABAIA,
            0x537269,
            0x71948a,
            MTSEntities.ABAIA.getId().getPath()
    );

    public static final RegistryEntry<SpawnEggItem> BAKE_KUJIRA_SPAWN_EGG = registerEgg(
            MTSEntities.BAKE_KUJIRA,
            0xeae0ca,
            0xf4eddf,
            MTSEntities.BAKE_KUJIRA.getId().getPath()
    );

    public static final RegistryEntry<SpawnEggItem> BUNYIP_SPAWN_EGG = registerEgg(
            MTSEntities.BUNYIP,
            0x7e513a,
            0x956d59,
            MTSEntities.BUNYIP.getId().getPath()
    );

    public static final RegistryEntry<SpawnEggItem> LEVIATHAN_SPAWN_EGG = registerEgg(
            MTSEntities.LEVIATHAN,
            0x556c76,
            0x677e87,
            MTSEntities.LEVIATHAN.getId().getPath()
    );

    public static final RegistryEntry<SpawnEggItem> HIPPOCAMPUS_SPAWN_EGG = registerEgg(
            MTSEntities.HIPPOCAMPUS,
            0x3fadcd,
            0xdfca6f,
            MTSEntities.HIPPOCAMPUS.getId().getPath()
    );

    public static final RegistryEntry<SpawnEggItem> KRAKEN_SPAWN_EGG = registerEgg(
            MTSEntities.KRAKEN,
            0x5b7468,
            0x6d8278,
            MTSEntities.KRAKEN.getId().getPath()
    );

    // ============== Registration methods ==============
    private static Map<ArmorItem.Type, RegistryEntry<ArmorItem>> registerFullArmorSet(ArmorMaterial material) {
        return registerFullArmorSet(material, properties -> properties);
    }

    private static Map<ArmorItem.Type, RegistryEntry<ArmorItem>> registerFullArmorSet(ArmorMaterial material, UnaryOperator<Item.Properties> itemProperties) {
        return ImmutableMap.of(
                ArmorItem.Type.HELMET, registerArmor(ArmorItem.Type.HELMET, material, itemProperties),
                ArmorItem.Type.CHESTPLATE, registerArmor(ArmorItem.Type.CHESTPLATE, material, itemProperties),
                ArmorItem.Type.LEGGINGS, registerArmor(ArmorItem.Type.LEGGINGS, material, itemProperties),
                ArmorItem.Type.BOOTS, registerArmor(ArmorItem.Type.BOOTS, material, itemProperties)
        );
    }

    private static RegistryEntry<ArmorItem> registerArmor(ArmorItem.Type armorType, ArmorMaterial material, UnaryOperator<Item.Properties> itemProperties) {
        String materialName = material.getName().split(":")[1];
        return registerItem(() -> new ArmorItem(material, armorType, itemProperties.apply(new Item.Properties())), materialName + "_" + armorType.getName());
    }

    public static <T extends Mob> RegistryEntry<SpawnEggItem> registerEgg(Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor, String id) {
        return registerItem(() -> new MTSSpawnEggItem(entityType, backgroundColor, highlightColor, new Item.Properties()), id + "_spawn_egg");
    }

    private static RegistryEntry<Item> registerItem(String id) {
        return registerItem(new Item.Properties(), id);
    }

    private static RegistryEntry<Item> registerItem(Item.Properties itemProperties, String id) {
        return registerItem(() -> new Item(itemProperties), id);
    }

    private static <T extends Item> RegistryEntry<T> registerItem(Supplier<T> item, String id) {
        return ITEMS.register(id, item);
    }

    public static void register() {
        ITEMS.register();
    }

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(MythsOfTheSea.MOD_ID, name));
    }
}
