package com.cerbon.myths_of_the_sea.forge.datagen.providers;

import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.myths_of_the_sea.MythsOfTheSea;
import com.cerbon.myths_of_the_sea.sound.MTSSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

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

        addSound(MTSSounds.BAKE_KUJIRA_IDLE, 3);
        addSound(MTSSounds.BAKE_KUJIRA_MOVEMENT, 4);
        addSound(MTSSounds.BAKE_KUJIRA_DAMAGE, 4);
        addSound(MTSSounds.BAKE_KUJIRA_ATTACK, 4);
        addSound(MTSSounds.BAKE_KUJIRA_DEATH, 4);

        addSound(MTSSounds.BUNYIP_IDLE, 4);
        addSound(MTSSounds.BUNYIP_MOVEMENT, 5);
        addSound(MTSSounds.BUNYIP_DAMAGE, 3);
        addSound(MTSSounds.BUNYIP_ATTACK, 5);
        addSound(MTSSounds.BUNYIP_DEATH, 3);
    }

    private void addSound(RegistryEntry<SoundEvent> sound, int soundVariationAmount) {
        SoundDefinition definition = definition().subtitle("subtitles." + MythsOfTheSea.MOD_ID + "." + sound.getId().getPath());

        for (int i = 1; i <= soundVariationAmount; i++)
            definition.with(sound(MythsOfTheSea.MOD_ID + ":" + sound.getId().getPath() + i));

        this.add(sound.get(), definition);
    }
}
