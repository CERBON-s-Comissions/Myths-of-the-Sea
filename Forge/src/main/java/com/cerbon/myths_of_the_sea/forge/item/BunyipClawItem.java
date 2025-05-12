package com.cerbon.myths_of_the_sea.forge.item;

import com.cerbon.myths_of_the_sea.item.custom.bunyip_claw.BaseBunyipClawItem;
import com.cerbon.myths_of_the_sea.item.custom.bunyip_claw.BunyipClawRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class BunyipClawItem extends BaseBunyipClawItem {

    public BunyipClawItem(float attackDamage, float attackSpeed, Properties properties) {
        super(attackDamage, attackSpeed, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BunyipClawRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null)
                    renderer = new BunyipClawRenderer();

                return renderer;
            }
        });
    }
}
