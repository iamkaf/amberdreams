package com.iamkaf.amberdreams.neoforge;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterImpl {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(AmberDreams.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(AmberDreams.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AmberDreams.MOD_ID);

    public static <T extends Block> Supplier<T> block(String id, Supplier<T> supplier) {
        var block = BLOCKS.register(id, supplier);
        item(id, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static <T extends Item> Supplier<T> item(String id, Supplier<T> supplier) {
        return ITEMS.register(id, supplier);
    }

    public static <T extends Item> void fuelItem(Supplier<T> supplier, int burnTime) {
        // NO-OP, done in datapack
    }

    public static <T extends CreativeModeTab> Supplier<T> creativeModeTab() {
        var tab = TABS.register("amberdreams_tab", () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(AmberDreams.Blocks.BISMUTH_BLOCK.get()))
                .title(Component.translatable("creativetab.amberdreams.amberdreams_tab"))
                .displayItems((itemDisplayParameters, output) -> {
                    for (var item : AmberDreams.CreativeModeTabs.getCreativeModeTabItems()) {
                        output.accept(item);
                    }
                }).build());
        return () -> (T) tab.get();
    }
}
