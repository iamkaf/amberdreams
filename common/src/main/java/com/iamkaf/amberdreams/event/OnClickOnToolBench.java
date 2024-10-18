package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.feedback.FeedbackHelper;
import com.iamkaf.amberdreams.inventory.InventoryHelper;
import com.iamkaf.amberdreams.inventory.ItemHelper;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class OnClickOnToolBench {
    public static void init() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register(OnClickOnToolBench::handleRightClick);
    }

    private static EventResult handleRightClick(Player player, InteractionHand hand, BlockPos blockPos,
            Direction direction) {
        Level level = player.level();

        if (level.isClientSide || !hand.equals(InteractionHand.MAIN_HAND)) {
            return EventResult.pass();
        }


        var handItem = player.getMainHandItem();

        var blockState = level.getBlockState(blockPos);

        if (!blockState.is(AmberDreams.Blocks.TOOL_BENCH.get())) {
            return EventResult.pass();
        }

        var levelingComponent = handItem.get(AmberDreams.DataComponents.ITEM_EXPERIENCE.get());

        if (levelingComponent == null) {
            return EventResult.interruptTrue();
        }

        if (!EquipmentLeveler.hasEnoughLevelsToUpgrade(handItem)) {
            errorFeedback(level,
                    player,
                    Component.literal("Item not max level").withStyle(ChatFormatting.RED)
            );
            return EventResult.interruptTrue();
        }

        var upgradeIngredient = EquipmentLeveler.getUpgradeIngredient(handItem);

        if (upgradeIngredient == null || upgradeIngredient.isEmpty()) {
            errorFeedback(level, player, Component.literal("This tier doesn't have an upgrade path."));
            return EventResult.interruptTrue();
        }

        if (!InventoryHelper.has(player.getInventory(), upgradeIngredient)) {
            errorFeedback(level,
                    player,
                    Component.literal("Missing 1 " + ItemHelper.getIngredientDisplayName(upgradeIngredient))
                            .withStyle(ChatFormatting.RED)
            );
            return EventResult.interruptTrue();
        }

        var upgraded = EquipmentLeveler.upgrade(handItem);
        if (upgraded != null) {
            InventoryHelper.consumeIfAvailable(player.getInventory(), upgradeIngredient, 1);
            handItem.shrink(1);
            player.setItemSlot(EquipmentSlot.MAINHAND, upgraded);
            level.playSound(null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.ANVIL_PLACE,
                    SoundSource.PLAYERS
            );
            return EventResult.interruptTrue();
        }

        return EventResult.interruptTrue();
    }

    private static void errorFeedback(Level level, Player player, Component message) {
        level.playSound(null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.CRAFTER_FAIL,
                SoundSource.PLAYERS
        );
        FeedbackHelper.actionBarMessage(player, message);
    }
}
