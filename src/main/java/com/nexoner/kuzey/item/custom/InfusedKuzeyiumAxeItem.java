package com.nexoner.kuzey.item.custom;

import com.nexoner.kuzey.item.custom.helper.BlockBreaker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class InfusedKuzeyiumAxeItem extends AxeItem {

    public final int RADIUS = 1;

    public InfusedKuzeyiumAxeItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        if (!pLevel.isClientSide) {
            ItemStack pStack = pPlayer.getItemInHand(pPlayer.swingingArm);
            if (pStack.hasTag()) {
                if (pStack.getTag().getBoolean("kuzey.ability")) {
                    if (pPlayer.getItemInHand(pPlayer.swingingArm).isCorrectToolForDrops(pLevel.getBlockState(pPos))){
                        if ((Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(pLevel.getBlockState(pPos).getBlock()).get()).is(BlockTags.LOGS))) {
                            lumberBlock(RADIUS, pPos, pLevel, pPlayer);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (Screen.hasShiftDown()) {
            ItemStack item = pPlayer.getItemInHand(pUsedHand);
            if (item.hasTag()) {
                CompoundTag nbt = item.getTag();
                Boolean ability = nbt.getBoolean("kuzey.ability");
                nbt.putBoolean("kuzey.ability", !ability);
                pPlayer.displayClientMessage(new TranslatableComponent("tooltip.kuzey.infused_kuzeyium_axe_toggled",getTextForBool(nbt.getBoolean("kuzey.ability"))),true);
                pPlayer.playSound(SoundEvents.ARROW_HIT_PLAYER,1,1);
                item.setTag(nbt);
            } else {
                //this stuff is for complete redundancy purposes they don't actually do anything
                CompoundTag nbt = new CompoundTag();
                nbt.putBoolean("kuzey.ability", true);
                item.setTag(nbt);
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasAltDown()) {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.infused_kuzeyium_axe"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.alt"));
        }
        if (pStack.hasTag()) {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.infused_kuzeyium_axe_ability", getTextForBool(pStack.getTag().getBoolean("kuzey.ability"))));
        } /*this is also just for redundancy it's pretty useless*/else {pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.ability_activate"));}
    }

    private TranslatableComponent getTextForBool(Boolean pValue){
        return pValue ? new TranslatableComponent("tooltip.kuzey.on") : new TranslatableComponent("tooltip.kuzey.off");
    }

    private void lumberBlock(int radius, BlockPos pPos, Level pLevel, Player pPlayer){
        for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos toProcess = pPos.offset(x,0,z);
                    breakBlockAndRecurse(toProcess,pPos,pLevel,pPlayer);
                }
        }
    }
    private void breakBlockAndRecurse(BlockPos pPos, BlockPos originPos, Level pLevel, Player pPlayer){
        if (!pLevel.isClientSide) {
            if (pPlayer.getItemInHand(pPlayer.swingingArm).isCorrectToolForDrops(pLevel.getBlockState(pPos))){
                if ((Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(pLevel.getBlockState(pPos).getBlock()).get()).is(BlockTags.LOGS))) {
                    List<ItemStack> drops = new LinkedList<>();
                    drops.addAll(BlockBreaker.breakBlockAndReturnDrops(pPos, pLevel, pPos, originPos, pPlayer.getItemInHand(pPlayer.swingingArm), pPlayer));
                    breakBlockAndRecurse(pPos.above(), originPos, pLevel, pPlayer);
                    drops.forEach(itemStack -> {
                        ItemEntity entity = new ItemEntity(pLevel,pPos.getX(),pPos.getY(),pPos.getZ(),itemStack);
                        pLevel.addFreshEntity(entity);
                    });
                }
            }
        }
    }
}
