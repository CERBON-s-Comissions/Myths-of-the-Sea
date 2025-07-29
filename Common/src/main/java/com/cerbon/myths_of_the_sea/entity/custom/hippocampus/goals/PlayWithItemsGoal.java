package com.cerbon.myths_of_the_sea.entity.custom.hippocampus.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PlayWithItemsGoal extends Goal {
		private int cooldown;
		
		private final Mob mob;
		
		public PlayWithItemsGoal(Mob mob){
			this.mob=mob;
		}

		@Override
		public boolean canUse() {
			if (this.cooldown > this.mob.tickCount) {
				return false;
			} else {
				List<ItemEntity> list = this.mob.level()
					.getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0), Dolphin.ALLOWED_ITEMS);
				return !list.isEmpty() || !this.mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
			}
		}

		@Override
		public void start() {
			List<ItemEntity> list = this.mob.level()
				.getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0), Dolphin.ALLOWED_ITEMS);
			if (!list.isEmpty()) {
				this.mob.getNavigation().moveTo(list.get(0), 1.2F);
//				this.mob.playSound(SoundEvents.DOLPHIN_PLAY, 1.0F, 1.0F);
			}

			this.cooldown = 0;
		}

		@Override
		public void stop() {
			ItemStack itemStack = this.mob.getItemBySlot(EquipmentSlot.MAINHAND);
			if (!itemStack.isEmpty()) {
				this.drop(itemStack);
				this.mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
				this.cooldown = this.mob.tickCount + this.mob.getRandom().nextInt(100);
			}
		}

		@Override
		public void tick() {
			List<ItemEntity> list = this.mob.level()
				.getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(8.0, 8.0, 8.0), Dolphin.ALLOWED_ITEMS);
			ItemStack itemStack = this.mob.getItemBySlot(EquipmentSlot.MAINHAND);
			if (!itemStack.isEmpty()) {
				this.drop(itemStack);
				this.mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			} else if (!list.isEmpty()) {
				this.mob.getNavigation().moveTo(list.get(0), 1.2F);
			}
		}

		private void drop(ItemStack stack) {
			if (!stack.isEmpty()) {
				double d = this.mob.getEyeY() - 0.3F;
				ItemEntity itemEntity = new ItemEntity(this.mob.level(), this.mob.getX(), d, this.mob.getZ(), stack);
				itemEntity.setPickUpDelay(40);
				itemEntity.setThrower(this.mob.getUUID());
				float f = 0.3F;
				float g = this.mob.getRandom().nextFloat() * (float) (Math.PI * 2);
				float h = 0.02F * this.mob.getRandom().nextFloat();
				itemEntity.setDeltaMovement(
                        0.3F * -Mth.sin(this.mob.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.mob.getXRot() * (float) (Math.PI / 180.0)) + Mth.cos(g) * h,
                        0.3F * Mth.sin(this.mob.getXRot() * (float) (Math.PI / 180.0)) * 1.5F,
                        0.3F * Mth.cos(this.mob.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.mob.getXRot() * (float) (Math.PI / 180.0)) + Mth.sin(g) * h
				);
				this.mob.level().addFreshEntity(itemEntity);
			}
		}
	}