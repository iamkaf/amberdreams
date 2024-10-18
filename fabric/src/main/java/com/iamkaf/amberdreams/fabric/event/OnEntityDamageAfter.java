package com.iamkaf.amberdreams.fabric.event;

import com.iamkaf.amberdreams.event.OnDamageBlockedByShield;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class OnEntityDamageAfter {
    public static void init() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((LivingEntity entity, DamageSource source,
                float baseDamageTaken, float damageTaken, boolean blocked) -> {
            if (entity instanceof ServerPlayer player && blocked) {
                OnDamageBlockedByShield.handleDamageEvent(player, baseDamageTaken);
            }
        });
    }
}
