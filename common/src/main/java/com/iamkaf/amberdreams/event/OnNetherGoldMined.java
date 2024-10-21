package com.iamkaf.amberdreams.event;

import com.iamkaf.amber.api.enchantment.EnchantmentUtils;
import com.iamkaf.amberdreams.AmberDreams;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.utils.value.IntValue;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

// If you know a better way of doing this HMU, I couldn't figure out the loot table.

/**
 * Drops 1-3 tempered gold nuggets when a nether gold ore is mined in the nether.
 * Must be nether gold ore.
 * Must be in the nether.
 * Must not have silk touch.
 */
public class OnNetherGoldMined {
    static {
        BlockEvent.BREAK.register((Level level, BlockPos pos, BlockState state, ServerPlayer player,
                @Nullable IntValue xp) -> {
            var handItem = player.getMainHandItem();
            if (handItem.isEmpty() || !handItem.isCorrectToolForDrops(state)) {
                return EventResult.pass();
            }

            if (!level.dimension().equals(Level.NETHER)) {
                return EventResult.pass();
            }

            if (!state.is(Blocks.NETHER_GOLD_ORE)) {
                return EventResult.pass();
            }

            if (EnchantmentUtils.containsEnchantment(handItem,
                    ResourceLocation.fromNamespaceAndPath("minecraft", "silk_touch")
            )) {
                return EventResult.pass();
            }

            if (level.getRandom().nextFloat() < 0.5f) {
                level.addFreshEntity(new ItemEntity(level,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        new ItemStack(AmberDreams.Items.TEMPERED_GOLD_NUGGET.get(),
                                level.getRandom().nextInt(3) + 1
                        )
                ));
            }


            return EventResult.pass();
        });
    }

    public static void init() {

    }
}
