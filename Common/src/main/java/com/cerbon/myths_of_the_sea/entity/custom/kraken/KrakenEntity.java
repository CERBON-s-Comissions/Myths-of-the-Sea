package com.cerbon.myths_of_the_sea.entity.custom.kraken;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.LeviathanMoveControl;
import com.cerbon.myths_of_the_sea.entity.custom.util.ExtraReachNearestAttackGoal;
import com.cerbon.myths_of_the_sea.entity.custom.kraken.goal.KrakenMeleeAttackGoal;
import com.cerbon.myths_of_the_sea.entity.custom.util.BreachingWaterBoundPathNavigation;
import com.cerbon.myths_of_the_sea.util.GeoControllersUtil;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class KrakenEntity extends WaterAnimal implements GeoEntity, MultipartAwareEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation SIMPLE_ATTACK_ANIM = RawAnimation.begin().thenPlay("attack_simple");
    private static final RawAnimation GRAB_ATTACK_ANIM = RawAnimation.begin().thenPlay("attack_grab");
    private static final RawAnimation MOVE_GROUND = RawAnimation.begin().then("move_ground", Animation.LoopType.PLAY_ONCE);
    private static final RawAnimation IDLE_GROUND = RawAnimation.begin().then("idle_ground", Animation.LoopType.PLAY_ONCE);

    public static final RawAnimation JUMP = RawAnimation.begin().thenPlayAndHold("jump_start");
    public static final RawAnimation LANDING = RawAnimation.begin().thenPlay("jump_landing");

    private static final int ATTACK_ANIM_TIME = 21;

    private final KrakenEntityHitboxes hitboxManager = new KrakenEntityHitboxes(this);

    public static final EntityDataAccessor<Float> HEALTH_WHEN_START_RIDING = SynchedEntityData.defineId(KrakenEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Boolean> FALLING = SynchedEntityData.defineId(KrakenEntity.class, EntityDataSerializers.BOOLEAN);



    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEALTH_WHEN_START_RIDING, 0f);
        this.entityData.define(FALLING, false);
    }

    public float getHealthWhenStartRiding() {
        return this.entityData.get(HEALTH_WHEN_START_RIDING);
    }

    public void setHealthWhenStartRiding(float healthWhenStartRiding) {
        this.entityData.set(HEALTH_WHEN_START_RIDING, healthWhenStartRiding);
    }

    public void setIsFalling(boolean isFalling) {
        this.entityData.set(FALLING, isFalling);
    }

    public boolean isFalling() {
        return this.entityData.get(FALLING);
    }

    public KrakenEntity(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);

        this.xpReward=50;
//        this.setMaxUpStep(1.0F);
        this.moveControl = new LeviathanMoveControl(this, 85, 10, 0.3F, 0.5F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 20);
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.21F)
                .add(Attributes.FOLLOW_RANGE, 64F)
                .build();
    }

    @SuppressWarnings({"unused", "deprecation"})
    public static boolean surfaceWaterSpawnRulesDeepAndNotNearKraken(
            EntityType<? extends WaterAnimal> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int radiusToSearchKraken=200;

        int seaLevel = level.getSeaLevel();
        //70 blocks below water
        int spawnLevel = seaLevel - 70;

        return pos.getY() >= spawnLevel
                && pos.getY() <= seaLevel
                && level.getFluidState(pos.below()).is(FluidTags.WATER)
                && level.getBlockState(pos.above()).is(Blocks.WATER)
                //This make not spawn two kraken too nearby
                && level.getEntitiesOfClass(
                KrakenEntity.class,
                new AABB(pos.getX()-radiusToSearchKraken, pos.getY()-radiusToSearchKraken, pos.getZ()-radiusToSearchKraken,
                        pos.getX()+radiusToSearchKraken, pos.getY()+radiusToSearchKraken, pos.getZ()+radiusToSearchKraken),
                kraken -> true).isEmpty();
    }

    @Override
    public int getExperienceReward() {
        return this.xpReward;
    }



    //Goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new KrakenMeleeAttackGoal(this, 2.0F, false));

        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new ExtraReachNearestAttackGoal<>(this, Player.class, 10, false, false,
                target -> target.isInWater() || (target.getVehicle()!=null && target.getVehicle() instanceof Boat)));

        this.targetSelector.addGoal(2, new ExtraReachNearestAttackGoal<>(this, Villager.class, 10, false, false,
                target -> target.isInWater() || (target.getVehicle()!=null && target.getVehicle() instanceof Boat)));
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity target) {
        return super.canAttack(target) && !(target.getVehicle() instanceof KrakenEntity) && !this.isVehicle();
    }

    public float vehicleRotationProgress = 1.0f; //
    @Override
    public void aiStep() {
        super.aiStep();

        if(this.isVehicle()){
            this.vehicleRotationProgress=0f;
        } else if (this.vehicleRotationProgress<1){
            float speed = 0.05f; // Lower = smoother transition
            this.vehicleRotationProgress = Mth.clamp(this.vehicleRotationProgress + speed, 0, 1);
        }


        //Damages its victim and heals 1 heart each second
        if(this.isVehicle() && this.tickCount%20==0){
            Entity passenger=this.getPassengers().get(0);

            passenger.hurt(this.damageSources().mobAttack(this), 2);

            if(this.getHealth()<this.getMaxHealth()){
                this.heal(2);

                //Particles on the victim
                for (int i = 0; i < 6; i++) {
                    double d = this.random.nextGaussian() * 0.02;
                    double e = this.random.nextGaussian() * 0.02;
                    double f = this.random.nextGaussian() * 0.02;
                    this.level().addParticle(ParticleTypes.DAMAGE_INDICATOR, passenger.getRandomX(1.0), passenger.getRandomY(), passenger.getRandomZ(1.0f), d, e, f);
                    this.level().addParticle(ParticleTypes.SOUL, passenger.getRandomX(1.0), passenger.getRandomY(), passenger.getRandomZ(1.0f), d, e, f);
                }


                for (int i = 0; i < 6; i++) {
                    double d = this.random.nextGaussian() * 0.02;
                    double e = this.random.nextGaussian() * 0.02;
                    double f = this.random.nextGaussian() * 0.02;
                    this.level().addParticle(ParticleTypes.GLOW, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), d, e, f);
                }


                //To update the threshold to being released
                this.setHealthWhenStartRiding(this.getHealthWhenStartRiding()+2f);
            }
        }
    }

    @SuppressWarnings("all")
    //Damage necessary to being released
    private final float healthLossToBeReleased =2.5f;

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        //If receives more than the threshold of damage that when first got its pray, it dismounts it
        if(this.isVehicle() && this.getHealth() < (this.getHealthWhenStartRiding() - healthLossToBeReleased)){
            this.getPassengers().get(0).stopRiding();
        }

        return super.hurt(source, amount);
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        return new Vec3(passenger.getX(), passenger.getBoundingBox().maxY, passenger.getZ());
    }

    @Override
    protected void positionRider(@NotNull Entity passenger, @NotNull MoveFunction callback) {
        if (this.hasPassenger(passenger)) {

            // Vertical position
            double y = this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() - (GeoControllersUtil.isMoving(this, 0.0015f)? 4.8: 2.8);

            // Offset forward and right relative to the body rotation
            float bodyYawRad = this.yBodyRot * Mth.DEG_TO_RAD;
            double forwardOffset =  GeoControllersUtil.isMoving(this, 0.0015f)? -1.8: -0.9; // Forward (Z direction)
            double sideOffset = GeoControllersUtil.isMoving(this, 0.0015f)? -3.2 : -2.1;    // Right (X direction)

            double x = this.getX() + (Mth.sin(bodyYawRad) * forwardOffset) + (Mth.cos(bodyYawRad) * sideOffset);
            double z = this.getZ() - (Mth.cos(bodyYawRad) * forwardOffset) + (Mth.sin(bodyYawRad) * sideOffset);

            callback.accept(passenger, x, y, z);
        }
    }



    //Gecko
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Attack", 5, (state -> {
            if (this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
                state.getController().forceAnimationReset();
                state.getController().setAnimation(this.swingingArm.equals(InteractionHand.OFF_HAND)?  GRAB_ATTACK_ANIM: SIMPLE_ATTACK_ANIM);

                CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(
                        () -> this.swinging = false,
                        ATTACK_ANIM_TIME
                ));
            }
            return PlayState.CONTINUE;
        })));


        controllers.add(new AnimationController<>(this, "Movement", 10, (state) -> {
            if (this.swinging)
                return PlayState.STOP;

            if (!state.isMoving())
                return state.setAndContinue(this.isInWater()? GeoControllersUtil.IDLE_ANIM: IDLE_GROUND);

            else if (state.isMoving())
                return state.setAndContinue(this.isInWater()? GeoControllersUtil.MOVE_ANIM: MOVE_GROUND);

            return PlayState.STOP;
        }));

        //Jumping
        controllers.add(
                new AnimationController<>(this, "JumpController", 1, state -> {
                    if(this.isFalling()){
                        state.setAnimation(JUMP);
                        return PlayState.CONTINUE;
                    } else {
                        state.getController().forceAnimationReset();
                        return PlayState.STOP;
                    }
                }));

        //Landing
        controllers.add(
                new AnimationController<>(this, "LandingController", 1, state -> PlayState.STOP)
                        .triggerableAnim("landing", LANDING)
        );

        controllers.add(new AnimationController<>(this, "Die", 0, state -> GeoControllersUtil.commonWaterAnimalDie(state, this)));
    }



    //Navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new KrakenBodyRotationControl(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new BreachingWaterBoundPathNavigation(this, level);
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }

    @Override
    public int getHeadRotSpeed() {
        return 1;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));

            if (this.getTarget() == null)
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.008, 0.0));
        }
        else {
            if (!this.isInWater() && !this.isFalling()) {
                // Try to find water nearby
                BlockPos waterPos = findNearestWater(this.blockPosition(), 64);

//                System.out.println("Init: "+waterPos);
                if (waterPos != null && (this.level().getBlockState(waterPos.above()).isAir() || this.level().getFluidState(waterPos.above()).is(Fluids.WATER))) {

                    waterPos=waterPos.east(random.nextInt(0,20));
                    waterPos=waterPos.west(random.nextInt(0,20));
                    waterPos=waterPos.south(random.nextInt(0,20));
                    waterPos=waterPos.north(random.nextInt(0,20));

//                    System.out.println("Final: "+waterPos);

                    Vec3 direction = Vec3.atCenterOf(waterPos).subtract(this.position()).normalize();

                    Vec3 jumpVec = new Vec3(direction.x * 0.8, 0.8, direction.z * 0.8);
                    this.setDeltaMovement(jumpVec);
                } else {

                    waterPos= new BlockPos(this.blockPosition().getX()+(random.nextIntBetweenInclusive(1,20)), this.blockPosition().getY(), this.blockPosition().getZ()+(random.nextIntBetweenInclusive(1,20)));

                    Vec3 direction = Vec3.atCenterOf(waterPos).subtract(this.position()).normalize();

                    // No water found, jump randomly
                    this.setDeltaMovement(new Vec3(direction.x*0.8, 0.8, direction.z*0.8));
                }

                jumpFromGround(); // required to animate the jump
                this.setIsFalling(true);
            }

            super.travel(travelVector);
        }
    }

    @Nullable
    private BlockPos findNearestWater(BlockPos origin, int radius) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    mutable.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
                    if (this.level().getBlockState(mutable).getFluidState().is(Fluids.WATER)) {
                        return mutable.immutable();
                    }
                }
            }
        }

        return null;
    }


    @Override
    protected float getJumpPower() {
        return 1.5f * this.getBlockJumpFactor() + this.getJumpBoostPower();
    }

    @Override
    public void resetFallDistance() {
        if(isFalling()){
            setIsFalling(false);
            this.triggerAnim("LandingController","landing");
            this.playSound(SoundEvents.HOSTILE_BIG_FALL, 1.0f, 1.0f);
        }
        super.resetFallDistance();
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return super.calculateFallDamage(fallDistance, damageMultiplier) - 20;
    }

    //Sounds
    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        if (this.level() instanceof ServerLevel serverLevel)
            SoundUtils.playSound(serverLevel, this.position(), MTSSounds.KRAKEN_ATTACK.get(), SoundSource.HOSTILE, 3F, 6D);

        return super.doHurtTarget(target);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MTSSounds.KRAKEN_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return MTSSounds.KRAKEN_DAMAGE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MTSSounds.KRAKEN_DEATH.get();
    }


    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }


    //Hitboxes
    @Override
    public EntityBounds getBounds() {
        return hitboxManager.getHitbox();
    }

    @Override
    public void onSetPos(double x, double y, double z) {
        if (hitboxManager != null) hitboxManager.updatePosition();
    }

    @Override
    public void setNextDamagedPart(@Nullable String part) {}

    @Override
    public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) {
        return hitboxManager.getHitbox().getBox(bounds);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }


    //Misc
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    @Override
    protected void tickDeath() {
        if(this.isVehicle()){
            Entity prey = this.getPassengers().get(0);

            prey.stopRiding();
        }

        CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(super::tickDeath, 80));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putFloat("HealthWhenStarted", this.getHealthWhenStartRiding());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        this.setHealthWhenStartRiding(compound.getFloat("HealthWhenStarted"));
    }

    @Override
    protected void handleAirSupply(int airSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(airSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(this.getMaxAirSupply());
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isNoAi())
            this.setAirSupply(this.getMaxAirSupply());

        //Make it jump out of the water like a fish
        if (this.onGround() && !this.isInWater() && !this.isDeadOrDying()) {
            this.setDeltaMovement(
                    this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F, 0.8, (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)
            );
            this.setYRot(this.random.nextFloat() * 360.0F);
            this.setOnGround(false);
            this.hasImpulse = true;
        }
    }

    @Override
    public int getMaxAirSupply() {
        return 4800;
    }


    @Override
    public @Nullable ItemStack getPickResult() {
        return new ItemStack(MTSItems.KRAKEN_SPAWN_EGG.get());
    }
}
