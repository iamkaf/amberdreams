package com.iamkaf.amberdreams.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin is used to decrease the time it takes to fish.
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @Inject(method = "getFishingTimeReduction(Lnet/minecraft/server/level/ServerLevel;" +
            "Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/Entity;)F", at = @At("RETURN")
            , cancellable = true)
    private static void getFishingTimeReduction(ServerLevel level, ItemStack stack, Entity entity,
            CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValueF() + 10);
    }
}
