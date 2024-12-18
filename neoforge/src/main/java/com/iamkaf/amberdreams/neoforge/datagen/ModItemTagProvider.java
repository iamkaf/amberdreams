package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.tags.Tags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture,
            CompletableFuture<TagLookup<Block>> completableFuture2,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, completableFuture2, AmberDreams.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Items.TRANSFORMABLE_ITEMS).add(AmberDreams.Items.BISMUTH.get())
                .add(AmberDreams.Items.RAW_BISMUTH.get())
                .add(Items.COAL)
                .add(Items.STICK)
                .add(Items.COMPASS);

        tag(ItemTags.SWORDS).add(AmberDreams.Items.BISMUTH_SWORD.get());
        tag(ItemTags.PICKAXES).add(AmberDreams.Items.BISMUTH_PICKAXE.get());
        tag(ItemTags.PICKAXES).add(AmberDreams.Items.BISMUTH_HAMMER.get());
        tag(ItemTags.SHOVELS).add(AmberDreams.Items.BISMUTH_SHOVEL.get());
        tag(ItemTags.AXES).add(AmberDreams.Items.BISMUTH_AXE.get());
        tag(ItemTags.HOES).add(AmberDreams.Items.BISMUTH_HOE.get());
        tag(ItemTags.HEAD_ARMOR).add(AmberDreams.Items.BISMUTH_HELMET.get());
        tag(ItemTags.CHEST_ARMOR).add(AmberDreams.Items.BISMUTH_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR).add(AmberDreams.Items.BISMUTH_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR).add(AmberDreams.Items.BISMUTH_BOOTS.get());

        tag(ItemTags.SWORDS).add(AmberDreams.Items.TEMPERED_GOLD_SWORD.get());
        tag(ItemTags.PICKAXES).add(AmberDreams.Items.TEMPERED_GOLD_PICKAXE.get());
        tag(ItemTags.SHOVELS).add(AmberDreams.Items.TEMPERED_GOLD_SHOVEL.get());
        tag(ItemTags.AXES).add(AmberDreams.Items.TEMPERED_GOLD_AXE.get());
        tag(ItemTags.HOES).add(AmberDreams.Items.TEMPERED_GOLD_HOE.get());
        tag(ItemTags.HEAD_ARMOR).add(AmberDreams.Items.TEMPERED_GOLD_HELMET.get());
        tag(ItemTags.CHEST_ARMOR).add(AmberDreams.Items.TEMPERED_GOLD_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR).add(AmberDreams.Items.TEMPERED_GOLD_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR).add(AmberDreams.Items.TEMPERED_GOLD_BOOTS.get());

        tag(ItemTags.PIGLIN_LOVED).add(AmberDreams.Items.TEMPERED_GOLD_SWORD.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_PICKAXE.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_SHOVEL.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_AXE.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_HOE.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_HELMET.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_CHESTPLATE.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_LEGGINGS.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_BOOTS.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_INGOT.get())
                .add(AmberDreams.Items.TEMPERED_GOLD_NUGGET.get())
                .add(AmberDreams.Blocks.TEMPERED_GOLD_BLOCK.get().asItem());
    }
}
