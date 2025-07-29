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

            //Green part
            .overrideCollisionBox(mainCollisionHitbox)
            .getFactory().create();


    public EntityBounds getHitbox() {
        return hitboxes;
    }

    public void updatePosition() {
        EntityPart rootYaw = hitboxes.getPart(rootBoxYaw);

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
