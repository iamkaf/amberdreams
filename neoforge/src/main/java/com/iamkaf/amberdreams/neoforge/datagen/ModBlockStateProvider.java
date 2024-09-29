package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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

        var bismuthBlockTexture = blockTexture(AmberDreams.Blocks.BISMUTH_BLOCK.get());

        stairsBlock(AmberDreams.Blocks.BISMUTH_STAIRS.get(), bismuthBlockTexture);
        slabBlock(AmberDreams.Blocks.BISMUTH_SLAB.get(), bismuthBlockTexture, bismuthBlockTexture);

        buttonBlock(AmberDreams.Blocks.BISMUTH_BUTTON.get(), bismuthBlockTexture);
        pressurePlateBlock(AmberDreams.Blocks.BISMUTH_PRESSURE_PLATE.get(), bismuthBlockTexture);

        fenceBlock(AmberDreams.Blocks.BISMUTH_FENCE.get(), bismuthBlockTexture);
        fenceGateBlock(AmberDreams.Blocks.BISMUTH_FENCE_GATE.get(), bismuthBlockTexture);
        wallBlock(AmberDreams.Blocks.BISMUTH_WALL.get(), bismuthBlockTexture);

        doorBlockWithRenderType(AmberDreams.Blocks.BISMUTH_DOOR.get(), modLoc("block/bismuth_door_bottom"), modLoc("block/bismuth_door_top"), "cutout");
        trapdoorBlockWithRenderType(AmberDreams.Blocks.BISMUTH_TRAPDOOR.get(), modLoc("block/bismuth_trapdoor"), true, "cutout");

        blockItem("bismuth_stairs", AmberDreams.Blocks.BISMUTH_STAIRS);
        blockItem("bismuth_slab", AmberDreams.Blocks.BISMUTH_SLAB);
        blockItem("bismuth_pressure_plate", AmberDreams.Blocks.BISMUTH_PRESSURE_PLATE);
        blockItem("bismuth_fence_gate", AmberDreams.Blocks.BISMUTH_FENCE_GATE);
        blockItem("bismuth_trapdoor_bottom", AmberDreams.Blocks.BISMUTH_TRAPDOOR);
    }

    private void blockWithItem(Supplier<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private <T extends Block> void blockItem(String id, Supplier<T> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + id)));
    }
}
