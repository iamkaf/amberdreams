package com.iamkaf.amberdreams.item;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.util.LevelUtil;
import com.iamkaf.amberdreams.util.SimpleIntegerDataComponent;
import com.iamkaf.amberdreams.util.SoundUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class InertRecallPotionItem extends Item {

    public InertRecallPotionItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        SimpleIntegerDataComponent chargeComponent =
                stack.getOrDefault(AmberDreams.DataComponents.INERT_RECALL_POTION_CHARGE.get(),
                        SimpleIntegerDataComponent.empty()
                );
        int charge = chargeComponent.value();
        if (charge == 100) {
            tooltipComponents.add(Component.literal("Charging Complete").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.literal("Use To Activate").withStyle(ChatFormatting.YELLOW));
        } else {
            tooltipComponents.add(Component.literal("Charge " + charge + "%").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack recallPotion = new ItemStack(AmberDreams.Items.RECALL_POTION.get());
        player.setItemInHand(usedHand, recallPotion);
        if (player instanceof ServerPlayer serverPlayer) {
            SoundUtil.sendClientSound(serverPlayer, SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.8f);
        }
        return InteractionResultHolder.consume(recallPotion);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        SimpleIntegerDataComponent chargeComponent =
                stack.getOrDefault(AmberDreams.DataComponents.INERT_RECALL_POTION_CHARGE.get(),
                        SimpleIntegerDataComponent.empty()
                );
        int charge = chargeComponent.value();
        if (charge < 100 && isSelected && level instanceof ServerLevel serverLevel) {
            LevelUtil.runEveryXTicks(level, 5, (gameTime) -> {
                serverLevel.sendParticles(ParticleTypes.CLOUD,
                        entity.getX(),
                        entity.getY() + 1,
                        entity.getZ(),
                        5,
                        0.1,
                        0.1,
                        0.1,
                        0.2d
                );
                stack.set(AmberDreams.DataComponents.INERT_RECALL_POTION_CHARGE.get(),
                        chargeComponent.increment()
                );
                if (charge == 99 && entity instanceof ServerPlayer player) {
                    SoundUtil.sendClientSound(
                            player,
                            SoundEvents.EXPERIENCE_ORB_PICKUP,
                            SoundSource.PLAYERS,
                            0.8f
                    );
                }
                return null;
            });
        }
    }
}
