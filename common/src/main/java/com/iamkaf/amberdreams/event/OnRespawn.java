package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.AmberDreams;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class OnRespawn {
    public static void init() {
        PlayerEvent.PLAYER_CLONE.register(OnRespawn::onRespawn);
        PlayerEvent.PLAYER_RESPAWN.register(OnRespawn::onRespawn);
    }

    private static void onRespawn(ServerPlayer serverPlayer, boolean wonGame, Entity.RemovalReason removalReason) {
        AmberDreams.LOGGER.info("PLAYER_RESPAWN");
        OnPlayerJoin.addMaxHealthAttribute(serverPlayer);
    }

    private static void onRespawn(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean wonGame) {
        AmberDreams.LOGGER.info("PLAYER_CLONE");
        OnPlayerJoin.addMaxHealthAttribute(oldPlayer);
        OnPlayerJoin.addMaxHealthAttribute(newPlayer);
    }
}
