package com.nexoner.kuzey.datagen;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, KuzeyMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.KUZEYIUM_INGOT.get());
        simpleItem(ModItems.KUZEYIUM_CHUNK.get());
        simpleItem(ModItems.RAW_KUZEYIUM.get());
        simpleItem(ModItems.REGENERATOR.get());
        simpleItem(ModItems.KUZEYIUM_NUGGET.get());
        simpleItem(ModItems.KUZEYIUM_COAL.get());
        simpleItem(ModItems.KUZEYIUM_HELMET.get());
        simpleItem(ModItems.KUZEYIUM_CHESTPLATE.get());
        simpleItem(ModItems.KUZEYIUM_LEGGINGS.get());
        simpleItem(ModItems.KUZEYIUM_BOOTS.get());
        simpleItem(ModItems.KUZEYIUM_MESH.get());
        simpleItem(ModItems.KUZEYIUM_FABRIC.get());
        simpleItem(ModItems.KUZEYIUM_GLASS_PLATE.get());
        simpleItem(ModItems.BLACK_CARROTS.get());
        simpleItem(ModItems.KUZEYIUM_SAW.get());
        simpleItem(ModItems.KUZEYIAN_TOOL_HANDLE.get());
        simpleItem(ModItems.KUZEYIUM_PURIFICATION_CORE.get());
        simpleItem(ModItems.SPAWNER_IRRITATOR.get());

        handheldItem(ModItems.KUZEYIUM_SWORD.get());
        handheldItem(ModItems.KUZEYIUM_AXE.get());
        handheldItem(ModItems.KUZEYIUM_PICKAXE.get());
        handheldItem(ModItems.KUZEYIUM_SHOVEL.get());
        handheldItem(ModItems.KUZEYIUM_SMITHING_HAMMER.get());

        for (RegistryObject<Block> block: ModBlocks.BLOCKS.getEntries() ) {
            if (!(block.get() instanceof CropBlock)){
                blockItem(block.get());
            }
        }

    }
    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(KuzeyMod.MOD_ID,"item/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(KuzeyMod.MOD_ID,"item/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder blockItem(Block block) {
        return withExistingParent(block.getRegistryName().getPath(),
                new ResourceLocation(KuzeyMod.MOD_ID,"block/" + block.getRegistryName().getPath()));
    }
}
