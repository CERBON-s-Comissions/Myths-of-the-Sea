package com.cerbon.myths_of_the_sea.fabric.item;

import com.cerbon.myths_of_the_sea.item.custom.abaia_tail.AbaiaTailRenderer;
import com.cerbon.myths_of_the_sea.item.custom.abaia_tail.BaseAbaiaTailItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AbaiaTailItem extends BaseAbaiaTailItem {
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public AbaiaTailItem(Properties properties) {
        super(properties);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
                if (this.renderer == null)
                    this.renderer = new AbaiaTailRenderer();


                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }
}
