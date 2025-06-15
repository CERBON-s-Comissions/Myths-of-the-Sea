package com.cerbon.myths_of_the_sea.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public final class MTSUtils {

    public static boolean isUsingFullArmorSet(ArmorMaterial material, Player player) {
        return player.getInventory().armor.stream().allMatch(armorStack -> armorStack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == material);
    }

    public static void pounceAtTarget(LivingEntity attacker, LivingEntity target, float horizontalStrength, float upwardStrength) {
        double dx = target.getX() - attacker.getX();
        double dz = target.getZ() - attacker.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);

        if (distance > 0.0001) {
            double vx = (dx / distance) * horizontalStrength;
            double vz = (dz / distance) * horizontalStrength;
            attacker.setDeltaMovement(
                    vx,
                    upwardStrength,
                    vz
            );
            attacker.hasImpulse = true;
        }
    }
}
