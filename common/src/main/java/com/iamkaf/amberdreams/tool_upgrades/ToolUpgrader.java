package com.iamkaf.amberdreams.tool_upgrades;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.item.HammerItem;
import com.iamkaf.amberdreams.item.ToolTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ToolUpgrader {
    public static final Map<Tier, Tier> upgradeMap =
            Map.of(Tiers.WOOD, Tiers.STONE, Tiers.STONE, Tiers.IRON, Tiers.IRON,
//            ToolTier.BISMUTH,
//            ToolTier.BISMUTH,
                    Tiers.DIAMOND,
                    Tiers.GOLD,
                    ToolTier.TEMPERED_GOLD
            );

    public static @Nullable ItemStack upgrade(ItemStack oldTool) {
        if (!(oldTool.getItem() instanceof TieredItem)) {
            return null;
        }
        if (!upgradeMap.containsKey(((TieredItem) oldTool.getItem()).getTier())) {
            return null;
        }

        var newTool = makeUpgrade(oldTool);
        copyAppropriateDataComponents(oldTool, newTool);
        return newTool;
    }

    private static ItemStack makeUpgrade(ItemStack oldTool) {
        var nextTier = upgradeMap.get(((TieredItem) oldTool.getItem()).getTier());
        return new ItemStack(getItemForTypeAndTier(getToolType(oldTool), nextTier));
    }

    private static Tier getTierForTool(ItemStack tool) {
        return ((TieredItem) tool.getItem()).getTier();
    }

    private static void copyAppropriateDataComponents(ItemStack oldTool, ItemStack newTool) {
        newTool.set(DataComponents.CUSTOM_NAME, oldTool.get(DataComponents.CUSTOM_NAME));
        newTool.set(DataComponents.ENCHANTMENTS, oldTool.get(DataComponents.ENCHANTMENTS));
        newTool.set(DataComponents.LORE, oldTool.get(DataComponents.LORE));
        newTool.set(DataComponents.DAMAGE, oldTool.get(DataComponents.DAMAGE));
        var levelingComponent = AmberDreams.DataComponents.ITEM_EXPERIENCE.get();
        newTool.set(
                levelingComponent,
                new ItemLevelDataComponent(0,
                        ToolLeveler.getMaxExperienceForTier(getTierForTool(newTool)),
                        1,
                        oldTool.get(levelingComponent).getBond()
                )
        );
    }

    private static ToolTypes getToolType(ItemStack oldTool) {
        var item = oldTool.getItem();

        return switch (item) {
            case SwordItem swordItem -> ToolTypes.SWORD;
            case AxeItem axeItem -> ToolTypes.AXE;
            case PickaxeItem pickaxeItem -> ToolTypes.PICKAXE;
            case ShovelItem shovelItem -> ToolTypes.SHOVEL;
            case HoeItem hoeItem -> ToolTypes.HOE;
            case HammerItem hammerItem -> ToolTypes.HAMMER;
            default -> throw new IllegalStateException("Unknown tool type: " + item);
        };

    }

    private static Item getItemForTypeAndTier(ToolTypes type, Tier tier) {
        AmberDreams.LOGGER.info("upgrade");
        AmberDreams.LOGGER.info(type.toString());
        AmberDreams.LOGGER.info(tier.toString());

        switch (type) {
            case SWORD: {
                // If Intellij says that this 'if' statement can be replaced with 'switch' statement
                // ignore it as it broke when I tested it in a live minecraft instance.
                if (Tiers.WOOD == tier) return Items.WOODEN_SWORD;
                if (Tiers.STONE == tier) return Items.STONE_SWORD;
                if (Tiers.IRON == tier) return Items.IRON_SWORD;
                if (Tiers.DIAMOND == tier) return Items.DIAMOND_SWORD;
                if (ToolTier.BISMUTH == tier) return AmberDreams.Items.BISMUTH_SWORD.get();
                if (ToolTier.TEMPERED_GOLD == tier) return AmberDreams.Items.TEMPERED_GOLD_SWORD.get();
            };
            case AXE: {
                if (Tiers.WOOD == tier) return Items.WOODEN_AXE;
                if (Tiers.STONE == tier) return Items.STONE_AXE;
                if (Tiers.IRON == tier) return Items.IRON_AXE;
                if (Tiers.DIAMOND == tier) return Items.DIAMOND_AXE;
                if (ToolTier.BISMUTH == tier) return AmberDreams.Items.BISMUTH_AXE.get();
                if (ToolTier.TEMPERED_GOLD == tier) return AmberDreams.Items.TEMPERED_GOLD_AXE.get();
            };
            case PICKAXE: {
                if (Tiers.WOOD == tier) return Items.WOODEN_PICKAXE;
                if (Tiers.STONE == tier) return Items.STONE_PICKAXE;
                if (Tiers.IRON == tier) return Items.IRON_PICKAXE;
                if (Tiers.DIAMOND == tier) return Items.DIAMOND_PICKAXE;
                if (ToolTier.BISMUTH == tier) return AmberDreams.Items.BISMUTH_PICKAXE.get();
                if (ToolTier.TEMPERED_GOLD == tier) return AmberDreams.Items.TEMPERED_GOLD_PICKAXE.get();
            };
            case SHOVEL: {
                if (Tiers.WOOD == tier) return Items.WOODEN_SHOVEL;
                if (Tiers.STONE == tier) return Items.STONE_SHOVEL;
                if (Tiers.IRON == tier) return Items.IRON_SHOVEL;
                if (Tiers.DIAMOND == tier) return Items.DIAMOND_SHOVEL;
                if (ToolTier.BISMUTH == tier) return AmberDreams.Items.BISMUTH_SHOVEL.get();
                if (ToolTier.TEMPERED_GOLD == tier) return AmberDreams.Items.TEMPERED_GOLD_SHOVEL.get();
            };
            case HOE: {
                if (Tiers.WOOD == tier) return Items.WOODEN_HOE;
                if (Tiers.STONE == tier) return Items.STONE_HOE;
                if (Tiers.IRON == tier) return Items.IRON_HOE;
                if (Tiers.DIAMOND == tier) return Items.DIAMOND_HOE;
                if (ToolTier.BISMUTH == tier) return AmberDreams.Items.BISMUTH_HOE.get();
                if (ToolTier.TEMPERED_GOLD == tier) return AmberDreams.Items.TEMPERED_GOLD_HOE.get();
            };
            case HAMMER: {
                return AmberDreams.Items.BISMUTH_HAMMER.get(); // Custom hammer for all tiers
            }
        }
        return Items.STICK;
    }

    public static @Nullable Tier getNextTier(ItemStack tool) {
        if (!(tool.getItem() instanceof TieredItem tieredItem)) {
            return null;
        }

        return upgradeMap.get(tieredItem.getTier());
    }

    private enum ToolTypes {
        SWORD,
        AXE,
        PICKAXE,
        SHOVEL,
        HOE,
        HAMMER
    }
}
