package com.cerbon.myths_of_the_sea.entity.custom.abaia;

import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityBounds;
import com.cerbon.cerbons_api.api.multipart_entities.entity.EntityPart;
import com.cerbon.cerbons_api.api.multipart_entities.entity.MutableBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AbaiaHitboxes {
    private final AbaiaEntity entity;

    private final String rootBoxYaw = "rootYaw";
    private final String mainBox = "mainBox";

    private final AABB collisionHitbox = new AABB(
            new Vec3(-1.0, 0, -1.0),
            new Vec3(3.0, 2.0, 3.0)
    );

    private final EntityBounds hitboxes = EntityBounds.builder()
            .add(rootBoxYaw).setBounds(0.0, 0.0, 0.0).build()
            .add(mainBox).setBounds(1.0, 1.0, 7.5).setOffset(0.0, 0.6, 0.0).setParent(rootBoxYaw).build()
            .overrideCollisionBox(collisionHitbox)
            .getFactory().create();

    public AbaiaHitboxes(AbaiaEntity entity) {
        this.entity = entity;
    }

    public EntityBounds getHitbox() {
        return hitboxes;
    }

    public void updatePosition() {
        EntityPart rootYaw = hitboxes.getPart(rootBoxYaw);

        rootYaw.setRotation(0.0, -entity.getYRot(), 0.0, true);

        rootYaw.setX(entity.getX());
        rootYaw.setY(entity.getY());
        rootYaw.setZ(entity.getZ());

        EntityPart main = hitboxes.getPart(mainBox);
        main.setRotation(entity.getXRot(), 0.0, 0.0, true);

        MutableBox overrideBox = hitboxes.getOverrideBox();
        if (overrideBox != null)
            overrideBox.setBox(collisionHitbox.move(entity.position()).move(-1.0, 0.0, -1.0));
    }
}
