package com.iamkaf.amberdreams.neoforge.event;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.event.OnDamageBlockedByShield;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

@EventBusSubscriber
public class OnEntityDamageAfter {
    @SubscribeEvent
    public static void onEntityDamageAfter(LivingShieldBlockEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // this is the ideal implementation but the fabric side doesn't have anything similar (as of 1.21.1)
            // OnDamageBlockedByShield.handleDamageEvent(player, event.getBlockedDamage());

            OnDamageBlockedByShield.handleDamageEvent(player, event.getOriginalBlockedDamage());
        }
    }
}
