package com.iamkaf.amberdreams;

import com.iamkaf.amberdreams.block.BismuthLampBlock;
import com.iamkaf.amberdreams.block.MagicBlock;
import com.iamkaf.amberdreams.event.*;
import com.iamkaf.amberdreams.item.*;
import com.iamkaf.amberdreams.loot.LootModifications;
import com.iamkaf.amberdreams.registry.CreativeTabRegistryHolder;
import com.iamkaf.amberdreams.tool_upgrades.ItemLevelDataComponent;
import com.mojang.logging.LogUtils;
import dev.architectury.event.EventResult;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.slf4j.Logger;

import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public final class AmberDreams {
    public static final String MOD_ID = "amberdreams";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LOGGER.info("Dreams of golden Amber.");

        // Common init code here.
        Items.init();
        Blocks.init();
        CreativeModeTabs.init();
        DataComponents.init();
        LootModifications.init();

        // Events
        OnHammerUsage.init();
        OnBlockBreak.init();
        OnEntityHurt.init();
        OnClickOnRepairBench.init();
        OnClickOnToolBench.init();
        OnCraftItem.init();
//        OnPlayerJoin.init();
//        OnRespawn.init();
        OnBrittleyBlockCollapse.init();
        OnBottleUsedOnLavaCauldron.init();
        OnNetherGoldMined.init();
    }

    /**
     * Creates resource location in the mod namespace with the given path.
     */
    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static class CreativeModeTabs {
        public static final Supplier<CreativeModeTab> CREATIVE_MODE_TAB = Register.creativeModeTab();
        public static final CreativeTabRegistryHolder TAB = new CreativeTabRegistryHolder();

        public static List<ItemLike> getCreativeModeTabItems() {
            return TAB.getItems();
        }

        static void init() {
        }
    }

    public static class Items {
        public static final Supplier<Item> BISMUTH =
                CreativeModeTabs.TAB.add(Register.item("bismuth", () -> new Item(new Item.Properties())));
        public static final Supplier<Item> RAW_BISMUTH =
                CreativeModeTabs.TAB.add(Register.item("raw_bismuth", () -> new Item(new Item.Properties())));

        public static final Supplier<Item> CHISEL = CreativeModeTabs.TAB.add(Register.item("chisel",
                () -> new ChiselItem(new Item.Properties().durability(32))
        ));

        public static final Supplier<Item> RADISH = CreativeModeTabs.TAB.add(Register.item("radish",
                () -> new RadishItem(new Item.Properties().food(RadishItem.FOOD_PROPERTIES))
        ));
        public static final Supplier<Item> FROSTFIRE_ICE = CreativeModeTabs.TAB.add(Register.item(
                "frostfire_ice",
                () -> new Item(new Item.Properties())
        ));
        public static final Supplier<Item> STARLIGHT_ASHES = CreativeModeTabs.TAB.add(Register.item(
                "starlight_ashes",
                () -> new Item(new Item.Properties())
        ));

        public static final Supplier<SwordItem> BISMUTH_SWORD = CreativeModeTabs.TAB.add(Register.item(
                "bismuth_sword",
                () -> new SwordItem(ToolTier.BISMUTH,
                        new Item.Properties().attributes(SwordItem.createAttributes(ToolTier.BISMUTH,
                                5,
                                -2.4f
                        ))
                )
        ));
        public static final Supplier<PickaxeItem> BISMUTH_PICKAXE = CreativeModeTabs.TAB.add(Register.item(
                "bismuth_pickaxe",
                () -> new PickaxeItem(ToolTier.BISMUTH,
                        new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTier.BISMUTH,
                                1.0F,
                                -2.8f
                        ))
                )
        ));
        public static final Supplier<ShovelItem> BISMUTH_SHOVEL = CreativeModeTabs.TAB.add(Register.item(
                "bismuth_shovel",
                () -> new ShovelItem(ToolTier.BISMUTH,
                        new Item.Properties().attributes(ShovelItem.createAttributes(ToolTier.BISMUTH,
                                1.5F,
                                -3.0f
                        ))
                )
        ));
        public static final Supplier<AxeItem> BISMUTH_AXE = CreativeModeTabs.TAB.add(Register.item(
                "bismuth_axe",
                () -> new AxeItem(ToolTier.BISMUTH,
                        new Item.Properties().attributes(AxeItem.createAttributes(ToolTier.BISMUTH,
                                6.0F,
                                -3.2f
                        ))
                )
        ));
        public static final Supplier<HoeItem> BISMUTH_HOE = CreativeModeTabs.TAB.add(Register.item(
                "bismuth_hoe",
                () -> new HoeItem(ToolTier.BISMUTH,
                        new Item.Properties().attributes(HoeItem.createAttributes(ToolTier.BISMUTH,
                                0F,
                                -3.0f
                        ))
                )
        ));
        public static final Supplier<HammerItem> BISMUTH_HAMMER = CreativeModeTabs.TAB.add(Register.item(
                "bismuth_hammer",
                () -> new HammerItem(ToolTier.BISMUTH,
                        new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTier.BISMUTH,
                                7F,
                                -3.5f
                        ))
                )
        ));

        public static final Supplier<ArmorItem> BISMUTH_HELMET =
                CreativeModeTabs.TAB.add(Register.item("bismuth_helmet", () -> new ArmorItemWithFullSetEffect(
                        ArmorMaterials.BISMUTH,
                        ArmorItem.Type.HELMET,
                        new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(ArmorMaterials.BISMUTH_DURABILITY_MULTIPLIER))
                )));
        public static final Supplier<ArmorItem> BISMUTH_CHESTPLATE =
                CreativeModeTabs.TAB.add(Register.item("bismuth_chestplate", () -> new ArmorItem(
                        ArmorMaterials.BISMUTH,
                        ArmorItem.Type.CHESTPLATE,
                        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(
                                ArmorMaterials.BISMUTH_DURABILITY_MULTIPLIER))
                )));
        public static final Supplier<ArmorItem> BISMUTH_LEGGINGS = CreativeModeTabs.TAB.add(Register.item(
                "bismuth_leggings",
                () -> new ArmorItem(ArmorMaterials.BISMUTH,
                        ArmorItem.Type.LEGGINGS,
                        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(ArmorMaterials.BISMUTH_DURABILITY_MULTIPLIER))
                )
        ));
        public static final Supplier<ArmorItem> BISMUTH_BOOTS = CreativeModeTabs.TAB.add(Register.item(
                "bismuth_boots",
                () -> new ArmorItem(ArmorMaterials.BISMUTH,
                        ArmorItem.Type.BOOTS,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(ArmorMaterials.BISMUTH_DURABILITY_MULTIPLIER))
                )
        ));

        public static final Supplier<Item> BISMUTH_HORSE_ARMOR =
                CreativeModeTabs.TAB.add(Register.item("bismuth_horse_armor", () -> new AnimalArmorItem(
                        ArmorMaterials.BISMUTH,
                        AnimalArmorItem.BodyType.EQUESTRIAN,
                        false,
                        new Item.Properties().stacksTo(1)
                )));

        public static final Supplier<Item> BOTTLE_O_HOTSTUFF = CreativeModeTabs.TAB.add(Register.item(
                "bottleohotstuff",
                () -> new Item(new Item.Properties().stacksTo(1)
                        .fireResistant()
                        .craftRemainder(GLASS_BOTTLE)
                        .food(new FoodProperties.Builder().nutrition(1)
                                .saturationModifier(0.1F)
                                .usingConvertsTo(GLASS_BOTTLE)
                                .alwaysEdible()
                                .build())) {
                    @Override
                    public ItemStack finishUsingItem(ItemStack stack, Level level,
                            LivingEntity livingEntity) {
                        // muahahahahahaha >:D
                        if (!livingEntity.fireImmune()) {
                            livingEntity.igniteForSeconds(4);
                        }
                        return super.finishUsingItem(stack, level, livingEntity);
                    }

                    @Override
                    public SoundEvent getDrinkingSound() {
                        return SoundEvents.HONEY_DRINK;
                    }

                    @Override
                    public SoundEvent getEatingSound() {
                        return SoundEvents.HONEY_DRINK;
                    }

                    @Override
                    public void appendHoverText(ItemStack stack, TooltipContext context,
                            List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                        tooltipComponents.add(Component.literal("Hot!").withStyle(ChatFormatting.GOLD));
                        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                    }
                }
        ));
        public static final Supplier<SwordItem> TEMPERED_GOLD_SWORD = CreativeModeTabs.TAB.add(Register.item(
                "tempered_gold_sword",
                () -> new SwordItem(ToolTier.TEMPERED_GOLD,
                        new Item.Properties().attributes(SwordItem.createAttributes(ToolTier.TEMPERED_GOLD,
                                2,
                                -2.4f
                        ))
                )
        ));
        public static final Supplier<PickaxeItem> TEMPERED_GOLD_PICKAXE =
                CreativeModeTabs.TAB.add(Register.item("tempered_gold_pickaxe",
                        () -> new PickaxeItem(ToolTier.TEMPERED_GOLD,
                                new Item.Properties().attributes(PickaxeItem.createAttributes(ToolTier.TEMPERED_GOLD,
                                        0.0F,
                                        -2.8f
                                ))
                        )
                ));
        public static final Supplier<ShovelItem> TEMPERED_GOLD_SHOVEL =
                CreativeModeTabs.TAB.add(Register.item("tempered_gold_shovel",
                        () -> new ShovelItem(ToolTier.TEMPERED_GOLD,
                                new Item.Properties().attributes(ShovelItem.createAttributes(ToolTier.TEMPERED_GOLD,
                                        0.5F,
                                        -3.0f
                                ))
                        )
                ));
        public static final Supplier<AxeItem> TEMPERED_GOLD_AXE = CreativeModeTabs.TAB.add(Register.item(
                "tempered_gold_axe",
                () -> new AxeItem(ToolTier.TEMPERED_GOLD,
                        new Item.Properties().attributes(AxeItem.createAttributes(ToolTier.TEMPERED_GOLD,
                                3.0F,
                                -3.0f
                        ))
                )
        ));
        public static final Supplier<HoeItem> TEMPERED_GOLD_HOE = CreativeModeTabs.TAB.add(Register.item(
                "tempered_gold_hoe",
                () -> new HoeItem(ToolTier.TEMPERED_GOLD,
                        new Item.Properties().attributes(HoeItem.createAttributes(ToolTier.TEMPERED_GOLD,
                                -3F,
                                -1.0f
                        ))
                )
        ));
        public static final Supplier<ArmorItem> TEMPERED_GOLD_HELMET =
                CreativeModeTabs.TAB.add(Register.item("tempered_gold_helmet", () -> new ArmorItem(
                        ArmorMaterials.TEMPERED_GOLD,
                        ArmorItem.Type.HELMET,
                        new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(ArmorMaterials.TEMPERED_GOLD_DURABILITY_MULTIPLIER))
                )));
        public static final Supplier<ArmorItem> TEMPERED_GOLD_CHESTPLATE =
                CreativeModeTabs.TAB.add(Register.item("tempered_gold_chestplate", () -> new ArmorItem(
                        ArmorMaterials.TEMPERED_GOLD,
                        ArmorItem.Type.CHESTPLATE,
                        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(
                                ArmorMaterials.TEMPERED_GOLD_DURABILITY_MULTIPLIER))
                )));
        public static final Supplier<ArmorItem> TEMPERED_GOLD_LEGGINGS =
                CreativeModeTabs.TAB.add(Register.item("tempered_gold_leggings", () -> new ArmorItem(
                        ArmorMaterials.TEMPERED_GOLD,
                        ArmorItem.Type.LEGGINGS,
                        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(ArmorMaterials.TEMPERED_GOLD_DURABILITY_MULTIPLIER))
                )));
        public static final Supplier<ArmorItem> TEMPERED_GOLD_BOOTS =
                CreativeModeTabs.TAB.add(Register.item("tempered_gold_boots", () -> new ArmorItem(
                        ArmorMaterials.TEMPERED_GOLD,
                        ArmorItem.Type.BOOTS,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(ArmorMaterials.TEMPERED_GOLD_DURABILITY_MULTIPLIER))
                )));
        public static Supplier<Item> TEMPERED_GOLD_INGOT = CreativeModeTabs.TAB.add(Register.item(
                "tempered_gold_ingot",
                () -> new Item(new Item.Properties()) {
                    @Override
                    public InteractionResultHolder<ItemStack> use(Level level, Player player,
                            InteractionHand usedHand) {
                        player.getItemInHand(usedHand)
                                .getTags()
                                .forEach(itemTagKey -> player.sendSystemMessage(Component.literal(itemTagKey.toString())));
                        return super.use(level, player, usedHand);
                    }
                }
        ));
        public static Supplier<Item> TEMPERED_GOLD_NUGGET = CreativeModeTabs.TAB.add(Register.item(
                "tempered_gold_nugget",
                () -> new Item(new Item.Properties())
        ));

        static void init() {
            Register.fuelItem(FROSTFIRE_ICE, 800);
            Register.fuelItem(STARLIGHT_ASHES, 1200);
        }
    }

    public static class Blocks {
        public static final Supplier<Block> BISMUTH_BLOCK = CreativeModeTabs.TAB.add(Register.block(
                "bismuth_block",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.AMETHYST_BLOCK))
        ));
        public static final Supplier<Block> BISMUTH_ORE =
                CreativeModeTabs.TAB.add(Register.block("bismuth_ore", () -> new DropExperienceBlock(
                        UniformInt.of(2, 4),
                        BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.IRON_ORE)
                )));
        public static final Supplier<Block> BISMUTH_DEEPSLATE_ORE = CreativeModeTabs.TAB.add(Register.block(
                "bismuth_deepslate_ore",
                () -> new DropExperienceBlock(UniformInt.of(2, 4),
                        BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.DEEPSLATE_IRON_ORE)
                )
        ));

        public static final Supplier<Block> MAGIC_BLOCK = CreativeModeTabs.TAB.add(Register.block(
                "magic_block",
                () -> new MagicBlock(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.SLIME_BLOCK)
                        .sound(SoundType.FUNGUS)
                        .noLootTable())
        ));

        public static final Supplier<StairBlock> BISMUTH_STAIRS = CreativeModeTabs.TAB.add(Register.block(
                "bismuth_stairs",
                () -> new StairBlock(BISMUTH_BLOCK.get().defaultBlockState(),
                        BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()
                )
        ));
        public static final Supplier<SlabBlock> BISMUTH_SLAB = CreativeModeTabs.TAB.add(Register.block(
                "bismuth_slab",
                () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops())
        ));

        public static final Supplier<PressurePlateBlock> BISMUTH_PRESSURE_PLATE =
                CreativeModeTabs.TAB.add(Register.block("bismuth_pressure_plate",
                        () -> new PressurePlateBlock(BlockSetType.IRON,
                                BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()
                        )
                ));
        public static final Supplier<ButtonBlock> BISMUTH_BUTTON = CreativeModeTabs.TAB.add(Register.block(
                "bismuth_button",
                () -> new ButtonBlock(BlockSetType.IRON,
                        20,
                        BlockBehaviour.Properties.of()
                                .strength(2f)
                                .requiresCorrectToolForDrops()
                                .noCollission()
                )
        ));

        public static final Supplier<FenceBlock> BISMUTH_FENCE = CreativeModeTabs.TAB.add(Register.block(
                "bismuth_fence",
                () -> new FenceBlock(BlockBehaviour.Properties.of()
                        .strength(2f)
                        .requiresCorrectToolForDrops())
        ));
        public static final Supplier<FenceGateBlock> BISMUTH_FENCE_GATE =
                CreativeModeTabs.TAB.add(Register.block("bismuth_fence_gate", () -> new FenceGateBlock(
                        WoodType.ACACIA,
                        BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()
                )));
        public static final Supplier<WallBlock> BISMUTH_WALL = CreativeModeTabs.TAB.add(Register.block(
                "bismuth_wall",
                () -> new WallBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops())
        ));

        public static final Supplier<DoorBlock> BISMUTH_DOOR =
                CreativeModeTabs.TAB.add(Register.block("bismuth_door", () -> new DoorBlock(BlockSetType.IRON,
                        BlockBehaviour.Properties.of()
                                .strength(2f)
                                .requiresCorrectToolForDrops()
                                .noOcclusion()
                )));
        public static final Supplier<TrapDoorBlock> BISMUTH_TRAPDOOR =
                CreativeModeTabs.TAB.add(Register.block("bismuth_trapdoor", () -> new TrapDoorBlock(
                        BlockSetType.IRON,
                        BlockBehaviour.Properties.of()
                                .strength(2f)
                                .requiresCorrectToolForDrops()
                                .noOcclusion()
                )));

        public static final Supplier<BismuthLampBlock> BISMUTH_LAMP = CreativeModeTabs.TAB.add(Register.block(
                "bismuth_lamp",
                () -> new BismuthLampBlock(BlockBehaviour.Properties.of()
                        .strength(2f)
                        .requiresCorrectToolForDrops()
                        .lightLevel(BismuthLampBlock::getLightLevel))
        ));

        public static final Supplier<Block> TOOL_BENCH = CreativeModeTabs.TAB.add(Register.block("tool_bench",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.CRAFTING_TABLE)
                        .noOcclusion()) {
                    @Override
                    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                            Player player, BlockHitResult hitResult) {
                        if (level.isClientSide) {
                            return InteractionResult.SUCCESS;
                        } else {
                            return InteractionResult.CONSUME;
                        }
                    }
                }
        ));
        public static final Supplier<Block> REPAIR_BENCH = CreativeModeTabs.TAB.add(Register.block(
                "repair_bench",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.COBBLESTONE)
                        .noOcclusion()) {
                    // FIXME: this is ugly, make a custom class for the block
                    @Override
                    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                            Player player, BlockHitResult hitResult) {
                        if (level.isClientSide) {
                            return InteractionResult.SUCCESS;
                        } else {
                            return InteractionResult.CONSUME;
                        }
                    }
                }
        ));

        public static final Supplier<Block> BRITTLEY_BLOCK = CreativeModeTabs.TAB.add(Register.block(
                "brittley_block",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.FLOWER_POT)
                        .sound(SoundType.BONE_BLOCK))
        ));

        public static final Supplier<Block> TEMPERED_GOLD_BLOCK = CreativeModeTabs.TAB.add(Register.block(
                "tempered_gold_block",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.GOLD_BLOCK))
        ));

        static void init() {
        }
    }

    public static class ArmorMaterials {
        public static final Holder<ArmorMaterial> BISMUTH =
                Register.armorMaterial("bismuth", new ArmorMaterial(TierHelper.defense(2, 4, 6, 2, 4),
                        TierHelper.VanillaEnchantability.GOLD.enchantability - 5,
                        SoundEvents.ARMOR_EQUIP_GENERIC,
                        TierHelper.repair(Items.BISMUTH),
                        TierHelper.genericLayers("bismuth"),
                        TierHelper.VanillaToughness.IRON.chestplate,
                        TierHelper.VanillaKnockbackResistance.IRON.knockbackResistance
                ));
        public static final int BISMUTH_DURABILITY_MULTIPLIER = 15;

        public static final Holder<ArmorMaterial> TEMPERED_GOLD =
                Register.armorMaterial("tempered_gold", new ArmorMaterial(TierHelper.defense(1, 3, 5, 2, 4),
                        TierHelper.VanillaEnchantability.GOLD.enchantability,
                        SoundEvents.ARMOR_EQUIP_GOLD,
                        () -> Ingredient.of(Items.TEMPERED_GOLD_INGOT.get()),
                        TierHelper.genericLayers("tempered_gold"),
                        TierHelper.VanillaToughness.GOLD.chestplate,
                        TierHelper.VanillaKnockbackResistance.GOLD.knockbackResistance
                ));
        public static final int TEMPERED_GOLD_DURABILITY_MULTIPLIER = 8;

        static void init() {
        }
    }

    public static class DataComponents {
        public static final Supplier<DataComponentType<BlockPos>> COORDINATES =
                Register.dataComponentType("coordinates", builder -> builder.persistent(BlockPos.CODEC));
        public static final Supplier<DataComponentType<ItemLevelDataComponent>> ITEM_EXPERIENCE =
                Register.dataComponentType("item_level",
                        builder -> builder.persistent(ItemLevelDataComponent.CODEC)
                );

        static void init() {
        }
    }
}
