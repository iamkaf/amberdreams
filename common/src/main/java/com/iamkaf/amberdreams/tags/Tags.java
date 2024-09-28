package com.iamkaf.amberdreams.tags;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Tags {
    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, AmberDreams.resource(name));
        }
    }

    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, AmberDreams.resource(name));
        }
    }
}
