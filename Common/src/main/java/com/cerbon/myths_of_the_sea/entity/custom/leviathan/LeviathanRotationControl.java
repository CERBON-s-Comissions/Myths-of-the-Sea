package com.cerbon.myths_of_the_sea.entity.custom.leviathan;

import com.cerbon.myths_of_the_sea.util.MTSUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class LeviathanRotationControl extends BodyRotationControl {
    private final LeviathanEntity leviathan;

    public LeviathanRotationControl(LeviathanEntity leviathan) {
        super(leviathan);
        this.leviathan = leviathan;
    }

    @Override
    public void clientTick() {
        if (this.leviathan.getTarget() != null) {
            this.leviathan.yBodyRot = MTSUtils.smoothAngle(this.leviathan.yBodyRot, this.leviathan.yHeadRot, 5);
            this.leviathan.setYRot(MTSUtils.smoothAngle(this.leviathan.getYRot(),this.leviathan.yHeadRot -18.0f, 5) );
        }
        else super.clientTick();

        if(this.leviathan.level().isClientSide){
            return;
        }

        float h = leviathan.getTailAnimation(leviathan.tickCount);
        float sinVariation = Mth.sin(h) * (float) Math.PI;

        float deltaYaw = Mth.wrapDegrees(this.leviathan.yRotO - this.leviathan.getYRot());
        int maxYawChange=1;

        // Mob is turning
        if (Math.abs(deltaYaw) >= 0.5f) {
            if (deltaYaw < 0) {
                //System.out.println("RIGHT: "+Math.min(-12*deltaYaw, 25));
                leviathan.setYRot2(MTSUtils.smoothAngle(leviathan.getYRot2(), Math.min(-12 * deltaYaw + sinVariation * 0.5f, 25), maxYawChange));
                leviathan.setYRot3(MTSUtils.smoothAngle(leviathan.getYRot3(), Math.min(-12 * deltaYaw + sinVariation * 1.0f, 25), maxYawChange));
                leviathan.setYRot4(MTSUtils.smoothAngle(leviathan.getYRot4(), Math.min(-12 * deltaYaw + sinVariation * 2.0f, 25), maxYawChange));
                leviathan.setYRot5(MTSUtils.smoothAngle(leviathan.getYRot5(), Math.min(-12 * deltaYaw + sinVariation * 3.0f, 25), maxYawChange));
                leviathan.setYRot6(MTSUtils.smoothAngle(leviathan.getYRot6(), Math.min(-12 * deltaYaw + sinVariation * 8.0f, 25), maxYawChange));
                leviathan.setYRot7(MTSUtils.smoothAngle(leviathan.getYRot7(), Math.min(-12 * deltaYaw + sinVariation * 9.0f, 25), maxYawChange));
                leviathan.setYRot8(MTSUtils.smoothAngle(leviathan.getYRot8(), Math.min(-12 * deltaYaw + sinVariation * 10.0f, 25), maxYawChange));
            } else if (deltaYaw > 0) {
                //System.out.println("LEFT: "+Math.max(-12f*deltaYaw, -25));
                leviathan.setYRot2(MTSUtils.smoothAngle(leviathan.getYRot2(), Math.max(-12 * deltaYaw - sinVariation * 0.5f, -25), maxYawChange));
                leviathan.setYRot3(MTSUtils.smoothAngle(leviathan.getYRot3(), Math.max(-12 * deltaYaw - sinVariation * 1.0f, -25), maxYawChange));
                leviathan.setYRot4(MTSUtils.smoothAngle(leviathan.getYRot4(), Math.max(-12 * deltaYaw - sinVariation * 2.0f, -25), maxYawChange));
                leviathan.setYRot5(MTSUtils.smoothAngle(leviathan.getYRot5(), Math.max(-12 * deltaYaw - sinVariation * 3.0f, -25), maxYawChange));
                leviathan.setYRot6(MTSUtils.smoothAngle(leviathan.getYRot6(), Math.max(-12 * deltaYaw - sinVariation * 8.0f, -25), maxYawChange));
                leviathan.setYRot7(MTSUtils.smoothAngle(leviathan.getYRot7(), Math.max(-12 * deltaYaw - sinVariation * 9.0f, -25), maxYawChange));
                leviathan.setYRot8(MTSUtils.smoothAngle(leviathan.getYRot8(), Math.max(-12 * deltaYaw - sinVariation * 10.0f, -25), maxYawChange));
            }
        } else {
            // Not turning, just sinuous idle motion
            leviathan.setYRot2(MTSUtils.smoothAngle(leviathan.getYRot2(), sinVariation * 0.5f, maxYawChange));
            leviathan.setYRot3(MTSUtils.smoothAngle(leviathan.getYRot3(), sinVariation * 1.0f, maxYawChange));
            leviathan.setYRot4(MTSUtils.smoothAngle(leviathan.getYRot4(), sinVariation * 2.0f, maxYawChange));
            leviathan.setYRot5(MTSUtils.smoothAngle(leviathan.getYRot5(), sinVariation * 3.0f, maxYawChange));
            leviathan.setYRot6(MTSUtils.smoothAngle(leviathan.getYRot6(), sinVariation * 8.0f, maxYawChange));
            leviathan.setYRot7(MTSUtils.smoothAngle(leviathan.getYRot7(), sinVariation * 9.0f, maxYawChange));
            leviathan.setYRot8(MTSUtils.smoothAngle(leviathan.getYRot8(), sinVariation * 10.0f, maxYawChange));
        }
    }

}
