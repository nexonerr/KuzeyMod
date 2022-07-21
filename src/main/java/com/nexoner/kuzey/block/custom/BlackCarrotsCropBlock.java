package com.nexoner.kuzey.block.custom;

import com.nexoner.kuzey.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BlackCarrotsCropBlock extends CropBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;


    public BlackCarrotsCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.BLACK_CARROTS.get();
    }
}
