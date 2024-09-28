package com.iamkaf.amberdreams;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Register {
    @ExpectPlatform
    public static <T extends Block> Supplier<T> block(String id, Supplier<T> supplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Item> Supplier<T> item(String id, Supplier<T> supplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends CreativeModeTab> Supplier<T> creativeModeTab() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Item> void fuelItem(Supplier<T> supplier, int burnTime) {
        throw new AssertionError();
    }
}
