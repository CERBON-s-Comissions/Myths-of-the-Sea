package com.cerbon.myths_of_the_sea.entity.custom.bunyip.goal;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;

public class BunyipAttackGoal extends MeleeAttackGoal {

    public BunyipAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double distToEnemySqr) {
        double d = this.getAttackReachSqr(enemy);

        if (distToEnemySqr <= d && this.getTicksUntilNextAttack() <= 0) {
            this.mob.swing(InteractionHand.MAIN_HAND);

            CapabilityUtils.getLevelEventScheduler(this.mob.level()).addEvent(new TimedEvent(
                    () -> {
                        double newDistToEnemySqr = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(enemy);
                        double d1 = this.getAttackReachSqr(enemy);

                        if (newDistToEnemySqr <= d1 && this.getTicksUntilNextAttack() <= 0) {
                            this.resetAttackCooldown();
                            this.mob.doHurtTarget(enemy);
                        }
                    },
                    18,
                    this.mob::isDeadOrDying
            ));
        }
    }

    @Override
    protected double getAttackReachSqr(@NotNull LivingEntity attackTarget) {
        return this.mob.getBbWidth() * 1.5F * this.mob.getBbWidth() * 1.5F + attackTarget.getBbWidth();
    }
}
