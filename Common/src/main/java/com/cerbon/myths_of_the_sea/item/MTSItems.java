package com.cerbon.myths_of_the_sea.item;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.item.custom.LeviathanHeartItem;
import com.cerbon.myths_of_the_sea.item.custom.armor.MTSArmorMaterials;
import com.cerbon.myths_of_the_sea.platform.Services;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class MTSItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(
            BuiltInRegistries.ITEM,
            MythsOfTheSea.MOD_ID
    );

    public static final RegistryEntry<Item> BAKE_KUJIRA_BONE = registerItem("bake_kujira_bone");

    public static final RegistryEntry<Item> BUNYIP_FANG = registerItem("bunyip_fang");

    public static final RegistryEntry<Item> HIPPOCAMPUS_EYE = registerItem("hippocampus_eye");

    public static final RegistryEntry<Item> KRAKEN_TENTACLE = registerItem(
            Services.PLATFORM.krakenTentacleItem(new Item.Properties()),
            "kraken_tentacle"
    );

    public static final RegistryEntry<Item> BUNYIP_CLAW = registerItem(
            Services.PLATFORM.bunyipClawItem(5.0F, -2.4F, new Item.Properties().durability(250)),
            "bunyip_claw"
    );

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
}
