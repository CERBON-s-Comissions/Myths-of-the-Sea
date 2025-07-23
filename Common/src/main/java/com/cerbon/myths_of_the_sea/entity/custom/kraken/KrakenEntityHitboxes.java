package com.cerbon.myths_of_the_sea.entity.custom.kraken;

import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityPart;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MutableBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class KrakenEntityHitboxes {
    private final KrakenEntity kraken;

    private final String rootBoxYaw = "rootYaw";
    private final String mainBody = "mainBody";
    private final String tentacleRightA = "tentacleRightA";
    private final String tentacleLeftA  = "tentacleLeftA";
    private final String tentacleRightB = "tentacleRightB";
    private final String tentacleLeftB  = "tentacleLeftB";
    private final String tentacleRightC = "tentacleRightC";
    private final String tentacleLeftC  = "tentacleLeftC";
    private final String tentacleRightD = "tentacleRightD";
    private final String tentacleLeftD  = "tentacleLeftD";


    public KrakenEntityHitboxes(KrakenEntity kraken) {
        this.kraken = kraken;
    }

    private final AABB mainCollisionHitbox = new AABB(
            new Vec3(-3.0, 0.0, -3.0),
            new Vec3( 5.0,  6.0,  5.0)
    );

    //Calculate size: GeckoLib size/16
    private final EntityBounds hitboxes = EntityBounds.builder()
            .add(rootBoxYaw).setBounds(0.0, 0.0, 0.0).build()
            .add(mainBody).setBounds(8.0, 4.5, 8.0).setOffset(0.0, 2.25, 0.0).setParent(rootBoxYaw).build()

            //Calculate offset = Parent size/2 + new block size/2
//            .add(tentacleRightA).setBounds(10.625, 1.5, 1.5).setOffset(-1 -5.3125, -1.5, 1.25).setParent(mainBody).build()
//            .add(tentacleLeftA).setBounds(10.5, 1.5, 1.5).setOffset(  6.25, -1.5, 1.2).setParent(mainBody).build()
//
//            .add(tentacleRightB).setBounds(10.5, 1.5, 1.5).setOffset(-5.75, -1.5, 0.5).setParent(mainBody).build()
//            .add(tentacleLeftB).setBounds(10.5, 1.5, 1.5).setOffset(  5.75, -1.5, 0.5).setParent(mainBody).build()
//
//            .add(tentacleRightC).setBounds(10.5, 1.5, 1.5).setOffset(-5.5, -1.5, -0.2).setParent(mainBody).build()
//            .add(tentacleLeftC).setBounds(10.5, 1.5, 1.5).setOffset(  5.5, -1.5, -0.2).setParent(mainBody).build()
//
//            .add(tentacleRightD).setBounds(10.5, 1.5, 1.5).setOffset(-5.25, -1.5, -0.5).setParent(mainBody).build()
//            .add(tentacleLeftD).setBounds(10.5, 1.5, 1.5).setOffset(  5.25, -1.5, -0.5).setParent(mainBody).build()

            //Green part
            .overrideCollisionBox(mainCollisionHitbox)
            .getFactory().create();


    public EntityBounds getHitbox() {
        return hitboxes;
    }

    public void updatePosition() {
        EntityPart rootYaw = hitboxes.getPart(rootBoxYaw);
//        EntityPart tentacleRightA = hitboxes.getPart(this.tentacleRightA);
//        EntityPart tentacleLeftA  = hitboxes.getPart(this.tentacleLeftA);
//        EntityPart tentacleRightB = hitboxes.getPart(this.tentacleRightB);
//        EntityPart tentacleLeftB  = hitboxes.getPart(this.tentacleLeftB);
//        EntityPart tentacleRightC = hitboxes.getPart(this.tentacleRightC);
//        EntityPart tentacleLeftC  = hitboxes.getPart(this.tentacleLeftC);
//        EntityPart tentacleRightD = hitboxes.getPart(this.tentacleRightD);
//        EntityPart tentacleLeftD  = hitboxes.getPart(this.tentacleLeftD);
//
//      52.5

//        setPivotAndYaw(tentacleRightA,-5.3125, 0, 0,  90);
//        setPivotAndYaw(tentacleLeftA,  5, 0, 0.75, -52.5);
//
//        setPivotAndYaw(tentacleRightB,-5, 0, 0.75,  17.5);
//        setPivotAndYaw(tentacleLeftB,  5, 0, 0.75, -17.5);
//
//        setPivotAndYaw(tentacleRightC,-5, 0, 0.75, -20);
//        setPivotAndYaw(tentacleLeftC,  5, 0, 0.75,  20);
//
//        setPivotAndYaw(tentacleRightD,-5, 0, 0.75, -47.5);
//        setPivotAndYaw(tentacleLeftD,  5, 0, 0.75,  47.5);

        rootYaw.setRotation(0.0, -kraken.getYRot(), 0.0, true);

        rootYaw.setX(kraken.getX());
        rootYaw.setY(kraken.getY());
        rootYaw.setZ(kraken.getZ());

        EntityPart main = hitboxes.getPart(mainBody);
        main.setRotation(kraken.getXRot(), 0.0, 0.0, true);

        MutableBox overrideBox = hitboxes.getOverrideBox();
        if (overrideBox != null)
            overrideBox.setBox(mainCollisionHitbox.move(kraken.position()).move(-1.0, 0.0, -1.0));
    }

    protected static void setPivotAndYaw(EntityPart part, double pivotX, double pivotY, double pivotZ, double yaw){
        part.setPivotX(pivotX);
        part.setPivotY(pivotY);
        part.setPivotZ(pivotZ);
        part.setRotation(0, yaw, 0, true);
    }
}
