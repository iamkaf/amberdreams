package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients,
            RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput,
                RecipeSerializer.SMELTING_RECIPE,
                SmeltingRecipe::new,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTIme,
                pGroup,
                "_from_smelting"
        );
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients,
            RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput,
                RecipeSerializer.BLASTING_RECIPE,
                BlastingRecipe::new,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_blasting"
        );
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput,
            RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
            List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience,
            int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike),
                    pCategory,
                    pResult,
                    pExperience,
                    pCookingTime,
                    pCookingSerializer,
                    factory
            ).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(recipeOutput,
                    AmberDreams.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike)
            );
        }
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> BISMUTH_SMELTABLES = List.of(AmberDreams.Items.RAW_BISMUTH.get(),
                AmberDreams.Blocks.BISMUTH_ORE.get(),
                AmberDreams.Blocks.BISMUTH_DEEPSLATE_ORE.get()
        );

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AmberDreams.Blocks.BISMUTH_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', AmberDreams.Items.BISMUTH.get())
                .unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AmberDreams.Items.BISMUTH.get(), 9)
                .requires(AmberDreams.Blocks.BISMUTH_BLOCK.get())
                .unlockedBy("has_bismuth_block", has(AmberDreams.Blocks.BISMUTH_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AmberDreams.Items.BISMUTH.get(), 9)
                .requires(AmberDreams.Blocks.MAGIC_BLOCK.get())
                .unlockedBy("has_magic_block", has(AmberDreams.Blocks.MAGIC_BLOCK.get()))
                .save(recipeOutput, "amberdreams:bismuth_from_magic_block");
        oreSmelting(recipeOutput,
                BISMUTH_SMELTABLES,
                RecipeCategory.MISC,
                AmberDreams.Items.BISMUTH.get(),
                0.25f,
                200,
                "bismuth"
        );
        oreBlasting(recipeOutput,
                BISMUTH_SMELTABLES,
                RecipeCategory.MISC,
                AmberDreams.Items.BISMUTH.get(),
                0.25f,
                100,
                "bismuth"
        );

        stairBuilder(AmberDreams.Blocks.BISMUTH_STAIRS.get(),
                Ingredient.of(AmberDreams.Items.BISMUTH.get())
        ).unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
        slab(recipeOutput,
                RecipeCategory.BUILDING_BLOCKS,
                AmberDreams.Blocks.BISMUTH_SLAB.get(),
                AmberDreams.Items.BISMUTH.get()
        );

        buttonBuilder(AmberDreams.Blocks.BISMUTH_BUTTON.get(),
                Ingredient.of(AmberDreams.Items.BISMUTH.get())
        ).group("bismuth").unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
        pressurePlate(recipeOutput,
                AmberDreams.Blocks.BISMUTH_PRESSURE_PLATE.get(),
                AmberDreams.Items.BISMUTH.get()
        );

        fenceBuilder(AmberDreams.Blocks.BISMUTH_FENCE.get(),
                Ingredient.of(AmberDreams.Items.BISMUTH.get())
        ).group("bismuth").unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
        fenceGateBuilder(AmberDreams.Blocks.BISMUTH_FENCE_GATE.get(),
                Ingredient.of(AmberDreams.Items.BISMUTH.get())
        ).group("bismuth").unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
        wall(recipeOutput,
                RecipeCategory.BUILDING_BLOCKS,
                AmberDreams.Blocks.BISMUTH_WALL.get(),
                AmberDreams.Items.BISMUTH.get()
        );

        doorBuilder(AmberDreams.Blocks.BISMUTH_DOOR.get(),
                Ingredient.of(AmberDreams.Items.BISMUTH.get())
        ).group("bismuth").unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);
        trapdoorBuilder(AmberDreams.Blocks.BISMUTH_TRAPDOOR.get(),
                Ingredient.of(AmberDreams.Items.BISMUTH.get())
        ).group("bismuth").unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get())).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AmberDreams.Blocks.BISMUTH_LAMP.get())
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', AmberDreams.Items.BISMUTH.get())
                .define('B', Blocks.REDSTONE_BLOCK)
                .unlockedBy("has_bismuth", has(AmberDreams.Items.BISMUTH.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, AmberDreams.Blocks.TOOL_BENCH.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern("B B")
                .define('A', ItemTags.PLANKS)
                .define('B', ItemTags.LOGS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, AmberDreams.Blocks.REPAIR_BENCH.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern("B B")
                .define('A', Items.COBBLESTONE)
                .define('B', Items.STONE_BRICKS)
                .unlockedBy("has_cobby", has(Items.COBBLESTONE))
                .save(recipeOutput);

        simpleFullArmorSetRecipe("tempered_gold",
                AmberDreams.Items.TEMPERED_GOLD_INGOT.get(),
                AmberDreams.Items.TEMPERED_GOLD_HELMET.get(),
                AmberDreams.Items.TEMPERED_GOLD_CHESTPLATE.get(),
                AmberDreams.Items.TEMPERED_GOLD_LEGGINGS.get(),
                AmberDreams.Items.TEMPERED_GOLD_BOOTS.get(),
                recipeOutput
        );
        simpleFullToolSetRecipe("tempered_gold",
                AmberDreams.Items.TEMPERED_GOLD_INGOT.get(),
                AmberDreams.Items.TEMPERED_GOLD_SHOVEL.get(),
                AmberDreams.Items.TEMPERED_GOLD_PICKAXE.get(),
                AmberDreams.Items.TEMPERED_GOLD_AXE.get(),
                AmberDreams.Items.TEMPERED_GOLD_HOE.get(),
                AmberDreams.Items.TEMPERED_GOLD_SWORD.get(),
                recipeOutput
        );
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AmberDreams.Blocks.TEMPERED_GOLD_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', AmberDreams.Items.TEMPERED_GOLD_INGOT.get())
                .unlockedBy("has_tempered_gold_ingot", has(AmberDreams.Items.TEMPERED_GOLD_INGOT.get()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AmberDreams.Items.TEMPERED_GOLD_INGOT.get(), 9)
                .requires(AmberDreams.Blocks.TEMPERED_GOLD_BLOCK.get())
                .unlockedBy("has_tempered_gold_block", has(AmberDreams.Blocks.TEMPERED_GOLD_BLOCK.get()))
                .save(recipeOutput, "tempered_gold_from_block");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AmberDreams.Items.TEMPERED_GOLD_INGOT.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', AmberDreams.Items.TEMPERED_GOLD_NUGGET.get())
                .unlockedBy("has_tempered_gold_nugget", has(AmberDreams.Items.TEMPERED_GOLD_NUGGET.get()))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AmberDreams.Items.TEMPERED_GOLD_NUGGET.get(), 9)
                .requires(AmberDreams.Items.TEMPERED_GOLD_INGOT.get())
                .unlockedBy("has_tempered_gold_ingot", has(AmberDreams.Items.TEMPERED_GOLD_INGOT.get()))
                .save(recipeOutput, "tempered_gold_from_nuggets");
    }

    private void simpleFullArmorSetRecipe(String id, Item material, Item helmet, Item chestplate,
            Item leggings, Item boots, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
                .pattern("AAA")
                .pattern("A A")
                .define('A', material)
                .unlockedBy("has_" + id, has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("A A")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', material)
                .unlockedBy("has_" + id, has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
                .pattern("AAA")
                .pattern("A A")
                .pattern("A A")
                .define('A', material)
                .unlockedBy("has_" + id, has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots)
                .pattern("A A")
                .pattern("A A")
                .define('A', material)
                .unlockedBy("has_" + id, has(material))
                .save(recipeOutput);
    }

    private void simpleFullToolSetRecipe(String id, Item material, Item shovel, Item pickaxe, Item axe,
            Item hoe, Item sword, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .pattern(" A ")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', material)
                .define('B', Items.STICK)
                .unlockedBy("has_" + id, has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe)
                .pattern("AAA")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', material)
                .define('B', Items.STICK)
                .unlockedBy("has_" + id, has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .pattern("AA ")
                .pattern("AB ")
                .pattern(" B ")
                .define('A', material)
                .define('B', Items.STICK)
                .unlockedBy("has_" + id, has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe)
                .pattern("AA ")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', material)
                .define('B', Items.STICK)
                .unlockedBy("has_" + id, has(material))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword)
                .pattern(" A ")
                .pattern(" A ")
                .pattern(" B ")
                .define('A', material)
                .define('B', Items.STICK)
                .unlockedBy("has_" + id, has(material))
                .save(recipeOutput);
    }
}
