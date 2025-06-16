package com.cerbon.myths_of_the_sea.entity.custom.abaia.goal;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.multipart_entities.util.OrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.myths_of_the_sea.util.MTSUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class AbaiaMeleeAttackGoal extends MeleeAttackGoal {

    public AbaiaMeleeAttackGoal(PathfinderMob pathfinderMob, double d, boolean bl) {
        super(pathfinderMob, d, bl);
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double distToEnemySqr) {
        double d = this.getAttackReachSqr(enemy);

        if (distToEnemySqr <= d && this.getTicksUntilNextAttack() <= 0) {
            this.mob.swing(InteractionHand.MAIN_HAND);

            CapabilityUtils.getLevelEventScheduler(this.mob.level()).addEvent(new TimedEvent(
                    () -> {
                        MTSUtils.pounceAtTarget(this.mob, enemy, 0.55F, 0.1F);

                        double newDistToEnemySqr = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(enemy);
                        double d1 = this.getAttackReachSqr(enemy);

                        if (newDistToEnemySqr <= d1 && this.getTicksUntilNextAttack() <= 0) {
                            this.resetAttackCooldown();
                            this.mob.doHurtTarget(enemy);
                        }
                    },
                    15,
                    this.mob::isDeadOrDying
            ));
        }
    }

    @Override
    protected double getAttackReachSqr(@NotNull LivingEntity attackTarget) {
        if (!(this.mob instanceof MultipartAwareEntity thisMob)) return super.getAttackReachSqr(attackTarget);

        CompoundOrientedBox compound = thisMob.getCompoundBoundingBox(this.mob.getBoundingBox());

        double definitiveMaxSize = 0;
        for (OrientedBox orientedBox : compound) {
            AABB extents = orientedBox.getExtents();
            double xSize = extents.maxX - extents.minX;
            double zSize = extents.maxZ - extents.minZ;

            double maxSize = Math.max(xSize, zSize);

            definitiveMaxSize = Math.max(maxSize, definitiveMaxSize);
        }

        return definitiveMaxSize * 2.0F + attackTarget.getBbWidth() + 0.5F;
    }
}
