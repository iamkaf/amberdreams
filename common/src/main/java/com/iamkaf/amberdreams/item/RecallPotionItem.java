package com.iamkaf.amberdreams.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;

import java.util.List;

public class RecallPotionItem extends HoneyBottleItem {
    private static final int DRINK_DURATION = 100;

    public RecallPotionItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return DRINK_DURATION;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.literal("Takes 5 seconds to drink.").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Use To Return Home")
                .withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        var superStack = super.finishUsingItem(stack, level, livingEntity);
        stack.shrink(1);
        if (livingEntity instanceof ServerPlayer player) {
//            SoundUtil.sendClientSound(player, SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 0.8f);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.PORTAL,
                        player.getX(),
                        player.getY() + 2,
                        player.getZ(),
                        20,
                        0.5,
                        0.2,
                        0.5,
                        1.4d
                );
            }
            var transition =
                    player.findRespawnPositionAndUseSpawnBlock(false, DimensionTransition.PLAY_PORTAL_SOUND);
            player.changeDimension(transition);
        }
        return superStack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.PORTAL,
                    player.getX(),
                    player.getY() + 1,
                    player.getZ(),
                    20,
                    0.5,
                    0.2,
                    0.5,
                    0.8d
            );
        }
        return super.use(level, player, usedHand);
    }
}
