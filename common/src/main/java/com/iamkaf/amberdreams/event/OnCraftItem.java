package com.iamkaf.amberdreams.event;

import com.iamkaf.amberdreams.tool_upgrades.ToolLeveler;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class OnCraftItem {
    static {
        PlayerEvent.CRAFT_ITEM.register((Player player, ItemStack stack, Container container) -> {
            ToolLeveler.initLevelingComponent(stack);
        });
    }

    public static void init() {

    }
}
