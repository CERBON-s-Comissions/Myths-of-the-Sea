package com.cerbon.myths_of_the_sea.entity.custom.bake_kujira;

import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class BakeKujiraBodyRotationControl extends BodyRotationControl {
    private final BakeKujiraEntity mob;

    public BakeKujiraBodyRotationControl(BakeKujiraEntity mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void clientTick() {
        if (this.mob.getTarget() != null) {
            this.mob.yBodyRot = this.mob.yHeadRot;
            this.mob.setYRot(this.mob.yHeadRot - 18.0f);
        }
        else super.clientTick();
    }
}
