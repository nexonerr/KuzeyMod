package com.nexoner.kuzey.item.custom;

import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.item.custom.helper.BlockBreaker;
import com.nexoner.kuzey.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MiningHammerItem extends DiggerItem {

    public MiningHammerItem(float pAttackDamageModifier, float pAttackSpeedModifier, Tier pTier, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, ModTags.Blocks.HAMMER_MINEABLE, pProperties);
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        int lRadius = KuzeyCommonConfigs.miningHammerRadius.get();
        if (Screen.hasShiftDown()){lRadius = 0;}

        if (isCorrectToolForDrops(pPlayer.getItemInHand(pPlayer.swingingArm),pState)){
            BlockBreaker.breakInRadius(pLevel,pPlayer,lRadius,KuzeyCommonConfigs.miningHammerDepth.get(),pPos);
            return true;
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasAltDown()) {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.kuzeyium_mining_hammer"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.alt"));
        }
    }
}
