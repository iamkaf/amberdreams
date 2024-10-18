package com.iamkaf.amberdreams.tool_upgrades;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.Map;
import java.util.Objects;

public class ArmorLeveler {
    public static final Integer MAXIMUM_LEVEL = 10;
    public static final Map<ArmorMaterial, Integer> MAX_EXPERIENCE_PER_ARMOR_MATERIAL =
            Map.of(ArmorMaterials.LEATHER.value(),
                    90,
                    ArmorMaterials.CHAIN.value(),
                    150,
                    ArmorMaterials.IRON.value(),
                    300,
                    ArmorMaterials.TURTLE.value(),
                    900,
                    AmberDreams.ArmorMaterials.BISMUTH.value(),
                    900,
                    ArmorMaterials.DIAMOND.value(),
                    1500,
                    ArmorMaterials.NETHERITE.value(),
                    3000
            );

    public static Integer getMaxExperienceForMaterial(ArmorMaterial material) {
        return MAX_EXPERIENCE_PER_ARMOR_MATERIAL.getOrDefault(material,
                EquipmentLeveler.DEFAULT_MAX_EXPERIENCE_FOR_UNKNOWN_TIERS
        );
    }

    public static boolean giveItemExperience(ItemStack armor, int amount) {
        if (!(armor.getItem() instanceof ArmorItem armorItem)) {
            AmberDreams.LOGGER.warn("Tried to give experience to non-equipment item! {}", armor);
            return false;
        }

        var levelingComponent = AmberDreams.DataComponents.ITEM_EXPERIENCE.get();
        if (!armor.has(levelingComponent)) {
            armor.set(levelingComponent,
                    ItemLevelDataComponent.make(getMaxExperienceForMaterial(armorItem.getMaterial().value()))
            );
        }

        applyBondBonusModifiers(armor);

        ItemLevelDataComponent currentComponent = armor.get(levelingComponent);

        if (currentComponent.getLevel() == MAXIMUM_LEVEL && currentComponent.getExperience() < currentComponent.getMaxExperience()) {
            // armor is done leveling
            return false;
        }

        var newComponent = Objects.requireNonNull(currentComponent).addExperience(amount).addBond(amount);
        if (EquipmentLeveler.hasEnoughExpToLevel(newComponent)) {
            armor.set(levelingComponent,
                    newComponent.addLevel(getMaxExperienceForMaterial(armorItem.getMaterial().value()))
            );
            applyBondBonusModifiers(armor);
            return true;
        } else {
            armor.set(levelingComponent, newComponent);
            applyBondBonusModifiers(armor);
        }
        return false;
    }

    private static void applyBondBonusModifiers(ItemStack stack) {
        var bond = stack.get(AmberDreams.DataComponents.ITEM_EXPERIENCE.get()).getBond();

        var healthBonus = Math.clamp(Math.floor((double) bond / 1500), 0, 20);
        var armorBonus = Math.clamp(Math.floor((double) bond / 2500), 0, 5);

        if (healthBonus <= 0 && armorBonus <= 0) return;

        var attributeBuilder = ItemAttributeModifiers.builder();

        // See: https://bugs.mojang.com/browse/MC-271826
        var defaultModifiers = ((ArmorItem) stack.getItem()).getDefaultAttributeModifiers();
        var extraModifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        assert extraModifiers != null;
        for (var mod : defaultModifiers.modifiers()) {
            attributeBuilder.add(mod.attribute(), mod.modifier(), mod.slot());
        }

        EquipmentSlotGroup slotGroup = getEquipmentSlot(stack);

        attributeBuilder.add(Attributes.MAX_HEALTH,
                new AttributeModifier(AmberDreams.resource("bond_heath_bonus_" + slotGroup.toString().toLowerCase()),
                        healthBonus,
                        AttributeModifier.Operation.ADD_VALUE
                ), slotGroup
        );
        attributeBuilder.add(
                Attributes.ARMOR,
                new AttributeModifier(AmberDreams.resource("bond_armor_bonus_" + slotGroup.toString().toLowerCase()),
                        armorBonus,
                        AttributeModifier.Operation.ADD_VALUE
                ), slotGroup
        );


        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, attributeBuilder.build());
    }

    private static EquipmentSlotGroup getEquipmentSlot(ItemStack item) {
        // lol
        if (!(item.getItem() instanceof ArmorItem armorItem)) return EquipmentSlotGroup.OFFHAND;

        switch (armorItem.getType()) {
            case HELMET: {
                return EquipmentSlotGroup.HEAD;
            }
            case CHESTPLATE: {
                return EquipmentSlotGroup.CHEST;
            }
            case LEGGINGS: {
                return EquipmentSlotGroup.LEGS;
            }
            case BOOTS: {
                return EquipmentSlotGroup.FEET;
            }
            default: {
                return EquipmentSlotGroup.ANY;
            }
        }
    }
}
