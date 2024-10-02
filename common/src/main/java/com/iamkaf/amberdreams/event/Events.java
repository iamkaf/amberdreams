package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.item.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

public class Events {
    public static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    public static void onHammerUsage(Player player, Level level, BlockPos initialBlockPos) {
        ItemStack mainHandItem = player.getMainHandItem();

        if (mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, level.getBlockState(pos))) {
                    continue;
                }

                // HARVESTED_BLOCKS is used like this here to prevent recursion issues.
                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
            // repair the hammer by 1 to compensate for the extra 1 damage it takes
            Integer currentDamage = mainHandItem.get(DataComponents.DAMAGE);
            if (currentDamage != null) {
                mainHandItem.set(DataComponents.DAMAGE, currentDamage - 1);
            }
        }
    }
}
