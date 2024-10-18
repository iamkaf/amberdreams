package com.iamkaf.amberdreams.event;

import com.iamkaf.amber.api.inventory.InventoryHelper;
import com.iamkaf.amber.api.inventory.ItemHelper;
import com.iamkaf.amber.api.player.FeedbackHelper;
import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.tool_upgrades.EquipmentLeveler;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class OnClickOnRepairBench {
    public static final float DURABILITY_REPAIR_PER_MATERIAL = 0.2f;

    public static void init() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register(OnClickOnRepairBench::handleRightClick);
    }

    private static EventResult handleRightClick(Player player, InteractionHand hand, BlockPos blockPos,
            Direction direction) {
        Level level = player.level();

        if (level.isClientSide || !hand.equals(InteractionHand.MAIN_HAND)) {
            return EventResult.pass();
        }


        var handItem = player.getMainHandItem();

        var blockState = level.getBlockState(blockPos);

        if (!blockState.is(AmberDreams.Blocks.REPAIR_BENCH.get())) {
            return EventResult.pass();
        }

        if (!EquipmentLeveler.isEligibleForLeveling(handItem)) {
            return EventResult.interruptTrue();
        }
        EquipmentLeveler.initLevelingComponentIfNeeded(handItem);

        var repairMaterial = EquipmentLeveler.getRepairIngredient(handItem);
        if (repairMaterial == null || repairMaterial.isEmpty()) {
            return EventResult.interruptTrue();
        }

        if (!InventoryHelper.has(player.getInventory(), repairMaterial)) {
            FeedbackHelper.actionBarMessage(
                    player,
                    Component.literal("Missing 1 " + ItemHelper.getIngredientDisplayName(repairMaterial))
                            .withStyle(ChatFormatting.RED)
            );
            return EventResult.interruptTrue();
        }

        if (handItem.getDamageValue() <= 0) {
            return EventResult.interruptTrue();
        }

        ItemHelper.repairBy(handItem, DURABILITY_REPAIR_PER_MATERIAL);
        InventoryHelper.consumeIfAvailable(player.getInventory(), repairMaterial, 1);
        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ANVIL_USE,
                SoundSource.PLAYERS
        );
        return EventResult.interruptTrue();
    }
}
