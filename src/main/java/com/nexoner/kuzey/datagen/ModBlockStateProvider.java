package com.nexoner.kuzey.datagen;


import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.block.custom.BlackCarrotsCropBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, KuzeyMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.KUZEYIUM_ORE.get());
        simpleBlock(ModBlocks.KUZEYIUM_GLASS.get());
        simpleBlock(ModBlocks.KUZEYIAN_PLANKS.get());
        simpleBlock(ModBlocks.KUZEYIAN_LEAVES.get());
        simpleBlock(ModBlocks.KUZEYIUM_BLOCK.get());
        simpleBlock(ModBlocks.EMRE_ESSENCE_ROCK_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_EMRE_ESSENCE_ROCK_ORE.get());
        simpleBlock(ModBlocks.WOODEN_MACHINE_FRAME.get());
        simpleBlock(ModBlocks.IRON_MACHINE_FRAME.get());
        simpleBlock(ModBlocks.DIAMOND_MACHINE_FRAME.get());
        simpleBlock(ModBlocks.UNACTIVATED_INFUSED_MACHINE_FRAME.get());
        simpleBlock(ModBlocks.INFUSED_MACHINE_FRAME.get());

        simpleBlock(ModBlocks.KUZEYIAN_SAPLING.get(), models().cross(ModBlocks.KUZEYIAN_SAPLING.get().getRegistryName().getPath(), blockTexture(ModBlocks.KUZEYIAN_SAPLING.get())));

        logBlock((RotatedPillarBlock) ModBlocks.KUZEYIAN_LOG.get());
        logBlock((RotatedPillarBlock) ModBlocks.KUZEYIAN_LOG_STRIPPED.get());
        axisBlock((RotatedPillarBlock) ModBlocks.KUZEYIAN_WOOD.get(), blockTexture(ModBlocks.KUZEYIAN_LOG.get()),blockTexture(ModBlocks.KUZEYIAN_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.KUZEYIAN_WOOD_STRIPPED.get(), blockTexture(ModBlocks.KUZEYIAN_LOG_STRIPPED.get()),blockTexture(ModBlocks.KUZEYIAN_LOG_STRIPPED.get()));

        makeCrop((BlackCarrotsCropBlock)ModBlocks.BLACK_CARROTS_PLANT.get(), "black_carrots_stage_","black_carrots_stage_");
    }


    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(block.getAgeProperty()),
                new ResourceLocation(KuzeyMod.MOD_ID, "block/" + textureName + state.getValue(block.getAgeProperty()))));

        return models;
    }
}
