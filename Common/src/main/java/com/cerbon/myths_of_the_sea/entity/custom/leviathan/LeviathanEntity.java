package com.cerbon.myths_of_the_sea.entity.custom.leviathan;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.cerbons_api.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.goal.LeviathanFleeFromTarget;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.goal.LeviathanMeleeAttackGoal;
import com.cerbon.myths_of_the_sea.entity.custom.leviathan.goal.LeviathanNearestAttackGoal;
import com.cerbon.myths_of_the_sea.entity.custom.util.BreachingWaterBoundPathNavigation;
import com.cerbon.myths_of_the_sea.util.GeoControllersUtil;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class LeviathanEntity extends WaterAnimal implements GeoEntity, MultipartAwareEntity {

    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    private final LeviathanHitboxes hitboxManager = new LeviathanHitboxes(this);

    private static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE);
    private static final int ATTACK_ANIM_TIME = 21;

    public LeviathanEntity(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.xpReward=50;

        this.moveControl = new LeviathanMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.noCulling=true;
        this.setClientSideTailAnimation(this.random.nextFloat());
        this.setClientSideTailAnimationO(this.getClientSideTailAnimation());
    }

    @Override
    public int getExperienceReward() {
        return this.xpReward;
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.5F)
                .add(Attributes.FOLLOW_RANGE, 96F)
                .build();
    }

    @SuppressWarnings({"unused", "deprecation"})
    public static boolean surfaceWaterSpawnRulesAndNotNearLeviathan(
            EntityType<? extends WaterAnimal> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
    ) {

        int radiusToSearchLeviathan=120;

        int i = level.getSeaLevel();
        int j = i - 13;
        return
                pos.getY() >= j
                        && pos.getY() <= i
                        && level.getFluidState(pos.below()).is(FluidTags.WATER)
                        && level.getBlockState(pos.above()).is(Blocks.WATER)
                        //This make not spawn two leviathan too nearby
                        && level.getEntitiesOfClass(
                                LeviathanEntity.class,
                                new AABB(pos.getX()-radiusToSearchLeviathan, pos.getY()-radiusToSearchLeviathan, pos.getZ()-radiusToSearchLeviathan,
                                         pos.getX()+radiusToSearchLeviathan, pos.getY()+radiusToSearchLeviathan, pos.getZ()+radiusToSearchLeviathan),
                        leviathan -> true).isEmpty();
    }

    //Goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(0, new LeviathanFleeFromTarget(this, 2.0f, 1));
        this.goalSelector.addGoal(1, new LeviathanMeleeAttackGoal(this, 2.0F, true, 100));

        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0, 200));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());

        this.targetSelector.addGoal(0, new LeviathanNearestAttackGoal<>(this, Player.class, 10, false, false,
                entity -> getLastPrey()!=null && entity.is(getLastPrey()) && entity.isInWater()));
        this.targetSelector.addGoal(1, new LeviathanNearestAttackGoal<>(this, Player.class, 10, false, false, Entity::isInWater));
        this.targetSelector.addGoal(2, new LeviathanNearestAttackGoal<>(this, Villager.class, 10, false, false, Entity::isInWater));
    }

    @Nullable
    private UUID lastPreyUUID;
    @Nullable
    private Entity lastPrey;

    public void setLastPreyUUID(@Nullable UUID lastPreyUUID) {
        this.lastPreyUUID = lastPreyUUID;
    }

    public @Nullable UUID getLastPreyUUID() {
        return lastPreyUUID;
    }

    public @Nullable Entity getLastPrey() {
        return lastPrey;
    }

    public void setLastPrey(@Nullable Entity lastPrey) {
        this.lastPrey = lastPrey;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        if (this.getLastPreyUUID() != null) {
            compound.putUUID("LastPrey", this.getLastPreyUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (this.level() instanceof ServerLevel) {
            if (!compound.hasUUID("LastPrey")) {
                this.setLastPreyUUID(null);
            } else {
                UUID uUID = compound.getUUID("LastPrey");
                this.setLastPreyUUID(uUID);
                Entity entity = ((ServerLevel)this.level()).getEntity(uUID);
                if (entity != null) {
                    this.setLastPrey(entity);
                }
            }
        }
    }

    public boolean hasAttackedOnWater;
    public int cooldownSwimmingAttackTicks;

    public boolean isSeeingTarget(LivingEntity target) {
        Vec3 vec3d = target.position();

        Vec3 vec3d2 = this.calculateViewVector(0.0f, this.getYHeadRot());
        Vec3 vec3d3 = vec3d.vectorTo(this.position());
        vec3d3 = new Vec3(vec3d3.x, 0.0, vec3d3.z).normalize();

        return vec3d3.dot(vec3d2) < -0.9;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity target) {
        return super.canAttack(target) && target.isInWater() && !this.hasAttackedOnWater;
    }

    //Gecko Animations
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Attack", 5, (state -> {
            if (this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
                state.getController().forceAnimationReset();
                state.getController().setAnimation(ATTACK_ANIM);

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
                return state.setAndContinue(GeoControllersUtil.IDLE_ANIM);

            return PlayState.STOP;
        }));

        controllers.add(new AnimationController<>(this, "Die", 0, state -> GeoControllersUtil.commonWaterAnimalDie(state, this)));
    }

    //Navigation
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
        else super.travel(travelVector);
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
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return super.calculateFallDamage(fallDistance, damageMultiplier) - 10;
    }

    //Hitbox
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

    //Sounds
    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        if (this.level() instanceof ServerLevel serverLevel)
            SoundUtils.playSound(serverLevel, this.position(), MTSSounds.LEVIATHAN_ATTACK.get(), SoundSource.HOSTILE, 3F, 6D);

        return super.doHurtTarget(target);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MTSSounds.LEVIATHAN_IDLE.get();
    }

    @Override
    protected @NotNull SoundEvent getSwimSound() {
        return MTSSounds.LEVIATHAN_MOVEMENT.get();
    }

    @Override
    protected void playSwimSound(float volume) {
        float f = (float) this.getDeltaMovement().horizontalDistance();

        if (f >= 0.01F)
            super.playSwimSound(Mth.lerp(Mth.clamp(f, 0.0F, 0.5F), 0.0F, 1.2F));
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return MTSSounds.LEVIATHAN_DAMAGE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MTSSounds.LEVIATHAN_DEATH.get();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    //Rotation things
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new LeviathanRotationControl(this);
    }

    public static final EntityDataAccessor<Vector3f> BODY_ROT_2 = SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> BODY_ROT_3 = SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> BODY_ROT_4 = SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> BODY_ROT_5 = SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> BODY_ROT_6 = SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> BODY_ROT_7 = SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> BODY_ROT_8 = SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.VECTOR3);

    public static final EntityDataAccessor<Float> CLIENT_SIDE_TAIL_ANIMATION =       SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> CLIENT_SIDE_TAIL_ANIMATION_O =     SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> CLIENT_SIDE_TAIL_ANIMATION_SPEED = SynchedEntityData.defineId(LeviathanEntity.class, EntityDataSerializers.FLOAT);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(BODY_ROT_2, new Vector3f());
        this.getEntityData().define(BODY_ROT_3, new Vector3f());
        this.getEntityData().define(BODY_ROT_4, new Vector3f());
        this.getEntityData().define(BODY_ROT_5, new Vector3f());
        this.getEntityData().define(BODY_ROT_6, new Vector3f());
        this.getEntityData().define(BODY_ROT_7, new Vector3f());
        this.getEntityData().define(BODY_ROT_8, new Vector3f());

        this.getEntityData().define(CLIENT_SIDE_TAIL_ANIMATION, 0f);
        this.getEntityData().define(CLIENT_SIDE_TAIL_ANIMATION_O, 0f);
        this.getEntityData().define(CLIENT_SIDE_TAIL_ANIMATION_SPEED, 0f);
    }

    public float getYRot2() {
        return this.entityData.get(BODY_ROT_2).y;
    }

    public float getYRot3() {
        return this.entityData.get(BODY_ROT_3).y;
    }

    public float getYRot4() {
        return this.entityData.get(BODY_ROT_4).y;
    }

    public float getYRot5() {
        return this.entityData.get(BODY_ROT_5).y;
    }

    public float getYRot6() {
        return this.entityData.get(BODY_ROT_6).y;
    }

    public float getYRot7() {
        return this.entityData.get(BODY_ROT_7).y;
    }

    public float getYRot8() {
        return this.entityData.get(BODY_ROT_8).y;
    }

    public void setYRot2(float YRot2) {
        Vector3f actualVector = this.entityData.get(BODY_ROT_2);
        this.entityData.set(BODY_ROT_2, new Vector3f(actualVector.x, YRot2, actualVector.z));
    }

    public void setYRot3(float YRot3) {
        Vector3f actualVector = this.entityData.get(BODY_ROT_3);
        this.entityData.set(BODY_ROT_3, new Vector3f(actualVector.x, YRot3, actualVector.z));
    }

    public void setYRot4(float YRot4) {
        Vector3f actualVector = this.entityData.get(BODY_ROT_4);
        this.entityData.set(BODY_ROT_4, new Vector3f(actualVector.x, YRot4, actualVector.z));
    }

    public void setYRot5(float YRot5) {
        Vector3f actualVector = this.entityData.get(BODY_ROT_5);
        this.entityData.set(BODY_ROT_5, new Vector3f(actualVector.x, YRot5, actualVector.z));
    }

    public void setYRot6(float YRot6) {
        Vector3f actualVector = this.entityData.get(BODY_ROT_6);
        this.entityData.set(BODY_ROT_6, new Vector3f(actualVector.x, YRot6, actualVector.z));
    }

    public void setYRot7(float YRot7) {
        Vector3f actualVector = this.entityData.get(BODY_ROT_7);
        this.entityData.set(BODY_ROT_7, new Vector3f(actualVector.x, YRot7, actualVector.z));
    }

    public void setYRot8(float YRot8) {
        Vector3f actualVector = this.entityData.get(BODY_ROT_8);
        this.entityData.set(BODY_ROT_8, new Vector3f(actualVector.x, YRot8, actualVector.z));
    }

    public float getClientSideTailAnimation() {
        return this.entityData.get(CLIENT_SIDE_TAIL_ANIMATION);
    }

    public float getClientSideTailAnimationO() {
        return this.entityData.get(CLIENT_SIDE_TAIL_ANIMATION_O);
    }

    public float getClientSideTailAnimationSpeed() {
        return this.entityData.get(CLIENT_SIDE_TAIL_ANIMATION_SPEED);
    }

    public void setClientSideTailAnimation(float clientSideTailAnimation) {
        this.entityData.set(CLIENT_SIDE_TAIL_ANIMATION, clientSideTailAnimation);
    }

    public void setClientSideTailAnimationO(float clientSideTailAnimationO) {
        this.entityData.set(CLIENT_SIDE_TAIL_ANIMATION_O, clientSideTailAnimationO);
    }

    public void setClientSideTailAnimationSpeed(float clientSideTailAnimationSpeed) {
        this.entityData.set(CLIENT_SIDE_TAIL_ANIMATION_SPEED, clientSideTailAnimationSpeed);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(this.level().isClientSide){
            return;
        }

        this.setClientSideTailAnimationO(this.getClientSideTailAnimation());

        if (this.getDeltaMovement().length() != 0) {
            float updatedSpeed = this.getClientSideTailAnimationSpeed() + (0.050F - this.getClientSideTailAnimationSpeed()) * 0.1F;
            this.setClientSideTailAnimationSpeed(updatedSpeed);
        } else {
            float updatedSpeed = this.getClientSideTailAnimationSpeed() + (0.025F - this.getClientSideTailAnimationSpeed()) * 0.2F;
            this.setClientSideTailAnimationSpeed(updatedSpeed);
        }

        this.setClientSideTailAnimation(this.getClientSideTailAnimation() + this.getClientSideTailAnimationSpeed());
    }

    public float getTailAnimation(float partialTick) {
        return Mth.lerp(partialTick, this.getClientSideTailAnimationO(), this.getClientSideTailAnimation());
    }

    //Misc
    @Override
    protected void tickDeath() {
        CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(super::tickDeath, 80));
    }

    @Override
    public int getMaxAirSupply() {
        return 6000;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return 0.85F;
    }

    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return new ItemStack(MTSItems.LEVIATHAN_SPAWN_EGG.get());
    }
}
