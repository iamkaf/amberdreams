package com.iamkaf.amberdreams;

import com.iamkaf.amberdreams.block.MagicBlock;
import com.iamkaf.amberdreams.item.ChiselItem;
import com.iamkaf.amberdreams.item.RadishItem;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;

import java.util.function.Supplier;

public final class AmberDreams {
    public static final String MOD_ID = "amberdreams";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LOGGER.info("Dreams of golden Amber.");

        // Write common init code here.
        Items.init();
        Blocks.init();
        CreativeModeTabs.init();
    }

    /**
     * Creates resource location in the mod namespace with the given path.
     */
    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static class CreativeModeTabs {
        public static final Supplier<CreativeModeTab> CREATIVE_MODE_TAB = Register.creativeModeTab();

        static void init() {
        }
    }

    public static class Items {
        public static final Supplier<Item> BISMUTH = Register.item("bismuth", () -> new Item(new Item.Properties()));
        public static final Supplier<Item> RAW_BISMUTH = Register.item("raw_bismuth", () -> new Item(new Item.Properties()));

        public static final Supplier<Item> CHISEL = Register.item("chisel", () -> new ChiselItem(new Item.Properties().durability(32)));

        public static final Supplier<Item> RADISH = Register.item("radish", () -> new RadishItem(new Item.Properties().food(RadishItem.FOOD_PROPERTIES)));
        public static final Supplier<Item> FROSTFIRE_ICE = Register.item("frostfire_ice", () -> new Item(new Item.Properties()));
        public static final Supplier<Item> STARLIGHT_ASHES = Register.item("starlight_ashes", () -> new Item(new Item.Properties()));

        static void init() {
            Register.fuelItem(FROSTFIRE_ICE, 800);
            Register.fuelItem(STARLIGHT_ASHES, 1200);
        }
    }

    public static class Blocks {
        public static final Supplier<Block> BISMUTH_BLOCK = Register.block("bismuth_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.AMETHYST_BLOCK)));
        public static final Supplier<Block> BISMUTH_ORE = Register.block("bismuth_ore", () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.IRON_ORE)));
        public static final Supplier<Block> BISMUTH_DEEPSLATE_ORE = Register.block("bismuth_deepslate_ore", () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_IRON_ORE)));

        public static final Supplier<Block> MAGIC_BLOCK = Register.block("magic_block", () -> new MagicBlock(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SLIME_BLOCK).sound(SoundType.FUNGUS).noLootTable()));


        static void init() {
        }
    }
}
