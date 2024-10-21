package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.AmberDreams;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LavaCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class OnBottleUsedOnLavaCauldron {
    public static void init() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register(OnBottleUsedOnLavaCauldron::execute);
    }

    private static EventResult execute(Player player, InteractionHand hand, BlockPos blockPos,
            Direction direction) {
        Level level = player.level();
        BlockState state = level.getBlockState(blockPos);

        if (level.isClientSide) {
            return EventResult.pass();
        }

        if (state.is(Blocks.LAVA_CAULDRON)) {
            player.sendSystemMessage(Component.literal(state.toString()));
            player.sendSystemMessage(Component.literal("isFull: " + ((LavaCauldronBlock) state.getBlock()).isFull(
                    state)));
            player.sendSystemMessage(Component.literal(player.getMainHandItem().toString()));
            if (player.getItemInHand(hand).is(Items.GLASS_BOTTLE)) {
                player.getItemInHand(hand).shrink(1);
                ItemStack result = makeBottle();
                boolean inserted = player.getInventory().add(result);
                if (!inserted) {
                    player.drop(result, true);
                }
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));
                level.setBlockAndUpdate(blockPos, Blocks.CAULDRON.defaultBlockState());
                level.playSound(null, blockPos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
                return EventResult.interruptTrue();
            }
        }

        if (player.getItemInHand(hand).is(Items.GLASS_BOTTLE)) {
            BlockHitResult blockHitResult =
                    getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (blockHitResult.getType() == HitResult.Type.MISS) {
                return EventResult.pass();
            } else {
                if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos hitResultBlockPos = blockHitResult.getBlockPos();
                    if (!level.mayInteract(player, hitResultBlockPos)) {
                        return EventResult.pass();
                    }

                    if (level.getFluidState(hitResultBlockPos).is(FluidTags.LAVA)) {
                        level.playSound(null,
                                blockPos,
                                SoundEvents.BUCKET_FILL_LAVA,
                                SoundSource.BLOCKS,
                                1.0F,
                                1.0F
                        );
                        player.getItemInHand(hand).shrink(1);
                        ItemStack result = makeBottle();
                        boolean inserted = player.getInventory().add(result);
                        if (!inserted) {
                            player.drop(result, true);
                        }
                        level.gameEvent(player, GameEvent.FLUID_PICKUP, hitResultBlockPos);
                        player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));
                        return EventResult.interruptTrue();
                    }
                }

                return EventResult.pass();
            }
        }

        return EventResult.pass();
    }

    private static ItemStack makeBottle() {
        return new ItemStack(AmberDreams.Items.BOTTLE_O_HOTSTUFF.get());
    }

    private static BlockHitResult getPlayerPOVHitResult(Level level, Player player,
            ClipContext.Fluid fluidMode) {
        Vec3 vec3 = player.getEyePosition();
        Vec3 vec32 = vec3.add(player.calculateViewVector(player.getXRot(), player.getYRot())
                .scale(player.blockInteractionRange()));
        return level.clip(new ClipContext(vec3,
                vec32,
                net.minecraft.world.level.ClipContext.Block.OUTLINE,
                fluidMode,
                player
        ));
    }
}
