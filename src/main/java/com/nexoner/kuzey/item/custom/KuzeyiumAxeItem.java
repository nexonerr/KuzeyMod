package com.nexoner.kuzey.item.custom;

import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class KuzeyiumAxeItem extends AxeItem {

    public KuzeyiumAxeItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (isLog(pState.getBlock())){
        for (int i = 1; i < KuzeyCommonConfigs.kuzeyiumAxeLumberMax.get(); i++){
                BlockPos posToCheck = new BlockPos(pPos.getX(),pPos.getY() + i,pPos.getZ());
                Block blockToCheck = pLevel.getBlockState(posToCheck).getBlock();
                if (isLog(blockToCheck)){
                   pLevel.destroyBlock(posToCheck,true,pEntityLiving);
            }

        }}

        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }
    private boolean isLog(Block block){
        return Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(block).get()).is(BlockTags.LOGS);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasAltDown()){
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.kuzeyium_axe"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.alt"));
        }
}}

