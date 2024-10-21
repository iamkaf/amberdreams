package com.iamkaf.amberdreams.fabric;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.fabric.event.ClientEventHandler;
import com.iamkaf.amberdreams.fabric.event.OnEntityDamageAfter;
import com.iamkaf.amberdreams.tool_upgrades.DurabilityRebalance;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public final class AmberDreamsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Fabric: This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        AmberDreams.init();

        OnEntityDamageAfter.init();
        ClientEventHandler.init();

        modifyDefaultComponents();
    }

    private void modifyDefaultComponents() {
        DefaultItemComponentEvents.MODIFY.register((context) -> {
            for (var entry : DurabilityRebalance.ITEMS.entrySet()) {
                context.modify(entry.getKey(), builder -> {
                    builder.set(DataComponents.MAX_DAMAGE, entry.getValue());
                });
            }
        });
    }

}
