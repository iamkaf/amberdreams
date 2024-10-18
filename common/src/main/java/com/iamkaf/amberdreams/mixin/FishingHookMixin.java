package com.iamkaf.amberdreams.mixin;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin is used to award experience based on fishing.
 */
@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @Shadow
    @Nullable
    public abstract Player getPlayerOwner();

    @Inject(method = "Lnet/minecraft/world/entity/projectile/FishingHook;retrieve" + "(Lnet/minecraft/world"
            + "/item/ItemStack;)I", at = @At("RETURN"))
    public void retrieve(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        var fishingResult = cir.getReturnValue();

        switch (fishingResult) {
            case 0:
                AmberDreams.LOGGER.debug("Fishing result (0): FISHING NO NIBBLE");
                break;
            case 1:
                AmberDreams.LOGGER.debug("Fishing result (1): FISHING SUCCESSFUL");
                var player = getPlayerOwner();
                if (player != null && !player.level().isClientSide) {
                    player.sendSystemMessage(Component.literal("+1 Fishing Exp")
                            .withStyle(ChatFormatting.GREEN));
                }
                break;
            case 2:
                AmberDreams.LOGGER.debug("Fishing result (2): HOOK ON GROUND");
                break;
            case 3:
                AmberDreams.LOGGER.debug("Fishing result (3): FISHED ITEM ENTITY");
                break;
            case 4:
                AmberDreams.LOGGER.debug("Fishing result (4): UNKNOWN");
                break;
            case 5:
                AmberDreams.LOGGER.debug("Fishing result (5): FISHED ENTITY");
                break;
        }
    }
}
