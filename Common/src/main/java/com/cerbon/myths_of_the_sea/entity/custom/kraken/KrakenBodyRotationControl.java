package com.cerbon.myths_of_the_sea.entity.custom.kraken;

import com.cerbon.myths_of_the_sea.util.MTSUtils;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class KrakenBodyRotationControl extends BodyRotationControl {
    private final KrakenEntity mob;

    public KrakenBodyRotationControl(KrakenEntity mob) {
        super(mob);
        this.mob=mob;
    }

    @Override
    public void clientTick() {
        if (this.mob.getTarget() != null) {
            this.mob.yBodyRot = MTSUtils.smoothAngle(this.mob.yBodyRot, this.mob.yHeadRot, 5);
            this.mob.setYRot(MTSUtils.smoothAngle(this.mob.getYRot(),this.mob.yHeadRot -18.0f, 5) );
        }
        else super.clientTick();
    }
}
