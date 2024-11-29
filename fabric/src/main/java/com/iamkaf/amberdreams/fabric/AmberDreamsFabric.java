package com.iamkaf.amberdreams.fabric;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.fabric.event.ClientEventHandler;
import net.fabricmc.api.ModInitializer;

public final class AmberDreamsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Fabric: This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        AmberDreams.init();

        ClientEventHandler.init();
    }
}
