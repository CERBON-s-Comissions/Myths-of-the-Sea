package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.List;

public class MTSSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public MTSSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MythsOfTheSea.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        addSound(MTSSounds.ABAIA_IDLE, 4);
        addSound(MTSSounds.ABAIA_MOVEMENT, 1);
        addSound(MTSSounds.ABAIA_DAMAGE, 3);
        addSound(MTSSounds.ABAIA_ATTACK, 3);
        addSound(MTSSounds.ABAIA_DEATH, 3);
        addSpecificSound(MTSSounds.ABAIA_FLOP,
                List.of(
                        "minecraft:mob/guardian/flop1",
                        "minecraft:mob/guardian/flop2",
                        "minecraft:mob/guardian/flop3",
                        "minecraft:mob/guardian/flop4"
                ));

        addSound(MTSSounds.BAKE_KUJIRA_IDLE, 3);
        addSound(MTSSounds.BAKE_KUJIRA_MOVEMENT, 4);
        addSound(MTSSounds.BAKE_KUJIRA_DAMAGE, 4);
        addSound(MTSSounds.BAKE_KUJIRA_ATTACK, 4);
        addSound(MTSSounds.BAKE_KUJIRA_DEATH, 4);
        addSpecificSound(MTSSounds.BAKE_KUJIRA_FLOP,
                List.of(
                        "minecraft:entity/fish/flop1",
                        "minecraft:entity/fish/flop2",
                        "minecraft:entity/fish/flop3",
                        "minecraft:entity/fish/flop4"
                ));

        addSound(MTSSounds.BUNYIP_IDLE, 4);
        addSound(MTSSounds.BUNYIP_MOVEMENT, 5);
        addSound(MTSSounds.BUNYIP_DAMAGE, 3);
        addSound(MTSSounds.BUNYIP_ATTACK, 5);
        addSound(MTSSounds.BUNYIP_DEATH, 3);

        addSound(MTSSounds.LEVIATHAN_IDLE, 6);
        addSound(MTSSounds.LEVIATHAN_MOVEMENT, 3);
        addSound(MTSSounds.LEVIATHAN_DAMAGE, 3);
        addSound(MTSSounds.LEVIATHAN_ATTACK, 3);
        addSound(MTSSounds.LEVIATHAN_DEATH, 3);

        addSound(MTSSounds.HIPPOCAMPUS_IDLE, 3);
        addSound(MTSSounds.HIPPOCAMPUS_DAMAGE, 3);
        addSound(MTSSounds.HIPPOCAMPUS_ATTACK, 3);
        addSound(MTSSounds.HIPPOCAMPUS_DEATH, 3);
        addSpecificSound(MTSSounds.HIPPOCAMPUS_FLOP,
                List.of(
                        "minecraft:mob/guardian/flop1",
                        "minecraft:mob/guardian/flop2",
                        "minecraft:mob/guardian/flop3",
                        "minecraft:mob/guardian/flop4"
                ));
        addSpecificSound(MTSSounds.HIPPOCAMPUS_EAT,
                List.of(
                        "minecraft:mob/dolphin/eat1",
                        "minecraft:mob/dolphin/eat2",
                        "minecraft:mob/dolphin/eat3"
                ));

        addSound(MTSSounds.KRAKEN_IDLE, 3);
        addSound(MTSSounds.KRAKEN_DAMAGE, 3);
        addSound(MTSSounds.KRAKEN_ATTACK, 3);
        addSound(MTSSounds.KRAKEN_DEATH, 3);
    }

    private void addSpecificSound(RegistryEntry<SoundEvent> sound, List<String> names) {
        SoundDefinition definition = definition().subtitle("subtitles." + MythsOfTheSea.MOD_ID + "." + sound.getId().getPath());


        for(String name: names){
            definition.with(sound(name));
        }


        this.add(sound.get(), definition);
    }

    private void addSound(RegistryEntry<SoundEvent> sound, int soundVariationAmount) {
        SoundDefinition definition = definition().subtitle("subtitles." + MythsOfTheSea.MOD_ID + "." + sound.getId().getPath());

        for (int i = 1; i <= soundVariationAmount; i++)
            definition.with(sound(MythsOfTheSea.MOD_ID + ":" + sound.getId().getPath() + i));

        this.add(sound.get(), definition);
    }
}
