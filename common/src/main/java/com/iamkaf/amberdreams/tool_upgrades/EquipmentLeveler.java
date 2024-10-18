package com.iamkaf.amberdreams.tool_upgrades;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class EquipmentLeveler {
    public static final Integer MAXIMUM_LEVEL = 10;
    public static final Integer DEFAULT_MAX_EXPERIENCE_FOR_UNKNOWN_TIERS = 1000;

    public static boolean isEligibleForLeveling(ItemStack stack) {
        var item = stack.getItem();

        return item instanceof TieredItem || item instanceof ArmorItem || item instanceof ShieldItem || item instanceof TridentItem || item instanceof ProjectileWeaponItem;
    }

    public static void initLevelingComponentIfNeeded(ItemStack stack) {
        var levelingComponent = AmberDreams.DataComponents.ITEM_EXPERIENCE.get();

        if (stack.has(levelingComponent)) {
            return;
        }

        int maxExperience = DEFAULT_MAX_EXPERIENCE_FOR_UNKNOWN_TIERS;
        if (stack.getItem() instanceof ArmorItem armorItem) {
            maxExperience = ArmorLeveler.getMaxExperienceForMaterial(armorItem.getMaterial().value());
        }
        if (stack.getItem() instanceof TieredItem toolItem) {
            maxExperience = ToolLeveler.getMaxExperienceForTier(toolItem.getTier());
        }
        if (stack.getItem() instanceof ShieldItem) {
            // TODO: need to review this if we decide to add support for modded shields
            maxExperience = ArmorLeveler.getMaxExperienceForMaterial(ArmorMaterials.IRON.value());
        }

        stack.set(levelingComponent, ItemLevelDataComponent.make(maxExperience));
    }

    public static void itemLeveledFeedback(Level level, Player player, ItemStack handItem) {
        var itemLevel = handItem.get(AmberDreams.DataComponents.ITEM_EXPERIENCE.get()).getLevel();

        level.playSound(null,
                player.getX(),
                player.getY(),
                player.getZ(),
                itemLevel == MAXIMUM_LEVEL ? SoundEvents.PLAYER_LEVELUP : SoundEvents.EXPERIENCE_ORB_PICKUP,
                SoundSource.PLAYERS
        );

        String message =
                (itemLevel == MAXIMUM_LEVEL ? "§eMax Level! §f" : "§aLevel Up! §f") + handItem.getDisplayName()
                        .getString() + " §6+" + itemLevel;

        player.sendSystemMessage(Component.literal(message));
        var currentDmg = handItem.getDamageValue();
        handItem.setDamageValue(currentDmg / 2);
    }

    public static boolean isUpgradable(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            ArmorMaterial material = armorItem.getMaterial().value();
            var upgradeMaterial = ArmorUpgrader.upgradeMap.get(material);
            return upgradeMaterial != null;

        }
        if (stack.getItem() instanceof TieredItem tieredItem) {
            Tier tier = tieredItem.getTier();
            var upgradeTier = ToolUpgrader.upgradeMap.get(tier);
            return upgradeTier != null;
        }

        return false;
    }

    public static boolean hasEnoughLevelsToUpgrade(ItemStack tool) {
        var levelingComponent = AmberDreams.DataComponents.ITEM_EXPERIENCE.get();

        var leveling = tool.get(levelingComponent);
        if (leveling == null) {
            return false;
        }

        return leveling.getLevel() >= MAXIMUM_LEVEL;
    }

    public static Ingredient getRepairIngredient(ItemStack stack) {
        Ingredient ingredient = Ingredient.EMPTY;
        if (stack.getItem() instanceof ArmorItem armorItem) {
            ingredient = armorItem.getMaterial().value().repairIngredient().get();
        }
        if (stack.getItem() instanceof TieredItem tieredItem) {
            ingredient = tieredItem.getTier().getRepairIngredient();
        }
        if (stack.getItem() instanceof ShieldItem) {
            // TODO: need to review this if we decide to add support for modded shields
            ingredient = Ingredient.of(Items.IRON_INGOT);
        }
        if (stack.getItem() instanceof TridentItem) {
            ingredient = Ingredient.of(Items.PRISMARINE_SHARD);
        }

        return ingredient;
    }

    public static Ingredient getUpgradeIngredient(ItemStack stack) {
        Ingredient ingredient = Ingredient.EMPTY;
        if (stack.getItem() instanceof ArmorItem armorItem) {
            var nextTier = ArmorUpgrader.getNextTier(stack);
            if (nextTier == null) return null;
            ingredient = nextTier.repairIngredient().get();
        }
        if (stack.getItem() instanceof TieredItem tieredItem) {
            var nextTier = ToolUpgrader.getNextTier(stack);
            if (nextTier == null) return null;
            ingredient = nextTier.getRepairIngredient();

        }
        return ingredient;
    }

    public static ItemStack upgrade(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armorItem) {
            return ArmorUpgrader.upgrade(stack);
        }
        if (stack.getItem() instanceof TieredItem tieredItem) {
            return ToolUpgrader.upgrade(stack);
        }
        return stack;
    }

    static boolean hasEnoughExpToLevel(ItemLevelDataComponent component) {
        if (component == null) {
            return false;
        }

        return component.getExperience() >= component.getMaxExperience();
    }

    public static boolean giveItemExperience(ItemStack item, int amount) {
        if (item == null || item.isEmpty()) {
            return false;
        }

        var levelingComponent = AmberDreams.DataComponents.ITEM_EXPERIENCE.get();
        if (!item.has(levelingComponent)) {
            item.set(levelingComponent,
                    ItemLevelDataComponent.make(DEFAULT_MAX_EXPERIENCE_FOR_UNKNOWN_TIERS)
            );
        }

//        applyBondBonusModifiers(armor);

        ItemLevelDataComponent currentComponent = item.get(levelingComponent);

        if (currentComponent.getLevel() == MAXIMUM_LEVEL && currentComponent.getExperience() < currentComponent.getMaxExperience()) {
            // armor is done leveling
            return false;
        }

        var newComponent = Objects.requireNonNull(currentComponent).addExperience(amount).addBond(amount);
        if (hasEnoughExpToLevel(newComponent)) {
            item.set(levelingComponent, newComponent.addLevel(DEFAULT_MAX_EXPERIENCE_FOR_UNKNOWN_TIERS));
//            applyBondBonusModifiers(armor);
            return true;
        } else {
            item.set(levelingComponent, newComponent);
//            applyBondBonusModifiers(armor);
        }
        return false;
    }
}
