package com.iamkaf.amberdreams.registry;

import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CreativeTabRegistryHolder {
    private final List<Supplier<ItemLike>> tabItems = new ArrayList<>();

    public CreativeTabRegistryHolder() {
    }

    public <T extends ItemLike> Supplier<T> add(Supplier<T> item) {
        // TODO: find out how to suppress this warning
        tabItems.add((Supplier<ItemLike>) item);
        return item;
    }

    public List<ItemLike> getItems() {
        List<ItemLike> items = new ArrayList<>();
        for (Supplier<ItemLike> itemSupplier : tabItems) {
            items.add(itemSupplier.get());
        }
        return items;
    }
}
