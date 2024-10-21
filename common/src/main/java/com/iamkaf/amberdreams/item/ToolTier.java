package com.iamkaf.amberdreams.item;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.tags.Tags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public enum ToolTier implements Tier {
    BISMUTH(
            Tags.Blocks.INCORRECT_FOR_BISMUTH_TOOL,
            1400,
            8f,
            3f,
            28,
            TierHelper.repair(AmberDreams.Items.BISMUTH)
    ),

    TEMPERED_GOLD(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            500,
            4f,
            3f,
            52,
            () -> Ingredient.of(AmberDreams.Items.TEMPERED_GOLD_INGOT.get())
    );

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    private ToolTier(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float attackDamageBonus,
            int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String toString() {
        String blocks = String.valueOf(this.incorrectBlocksForDrops);
        return "ToolTier[incorrectBlocksForDrops=" + blocks + ", uses=" + this.uses + ", speed=" + this.speed + ", attackDamageBonus=" + this.attackDamageBonus + ", enchantmentValue=" + this.enchantmentValue + ", repairIngredient=" + String.valueOf(
                this.repairIngredient) + "]";
    }
}
