package com.iamkaf.amberdreams.item;


import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class TierHelper {
    public static List<ArmorMaterial.Layer> genericLayers(String name) {
        return List.of(
                // Creates a new armor texture that will be located at:
                // - 'assets/amberdreams/textures/models/armor/name_layer_1.png' for the outer texture
                // - 'assets/amberdreams/textures/models/armor/name_layer_2.png' for the inner texture (only legs)
                new ArmorMaterial.Layer(AmberDreams.resource(name)),
                // Creates a new armor texture that will be rendered on top of the previous at:
                // - 'assets/amberdreams/textures/models/armor/name_layer_1_overlay.png' for the outer texture
                // - 'assets/amberdreams/textures/models/armor/name_layer_2_overlay.png' for the inner texture (only legs)
                // 'true' means that the armor material is dyeable; however, the item must also be added to the 'minecraft:dyeable' tag
                new ArmorMaterial.Layer(AmberDreams.resource(name), "_overlay", true));
    }


    public static Supplier<Ingredient> repair(Supplier<Item> item) {
        return () -> Ingredient.of(item.get());
    }

    public enum VanillaToughness {
        TURTLE_SHELL(0, 0, 0, 0, 0),
        LEATHER(0, 0, 0, 0, 0),
        GOLD(0, 0, 0, 0, 0),
        CHAINMAIL(0, 0, 0, 0, 0),
        IRON(0, 0, 0, 0, 0),
        DIAMOND(8, 2, 2, 2, 2),
        NETHERITE(13, 3, 3, 3, 3);

        public final int fullSet;
        public final int helmet;
        public final int chestplate;
        public final int leggings;
        public final int boots;

        VanillaToughness(int fullSet, int helmet, int chestplate, int leggings, int boots) {
            this.fullSet = fullSet;
            this.helmet = helmet;
            this.chestplate = chestplate;
            this.leggings = leggings;
            this.boots = boots;
        }
    }

    public enum VanillaKnockbackResistance {
        TURTLE_SHELL(0.0),
        LEATHER(0.0),
        GOLD(0.0),
        CHAINMAIL(0.0),
        IRON(0.0),
        DIAMOND(0.02),
        NETHERITE(0.1);

        public final double knockbackResistance;

        VanillaKnockbackResistance(double knockbackResistance) {
            this.knockbackResistance = knockbackResistance;
        }
    }

    public enum VanillaEnchantability {
        TURTLE_SHELL(9),
        LEATHER(15),
        GOLD(25),
        CHAINMAIL(12),
        IRON(9),
        DIAMOND(10),
        NETHERITE(15);

        public final int enchantability;

        VanillaEnchantability(int enchantability) {
            this.enchantability = enchantability;
        }
    }
}
