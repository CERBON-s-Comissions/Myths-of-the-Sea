package com.cerbon.myths_of_the_sea.fabric.item;

import com.cerbon.myths_of_the_sea.item.custom.kraken_tentacle.BaseKrakenTentacleItem;
import com.cerbon.myths_of_the_sea.item.custom.kraken_tentacle.KrakenTentacleRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class KrakenTentacleItem extends BaseKrakenTentacleItem {
    private Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public KrakenTentacleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private KrakenTentacleRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null)
                    renderer = new KrakenTentacleRenderer();

                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }
}
