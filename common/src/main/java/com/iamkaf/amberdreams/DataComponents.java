package com.iamkaf.amberdreams;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;

import java.util.function.Supplier;

public class DataComponents {
    public static final Supplier<DataComponentType<BlockPos>> COORDINATES = Register.dataComponentType("coordinates", builder -> builder.persistent(BlockPos.CODEC));

    static void init() {
    }
}
