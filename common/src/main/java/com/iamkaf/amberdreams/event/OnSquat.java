package com.iamkaf.amberdreams.event;

import com.iamkaf.amber.api.level.LevelHelper;
import com.iamkaf.amber.api.sound.SoundHelper;
import com.iamkaf.amberdreams.AmberDreams;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

/**
 * Makes the player drop poop every few ticks if they're sneaking.
 * Runs every few ticks to prevent poop overload.
 */
public class OnSquat {
    // The time it takes to poop, the lower the rate, the faster the shitting.
    public static final int POOP_RATE = 20;

    public static void init() {
        TickEvent.PLAYER_POST.register(OnSquat::onPlayerTick);
    }

    private static void onPlayerTick(Player player) {
        Level level = player.level();

        if (level.isClientSide) {
            return;
        }

        LevelHelper.runEveryXTicks(level, POOP_RATE, time -> {
            if (player.isShiftKeyDown() && level.getBlockState(player.blockPosition().below()).is(Blocks.QUARTZ_BLOCK)) {
                ItemStack poopStack = new ItemStack(AmberDreams.Items.POOP.get());
                var poop = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), poopStack);
                poop.setPickUpDelay(30);
                level.addFreshEntity(poop);
                SoundHelper.sendClientSound(player, SoundEvents.HONEY_DRINK, SoundSource.PLAYERS, 0.5f, 1.2f);
            }
        });
    }
}
