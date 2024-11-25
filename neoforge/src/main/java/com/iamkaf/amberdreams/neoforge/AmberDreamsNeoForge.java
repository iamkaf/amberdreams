package com.iamkaf.amberdreams.neoforge;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.tool_upgrades.DurabilityRebalance;
import net.minecraft.core.component.DataComponents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

@Mod(AmberDreams.MOD_ID)
public final class AmberDreamsNeoForge {
    public AmberDreamsNeoForge(IEventBus eBussy) {
        // Run our common setup.
        AmberDreams.init();

        RegisterImpl.ITEMS.register(eBussy);
        RegisterImpl.BLOCKS.register(eBussy);
        RegisterImpl.TABS.register(eBussy);
        RegisterImpl.DATA_COMPONENT_TYPES.register(eBussy);
        RegisterImpl.SOUND_EVENT.register(eBussy);
        RegisterImpl.INSTRUMENT.register(eBussy);

        eBussy.addListener(this::modifyDefaultComponents);
    }

    private void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        for (var entry : DurabilityRebalance.ITEMS.entrySet()) {
            event.modify(entry.getKey(), builder -> {
                builder.set(DataComponents.MAX_DAMAGE, entry.getValue());
            });
        }
    }
}
