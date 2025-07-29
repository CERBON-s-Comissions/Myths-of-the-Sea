package com.cerbon.myths_of_the_sea.entity.custom.leviathan;

import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityPart;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MutableBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LeviathanHitboxes {
    private final LeviathanEntity entity;

    private final String rootBoxYaw = "rootYaw";
    private final String head = "head";
    private final String body1 = "body1";
    private final String body2 = "body2";
    private final String body3 = "body3";
    private final String body4 = "body4";
    private final String body5 = "body5";
    private final String body6 = "body6";
    private final String body7 = "body7";
    private final String body8 = "body8";

    private final AABB collisionHitbox = new AABB(
            new Vec3(-10.0, 0.0, -10.0),
            new Vec3(12.0, 2.5, 12.0)
    );

    //Calculate size: GeckoLib size/16
    private final EntityBounds hitboxes = EntityBounds.builder()
            .add(rootBoxYaw).setBounds(0, 0.0, 0.0).build()

            .add(head).setBounds(2.5, 2.5, 3).setOffset(0.0, 1.25, 9.5).setParent(rootBoxYaw).build()


            .add(body1).setBounds(2.5, 2.5, 1.625).setOffset(0.0, 1.25, 7.575).setParent(rootBoxYaw).build()

            //Calculate offset = Parent size/2 + new block size/2
            .add(body2).setBounds(2.5, 2.5, 2.3125).setOffset(0.0, 0, -1.96875).setParent(body1).build()
            .add(body3).setBounds(2.5, 2.5, 2.3125).setOffset(0.0, 0, -2.3125).setParent(body2).build()
            .add(body4).setBounds(2.5, 2.5, 2.3125).setOffset(0.0, 0, -2.3125).setParent(body3).build()
            .add(body5).setBounds(2.5, 2.5, 2.3125).setOffset(0.0, 0, -2.3125).setParent(body4).build()
            .add(body6).setBounds(2.5, 2.5, 2.3125).setOffset(0.0, 0, -2.3125).setParent(body5).build()
            .add(body7).setBounds(2.5, 2.5, 2.3125).setOffset(0.0, 0, -2.3125).setParent(body6).build()
            .add(body8).setBounds(2.5, 2.5, 4.5).setOffset(0.0, 0, -3.40625).setParent(body7).build()


            .overrideCollisionBox(collisionHitbox)
            .getFactory().create();

    public LeviathanHitboxes(LeviathanEntity entity) {
        this.entity = entity;
    }

    public EntityBounds getHitbox() {
        return hitboxes;
    }

    public void updatePosition() {
        EntityPart rootYaw = hitboxes.getPart(this.rootBoxYaw);
//        EntityPart head = hitboxes.getPart(this.head);
//        EntityPart body1 = hitboxes.getPart(this.body1);
        EntityPart body2 = hitboxes.getPart(this.body2);
        EntityPart body3 = hitboxes.getPart(this.body3);
        EntityPart body4 = hitboxes.getPart(this.body4);
        EntityPart body5 = hitboxes.getPart(this.body5);
        EntityPart body6 = hitboxes.getPart(this.body6);
        EntityPart body7 = hitboxes.getPart(this.body7);
        EntityPart body8 = hitboxes.getPart(this.body8);


        rootYaw.setRotation(-entity.getXRot(), -entity.getYRot(), 0.0, true);
//        rootYaw.setRotation(-entity.getXRot(), 0.0, 0.0, true);

        rootYaw.setX(entity.getX());
        rootYaw.setY(entity.getY());
        rootYaw.setZ(entity.getZ());

        setPivotAndYawAndPitch(body2, 0.0, 0, -1.15625, entity.getYRot2(), 0);
        setPivotAndYawAndPitch(body3, 0.0, 0, -1.15625, entity.getYRot3(), 0);
        setPivotAndYawAndPitch(body4, 0.0, 0, -1.15625, entity.getYRot4(), 0);
        setPivotAndYawAndPitch(body5, 0.0, 0, -1.15625, entity.getYRot5(), 0);
        setPivotAndYawAndPitch(body6, 0.0, 0, -1.15625, entity.getYRot6(), 0);
        setPivotAndYawAndPitch(body7, 0.0, 0, -1.15625, entity.getYRot7(), 0);
        setPivotAndYawAndPitch(body8, 0.0, 0, -2.25000, entity.getYRot8(), 0);




        MutableBox overrideBox = hitboxes.getOverrideBox();
        if (overrideBox != null)
            overrideBox.setBox(collisionHitbox.move(entity.position()).move(-1.0, 0.0, -1.0));

    }

    protected static void setPivotAndYawAndPitch(EntityPart part, double pivotX, double pivotY, double pivotZ, double yaw, double pitch){
        part.setPivotX(pivotX);
        part.setPivotY(pivotY);
        part.setPivotZ(pivotZ);
        part.setRotation(pitch, yaw, 0, true);
    }
}
