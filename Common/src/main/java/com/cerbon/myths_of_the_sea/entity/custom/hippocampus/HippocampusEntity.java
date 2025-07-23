package com.cerbon.myths_of_the_sea.entity.custom.hippocampus;

import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.myths_of_the_sea.entity.MTSEntities;
import com.cerbon.myths_of_the_sea.entity.custom.hippocampus.goals.PlayWithItemsGoal;
import com.cerbon.myths_of_the_sea.util.GeoControllersUtil;
import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HippocampusEntity extends AbstractWaterHorse implements GeoEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    public HippocampusEntity(EntityType<? extends AbstractWaterHorse> entityType, Level level) {
        super(entityType, level);

        this.setCanPickUpLoot(true);
        this.setMaxUpStep(0.6F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));

        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0, HippocampusEntity.class));

        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(Items.SALMON), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(Items.TROPICAL_FISH), false));

        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKED_COD), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(Items.COOKED_SALMON), false));

        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));


        this.goalSelector.addGoal(8, new PlayWithItemsGoal(this));
        this.goalSelector.addGoal(8, new FollowBoatGoal(this));

        this.goalSelector.addGoal(9, new AvoidEntityGoal<>(this, Guardian.class, 8.0F, 1.0, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Guardian.class).setAlertOthers());
    }

    @Override
    public boolean isEffectiveAi() {
        return super.isEffectiveAi() && !this.isDeadOrDying();
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 45.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D)
                .add(Attributes.MOVEMENT_SPEED, 1F)
                .add(Attributes.FOLLOW_RANGE, 48F)
                .add(Attributes.JUMP_STRENGTH, 0.0)
                .build();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Movement", 10, (state) -> GeoControllersUtil.commonMoveController(state, this)));

        controllers.add(new AnimationController<>(this, "Die", 0, state -> GeoControllersUtil.commonWaterAnimalDie(state, this)));
    }

    @Override
    protected void tickDeath() {
        CapabilityUtils.getLevelEventScheduler(this.level()).addEvent(new TimedEvent(super::tickDeath, 40));
    }

    //Sounds
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MTSSounds.HIPPOCAMPUS_IDLE.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
    }

    @Override
    protected @NotNull SoundEvent getSwimSound() {
        return MTSSounds.HIPPOCAMPUS_MOVEMENT.get();
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
        return MTSSounds.HIPPOCAMPUS_DAMAGE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MTSSounds.HIPPOCAMPUS_DEATH.get();
    }

    public SoundEvent getFlopSound(){
        return MTSSounds.HIPPOCAMPUS_FLOP.get();
    }


    @Nullable
    @Override
    protected SoundEvent getEatingSound() {
        return MTSSounds.HIPPOCAMPUS_EAT.get();
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater() && !this.hasControllingPassenger()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else if (this.isInWater() && this.hasControllingPassenger()){
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
            super.travel(travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    protected void doPlayerRide(@NotNull Player player) {
        this.setXRot(0);
        if (!this.level().isClientSide) {
            player.setYRot(this.getYRot());
            player.setXRot(this.getXRot());
            player.startRiding(this);
        }
    }


    @Override
    protected void tickRidden(@NotNull Player player, @NotNull Vec3 travelVector) {
        Vec2 vec2 = this.getRiddenRotation(player);
        this.setRot(vec2.y, vec2.x);
        this.yBodyRot = this.yHeadRot = this.getYRot();
        if (this.isControlledByLocalInstance()) {
            controlHippocampus(travelVector);
        }
    }

    protected void controlHippocampus(Vec3 travelVector) {
        if (this.getControllingPassenger() instanceof Player rider && travelVector.z > 0.0) {
            float pitch = rider.getXRot();
            float yaw = rider.getYRot();

            float fYaw = -Mth.sin(yaw * Mth.DEG_TO_RAD);
            float fPitchY = -Mth.sin(pitch * Mth.DEG_TO_RAD);
            float fYawCos = Mth.cos(yaw * Mth.DEG_TO_RAD);
            float fPitchCos = Mth.cos(pitch * Mth.DEG_TO_RAD);

            double speed = this.isInWater()? 0.8D: 0.001D;

            Vec3 direction = new Vec3(fYaw * fPitchCos, fPitchY, fYawCos * fPitchCos).normalize().scale(speed);

            // Maintain current Y motion if out of water
            if (!this.isInWater()) {
                // apply gravity so it comes down
                direction = new Vec3(direction.x, this.getDeltaMovement().y - 0.05, direction.z);
            }

            this.setDeltaMovement(direction);
        } else {
            // Keep previous motion if out of water
            if (!this.isInWater()) {
                Vec3 motion = this.getDeltaMovement();
                this.setDeltaMovement(motion.x, motion.y - 0.05, motion.z); // apply gravity
            } else {
                this.setDeltaMovement(Vec3.ZERO);
            }
        }
    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(@NotNull Player player, @NotNull Vec3 travelVector) {
        if(this.isInWater() || this.onGround()){
            float f = player.xxa * 0.5F;
            float g = player.zza;
            if (g <= 0.0F) {
                g *= 0.25F;
            }

            return new Vec3(f, 0.0, g);
        }
        else {
            return Vec3.ZERO;
        }
    }

    @Override
    protected float getRiddenSpeed(@NotNull Player player) {
        return this.isInWater()? (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED): 0.1f;
    }

    //Rider visual
    @Override
    public double getPassengersRidingOffset() {
        return (double)this.getDimensions(this.getPose()).height * 0.6;
    }

    @Override
    protected void positionRider(@NotNull Entity passenger, Entity.@NotNull MoveFunction callback) {
        super.positionRider(passenger, callback);

        float yawRad = this.yBodyRot * Mth.DEG_TO_RAD;
        float sinYaw = Mth.sin(yawRad);
        float cosYaw = Mth.cos(yawRad);

        float pitchRad = this.getXRot() * Mth.DEG_TO_RAD;

        float pitchOffset = (float) Mth.clamp(Mth.sin(pitchRad) * 1.2F, -0.9, 1.0);

        double verticalPitchOffset = Mth.clamp(Mth.sin(pitchRad) * 1.0, -0.6, -0.4);

        double offsetX = -sinYaw * pitchOffset;
        double offsetZ =  cosYaw * pitchOffset;

        callback.accept(
                passenger,
                this.getX() + (sinYaw * (verticalPitchOffset<= -0.5 ? 0.3: 0.4)) + offsetX,
                this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset() + verticalPitchOffset + (verticalPitchOffset<1 && verticalPitchOffset>-1? 0.15: 0),
                this.getZ() - (cosYaw * (verticalPitchOffset<= -0.5 ? 0.3: 0.4)) + offsetZ
        );

        if (passenger instanceof LivingEntity) {
            ((LivingEntity) passenger).yBodyRot = this.yBodyRot;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isNoAi())
            this.setAirSupply(this.getMaxAirSupply());

        //Make it jump out of the water like a fish
        if (this.onGround() && !this.isInWater() && !this.isDeadOrDying() && !this.isVehicle()) {
            this.setDeltaMovement(
                    this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F, 0.5, (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F)
            );
            this.setYRot(this.random.nextFloat() * 360.0F);
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
    }

    @Override
    public void openCustomInventoryScreen(@NotNull Player player) {
        if (!this.level().isClientSide) {
            player.openHorseInventory(this, this.inventory);
        }
    }

    @Override
    protected @NotNull Vec3 getLeashOffset() {
        return new Vec3(0.0, this.getEyeHeight()*0.6, 0.0);
    }

    @Override
    public int getMaxAirSupply() {
        return 400;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return dimensions.height * 0.75F;
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stackInHand = player.getItemInHand(hand);
        //Default
        if (this.isVehicle()) {
            return super.mobInteract(player, hand);
        }
        //To handle food logic
        else if(this.isFood(stackInHand) || this.isMatingFood(stackInHand)){
            return this.fedFood(player, stackInHand);
        }
        //To open the inventory if tamed
        else if (this.isTamed() && player.isSecondaryUseActive()) {
            this.openCustomInventoryScreen(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        //To ride the hippocampus
        else if (this.isTamed()){

            if (!stackInHand.isEmpty()) {
                InteractionResult interactionResult = stackInHand.interactLivingEntity(player, this, hand);
                if (interactionResult.consumesAction()) {
                    return interactionResult;
                }
            }

            this.doPlayerRide(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return InteractionResult.PASS;
    }

    //Mating
    @Nullable
    public HippocampusEntity getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        return MTSEntities.HIPPOCAMPUS.get().create(level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(MTSItems.HIPPOCAMPUS_FOOD);
    }

    private boolean isMatingFood(ItemStack stack) {
        return stack.is(MTSItems.HIPPOCAMPUS_FOOD_MATING);
    }

    @Override
    protected boolean handleEating(@NotNull Player player, @NotNull ItemStack stack) {
        if (this.isFood(stack) || this.isMatingFood(stack)) {
            //To try to tame it (Only when adult)
            if (!this.isTamed() && !this.isMatingFood(stack) && !this.isBaby()) {
                //20% of probability
                if (this.random.nextInt(5) == 0) {
                    tameWithName(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }

                return playEatingSound();
            }

            //Only when tamed
            if (this.isTamed()) {
                //To heal the hippocampus
                if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(stack.getItem().getFoodProperties() != null ? stack.getItem().getFoodProperties().getNutrition() : 1);

                    return playEatingSound();
                }

                //To try to mate
                if (this.getAge() == 0 && this.canFallInLove() && this.isMatingFood(stack)) {
                    this.setInLove(player);

                    return playEatingSound();
                }
            }

            //To age up if baby
            if (this.isBaby()) {
                this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
                if (!this.level().isClientSide) {
                    this.ageUp(this.isFood(stack)? 10: 20);
                }

                return playEatingSound();
            }

        }

        return false;
    }

    protected boolean playEatingSound(){
        //Play the sound
        if (!this.isSilent()) {
            SoundEvent soundEvent = this.getEatingSound();
            if (soundEvent != null) {
                this.level()
                        .playSound(
                                null, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
                        );
            }
        }

        return true;
    }

    @Override
    public boolean canMate(@NotNull Animal otherAnimal) {
        return otherAnimal instanceof HippocampusEntity camel && this.canParent() && camel.canParent();
    }

    @Override
    protected boolean canPerformRearing() {
        return false;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        int i = this.calculateFallDamage(fallDistance, multiplier);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(source, (float)i);
            if (this.isVehicle()) {
                for (Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(source, (float)i);
                }
            }

            this.playBlockFallSound();
            return true;
        }
    }

    @Override
    public boolean canEatGrass() {
        return false;
    }

    //Dolphin play
    @Override
    public boolean canTakeItem(@NotNull ItemStack stack) {
        EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(stack);
        return this.getItemBySlot(equipmentSlot).isEmpty() && equipmentSlot == EquipmentSlot.MAINHAND && super.canTakeItem(stack);
    }

    @Override
    protected void pickUpItem(@NotNull ItemEntity itemEntity) {
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            ItemStack itemStack = itemEntity.getItem();
            if (this.canHoldItem(itemStack)) {
                this.onItemPickup(itemEntity);
                this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
                this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
                this.take(itemEntity, itemStack.getCount());
                itemEntity.discard();
            }
        }
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return new ItemStack(MTSItems.HIPPOCAMPUS_SPAWN_EGG.get());
    }
}
