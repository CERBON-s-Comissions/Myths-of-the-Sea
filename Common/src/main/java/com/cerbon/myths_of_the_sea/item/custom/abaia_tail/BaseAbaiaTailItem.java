package com.cerbon.myths_of_the_sea.item.custom.abaia_tail;

import com.cerbon.myths_of_the_sea.entity.MTSEntityAttributes;
import com.cerbon.myths_of_the_sea.item.custom.armor.MTSArmorMaterials;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public abstract class BaseAbaiaTailItem extends ArmorItem implements GeoItem {
    protected final Multimap<Attribute, AttributeModifier> defaultModifiers;

    private final AnimatableInstanceCache animatableCache = GeckoLibUtil.createInstanceCache(this);

    public BaseAbaiaTailItem(Properties properties) {
        super(MTSArmorMaterials.ABAIA, Type.BOOTS, properties);

        UUID uUID = UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B");
        UUID uUID_WaterSpeed = UUID.fromString("d474d732-090e-47ac-a320-e6ca97ad4006");

        Multimap<Attribute, AttributeModifier> attributes = LinkedHashMultimap.create();

        attributes.put(Attributes.ARMOR,
                new AttributeModifier(uUID, "Armor modifier", 2, AttributeModifier.Operation.ADDITION));

        attributes.put(MTSEntityAttributes.WATER_SPEED.get(),
                new AttributeModifier(uUID_WaterSpeed, "Water speed modifier", 3, AttributeModifier.Operation.ADDITION));

        this.defaultModifiers = attributes;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return slot == this.type.getSlot() ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

    public Multimap<Attribute, AttributeModifier> getAttributes() {
        return this.defaultModifiers;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableCache;
    }
}
