package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider {
    // Parameters can be obtained from GatherDataEvent.
    public ModSoundProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        // Use your actual mod id instead of "examplemod".
        super(output, AmberDreams.MOD_ID, existingFileHelper);
    }

    @Override
    public void registerSounds() {
        registerSimpleSound(AmberDreams.Sounds.WHISTLE);
        registerSimpleSound(AmberDreams.Sounds.WHISTLE_CATCALL);
        registerSimpleSound(AmberDreams.Sounds.WHISTLE_SCIFI);
        registerSimpleSound(AmberDreams.Sounds.WHISTLE_TOY);
        registerSimpleSound(AmberDreams.Sounds.WHISTLE_BOTTLE);

        registerSimpleSound(AmberDreams.Sounds.POOP);
    }

    private void registerSimpleSound(Holder<SoundEvent> soundEvent) {
        ResourceLocation id = ResourceLocation.parse(soundEvent.getRegisteredName());
        String subtitle = String.format("sound.%s.%s", AmberDreams.MOD_ID, id.getPath());

        add(() -> soundEvent.value(), SoundDefinition.definition()
                // Add sound objects to the sound definition. Parameter is a vararg.
                .with(
                        // Accepts either a string or a ResourceLocation as the first parameter.
                        // The second parameter can be either SOUND or EVENT, and can be omitted if the
                        // former.
                        sound(id, SoundDefinition.SoundType.SOUND)
                                // Sets the volume. Also has a double counterpart.
                                .volume(1f)
                                // Sets the pitch. Also has a double counterpart.
                                .pitch(1f)
                                // Sets the weight.
                                .weight(2)
                                // Sets the attenuation distance.
                                .attenuationDistance(8))
                // Sets the subtitle.
                .subtitle(subtitle)
                // Enables replacing.
                .replace(true));
    }
}