package com.iamkaf.amberdreams.mixin;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    @Inject(method = "isBarterCurrency", at = @At("HEAD"), cancellable = true)
    private static void isBarterCurrency(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.is(AmberDreams.Items.TEMPERED_GOLD_INGOT.get())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isWearingGold", at = @At("HEAD"), cancellable = true)
    private static void isWearingGold(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        for (ItemStack itemStack : livingEntity.getArmorAndBodyArmorSlots()) {
            Item item = itemStack.getItem();
            if (item instanceof ArmorItem && ((ArmorItem) item).getMaterial()
                    .is(AmberDreams.ArmorMaterials.TEMPERED_GOLD)) {
                cir.setReturnValue(true);
            }
        }
    }
}
