package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AmberDreams.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(AmberDreams.Items.BISMUTH.get());
        basicItem(AmberDreams.Items.RAW_BISMUTH.get());
        basicItem(AmberDreams.Items.CHISEL.get());
        basicItem(AmberDreams.Items.RADISH.get());
        basicItem(AmberDreams.Items.FROSTFIRE_ICE.get());
        basicItem(AmberDreams.Items.STARLIGHT_ASHES.get());
    }
}
