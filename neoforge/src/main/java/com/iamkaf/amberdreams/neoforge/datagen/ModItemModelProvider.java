package com.iamkaf.amberdreams.neoforge.datagen;

import com.iamkaf.amberdreams.AmberDreams;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AmberDreams.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(AmberDreams.Items.BISMUTH.get());
        basicItem(AmberDreams.Items.RAW_BISMUTH.get());
        basicItem(AmberDreams.Items.CHISEL.get());
        basicItem(AmberDreams.Items.RADISH.get());
        basicItem(AmberDreams.Items.FROSTFIRE_ICE.get());
        basicItem(AmberDreams.Items.STARLIGHT_ASHES.get());

        buttonItem("bismuth_button", "bismuth_block");
        fenceItem("bismuth_fence", "bismuth_block");
        wallItem("bismuth_wall", "bismuth_block");

        basicItem(AmberDreams.Blocks.BISMUTH_DOOR.get().asItem());

        handheldItem("bismuth_sword");
        handheldItem("bismuth_pickaxe");
        handheldItem("bismuth_shovel");
        handheldItem("bismuth_axe");
        handheldItem("bismuth_hoe");
        handheldItem("bismuth_hammer");

        basicItem(AmberDreams.Items.BISMUTH_HELMET.get());
        basicItem(AmberDreams.Items.BISMUTH_CHESTPLATE.get());
        basicItem(AmberDreams.Items.BISMUTH_LEGGINGS.get());
        basicItem(AmberDreams.Items.BISMUTH_BOOTS.get());
    }

    public void buttonItem(String id, String baseBlockId) {
        this.withExistingParent(id, mcLoc("block/button_inventory"))
                .texture("texture", modLoc("block/" + baseBlockId));
    }

    public void fenceItem(String id, String baseBlockId) {
        this.withExistingParent(id, mcLoc("block/fence_inventory"))
                .texture("texture", modLoc("block/" + baseBlockId));
    }

    public void wallItem(String id, String baseBlockId) {
        this.withExistingParent(id, mcLoc("block/wall_inventory"))
                .texture("wall", modLoc("block/" + baseBlockId));
    }

    private ItemModelBuilder handheldItem(String id) {
        return this.withExistingParent(modLoc(id).getPath(), ResourceLocation.parse("item/handheld"))
                .texture("layer0", modLoc("item/" + id));
    }
}
