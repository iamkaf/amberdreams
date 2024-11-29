package com.iamkaf.amberdreams.neoforge;

import com.iamkaf.amberdreams.AmberDreams;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(AmberDreams.MOD_ID)
public final class AmberDreamsNeoForge {
    public AmberDreamsNeoForge(IEventBus eBussy) {
        AmberDreams.init();

        RegisterImpl.ITEMS.register(eBussy);
        RegisterImpl.BLOCKS.register(eBussy);
        RegisterImpl.TABS.register(eBussy);
        RegisterImpl.DATA_COMPONENT_TYPES.register(eBussy);
        RegisterImpl.SOUND_EVENT.register(eBussy);
        RegisterImpl.INSTRUMENT.register(eBussy);
    }
}
