package com.iamkaf.amberdreams.util;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;

public class EnchantmentUtils {
    public static boolean containsEnchantment(ItemStack stack, ResourceLocation enchantment) {
        if (!stack.isEnchanted() || stack.getEnchantments().isEmpty()) {
            return false;
        }

        final boolean[] hasEnchantment = {false};

        stack.getEnchantments().entrySet().forEach(holderEntry -> {
            var isTheEnchantment =
                    holderEntry.getKey().is(enchantment);
            if (isTheEnchantment && !hasEnchantment[0]) {
                hasEnchantment[0] = true;
            }
        });
        return hasEnchantment[0];
    }
}
