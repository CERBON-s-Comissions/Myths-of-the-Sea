package com.cerbon.myths_of_the_sea.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class GeoControllersUtil {

    public static final RawAnimation DIE_ANIM = RawAnimation.begin().thenPlayAndHold("death");
    public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    public static final RawAnimation MOVE_ANIM = RawAnimation.begin().thenLoop("move");

    public static  <E extends Mob & GeoEntity> PlayState commonWaterAnimalDie(final AnimationState<E> state, Mob mob) {
        if (state.getAnimatable().isDeadOrDying() && mob.isInWater())
            return state.setAndContinue(DIE_ANIM);

        return PlayState.STOP;
    }

    public static <E extends Mob & GeoEntity> PlayState commonMoveController(final AnimationState<E> state, Mob mob){
        if (mob.swinging)
            return PlayState.STOP;

        if (!state.isMoving())
            return state.setAndContinue(IDLE_ANIM);

        else if (state.isMoving())
            return state.setAndContinue(MOVE_ANIM);

        return PlayState.STOP;
    }

    /**
     * Works just like the GeckoLib {@link AnimationState#isMoving()} but on server side
     */
    public static boolean isMoving(LivingEntity entity, float motionThreshold){
        float limbSwingAmount = 0;


        if (entity.isAlive()) {
            limbSwingAmount = entity.walkAnimation.speed(entity.tickCount);


            if (limbSwingAmount > 1f)
                limbSwingAmount = 1f;
        }

        Vec3 velocity = entity.getDeltaMovement();
        float avgVelocity = (float)((Math.abs(velocity.x) + Math.abs(velocity.z)) / 2f);

        return avgVelocity >= motionThreshold && limbSwingAmount != 0;
    }
}
