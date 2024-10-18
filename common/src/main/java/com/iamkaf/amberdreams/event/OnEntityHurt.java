package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.tool_upgrades.ArmorLeveler;
import com.iamkaf.amberdreams.tool_upgrades.EquipmentLeveler;
import com.iamkaf.amberdreams.tool_upgrades.ToolLeveler;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;

public class OnEntityHurt {
    static {
        EntityEvent.LIVING_HURT.register((LivingEntity entity, DamageSource source, float amount) -> {
            var level = entity.level();

            if (level.isClientSide) {
                return EventResult.pass();
            }


            if (source.getEntity() instanceof Player player) {
                processPlayerDealtDamage(player, amount);
            }
            if (entity instanceof Player player) {
                AmberDreams.LOGGER.info("Player damage: {}", amount);
                processPlayerTakenDamage(player, source, amount);
            }

            return EventResult.pass();
        });
    }

    public static void init() {

    }

    private static void processPlayerDealtDamage(Player player, float amount) {
        var level = player.level();

        var handItem = player.getMainHandItem();
        if (handItem.isEmpty()) {
            return;
        }

        // only supporting tools for now
        // future plans could include: bows, tridents
        boolean isWeapon = handItem.getItem() instanceof AxeItem || handItem.getItem() instanceof SwordItem;
        if (!isWeapon) {
            return;
        }

        if (ToolLeveler.giveItemExperience(handItem, (int) amount)) {
            EquipmentLeveler.itemLeveledFeedback(level, player, handItem);
        }

        var playerArmorSlots = player.getArmorSlots();

        for (var slot : playerArmorSlots) {
            if (!EquipmentLeveler.isEligibleForLeveling(slot)) continue;
            EquipmentLeveler.initLevelingComponentIfNeeded(slot);

            if (ArmorLeveler.giveItemExperience(slot, (int) amount + 1)) {
                EquipmentLeveler.itemLeveledFeedback(player.level(), player, slot);
            }
        }
    }

    private static void processPlayerTakenDamage(Player player, DamageSource source, float amount) {
        var playerArmorSlots = player.getArmorSlots();

        if (source.getEntity() == null) return;

        for (var slot : playerArmorSlots) {
            if (!EquipmentLeveler.isEligibleForLeveling(slot)) continue;
            EquipmentLeveler.initLevelingComponentIfNeeded(slot);

            var hasLeveled = ArmorLeveler.giveItemExperience(slot, 5);
            if (hasLeveled) {
                EquipmentLeveler.itemLeveledFeedback(player.level(), player, slot);
            }
        }
    }
}
