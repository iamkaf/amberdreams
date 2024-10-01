package com.iamkaf.amberdreams;

import com.iamkaf.amberdreams.block.BismuthLampBlock;
import com.iamkaf.amberdreams.block.MagicBlock;
import com.iamkaf.amberdreams.item.ChiselItem;
import com.iamkaf.amberdreams.item.RadishItem;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
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

        public static List<ItemLike> getCreativeModeTabItems() {
            List<ItemLike> tabItems = new ArrayList<>();

            tabItems.add(Items.BISMUTH.get());
            tabItems.add(Items.RAW_BISMUTH.get());
            tabItems.add(Items.CHISEL.get());
            tabItems.add(Items.RADISH.get());
            tabItems.add(Items.FROSTFIRE_ICE.get());
            tabItems.add(Items.STARLIGHT_ASHES.get());

            tabItems.add(Blocks.BISMUTH_BLOCK.get());
            tabItems.add(Blocks.BISMUTH_ORE.get());
            tabItems.add(Blocks.BISMUTH_DEEPSLATE_ORE.get());
            tabItems.add(Blocks.BISMUTH_LAMP.get());
            tabItems.add(Blocks.MAGIC_BLOCK.get());
            tabItems.add(Blocks.BISMUTH_STAIRS.get());
            tabItems.add(Blocks.BISMUTH_SLAB.get());
            tabItems.add(Blocks.BISMUTH_PRESSURE_PLATE.get());
            tabItems.add(Blocks.BISMUTH_BUTTON.get());
            tabItems.add(Blocks.BISMUTH_FENCE.get());
            tabItems.add(Blocks.BISMUTH_FENCE_GATE.get());
            tabItems.add(Blocks.BISMUTH_WALL.get());
            tabItems.add(Blocks.BISMUTH_DOOR.get());
            tabItems.add(Blocks.BISMUTH_TRAPDOOR.get());

            return tabItems;
        }

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

        public static final Supplier<Block> MAGIC_BLOCK = Register.block("magic_block", () -> new MagicBlock(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SLIME_BLOCK)
                .sound(SoundType.FUNGUS).noLootTable()));

        public static final Supplier<StairBlock> BISMUTH_STAIRS = Register.block("bismuth_stairs", () -> new StairBlock(BISMUTH_BLOCK.get()
                .defaultBlockState(), BlockBehaviour.Properties.of().strength(2f)
                .requiresCorrectToolForDrops()));
        public static final Supplier<SlabBlock> BISMUTH_SLAB = Register.block("bismuth_slab", () -> new SlabBlock(BlockBehaviour.Properties.of()
                .strength(2f).requiresCorrectToolForDrops()));

        public static final Supplier<PressurePlateBlock> BISMUTH_PRESSURE_PLATE = Register.block("bismuth_pressure_plate", () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.of()
                .strength(2f).requiresCorrectToolForDrops()));
        public static final Supplier<ButtonBlock> BISMUTH_BUTTON = Register.block("bismuth_button", () -> new ButtonBlock(BlockSetType.IRON, 20, BlockBehaviour.Properties.of()
                .strength(2f).requiresCorrectToolForDrops().noCollission()));

        public static final Supplier<FenceBlock> BISMUTH_FENCE = Register.block("bismuth_fence", () -> new FenceBlock(BlockBehaviour.Properties.of()
                .strength(2f).requiresCorrectToolForDrops()));
        public static final Supplier<FenceGateBlock> BISMUTH_FENCE_GATE = Register.block("bismuth_fence_gate", () -> new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of()
                .strength(2f).requiresCorrectToolForDrops()));
        public static final Supplier<WallBlock> BISMUTH_WALL = Register.block("bismuth_wall", () -> new WallBlock(BlockBehaviour.Properties.of()
                .strength(2f).requiresCorrectToolForDrops()));

        public static final Supplier<DoorBlock> BISMUTH_DOOR = Register.block("bismuth_door", () -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of()
                .strength(2f).requiresCorrectToolForDrops().noOcclusion()));
        public static final Supplier<TrapDoorBlock> BISMUTH_TRAPDOOR = Register.block("bismuth_trapdoor", () -> new TrapDoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.of()
                .strength(2f).requiresCorrectToolForDrops().noOcclusion()));

        public static final Supplier<BismuthLampBlock> BISMUTH_LAMP = Register.block("bismuth_lamp", () -> new BismuthLampBlock(BlockBehaviour.Properties.of()
                .strength(2f).requiresCorrectToolForDrops()
                .lightLevel(BismuthLampBlock::getLightLevel)));

        static void init() {
        }
    }
}
