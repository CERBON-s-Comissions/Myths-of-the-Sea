package com.cerbon.myths_of_the_sea.entity.custom.abaia;

import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class AbaiaBodyRotationControl extends BodyRotationControl {
    private final AbaiaEntity mob;

    public AbaiaBodyRotationControl(AbaiaEntity mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void clientTick() {
        if (this.mob.isAngry()) {
            this.mob.yBodyRot = this.mob.yHeadRot;
            this.mob.setYRot(this.mob.yHeadRot - 18.0f);
        }
        else super.clientTick();
    }
}
