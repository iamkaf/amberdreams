package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import com.iamkaf.amberdreams.block.BismuthLampBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
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
//        blockWithItem(AmberDreams.Blocks.TOOL_BENCH);
        blockWithItem(AmberDreams.Blocks.BRITTLEY_BLOCK);

        var bismuthBlockTexture = blockTexture(AmberDreams.Blocks.BISMUTH_BLOCK.get());

        stairsBlock(AmberDreams.Blocks.BISMUTH_STAIRS.get(), bismuthBlockTexture);
        blockItem("bismuth_stairs", AmberDreams.Blocks.BISMUTH_STAIRS);

        slabBlock(AmberDreams.Blocks.BISMUTH_SLAB.get(), bismuthBlockTexture, bismuthBlockTexture);
        blockItem("bismuth_slab", AmberDreams.Blocks.BISMUTH_SLAB);

        buttonBlock(AmberDreams.Blocks.BISMUTH_BUTTON.get(), bismuthBlockTexture);
        pressurePlateBlock(AmberDreams.Blocks.BISMUTH_PRESSURE_PLATE.get(), bismuthBlockTexture);
        blockItem("bismuth_pressure_plate", AmberDreams.Blocks.BISMUTH_PRESSURE_PLATE);

        fenceBlock(AmberDreams.Blocks.BISMUTH_FENCE.get(), bismuthBlockTexture);
        fenceGateBlock(AmberDreams.Blocks.BISMUTH_FENCE_GATE.get(), bismuthBlockTexture);
        blockItem("bismuth_fence_gate", AmberDreams.Blocks.BISMUTH_FENCE_GATE);
        wallBlock(AmberDreams.Blocks.BISMUTH_WALL.get(), bismuthBlockTexture);

        doorBlockWithRenderType(AmberDreams.Blocks.BISMUTH_DOOR.get(), modLoc("block/bismuth_door_bottom"), modLoc("block/bismuth_door_top"), "cutout");
        trapdoorBlockWithRenderType(AmberDreams.Blocks.BISMUTH_TRAPDOOR.get(), modLoc("block/bismuth_trapdoor"), true, "cutout");
        blockItem("bismuth_trapdoor_bottom", AmberDreams.Blocks.BISMUTH_TRAPDOOR);

        getVariantBuilder(AmberDreams.Blocks.BISMUTH_LAMP.get()).forAllStates(state -> {
            if (state.getValue(BismuthLampBlock.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("bismuth_lamp_on", modLoc("block/bismuth_lamp_on")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("bismuth_lamp_off", modLoc("block/bismuth_lamp_off")))};
            }
        });
        simpleBlockItem(AmberDreams.Blocks.BISMUTH_LAMP.get(), models().cubeAll("bismuth_lamp_on", modLoc("block/bismuth_lamp_on")));

        blockWithItem(AmberDreams.Blocks.TEMPERED_GOLD_BLOCK);
    }

    private void blockWithItem(Supplier<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private <T extends Block> void blockItem(String id, Supplier<T> block) {
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + id)));
    }
}
