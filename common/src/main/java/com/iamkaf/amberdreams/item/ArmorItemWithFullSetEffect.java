package com.iamkaf.amberdreams.item;

import com.google.common.collect.ImmutableMap;
import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class ArmorItemWithFullSetEffect extends ArmorItem {
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP = createEffectMap();

    public ArmorItemWithFullSetEffect(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    private static Map<Holder<ArmorMaterial>, List<MobEffectInstance>> createEffectMap() {
        ImmutableMap.Builder<Holder<ArmorMaterial>, List<MobEffectInstance>> builder = new ImmutableMap.Builder<>();

        // Define effects for BISMUTH armor
        List<MobEffectInstance> bismuthEffects = List.of(new MobEffectInstance(MobEffects.JUMP, 200, 1, false, false),
                                                         new MobEffectInstance(MobEffects.GLOWING, 200, 1, false, false)
        );

        builder.put(AmberDreams.ArmorMaterials.BISMUTH, bismuthEffects);

        return builder.build();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && !level.isClientSide() && hasFullSuitOfArmorOn(player)) {
            evaluateArmorEffects(player);
        }
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<Holder<ArmorMaterial>, List<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            Holder<ArmorMaterial> mapArmorMaterial = entry.getKey();
            List<MobEffectInstance> mapEffect = entry.getValue();

            if (hasPlayerCorrectArmorOn(mapArmorMaterial, player)) {
                addEffectToPlayer(player, mapEffect);
            }
        }
    }

    private void addEffectToPlayer(Player player, List<MobEffectInstance> mapEffect) {
        boolean hasPlayerEffect = mapEffect.stream().allMatch(effect -> player.hasEffect(effect.getEffect()));

        if (hasPlayerEffect) return;

        for (MobEffectInstance effect : mapEffect) {
            player.addEffect(
                    new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.isVisible()));
        }
    }

    private boolean hasPlayerCorrectArmorOn(Holder<ArmorMaterial> mapArmorMaterial, Player player) {
        for (ItemStack armorStack : player.getArmorSlots()) {
            Item armorPiece = armorStack.getItem();
            if (!(armorPiece instanceof ArmorItem)) {
                return false;
            }
            if (((ArmorItem) armorPiece).getMaterial() != mapArmorMaterial) {
                return false;
            }
        }
        return true;
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        for (var armorPiece : player.getArmorSlots()) {
            if (armorPiece.isEmpty()) return false;
        }
        return true;
    }
}
