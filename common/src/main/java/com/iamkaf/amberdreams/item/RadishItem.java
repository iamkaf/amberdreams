package com.iamkaf.amberdreams.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class RadishItem extends Item {
    public static final FoodProperties FOOD_PROPERTIES = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 400), 0.35f).build();

    public RadishItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("You're looking radish today!").withStyle(ChatFormatting.GREEN));
    }
}
