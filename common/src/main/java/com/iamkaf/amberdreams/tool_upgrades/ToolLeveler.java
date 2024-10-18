package com.iamkaf.amberdreams.tool_upgrades;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.item.HammerItem;
import com.iamkaf.amberdreams.item.ToolTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;

import java.util.Map;
import java.util.Objects;

public class ToolLeveler {
    public static final Map<Tier, Integer> MAX_EXPERIENCE_PER_TIER = Map.of(Tiers.WOOD,
            30,
            Tiers.STONE,
            50,
            Tiers.IRON,
            100,
            ToolTier.BISMUTH,
            300,
            Tiers.DIAMOND,
            500,
            Tiers.NETHERITE,
            1000
    );
    public static final Integer DEFAULT_MAX_EXPERIENCE_FOR_UNKNOWN_TIERS = 1000;

    public static Integer getMaxExperienceForTier(Tier tier) {
        return MAX_EXPERIENCE_PER_TIER.getOrDefault(tier, DEFAULT_MAX_EXPERIENCE_FOR_UNKNOWN_TIERS);
    }

    public static boolean giveItemExperience(ItemStack tool, int amount) {
        if (!(EquipmentLeveler.isEligibleForLeveling(tool))) {
            AmberDreams.LOGGER.warn("Tried to give experience to non-equipment item! {}", tool);
            return false;
        }

        var levelingComponent = AmberDreams.DataComponents.ITEM_EXPERIENCE.get();
        if (!tool.has(levelingComponent)) {
            tool.set(levelingComponent,
                    ItemLevelDataComponent.make(getMaxExperienceForTier(((TieredItem) tool.getItem()).getTier()))
            );
        }

        applyBondBonusModifiers(tool);

        ItemLevelDataComponent currentComponent = tool.get(levelingComponent);

        if (currentComponent.getLevel() == EquipmentLeveler.MAXIMUM_LEVEL && currentComponent.getExperience() < currentComponent.getMaxExperience()) {
            // tool is done leveling
            return false;
        }

        var newComponent = Objects.requireNonNull(currentComponent).addExperience(amount).addBond(amount);
        if (hasEnoughExpToLevel(newComponent)) {
            tool.set(levelingComponent,
                    newComponent.addLevel(getMaxExperienceForTier(((TieredItem) tool.getItem()).getTier()))
            );
            applyBondBonusModifiers(tool);
            return true;
        } else {
            tool.set(levelingComponent, newComponent);
            applyBondBonusModifiers(tool);
        }
        return false;
    }

    private static boolean hasEnoughExpToLevel(ItemLevelDataComponent component) {
        if (component == null) {
            return false;
        }

        return component.getExperience() >= component.getMaxExperience();
    }

    public static void initLevelingComponent(ItemStack equipment) {
        var levelingComponent = AmberDreams.DataComponents.ITEM_EXPERIENCE.get();

        if (equipment.has(levelingComponent)) {
            return;
        }

        if (equipment.getItem() instanceof TieredItem tieredItem) {
            equipment.set(levelingComponent,
                    ItemLevelDataComponent.make(getMaxExperienceForTier(tieredItem.getTier()))
            );
        }
    }

    private static void applyBondBonusModifiers(ItemStack stack) {
        var bond = stack.get(AmberDreams.DataComponents.ITEM_EXPERIENCE.get()).getBond();

        if (stack.getItem() instanceof DiggerItem) {
            var blockBreakSpeedBonus = Math.floor((double) bond / 100) / 20;

            if (blockBreakSpeedBonus > 0) {
                var attributeModifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
                if (attributeModifiers != null) {
                    var newAttributeModifiers =
                            attributeModifiers.withModifierAdded(Attributes.MINING_EFFICIENCY,
                                    new AttributeModifier(AmberDreams.resource("bond_mining_bonus"),
                                            blockBreakSpeedBonus,
                                            AttributeModifier.Operation.ADD_VALUE
                                    ),
                                    EquipmentSlotGroup.MAINHAND
                            );

                    stack.set(DataComponents.ATTRIBUTE_MODIFIERS, newAttributeModifiers);
                }
            }
        }

        if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem || stack.getItem() instanceof HammerItem) {
            var damageBonus = Math.floor((double) bond / 100) / 10;

            if (damageBonus > 0) {
                var attributeModifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
                if (attributeModifiers != null) {
                    var newAttributeModifiers = attributeModifiers.withModifierAdded(Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(AmberDreams.resource("bond_attack_damage_bonus"),
                                    damageBonus,
                                    AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.MAINHAND
                    );

                    stack.set(DataComponents.ATTRIBUTE_MODIFIERS, newAttributeModifiers);
                }
            }
        }
    }
}
