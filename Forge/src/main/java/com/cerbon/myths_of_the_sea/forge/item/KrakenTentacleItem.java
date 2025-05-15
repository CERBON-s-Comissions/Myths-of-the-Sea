package com.cerbon.myths_of_the_sea.forge.item;

import com.cerbon.myths_of_the_sea.item.MTSItems;
import com.cerbon.myths_of_the_sea.item.custom.kraken_tentacle.BaseKrakenTentacleItem;
import com.cerbon.myths_of_the_sea.item.custom.kraken_tentacle.KrakenTentacleRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

import java.util.function.Consumer;

public class KrakenTentacleItem extends BaseKrakenTentacleItem {

    public KrakenTentacleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private KrakenTentacleRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null)
                    renderer = new KrakenTentacleRenderer();

                return renderer;
            }
        });
    }

    public static void registerCuriosAttributes(CurioAttributeModifierEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (!itemStack.is(MTSItems.KRAKEN_TENTACLE.get())) return;

        KrakenTentacleItem krakenTentacleItem = (KrakenTentacleItem) itemStack.getItem();
        krakenTentacleItem.getAttributes().forEach(event::addModifier);
    }
}
