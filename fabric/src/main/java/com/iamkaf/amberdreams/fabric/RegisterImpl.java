package com.iamkaf.amberdreams.fabric;

import com.iamkaf.amberdreams.AmberDreams;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class RegisterImpl {

    public static <T extends Block> Supplier<T> block(String id, Supplier<T> supplier) {
        var obj = Registry.register(BuiltInRegistries.BLOCK, AmberDreams.resource(id), supplier.get());
        item(id, () -> new BlockItem(obj, new Item.Properties()));
        return () -> obj;
    }

    public static <T extends Item> Supplier<T> item(String id, Supplier<T> supplier) {
        var obj = Registry.register(BuiltInRegistries.ITEM, AmberDreams.resource(id), supplier.get());
        return () -> obj;
    }

    public static <T extends Item> void fuelItem(Supplier<T> supplier, int burnTime) {
        FuelRegistry.INSTANCE.add(supplier.get(), burnTime);
    }

    public static Supplier<CreativeModeTab> creativeModeTab() {
        var obj = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, AmberDreams.resource("amberdreams_tab"), FabricItemGroup.builder()
                .icon(() -> new ItemStack(AmberDreams.Blocks.BISMUTH_BLOCK.get()))
                .title(Component.translatable("creativetab.amberdreams.amberdreams_tab"))
                .displayItems((itemDisplayParameters, output) -> {
                    for (var item : AmberDreams.CreativeModeTabs.getCreativeModeTabItems()) {
                        output.accept(item);
                    }
                }).build());
        return () -> obj;
    }

    public static <T> Supplier<DataComponentType<T>> dataComponentType(String name,
                                                              UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        var obj = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
                AmberDreams.resource(name), builderOperator.apply(DataComponentType.builder())
                .build());
        return () -> obj;
    }
}
