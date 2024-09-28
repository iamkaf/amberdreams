package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AmberDreams.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(AmberDreams.Blocks.BISMUTH_BLOCK);
        blockWithItem(AmberDreams.Blocks.BISMUTH_ORE);
        blockWithItem(AmberDreams.Blocks.BISMUTH_DEEPSLATE_ORE);
        blockWithItem(AmberDreams.Blocks.MAGIC_BLOCK);
    }

    private void blockWithItem(Supplier<Block> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
