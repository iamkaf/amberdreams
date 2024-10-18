package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.AmberDreams;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class OnPlayerJoin {
    public static void addMaxHealthAttribute(ServerPlayer player) {
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (attribute != null) {
            attribute.addPermanentModifier(new AttributeModifier(
                    AmberDreams.resource("max_health_modifier"),
                    -10,
                    AttributeModifier.Operation.ADD_VALUE
            ));
        }
    }

    public static void init() {
        PlayerEvent.PLAYER_JOIN.register(OnPlayerJoin::addMaxHealthAttribute);
    }
}
