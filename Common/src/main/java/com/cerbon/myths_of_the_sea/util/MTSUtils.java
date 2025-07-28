package com.cerbon.myths_of_the_sea.util;

import com.cerbon.myths_of_the_sea.entity.MTSEntityAttributes;
import com.cerbon.myths_of_the_sea.item.custom.bunyip_claw.BaseBunyipClawItem;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

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

    /**
     Checks the armor slots to see if the entity has any item with {@link MTSEntityAttributes#WATER_SPEED}
     @param entity The entity to check
     */
        public static int getWaterSpeedAmount(LivingEntity entity){
            int waterSpeed=0;

            for(ItemStack stack: entity.getArmorSlots()){
                if(stack.getItem() instanceof ArmorItem armor &&
                        armor.getDefaultAttributeModifiers(armor.getEquipmentSlot()).containsKey(MTSEntityAttributes.WATER_SPEED.get())){

                    AttributeModifier modifier = armor.getDefaultAttributeModifiers(armor.getEquipmentSlot()).get(MTSEntityAttributes.WATER_SPEED.get()).iterator().next();

                    if(entity.isInWater()){
                        waterSpeed += (int) modifier.getAmount();
                    }
                }
            }

            return waterSpeed;
        }

        /**
        Simple check to make the dualwield animations
         */
        public static boolean hasDualwieldWeapon(LivingEntity entity){
            return
                    entity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof BaseBunyipClawItem
                    && entity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof BaseBunyipClawItem;
        }
}
