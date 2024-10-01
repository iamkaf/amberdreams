package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, AmberDreams.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> BISMUTH_SMELTABLES = List.of(AmberDreams.Items.RAW_BISMUTH.get(), AmberDreams.Blocks.BISMUTH_ORE.get(), AmberDreams.Blocks.BISMUTH_DEEPSLATE_ORE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AmberDreams.Blocks.BISMUTH_BLOCK.get())
                .pattern("BBB").pattern("BBB").pattern("BBB")
                .define('B', AmberDreams.Items.BISMUTH.get())
                .unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AmberDreams.Items.BISMUTH.get(), 9)
                .requires(AmberDreams.Blocks.BISMUTH_BLOCK.get())
                .unlockedBy("has_bismuth_block", has(AmberDreams.Blocks.BISMUTH_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AmberDreams.Items.BISMUTH.get(), 9)
                .requires(AmberDreams.Blocks.MAGIC_BLOCK.get())
                .unlockedBy("has_magic_block", has(AmberDreams.Blocks.MAGIC_BLOCK.get()))
                .save(recipeOutput, "amberdreams:bismuth_from_magic_block");
        oreSmelting(recipeOutput, BISMUTH_SMELTABLES, RecipeCategory.MISC, AmberDreams.Items.BISMUTH.get(), 0.25f, 200, "bismuth");
        oreBlasting(recipeOutput, BISMUTH_SMELTABLES, RecipeCategory.MISC, AmberDreams.Items.BISMUTH.get(), 0.25f, 100, "bismuth");

        stairBuilder(AmberDreams.Blocks.BISMUTH_STAIRS.get(), Ingredient.of(AmberDreams.Items.BISMUTH.get())).unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get()))
                .save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, AmberDreams.Blocks.BISMUTH_SLAB.get(), AmberDreams.Items.BISMUTH.get());

        buttonBuilder(AmberDreams.Blocks.BISMUTH_BUTTON.get(), Ingredient.of(AmberDreams.Items.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
        pressurePlate(recipeOutput, AmberDreams.Blocks.BISMUTH_PRESSURE_PLATE.get(), AmberDreams.Items.BISMUTH.get());

        fenceBuilder(AmberDreams.Blocks.BISMUTH_FENCE.get(), Ingredient.of(AmberDreams.Items.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
        fenceGateBuilder(AmberDreams.Blocks.BISMUTH_FENCE_GATE.get(), Ingredient.of(AmberDreams.Items.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, AmberDreams.Blocks.BISMUTH_WALL.get(), AmberDreams.Items.BISMUTH.get());

        doorBuilder(AmberDreams.Blocks.BISMUTH_DOOR.get(), Ingredient.of(AmberDreams.Items.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
        trapdoorBuilder(AmberDreams.Blocks.BISMUTH_TRAPDOOR.get(), Ingredient.of(AmberDreams.Items.BISMUTH.get())).group("bismuth")
                .unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AmberDreams.Blocks.BISMUTH_LAMP.get())
                .pattern(" A ").pattern("ABA").pattern(" A ")
                .define('A', AmberDreams.Items.BISMUTH.get())
                .define('B', Blocks.REDSTONE_BLOCK)
                .unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
    }
}
