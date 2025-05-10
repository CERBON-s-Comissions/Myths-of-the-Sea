package com.cerbon.myths_of_the_sea.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public final class MTSUtils {

    public static boolean isUsingFullArmorSet(ArmorMaterial material, Player player) {
        return player.getInventory().armor.stream().allMatch(armorStack -> armorStack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == material);
    }
}
