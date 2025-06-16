package com.cerbon.myths_of_the_sea.item.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MTSSpawnEggItem extends SpawnEggItem {
    private static final List<MTSSpawnEggItem> MTS_EGGS = new ArrayList<>();
    private static final Map<EntityType<? extends Mob>, MTSSpawnEggItem> TYPE_MAP = new IdentityHashMap<>();
    private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;

    public MTSSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties properties) {
        super(null, backgroundColor, highlightColor, properties);
        this.typeSupplier = type;

        MTS_EGGS.add(this);
    }

    @Override
    public @NotNull EntityType<?> getType(@Nullable CompoundTag tag) {
        EntityType<?> type = super.getType(tag);
        return type != null ? type : typeSupplier.get();
    }

    @Nullable
    protected DispenseItemBehavior createDispenseBehavior() {
        return DEFAULT_DISPENSE_BEHAVIOR;
    }

    @Nullable
    public static SpawnEggItem fromEntityType(@Nullable EntityType<?> type) {
        SpawnEggItem ret = TYPE_MAP.get(type);
        return ret != null ? ret : SpawnEggItem.byId(type);
    }

    @Override
    public @NotNull FeatureFlagSet requiredFeatures() {
        return this.typeSupplier.get().requiredFeatures();
    }

    //Override in Forge
    protected EntityType<?> getDefaultType() {
        return this.typeSupplier.get();
    }

    private static final DispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) -> {
        Direction face = source.getBlockState().getValue(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem)stack.getItem()).getType(stack.getTag());

        try {
            type.spawn(source.getLevel(), stack, null, source.getPos().relative(face), MobSpawnType.DISPENSER, face != Direction.UP, false);
        }
        catch (Exception exception) {
            DispenseItemBehavior.LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.getPos(), exception);
            return ItemStack.EMPTY;
        }

        stack.shrink(1);
        source.getLevel().gameEvent(GameEvent.ENTITY_PLACE, source.getPos(), GameEvent.Context.of(source.getBlockState()));
        return stack;
    };

    public static void registerSpawnEggsDispenserBehaviour() {
        MTS_EGGS.forEach(egg -> {
            DispenseItemBehavior dispenseBehavior = egg.createDispenseBehavior();

            if (dispenseBehavior != null)
                DispenserBlock.registerBehavior(egg, dispenseBehavior);

            TYPE_MAP.put(egg.typeSupplier.get(), egg);
        });
    }

    @Environment(EnvType.CLIENT)
    public static void registerSpawnEggsColors(ItemColors itemColors) {
        MTS_EGGS.forEach(egg -> itemColors.register((stack, layer) -> egg.getColor(layer), egg));
    }
}
