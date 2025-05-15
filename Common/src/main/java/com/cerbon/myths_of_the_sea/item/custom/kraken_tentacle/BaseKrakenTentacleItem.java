package com.cerbon.myths_of_the_sea.item.custom.kraken_tentacle;

import com.cerbon.myths_of_the_sea.platform.Services;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public abstract class BaseKrakenTentacleItem extends Item implements GeoItem {
    protected final Multimap<Attribute, AttributeModifier> defaultModifiers;

    protected final AnimatableInstanceCache animatableCache = GeckoLibUtil.createInstanceCache(this);

    protected static final UUID TENTACLE_BLOCK_REACH_UUID = UUID.fromString("d6f76a9d-c416-4435-8d80-5564d3509cd6");
    protected static final UUID TENTACLE_ENTITY_REACH_UUID = UUID.fromString("f9bf8abf-0ef1-4d5e-989c-984ba28fc63d");

    public BaseKrakenTentacleItem(Properties properties) {
        super(properties);

        Multimap<Attribute, AttributeModifier> attributes = LinkedHashMultimap.create();

        attributes.put(Services.PLATFORM.blockReachAttribute(), new AttributeModifier(TENTACLE_BLOCK_REACH_UUID, "Weapon Modifier", 3.0D, AttributeModifier.Operation.ADDITION));
        attributes.put(Services.PLATFORM.entityReachAttribute(), new AttributeModifier(TENTACLE_ENTITY_REACH_UUID, "Weapon Modifier", 3.0D, AttributeModifier.Operation.ADDITION));

        this.defaultModifiers = attributes;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        return slot == EquipmentSlot.OFFHAND || slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
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
