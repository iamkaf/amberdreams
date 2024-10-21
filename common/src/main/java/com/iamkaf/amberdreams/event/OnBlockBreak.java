package com.iamkaf.amberdreams.event;

import com.iamkaf.amber.api.enchantment.EnchantmentUtils;
import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.tool_upgrades.EquipmentLeveler;
import com.iamkaf.amberdreams.tool_upgrades.ToolLeveler;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.utils.value.IntValue;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OnBlockBreak {
    static {
        BlockEvent.BREAK.register((Level level, BlockPos pos, BlockState state, ServerPlayer player,
                @Nullable IntValue xp) -> {
            var handItem = player.getMainHandItem();
            if (handItem.isEmpty()) {
                return EventResult.pass();
            }

            // future plans could include: bows, tridents, fishing rods
            boolean isEligibleItemType = handItem.getItem() instanceof DiggerItem;
            if (!isEligibleItemType || !(handItem.getItem()).isCorrectToolForDrops(handItem, state)) {
                return EventResult.pass();
            }

            boolean hasLeveled =
                    ToolLeveler.giveItemExperience(handItem, getExperienceAmount(level, pos, player));
            if (hasLeveled) {
                EquipmentLeveler.itemLeveledFeedback(level, player, handItem);
                var currentDmg = handItem.getDamageValue();
                handItem.setDamageValue(currentDmg / 2);
            }

            return EventResult.pass();
        });
    }

    private static int getExperienceAmount(Level level, BlockPos pos, ServerPlayer player) {
        var hasSilkTouch = EnchantmentUtils.containsEnchantment(player.getMainHandItem(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "silk_touch")
        );

        if (hasSilkTouch) {
            AmberDreams.LOGGER.info("SILK TOUCH DETECTED!!!");
        }

        boolean isPossiblyAnOreBlock =
                level.getBlockState(pos).getBlock().getDescriptionId().contains("_ore");

        AmberDreams.LOGGER.info("isPossiblyAnOreBlock: {}", isPossiblyAnOreBlock);
        if (isPossiblyAnOreBlock && !hasSilkTouch) {
            return 10;
        }
        return 1;
    }

    public static void init() {

    }
}
