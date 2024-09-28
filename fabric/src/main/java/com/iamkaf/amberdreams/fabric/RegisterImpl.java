package com.iamkaf.amberdreams.fabric;

import com.iamkaf.amberdreams.AmberDreams;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

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

    public static <T extends CreativeModeTab> Supplier<T> creativeModeTab() {
        var obj = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, AmberDreams.resource("amberdreams_tab"), FabricItemGroup.builder().icon(() -> new ItemStack(AmberDreams.Blocks.BISMUTH_BLOCK.get())).title(Component.translatable("creativetab.amberdreams.amberdreams_tab")).displayItems((itemDisplayParameters, output) -> {
            output.accept(AmberDreams.Items.BISMUTH.get());
            output.accept(AmberDreams.Items.RAW_BISMUTH.get());
            output.accept(AmberDreams.Items.CHISEL.get());
            output.accept(AmberDreams.Items.RADISH.get());
            output.accept(AmberDreams.Items.FROSTFIRE_ICE.get());
            output.accept(AmberDreams.Items.STARLIGHT_ASHES.get());
            output.accept(AmberDreams.Blocks.BISMUTH_BLOCK.get());
            output.accept(AmberDreams.Blocks.BISMUTH_ORE.get());
            output.accept(AmberDreams.Blocks.BISMUTH_DEEPSLATE_ORE.get());
            output.accept(AmberDreams.Blocks.MAGIC_BLOCK.get());
        }).build());
        return () -> (T) obj;
    }
}
