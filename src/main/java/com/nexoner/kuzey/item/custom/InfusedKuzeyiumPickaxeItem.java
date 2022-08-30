package com.nexoner.kuzey.item.custom;

import com.nexoner.kuzey.item.helper.BlockBreaker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class InfusedKuzeyiumPickaxeItem extends PickaxeItem {

    public final int blockLimit = 192;
    public final boolean abundantMining = false;

    public InfusedKuzeyiumPickaxeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        if (!pLevel.isClientSide) {
            ItemStack pStack = pPlayer.getItemInHand(pPlayer.swingingArm);
            if (pStack.hasTag()) {
                if (pStack.getTag().getBoolean("kuzey.ability")){
                    if (isCorrectToolForDrops(pStack, pState)) {
                        if ((!(Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(pState.getBlock()).get()).is(Tags.Blocks.STONE)) && pState.getBlock() != Blocks.DEEPSLATE) || abundantMining)
                            veinMineBlock(blockLimit, pPos, pLevel, pPlayer.getOnPos().above(), pStack, pPlayer,pPlayer.swingingArm,pState.getBlock());
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
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            Boolean ability = nbt.getBoolean("kuzey.ability");
            nbt.putBoolean("kuzey.ability", !ability);
            localPlayer.displayClientMessage(new TranslatableComponent("tooltip.kuzey.infused_kuzeyium_pickaxe_toggled.tooltip",getTextForBool(nbt.getBoolean("kuzey.ability"))),true);
            localPlayer.playSound(SoundEvents.ARROW_HIT_PLAYER,1,1);
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
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.infused_kuzeyium_pickaxe.tooltip"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.alt.tooltip"));
        }
            if (pStack.hasTag()) {
                pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.infused_kuzeyium_pickaxe_ability.tooltip", getTextForBool(pStack.getTag().getBoolean("kuzey.ability"))));
            } /*this is also just for redundancy it's pretty useless*/else {pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.ability_activate.tooltip"));}
    }

    private TranslatableComponent getTextForBool(Boolean pValue){
       return pValue ? new TranslatableComponent("tooltip.kuzey.on.tooltip") : new TranslatableComponent("tooltip.kuzey.off.tooltip");
    }

    private void veinMineBlock(int blockLimit,BlockPos pPos, Level pLevel, BlockPos spawnPos, ItemStack pTool, LivingEntity pEntity, InteractionHand pHand, Block pBlock){
        int blocksBroken = 0;
        Queue<BlockPos> positionQueue = new LinkedList<>();
        List<ItemStack> drops = new LinkedList<>();

        drops.addAll(BlockBreaker.breakBlockAndReturnDrops(pPos,pLevel,spawnPos,pPos,pTool,pEntity));
        positionQueue.add(pPos);
        blocksBroken++;

        floodBreak:
        while (!positionQueue.isEmpty()){
            BlockPos startPos = positionQueue.poll();

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x == 0 && y == 0 && z == 0){continue;}
                        if (blocksBroken >= blockLimit){break floodBreak;}
                        BlockPos toProcess = startPos.offset(x,y,z);
                        if (pLevel.getBlockState(toProcess).getBlock() == pBlock){
                            drops.addAll(BlockBreaker.breakBlockAndReturnDrops(toProcess,pLevel,spawnPos,startPos,pTool,pEntity));
                            positionQueue.add(toProcess);
                            blocksBroken++;
                        }
                    }
                }
            }
        }
        drops.forEach(itemStack -> {
            ItemEntity entity = new ItemEntity(pLevel,spawnPos.getX(),spawnPos.getY(),spawnPos.getZ(),itemStack);
            pLevel.addFreshEntity(entity);
        });
    }

}
