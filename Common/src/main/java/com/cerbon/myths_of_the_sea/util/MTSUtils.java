package com.cerbon.myths_of_the_sea.util;

import net.minecraft.util.Mth;
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

    /**
    Makes an angle move slowly to the desired angle
     @param current The rotation that you want to move
     @param target The desired rotation
     @param maxChange The maximum rotation that it moves every tick
     @return The new rotation
     */
        public static float smoothAngle(float current, float target, float maxChange) {
            float delta = Mth.wrapDegrees(target - current);
            delta = Mth.clamp(delta, -maxChange, maxChange);

            return current + delta;
        }
}
