package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.AmberDreams;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.utils.value.IntValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OnBrittleyBlockCollapse {

    public static final Set<BlockPos> VISITED = new HashSet<>();
    public static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    public static final int RANGE = 16;
    public static final int LIMIT = 2000;

    public static void init() {
        BlockEvent.BREAK.register(OnBrittleyBlockCollapse::onBreak);
    }

    private static EventResult onBreak(Level level, BlockPos blockPos, BlockState blockState,
            ServerPlayer serverPlayer, @Nullable IntValue intValue) {
        if (!blockState.is(AmberDreams.Blocks.BRITTLEY_BLOCK.get())) return EventResult.pass();
        if (HARVESTED_BLOCKS.contains(blockPos)) return EventResult.pass();

        var blocksToDestroy =
                new ArrayList<>(searchBlocksToCollapse(level, blockPos, blockPos, new HashSet<>()));
        blocksToDestroy.sort(Comparator.comparingInt(p -> p.distManhattan(blockPos)));

        VISITED.clear();

        AmberDreams.LOGGER.info("Found {} blocks.", blocksToDestroy.size());

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);


        for (var block : blocksToDestroy) {
            HARVESTED_BLOCKS.add(block);
            executor.schedule(() -> {
                // Gotta check again because the block at the position might have changed
                if (level.getBlockState(block).is(AmberDreams.Blocks.BRITTLEY_BLOCK.get())) {
                    serverPlayer.gameMode.destroyBlock(block);
                }
                HARVESTED_BLOCKS.remove(block);
                feedback(level, serverPlayer, block);
            }, 5L * (blocksToDestroy.indexOf(block) + 1), TimeUnit.MILLISECONDS);
        }

        return EventResult.pass();
    }


    private static HashSet<BlockPos> searchBlocksToCollapse(Level level, BlockPos myPos,
            BlockPos absoluteOrigin, HashSet<BlockPos> brittleyToCollapse) {
        if (VISITED.size() == LIMIT) return brittleyToCollapse;
        if (VISITED.contains(myPos)) return brittleyToCollapse;
        boolean isBrittley = level.getBlockState(myPos).is(AmberDreams.Blocks.BRITTLEY_BLOCK.get());
        if (!isBrittley) return brittleyToCollapse;

        brittleyToCollapse.add(myPos);
        VISITED.add(myPos);

        for (var neighborPos : getNeighbors(myPos, absoluteOrigin)) {
            searchBlocksToCollapse(level, neighborPos, absoluteOrigin, brittleyToCollapse);
        }
        return brittleyToCollapse;
    }

    private static List<BlockPos> getNeighbors(BlockPos myPos, BlockPos absoluteOrigin) {
        List<BlockPos> blocks = new ArrayList<>();

        blocks.add(myPos.above());
        blocks.add(myPos.below());
        blocks.add(myPos.north());
        blocks.add(myPos.south());
        blocks.add(myPos.east());
        blocks.add(myPos.west());

        return blocks.stream().filter(p -> p.distManhattan(absoluteOrigin) < RANGE).toList();
    }

    private static void feedback(Level level, ServerPlayer serverPlayer, BlockPos block) {
        level.playSound(
                null,
                block,
                SoundEvents.BONE_BLOCK_BREAK,
                SoundSource.BLOCKS,
                1f,
                level.getRandom().nextFloat()
        );
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.WITCH,
                    block.getX(),
                    block.getY() + 1,
                    block.getZ(),
                    100,
                    0.5,
                    0.2,
                    0.5,
                    0.1d
            );
        }
    }
}
