package com.cerbon.myths_of_the_sea.entity.custom.kraken.goal;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.multipart_entities.util.OrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.myths_of_the_sea.entity.custom.kraken.KrakenEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class KrakenMeleeAttackGoal extends MeleeAttackGoal {
    private final KrakenEntity kraken;

    public KrakenMeleeAttackGoal(KrakenEntity mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.kraken=mob;
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double distToEnemySqr) {
        double d = this.getAttackReachSqr(enemy);



        if (distToEnemySqr <= d && this.getTicksUntilNextAttack() <= 0) {


            boolean makesGrabAttack=
                    //50% probability if under 25% of life
                    this.kraken.getHealth()<this.kraken.getMaxHealth()*0.25?  kraken.getRandom().nextIntBetweenInclusive(0, 1)==0:
                            //33% probability if under 50% of life
                            this.kraken.getHealth()<this.kraken.getMaxHealth()*0.5? kraken.getRandom().nextIntBetweenInclusive(0, 2)==0:
                                    //20% probability if over 50% but less than max life
                                    this.kraken.getHealth() < this.kraken.getMaxHealth() && kraken.getRandom().nextIntBetweenInclusive(0, 4) == 0;

            //To being able to switch animations
            this.mob.swing(makesGrabAttack? InteractionHand.OFF_HAND: InteractionHand.MAIN_HAND);

            CapabilityUtils.getLevelEventScheduler(this.mob.level()).addEvent(new TimedEvent(
                    () -> {

                        double newDistToEnemySqr = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(enemy);
                        double d1 = this.getAttackReachSqr(enemy);

                        if (newDistToEnemySqr <= d1 && this.getTicksUntilNextAttack() <= 0) {
                            this.resetAttackCooldown();

                            if(!makesGrabAttack)
                                this.mob.doHurtTarget(enemy);


                            //If the enemy is in a boat, it sinks the boat
                            if(enemy.getVehicle()!=null && enemy.getVehicle() instanceof Boat boat){
                                Vec3 direction = boat.position().subtract(this.kraken.position());

                                // Remove vertical influence
                                Vec3 horizontal = new Vec3(direction.x, 0, direction.z).normalize();

                                // Apply horizontal "shove" + vertical "pull"
                                boat.setDeltaMovement(horizontal.scale(2).add(0, -2.0, 0));
                            }

                            //Makes the enemy go down
                            enemy.setDeltaMovement(enemy.getDeltaMovement().add(0.0, -1.0, 0.0));

                            if(makesGrabAttack){
                                this.kraken.setHealthWhenStartRiding(this.kraken.getHealth());
                                enemy.startRiding(this.mob);
                            }

                        }
                    },
                    12,
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

        return this.mob.getBbWidth() * 0.85F * this.mob.getBbWidth() * 0.85F + attackTarget.getBbWidth();
    }
}
