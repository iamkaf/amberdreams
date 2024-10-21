package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.tool_upgrades.ArmorLeveler;
import com.iamkaf.amberdreams.tool_upgrades.EquipmentLeveler;
import com.iamkaf.amberdreams.tool_upgrades.ToolLeveler;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;

public class OnEntityHurt {
    static {
        EntityEvent.LIVING_HURT.register((LivingEntity entity, DamageSource source, float amount) -> {
            var level = entity.level();

            if (level.isClientSide) {
                return EventResult.pass();
            }

            if (source.getEntity() instanceof Player player) {
                processPlayerDealtDamage(player, source, amount);
            }
            if (entity instanceof Player player) {
                processPlayerTakenDamage(player, source, amount);
            }

            return EventResult.pass();
        });
    }

    public static void init() {

    }

    private static void processPlayerDealtDamage(Player player, DamageSource source, float amount) {
        var level = player.level();

        ItemStack handItem = source.getWeaponItem();
        if (handItem == null) {
            handItem = checkForRocketCrossbow(player, source);
        }
        if (handItem == null || handItem.isEmpty()) {
            return;
        }

        boolean isMeleeWeapon =
                handItem.getItem() instanceof SwordItem || handItem.getItem() instanceof AxeItem || handItem.getItem() instanceof TridentItem;
        if (isMeleeWeapon) {
            if (handItem.getItem() instanceof TridentItem) {
                if (EquipmentLeveler.giveItemExperience(handItem, (int) amount)) {
                    EquipmentLeveler.itemLeveledFeedback(level, player, handItem);
                }
            } else {
                if (ToolLeveler.giveItemExperience(handItem, (int) amount)) {
                    EquipmentLeveler.itemLeveledFeedback(level, player, handItem);
                }
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

        boolean isRangedWeapon = handItem.getItem() instanceof ProjectileWeaponItem;
        boolean isProjectile = source.getDirectEntity() instanceof Projectile;
        if (isRangedWeapon && isProjectile) {
            ItemStack foundBow = tryToFindStack(player, handItem);
            if (EquipmentLeveler.giveItemExperience(foundBow, (int) amount)) {
                EquipmentLeveler.itemLeveledFeedback(level, player, handItem);
            }
        }
    }

    // this is not ideal but it's the best we got rn
    private static ItemStack tryToFindStack(Player player, ItemStack stack) {
        if (player.getMainHandItem().is(stack.getItem())) {
            return player.getMainHandItem();
        }
        if (player.getOffhandItem().is(stack.getItem())) {
            return player.getOffhandItem();
        }
        for (var slot : player.getInventory().items) {
            if (slot.is(stack.getItem())) {
                return slot;
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack checkForRocketCrossbow(Player player, DamageSource source) {
        if (!(source.getDirectEntity() instanceof FireworkRocketEntity)) {
            return null;
        }
        return tryToFindStack(player, new ItemStack(Items.CROSSBOW));
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
