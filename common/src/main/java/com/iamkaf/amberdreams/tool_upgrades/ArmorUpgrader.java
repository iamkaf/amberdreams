package com.iamkaf.amberdreams.tool_upgrades;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ArmorUpgrader {
    public static final Map<ArmorMaterial, ArmorMaterial> upgradeMap = Map.of(
            ArmorMaterials.LEATHER.value(),
            ArmorMaterials.CHAIN.value(),
            ArmorMaterials.CHAIN.value(),
            ArmorMaterials.IRON.value(),
            ArmorMaterials.IRON.value(),
            ArmorMaterials.DIAMOND.value(),
            ArmorMaterials.GOLD.value(),
            AmberDreams.ArmorMaterials.TEMPERED_GOLD.value()
    );

    public static @Nullable ItemStack upgrade(ItemStack oldArmor) {
        if (!(oldArmor.getItem() instanceof ArmorItem armorItem)) {
            return null;
        }
        if (!upgradeMap.containsKey(armorItem.getMaterial().value())) {
            return null;
        }

        var newArmor = makeUpgrade(oldArmor);
        copyAppropriateDataComponents(oldArmor, newArmor);
        return newArmor;
    }

    private static ItemStack makeUpgrade(ItemStack oldArmor) {
        var nextTier = upgradeMap.get(getTierForArmor(oldArmor));
        return new ItemStack(getItemForTypeAndTier(getArmorType(oldArmor), nextTier));
    }

    private static ArmorMaterial getTierForArmor(ItemStack tool) {
        return ((ArmorItem) tool.getItem()).getMaterial().value();
    }

    private static void copyAppropriateDataComponents(ItemStack oldTool, ItemStack newTool) {
        // might need to find a broader solution for copying data components because this could delete data components of other mods
        newTool.set(DataComponents.CUSTOM_NAME, oldTool.get(DataComponents.CUSTOM_NAME));
        newTool.set(DataComponents.ENCHANTMENTS, oldTool.get(DataComponents.ENCHANTMENTS));
        newTool.set(DataComponents.LORE, oldTool.get(DataComponents.LORE));
        newTool.set(DataComponents.DAMAGE, oldTool.get(DataComponents.DAMAGE));
        var levelingComponent = AmberDreams.DataComponents.ITEM_EXPERIENCE.get();
        newTool.set(levelingComponent, new ItemLevelDataComponent(
                0,
                ArmorLeveler.getMaxExperienceForMaterial(getTierForArmor(newTool)),
                1,
                oldTool.get(levelingComponent).getBond()
        ));
    }

    private static ArmorItem.Type getArmorType(ItemStack oldArmor) {
        var item = (ArmorItem) oldArmor.getItem();

        return item.getType();
    }

    private static Item getItemForTypeAndTier(ArmorItem.Type type, ArmorMaterial material) {
        switch (type) {
            case ArmorItem.Type.HELMET: {
                if (ArmorMaterials.LEATHER.value() == material) return Items.LEATHER_HELMET;
                if (ArmorMaterials.CHAIN.value() == material) return Items.CHAINMAIL_HELMET;
                if (ArmorMaterials.IRON.value() == material) return Items.IRON_HELMET;
                if (ArmorMaterials.TURTLE.value() == material) return Items.TURTLE_HELMET;
                if (AmberDreams.ArmorMaterials.BISMUTH.value() == material)
                    return AmberDreams.Items.BISMUTH_HELMET.get();
                if (ArmorMaterials.DIAMOND.value() == material) return Items.DIAMOND_HELMET;
                if (ArmorMaterials.NETHERITE.value() == material) return Items.NETHERITE_HELMET;
                if (ArmorMaterials.GOLD.value() == material) return Items.GOLDEN_HELMET;
                if (AmberDreams.ArmorMaterials.TEMPERED_GOLD.value() == material)
                    return AmberDreams.Items.TEMPERED_GOLD_HELMET.get();
            }

            case ArmorItem.Type.CHESTPLATE: {
                if (ArmorMaterials.LEATHER.value() == material) return Items.LEATHER_CHESTPLATE;
                if (ArmorMaterials.CHAIN.value() == material) return Items.CHAINMAIL_CHESTPLATE;
                if (ArmorMaterials.IRON.value() == material) return Items.IRON_CHESTPLATE;
                if (AmberDreams.ArmorMaterials.BISMUTH.value() == material)
                    return AmberDreams.Items.BISMUTH_CHESTPLATE.get();
                if (ArmorMaterials.DIAMOND.value() == material) return Items.DIAMOND_CHESTPLATE;
                if (ArmorMaterials.NETHERITE.value() == material) return Items.NETHERITE_CHESTPLATE;
                if (ArmorMaterials.GOLD.value() == material) return Items.GOLDEN_CHESTPLATE;
                if (AmberDreams.ArmorMaterials.TEMPERED_GOLD.value() == material)
                    return AmberDreams.Items.TEMPERED_GOLD_CHESTPLATE.get();
            }

            case ArmorItem.Type.LEGGINGS: {
                if (ArmorMaterials.LEATHER.value() == material) return Items.LEATHER_LEGGINGS;
                if (ArmorMaterials.CHAIN.value() == material) return Items.CHAINMAIL_LEGGINGS;
                if (ArmorMaterials.IRON.value() == material) return Items.IRON_LEGGINGS;
                if (AmberDreams.ArmorMaterials.BISMUTH.value() == material)
                    return AmberDreams.Items.BISMUTH_LEGGINGS.get();
                if (ArmorMaterials.DIAMOND.value() == material) return Items.DIAMOND_LEGGINGS;
                if (ArmorMaterials.NETHERITE.value() == material) return Items.NETHERITE_LEGGINGS;
                if (ArmorMaterials.GOLD.value() == material) return Items.GOLDEN_LEGGINGS;
                if (AmberDreams.ArmorMaterials.TEMPERED_GOLD.value() == material)
                    return AmberDreams.Items.TEMPERED_GOLD_LEGGINGS.get();
            }

            case ArmorItem.Type.BOOTS: {
                if (ArmorMaterials.LEATHER.value() == material) return Items.LEATHER_BOOTS;
                if (ArmorMaterials.CHAIN.value() == material) return Items.CHAINMAIL_BOOTS;
                if (ArmorMaterials.IRON.value() == material) return Items.IRON_BOOTS;
                if (AmberDreams.ArmorMaterials.BISMUTH.value() == material)
                    return AmberDreams.Items.BISMUTH_BOOTS.get();
                if (ArmorMaterials.DIAMOND.value() == material) return Items.DIAMOND_BOOTS;
                if (ArmorMaterials.NETHERITE.value() == material) return Items.NETHERITE_BOOTS;
                if (ArmorMaterials.GOLD.value() == material) return Items.GOLDEN_BOOTS;
                if (AmberDreams.ArmorMaterials.TEMPERED_GOLD.value() == material)
                    return AmberDreams.Items.TEMPERED_GOLD_BOOTS.get();
            }

            default:
                throw new IllegalStateException("No item found for material and type: " + type + " " + material);
                // TODO: add shields
        }

    }

    public static @Nullable ArmorMaterial getNextTier(ItemStack tool) {
        if (!(tool.getItem() instanceof ArmorItem tieredItem)) {
            return null;
        }

        return upgradeMap.get(tieredItem.getMaterial().value());
    }
}
