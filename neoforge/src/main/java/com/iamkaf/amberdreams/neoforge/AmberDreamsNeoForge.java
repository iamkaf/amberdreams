package com.iamkaf.amberdreams.neoforge;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import com.iamkaf.amberdreams.AmberDreams;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(AmberDreams.MOD_ID)
public final class AmberDreamsNeoForge {
    public AmberDreamsNeoForge(IEventBus modBus) {
        // Run our common setup.
        AmberDreams.init();

        RegisterImpl.ITEMS.register(modBus);
        RegisterImpl.BLOCKS.register(modBus);
        RegisterImpl.TABS.register(modBus);

        modBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(AmberDreams.Items.BISMUTH.get());
            event.accept(AmberDreams.Items.RAW_BISMUTH.get());
            event.accept(AmberDreams.Blocks.BISMUTH_BLOCK.get());
            event.accept(AmberDreams.Blocks.BISMUTH_ORE.get());
            event.accept(AmberDreams.Blocks.BISMUTH_DEEPSLATE_ORE.get());
        }
    }
}
