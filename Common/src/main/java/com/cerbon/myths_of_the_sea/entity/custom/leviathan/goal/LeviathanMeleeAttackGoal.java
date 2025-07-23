package com.cerbon.myths_of_the_sea.entity.custom.leviathan.goal;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.multipart_entities.util.OrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.LeviathanEntity;
import com.cerbon.myths_of_the_sea.util.MTSUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class LeviathanMeleeAttackGoal extends MeleeAttackGoal {

    private final LeviathanEntity leviathan;
    private final int cooldownBetweenAttacks;

    public LeviathanMeleeAttackGoal(LeviathanEntity pathfinderMob, double speedModifier, boolean followingTargetEvenIfNotSeen, int cooldownBetweenAttacks) {
        super(pathfinderMob, speedModifier, followingTargetEvenIfNotSeen);
        this.leviathan =pathfinderMob;
        this.cooldownBetweenAttacks= cooldownBetweenAttacks;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !leviathan.hasAttackedOnWater;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !leviathan.hasAttackedOnWater;
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double distToEnemySqr) {
        double d = this.getAttackReachSqr(enemy);

        if (distToEnemySqr <= d && this.getTicksUntilNextAttack() <= 0 && this.leviathan.isSeeingTarget(enemy)) {
            this.leviathan.swing(InteractionHand.MAIN_HAND);

            CapabilityUtils.getLevelEventScheduler(this.leviathan.level()).addEvent(new TimedEvent(
                    () -> {
                        MTSUtils.pounceAtTarget(this.leviathan, enemy, 0.3F, 0.1F);

                        double newDistToEnemySqr = this.leviathan.getPerceivedTargetDistanceSquareForMeleeAttack(enemy);
                        double d1 = this.getAttackReachSqr(enemy);

                        if (newDistToEnemySqr <= d1 && this.getTicksUntilNextAttack() <= 0) {
                            this.resetAttackCooldown();
                            this.leviathan.doHurtTarget(enemy);
                        }
                    },
                    15,
                    this.leviathan::isDeadOrDying
            ));

                                                                                     //Add a random extra between 0s or 5s
            this.leviathan.cooldownSwimmingAttackTicks = this.cooldownBetweenAttacks+this.leviathan.getRandom().nextIntBetweenInclusive(0, 100);
            this.leviathan.hasAttackedOnWater=true;
        }
    }

    @Override
    protected double getAttackReachSqr(@NotNull LivingEntity attackTarget) {

        CompoundOrientedBox compound = this.leviathan.getCompoundBoundingBox(this.leviathan.getBoundingBox());

        double definitiveMaxSize = 0;
        for (OrientedBox orientedBox : compound) {
            AABB extents = orientedBox.getExtents();
            double xSize = extents.maxX - extents.minX;
            double zSize = extents.maxZ - extents.minZ;

            double maxSize = Math.max(xSize, zSize);

            definitiveMaxSize = Math.max(maxSize, definitiveMaxSize);
        }

        return this.leviathan.getBbWidth() * 0.6F * this.leviathan.getBbWidth() * 0.6F + attackTarget.getBbWidth();
    }
}
