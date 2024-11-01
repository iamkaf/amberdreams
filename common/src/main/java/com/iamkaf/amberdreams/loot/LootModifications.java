package com.iamkaf.amberdreams.loot;

import com.iamkaf.amberdreams.AmberDreams;
import dev.architectury.event.events.common.LootEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class LootModifications {
    private static final ResourceKey<LootTable> GRASS = Blocks.NETHER_GOLD_ORE.getLootTable();

    static {
        LootEvent.MODIFY_LOOT_TABLE.register((key, context, builtin) -> {
            if (builtin && (GRASS.equals(key))) {
                modifyNetherGoldOre(context);
            }
        });
    }

    private static void modifyNetherGoldOre(LootEvent.LootTableModificationContext context) {
        // FIXME: i can't figure out how to add the silk touch condition to this, so im commenting it for now
//        LootPool.Builder pool = LootPool.lootPool()
//                .setRolls(ConstantValue.exactly(1))
//                .when(LootItemRandomChanceCondition.randomChance(0.5f))
//                .add(LootItem.lootTableItem(AmberDreams.Items.TEMPERED_GOLD_NUGGET.get()))
//                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f)))
//                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)));
//        context.addPool(pool);
    }

    public static void init() {

    }
}
