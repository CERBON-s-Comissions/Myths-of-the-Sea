package com.cerbon.myths_of_the_sea.entity.custom.bunyip;

import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class BunyipBodyRotationControl extends BodyRotationControl {
    private final BunyipEntity mob;

    public BunyipBodyRotationControl(BunyipEntity mob) {
        super(mob);
        this.mob=mob;
    }

    @Override
    public void clientTick() {
        if (this.mob.getTarget() != null) {
            this.mob.yBodyRot = this.mob.yHeadRot;
            this.mob.setYRot(this.mob.yHeadRot);
        }

        else super.clientTick();
    }
}
