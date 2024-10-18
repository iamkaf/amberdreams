package com.iamkaf.amberdreams.tags;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Tags {
    private static TagKey<Item> createItemTag(String name) {
        return TagKey.create(Registries.ITEM, AmberDreams.resource(name));
    }

    private static TagKey<Item> createItemTag(String namespace, String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, AmberDreams.resource(name));
    }

    private static TagKey<Block> createBlockTag(String namespace, String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createItemTag("transformable_items");
        public static final TagKey<Item> HEAD_ARMOR = createItemTag("minecraft", "head_armor");
        public static final TagKey<Item> CHEST_ARMOR = createItemTag("minecraft", "chest_armor");
        public static final TagKey<Item> LEG_ARMOR = createItemTag("minecraft", "leg_armor");
        public static final TagKey<Item> FOOT_ARMOR = createItemTag("minecraft", "foot_armor");
    }

    public static class Blocks {
        public static final TagKey<Block> NEEDS_BISMUTH_TOOL = createBlockTag("needs_bismuth_tool");
        public static final TagKey<Block> INCORRECT_FOR_BISMUTH_TOOL = createBlockTag("incorrect_for_bismuth_tool");
    }
}
