package com.iamkaf.amberdreams.loot;

import dev.architectury.event.events.common.LootEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;

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
//                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f)));
//        context.addPool(pool);
    }

    public static void init() {

    }
}
