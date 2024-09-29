package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AmberDreams.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(AmberDreams.Blocks.BISMUTH_BLOCK.get())
                .add(AmberDreams.Blocks.BISMUTH_ORE.get())
                .add(AmberDreams.Blocks.BISMUTH_DEEPSLATE_ORE.get())
                .add(AmberDreams.Blocks.MAGIC_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL).add(AmberDreams.Blocks.BISMUTH_DEEPSLATE_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(AmberDreams.Blocks.MAGIC_BLOCK.get());

        tag(BlockTags.FENCES).add(AmberDreams.Blocks.BISMUTH_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(AmberDreams.Blocks.BISMUTH_FENCE_GATE.get());
        tag(BlockTags.WALLS).add(AmberDreams.Blocks.BISMUTH_WALL.get());
    }
}
