package com.iamkaf.amberdreams.item;

import com.iamkaf.amberdreams.util.SoundUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MedpackItem extends Item {
    public static final int HEAL_AMOUNT = 4;

    public MedpackItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        stack.shrink(1);
        player.heal(HEAL_AMOUNT);
        SoundUtil.sendClientSound(player, SoundEvents.HONEY_DRINK);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.HEART,
                    player.getX(),
                    player.getY() + 1,
                    player.getZ(),
                    8,
                    0.5,
                    0.2,
                    0.5,
                    0.8d
            );
            player.getCooldowns().addCooldown(this, 20);
        }

        return InteractionResultHolder.consume(stack);
    }
}
