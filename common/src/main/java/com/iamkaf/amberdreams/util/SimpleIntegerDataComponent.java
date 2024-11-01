package com.iamkaf.amberdreams.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SimpleIntegerDataComponent(int value) {
    // codec for serializing this class
    public static final Codec<SimpleIntegerDataComponent> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("value")
                            .forGetter(SimpleIntegerDataComponent::value))
                    .apply(instance, SimpleIntegerDataComponent::new));

    public static SimpleIntegerDataComponent empty() {
        return new SimpleIntegerDataComponent(0);
    }

    public SimpleIntegerDataComponent increment() {
        return new SimpleIntegerDataComponent(value + 1);
    }

    public SimpleIntegerDataComponent decrement() {
        return new SimpleIntegerDataComponent(value - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof SimpleIntegerDataComponent ex && this.value == ex.value;
        }
    }
}
