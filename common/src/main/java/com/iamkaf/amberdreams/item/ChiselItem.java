package com.iamkaf.amberdreams.item;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP = Map.of(Blocks.STONE, Blocks.STONE_BRICKS, Blocks.END_STONE, Blocks.END_STONE_BRICKS, Blocks.DEEPSLATE, Blocks.DEEPSLATE_BRICKS, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.COPPER_BLOCK, AmberDreams.Blocks.BISMUTH_BLOCK.get(), Blocks.SLIME_BLOCK, AmberDreams.Blocks.MAGIC_BLOCK.get());

    public ChiselItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (CHISEL_MAP.containsKey(clickedBlock)) {
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(context.getClickedPos(), CHISEL_MAP.get(clickedBlock)
                        .defaultBlockState());

                context.getItemInHand()
                        .hurtAndBreak(1, ((ServerLevel) level), (ServerPlayer) context.getPlayer(), item -> {
                            context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND);
                        });
                context.getItemInHand().set(AmberDreams.DataComponents.COORDINATES.get(), context.getClickedPos());
                level.playSound(null, context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (!Screen.hasShiftDown()) {
            tooltipComponents.add(Component.literal("Can chisel some blocks into bricks."));
            tooltipComponents.add(Component.literal("Hold §e[SHIFT]§r to so learn more."));
        } else {
            tooltipComponents.add(Component.literal("§bCan chisel:"));
            for (var block : CHISEL_MAP.keySet()) {
                tooltipComponents.add(block.getName());
            }
        }

        var lastBlockChiseled = stack.get(AmberDreams.DataComponents.COORDINATES.get());
        if (lastBlockChiseled != null) {
            tooltipComponents.add(Component.literal(String.format("Last block chiseled: x%d y%d z%d", lastBlockChiseled.getX(), lastBlockChiseled.getY(), lastBlockChiseled.getZ())));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide() && player.isShiftKeyDown()) {
            var item = player.getItemInHand(usedHand);
            item.set(AmberDreams.DataComponents.COORDINATES.get(), null);
        }
        return super.use(level, player, usedHand);
    }
}
