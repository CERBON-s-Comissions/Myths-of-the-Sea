package com.cerbon.myths_of_the_sea.forge.item;

import com.cerbon.myths_of_the_sea.item.custom.abaia_tail.AbaiaTailRenderer;
import com.cerbon.myths_of_the_sea.item.custom.abaia_tail.BaseAbaiaTailItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;

public class AbaiaTailItem extends BaseAbaiaTailItem {

    public AbaiaTailItem(Properties properties) {
        super(properties);
    }

    // Create our armor model/renderer for forge and return it
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new AbaiaTailRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }
}
