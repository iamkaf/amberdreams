package com.iamkaf.amberdreams.tool_upgrades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public class ItemLevelDataComponent {
    // codec for serializing this class
    public static final Codec<ItemLevelDataComponent> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("experience").forGetter(ItemLevelDataComponent::getExperience),
                    Codec.INT.fieldOf("maxExperience").forGetter(ItemLevelDataComponent::getMaxExperience),
                    Codec.INT.fieldOf("level").forGetter(ItemLevelDataComponent::getLevel),
                    Codec.INT.fieldOf("bond").forGetter(ItemLevelDataComponent::getBond)
            ).apply(instance, ItemLevelDataComponent::new));


    /**
     * The current experience the item has.
     */
    private final int experience;
    /**
     * The experience needed for the item to reach the next level.
     */
    private final int maxExperience;
    /**
     * The item's level.
     */
    private final int level;
    /**
     * The accumulated bond level with the item.
     */
    private final int bond;

    public ItemLevelDataComponent(int experience, int maxExperience, int level, int bond) {
        this.experience = experience;
        this.maxExperience = maxExperience;
        this.level = level;
        this.bond = bond;
    }

    public static ItemLevelDataComponent make() {
        return new ItemLevelDataComponent(0, 100, 1, 0);
    }

    public static ItemLevelDataComponent make(int maxExperience) {
        return new ItemLevelDataComponent(0, maxExperience, 1, 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.experience, this.maxExperience, this.level);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof ItemLevelDataComponent ex && this.experience == ex.experience && this.maxExperience == ex.maxExperience && this.level == ex.level && this.bond == ex.bond;
        }
    }

    public int getExperience() {
        return experience;
    }

    public int getMaxExperience() {
        return maxExperience;
    }

    public int getLevel() {
        return level;
    }

    public int getBond() {
        return bond;
    }

    public ItemLevelDataComponent addExperience(int amount) {
        return new ItemLevelDataComponent(experience + amount, maxExperience, level, bond);
    }

    public ItemLevelDataComponent addLevel(int newMaxExperience) {
        return new ItemLevelDataComponent(0, newMaxExperience, level + 1, bond);
    }

    public ItemLevelDataComponent addBond(int amount) {
        return new ItemLevelDataComponent(experience, maxExperience, level, bond + amount);
    }
}
