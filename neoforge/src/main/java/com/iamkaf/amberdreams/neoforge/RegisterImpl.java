package com.iamkaf.amberdreams.neoforge;

import com.iamkaf.amberdreams.AmberDreams;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.util.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class RegisterImpl {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(AmberDreams.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(AmberDreams.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB,
            AmberDreams.MOD_ID
    );
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(
            Registries.DATA_COMPONENT_TYPE,
            AmberDreams.MOD_ID
    );
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIAL = DeferredRegister.create(
            Registries.ARMOR_MATERIAL,
            AmberDreams.MOD_ID
    );
    public static final DeferredRegister<Potion> POTION = DeferredRegister.create(Registries.POTION, AmberDreams.MOD_ID);
    public static final DeferredRegister<Instrument> INSTRUMENT = DeferredRegister.create(Registries.INSTRUMENT, AmberDreams.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(Registries.SOUND_EVENT, AmberDreams.MOD_ID);

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

    public static Supplier<CreativeModeTab> creativeModeTab() {
        var tab = TABS.register(
                "amberdreams_tab",
                () -> CreativeModeTab.builder()
                        .icon(() -> new ItemStack(AmberDreams.Blocks.BISMUTH_BLOCK.get()))
                        .title(Component.translatable("creativetab.amberdreams.amberdreams_tab"))
                        .displayItems((itemDisplayParameters, output) -> {
                            for (var item : AmberDreams.CreativeModeTabs.getCreativeModeTabItems()) {
                                output.accept(item);
                            }
                        })
                        .build()
        );

        return () -> tab.get();
    }

    public static <T> Supplier<DataComponentType<T>> dataComponentType(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(
                name,
                () -> builderOperator.apply(DataComponentType.builder())
                        .build()
        );
    }

    public static Holder<ArmorMaterial> armorMaterial(String name, ArmorMaterial material) {
        var obj = Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, AmberDreams.resource(name), material);
        return obj;
//        return ARMOR_MATERIAL.register(name, () -> material);
    }

    public static Holder<Potion> potion(String id, Supplier<Potion> potion) {
        return POTION.register(id, potion);
    }

    public static final List<KeyMapping> KEY_MAPPINGS = new ArrayList<>();

    public static void keybinding(Lazy<KeyMapping> mapping) {
        KEY_MAPPINGS.add(mapping.get());
    }

    public static Holder<Instrument> instrument(String id, Supplier<Instrument> instrument) {
        return INSTRUMENT.register(id, instrument);
    }

    public static Holder<SoundEvent> soundEvent(String id) {
        return SOUND_EVENT.register(id, () -> SoundEvent.createVariableRangeEvent(AmberDreams.resource(id)));
    }
}
