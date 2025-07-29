package com.cerbon.myths_of_the_sea.entity.custom.abaia.goal;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.multipart_entities.util.OrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.myths_of_the_sea.util.MTSUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class AbaiaMeleeAttackGoal extends MeleeAttackGoal {

    private boolean soundTriggered=false;

    private final SoundEvent attackSound;

    private final int attackDelay;
    private final int soundDelay;

    public AbaiaMeleeAttackGoal(PathfinderMob pathfinderMob, double d, boolean bl, SoundEvent attackSound, int attackDelay, int soundDelay) {
        super(pathfinderMob, d, bl);
        this.attackSound=attackSound;
        this.attackDelay=attackDelay;
        this.soundDelay=soundDelay;
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double distToEnemySqr) {
        double d = this.getAttackReachSqr(enemy);

        if (distToEnemySqr <= d && this.getTicksUntilNextAttack() <= 0) {
            this.mob.swing(InteractionHand.MAIN_HAND);

            //Sound with better synchronization
            if(!soundTriggered)
                CapabilityUtils.getLevelEventScheduler(this.mob.level()).addEvent(new TimedEvent(
                    () -> {
                        if (this.mob.level() instanceof ServerLevel serverLevel)
                            SoundUtils.playSound(serverLevel, this.mob.position(), attackSound, SoundSource.HOSTILE, 3F, 6D);
                        soundTriggered=true;
                    },
                    soundDelay,
                    () -> this.mob.isDeadOrDying() || this.soundTriggered || this.getTicksUntilNextAttack()>0
                ));

            CapabilityUtils.getLevelEventScheduler(this.mob.level()).addEvent(new TimedEvent(
                    () -> {
                        MTSUtils.pounceAtTarget(this.mob, enemy, 0.55F, 0.1F);

                        double newDistToEnemySqr = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(enemy);
                        double d1 = this.getAttackReachSqr(enemy);

                        if (newDistToEnemySqr <= d1 && this.getTicksUntilNextAttack() <= 0) {
                            this.resetAttackCooldown();
                            this.mob.doHurtTarget(enemy);
                        }

                        soundTriggered=false;
                    },
                    attackDelay,
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

        return definitiveMaxSize * 2.35F + attackTarget.getBbWidth() + 0.5F;
    }
}
