package com.iamkaf.amberdreams.fabric;

import com.iamkaf.amberdreams.AmberDreams;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public final class AmberDreamsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        AmberDreams.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> {
            content.accept(AmberDreams.Items.BISMUTH.get());
            content.accept(AmberDreams.Items.RAW_BISMUTH.get());
            content.accept(AmberDreams.Blocks.BISMUTH_BLOCK.get());
            content.accept(AmberDreams.Blocks.BISMUTH_ORE.get());
            content.accept(AmberDreams.Blocks.BISMUTH_DEEPSLATE_ORE.get());
        });
    }
}
