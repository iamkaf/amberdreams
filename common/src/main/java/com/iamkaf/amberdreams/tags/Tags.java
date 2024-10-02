package com.iamkaf.amberdreams.tags;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Tags {
    private static TagKey<Item> createItemTag(String name) {
        return TagKey.create(Registries.ITEM, AmberDreams.resource(name));
    }

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, AmberDreams.resource(name));
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createItemTag("transformable_items");
    }

    public static class Blocks {
        public static final TagKey<Block> NEEDS_BISMUTH_TOOL = createBlockTag("needs_bismuth_tool");
        public static final TagKey<Block> INCORRECT_FOR_BISMUTH_TOOL = createBlockTag("incorrect_for_bismuth_tool");
    }
}
