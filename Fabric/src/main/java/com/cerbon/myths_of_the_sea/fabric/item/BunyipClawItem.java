package com.cerbon.myths_of_the_sea.fabric.item;

import com.cerbon.myths_of_the_sea.item.custom.bunyip_claw.BaseBunyipClawItem;
import com.cerbon.myths_of_the_sea.item.custom.bunyip_claw.BunyipClawRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BunyipClawItem extends BaseBunyipClawItem {
    private Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public BunyipClawItem(float attackDamage, float attackSpeed, Properties properties) {
        super(attackDamage, attackSpeed, properties);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private BunyipClawRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null)
                    renderer = new BunyipClawRenderer();

                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }
}
