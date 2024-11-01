package com.iamkaf.amberdreams.neoforge.event;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.neoforge.RegisterImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = AmberDreams.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyMappings {
    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        RegisterImpl.KEY_MAPPINGS.forEach(event::register);
    }
}
