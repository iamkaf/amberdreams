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
    public ModItemTagProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> completableFuture2, @Nullable ExistingFileHelper existingFileHelper) {
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
        tag(ItemTags.SHOVELS).add(AmberDreams.Items.BISMUTH_SHOVEL.get());
        tag(ItemTags.AXES).add(AmberDreams.Items.BISMUTH_AXE.get());
        tag(ItemTags.HOES).add(AmberDreams.Items.BISMUTH_HOE.get());
    }
}
