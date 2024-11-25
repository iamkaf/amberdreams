package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.tags.Tags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.InstrumentTagsProvider;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModInstrumentTagProvider extends InstrumentTagsProvider {

    public ModInstrumentTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, AmberDreams.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Instruments.WHISTLE).add(AmberDreams.Instruments.WHISTLE.getKey());
        tag(Tags.Instruments.CATCALL_WHISTLE).add(AmberDreams.Instruments.WHISTLE_CATCALL.getKey());
        tag(Tags.Instruments.SCIFI_WHISTLE).add(AmberDreams.Instruments.WHISTLE_SCIFI.getKey());
        tag(Tags.Instruments.TOY_WHISTLE).add(AmberDreams.Instruments.WHISTLE_TOY.getKey());
        tag(Tags.Instruments.BOTTLE_WHISTLE).add(AmberDreams.Instruments.WHISTLE_BOTTLE.getKey());
    }
}
