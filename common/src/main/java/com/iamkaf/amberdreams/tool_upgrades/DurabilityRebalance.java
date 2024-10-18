package com.iamkaf.amberdreams.tool_upgrades;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;

public class DurabilityRebalance {
    public static HashMap<Item, Integer> ITEMS = new HashMap<>();

    static {
        ITEMS.put(Items.FISHING_ROD, 384);

        ITEMS.put(Items.GOLDEN_SHOVEL, 80);
        ITEMS.put(Items.GOLDEN_SWORD, 80);
        ITEMS.put(Items.GOLDEN_AXE, 80);
        ITEMS.put(Items.GOLDEN_PICKAXE, 80);
        ITEMS.put(Items.GOLDEN_HOE, 80);
        ITEMS.put(Items.IRON_SHOVEL, 500);
        ITEMS.put(Items.IRON_SWORD, 500);
        ITEMS.put(Items.IRON_AXE, 500);
        ITEMS.put(Items.IRON_PICKAXE, 500);
        ITEMS.put(Items.IRON_HOE, 500);
    }
}
