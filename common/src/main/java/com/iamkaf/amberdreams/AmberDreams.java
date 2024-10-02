package com.iamkaf.amberdreams;

import com.iamkaf.amberdreams.block.BismuthLampBlock;
import com.iamkaf.amberdreams.block.MagicBlock;
import com.iamkaf.amberdreams.event.Events;
import com.iamkaf.amberdreams.item.*;
import com.iamkaf.amberdreams.tags.Tags;
import com.mojang.logging.LogUtils;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.utils.value.IntValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class AmberDreams {
    public static final String MOD_ID = "amberdreams";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LOGGER.info("Dreams of golden Amber.");

        // Common init code here.
        Items.init();
        Blocks.init();
        CreativeModeTabs.init();
        DataComponents.init();
        ToolTiers.init();

        // Events
        BlockEvent.BREAK.register((Level level, BlockPos pos, BlockState state, ServerPlayer player, @Nullable IntValue xp) -> {
            Events.onHammerUsage(player, level, pos);
            return EventResult.pass();
        });
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

            tabItems.add(Items.BISMUTH_SWORD.get());
            tabItems.add(Items.BISMUTH_PICKAXE.get());
            tabItems.add(Items.BISMUTH_SHOVEL.get());
            tabItems.add(Items.BISMUTH_AXE.get());
            tabItems.add(Items.BISMUTH_HOE.get());
            tabItems.add(Items.BISMUTH_HAMMER.get());

            tabItems.add(Items.BISMUTH_HELMET.get());
            tabItems.add(Items.BISMUTH_CHESTPLATE.get());
            tabItems.add(Items.BISMUTH_LEGGINGS.get());
            tabItems.add(Items.BISMUTH_BOOTS.get());

            return tabItems;
        }

        static void init() {
        }
    }

    public static class Items {
        public static final Supplier<Item> BISMUTH = Register.item("bismuth", () -> new Item(new Item.Properties()));
        public static final Supplier<Item> RAW_BISMUTH = Register.item("raw_bismuth", () -> new Item(new Item.Properties()));

        public static final Supplier<Item> CHISEL = Register.item("chisel",
                () -> new ChiselItem(new Item.Properties().durability(32))
        );

        public static final Supplier<Item> RADISH = Register.item("radish",
                () -> new RadishItem(new Item.Properties().food(RadishItem.FOOD_PROPERTIES))
        );
        public static final Supplier<Item> FROSTFIRE_ICE = Register.item("frostfire_ice", () -> new Item(new Item.Properties()));
        public static final Supplier<Item> STARLIGHT_ASHES = Register.item("starlight_ashes",
                () -> new Item(new Item.Properties())
        );

        public static final Supplier<SwordItem> BISMUTH_SWORD = Register.item("bismuth_sword",
                () -> new SwordItem(ToolTiers.BISMUTH,
                        new Item.Properties().attributes(SwordItem.createAttributes(ToolTiers.BISMUTH, 5, -2.4f))
                )
        );
        public static final Supplier<PickaxeItem> BISMUTH_PICKAXE = Register.item("bismuth_pickaxe", () -> new PickaxeItem(
                ToolTiers.BISMUTH,
                new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTiers.BISMUTH, 1.0F, -2.8f))
        ));
        public static final Supplier<ShovelItem> BISMUTH_SHOVEL = Register.item("bismuth_shovel",
                () -> new ShovelItem(ToolTiers.BISMUTH,
                        new Item.Properties().attributes(ShovelItem.createAttributes(ToolTiers.BISMUTH, 1.5F, -3.0f))
                )
        );
        public static final Supplier<AxeItem> BISMUTH_AXE = Register.item("bismuth_axe",
                () -> new AxeItem(ToolTiers.BISMUTH,
                        new Item.Properties().attributes(AxeItem.createAttributes(ToolTiers.BISMUTH, 6.0F, -3.2f))
                )
        );
        public static final Supplier<HoeItem> BISMUTH_HOE = Register.item("bismuth_hoe",
                () -> new HoeItem(ToolTiers.BISMUTH,
                        new Item.Properties().attributes(HoeItem.createAttributes(ToolTiers.BISMUTH, 0F, -3.0f))
                )
        );
        public static final Supplier<HammerItem> BISMUTH_HAMMER = Register.item("bismuth_hammer",
                () -> new HammerItem(ToolTiers.BISMUTH,
                        new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTiers.BISMUTH, 7F, -3.5f))
                )
        );

        public static final Supplier<ArmorItem> BISMUTH_HELMET = Register.item("bismuth_helmet", () -> new ArmorItem(
                ArmorMaterials.BISMUTH,
                ArmorItem.Type.HELMET,
                new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(ArmorMaterials.BISMUTH_DURABILITY_MULTIPLIER))
        ));
        public static final Supplier<ArmorItem> BISMUTH_CHESTPLATE = Register.item("bismuth_chestplate", () -> new ArmorItem(
                ArmorMaterials.BISMUTH,
                ArmorItem.Type.CHESTPLATE,
                new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(ArmorMaterials.BISMUTH_DURABILITY_MULTIPLIER))
        ));
        public static final Supplier<ArmorItem> BISMUTH_LEGGINGS = Register.item("bismuth_leggings", () -> new ArmorItem(
                ArmorMaterials.BISMUTH,
                ArmorItem.Type.LEGGINGS,
                new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(ArmorMaterials.BISMUTH_DURABILITY_MULTIPLIER))
        ));
        public static final Supplier<ArmorItem> BISMUTH_BOOTS = Register.item("bismuth_boots",
                () -> new ArmorItem(ArmorMaterials.BISMUTH,
                        ArmorItem.Type.BOOTS,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(ArmorMaterials.BISMUTH_DURABILITY_MULTIPLIER))
                )
        );

        static void init() {
            Register.fuelItem(FROSTFIRE_ICE, 800);
            Register.fuelItem(STARLIGHT_ASHES, 1200);
        }
    }

    public static class Blocks {
        public static final Supplier<Block> BISMUTH_BLOCK = Register.block("bismuth_block",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.AMETHYST_BLOCK))
        );
        public static final Supplier<Block> BISMUTH_ORE = Register.block("bismuth_ore",
                () -> new DropExperienceBlock(UniformInt.of(2, 4),
                        BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.IRON_ORE)
                )
        );
        public static final Supplier<Block> BISMUTH_DEEPSLATE_ORE = Register.block("bismuth_deepslate_ore",
                () -> new DropExperienceBlock(UniformInt.of(2, 4),
                        BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_IRON_ORE)
                )
        );

        public static final Supplier<Block> MAGIC_BLOCK = Register.block("magic_block",
                () -> new MagicBlock(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SLIME_BLOCK)
                        .sound(SoundType.FUNGUS)
                        .noLootTable())
        );

        public static final Supplier<StairBlock> BISMUTH_STAIRS = Register.block("bismuth_stairs", () -> new StairBlock(
                BISMUTH_BLOCK.get()
                        .defaultBlockState(),
                BlockBehaviour.Properties.of()
                        .strength(2f)
                        .requiresCorrectToolForDrops()
        ));
        public static final Supplier<SlabBlock> BISMUTH_SLAB = Register.block("bismuth_slab",
                () -> new SlabBlock(BlockBehaviour.Properties.of()
                        .strength(2f)
                        .requiresCorrectToolForDrops())
        );

        public static final Supplier<PressurePlateBlock> BISMUTH_PRESSURE_PLATE = Register.block("bismuth_pressure_plate",
                () -> new PressurePlateBlock(BlockSetType.IRON,
                        BlockBehaviour.Properties.of()
                                .strength(2f)
                                .requiresCorrectToolForDrops()
                )
        );
        public static final Supplier<ButtonBlock> BISMUTH_BUTTON = Register.block("bismuth_button", () -> new ButtonBlock(
                BlockSetType.IRON,
                20,
                BlockBehaviour.Properties.of()
                        .strength(2f)
                        .requiresCorrectToolForDrops()
                        .noCollission()
        ));

        public static final Supplier<FenceBlock> BISMUTH_FENCE = Register.block("bismuth_fence",
                () -> new FenceBlock(BlockBehaviour.Properties.of()
                        .strength(2f)
                        .requiresCorrectToolForDrops())
        );
        public static final Supplier<FenceGateBlock> BISMUTH_FENCE_GATE = Register.block("bismuth_fence_gate",
                () -> new FenceGateBlock(WoodType.ACACIA,
                        BlockBehaviour.Properties.of()
                                .strength(2f)
                                .requiresCorrectToolForDrops()
                )
        );
        public static final Supplier<WallBlock> BISMUTH_WALL = Register.block("bismuth_wall",
                () -> new WallBlock(BlockBehaviour.Properties.of()
                        .strength(2f)
                        .requiresCorrectToolForDrops())
        );

        public static final Supplier<DoorBlock> BISMUTH_DOOR = Register.block("bismuth_door",
                () -> new DoorBlock(BlockSetType.IRON,
                        BlockBehaviour.Properties.of()
                                .strength(2f)
                                .requiresCorrectToolForDrops()
                                .noOcclusion()
                )
        );
        public static final Supplier<TrapDoorBlock> BISMUTH_TRAPDOOR = Register.block("bismuth_trapdoor", () -> new TrapDoorBlock(
                BlockSetType.IRON,
                BlockBehaviour.Properties.of()
                        .strength(2f)
                        .requiresCorrectToolForDrops()
                        .noOcclusion()
        ));

        public static final Supplier<BismuthLampBlock> BISMUTH_LAMP = Register.block("bismuth_lamp",
                () -> new BismuthLampBlock(BlockBehaviour.Properties.of()
                        .strength(2f)
                        .requiresCorrectToolForDrops()
                        .lightLevel(BismuthLampBlock::getLightLevel))
        );

        static void init() {
        }
    }

    public static class ToolTiers {
        public static final ToolTier BISMUTH = new ToolTier(Tags.Blocks.INCORRECT_FOR_BISMUTH_TOOL,
                1400,
                4f,
                3f,
                28,
                TierHelper.repair(Items.BISMUTH)
        );

        static void init() {
        }
    }

    public static class ArmorMaterials {
        public static final Holder<ArmorMaterial> BISMUTH = Register.armorMaterial("bismuth",
                new ArmorMaterial(TierHelper.defense(2, 4, 6, 2, 4),
                        TierHelper.VanillaEnchantability.GOLD.enchantability - 5,
                        SoundEvents.ARMOR_EQUIP_GENERIC,
                        TierHelper.repair(Items.BISMUTH),
                        TierHelper.genericLayers("bismuth"),
                        TierHelper.VanillaToughness.IRON.chestplate,
                        TierHelper.VanillaKnockbackResistance.IRON.knockbackResistance
                )
        );
        public static final int BISMUTH_DURABILITY_MULTIPLIER = 15;

        static void init() {
        }
    }
}
