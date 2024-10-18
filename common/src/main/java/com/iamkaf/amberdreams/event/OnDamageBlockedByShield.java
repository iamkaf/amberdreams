package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.tool_upgrades.EquipmentLeveler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.Nullable;

public class OnDamageBlockedByShield {
    public static void handleDamageEvent(ServerPlayer player, float damage) {
        var shield = findShield(player);
        if (shield == null) return;

        EquipmentLeveler.initLevelingComponentIfNeeded(shield);
        if (EquipmentLeveler.giveItemExperience(shield, ((int) damage + 1) * 5)) {
            EquipmentLeveler.itemLeveledFeedback(player.level(), player, shield);
        }
    }

    private static @Nullable ItemStack findShield(ServerPlayer player) {
        if (player.getMainHandItem().getItem() instanceof ShieldItem) {
            return player.getMainHandItem();
        }
        if (player.getOffhandItem().getItem() instanceof ShieldItem) {
            return player.getOffhandItem();
        }
        return null;
    }
}
