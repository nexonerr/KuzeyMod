package com.nexoner.kuzey.block.custom;

import com.mojang.authlib.GameProfile;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;


public class DevastatorBlock extends Block {
    public DevastatorBlock(Properties properties) {
        super(properties);
    }

    public static final BooleanProperty TOGGLED = BooleanProperty.create("toggled");

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(!pLevel.isClientSide){
            if(pEntity instanceof LivingEntity && pState.getValue(TOGGLED) == true){
                LivingEntity livingEntity = ((LivingEntity) pEntity);
                livingEntity.hurt(DamageSource.playerAttack(new Player(pLevel,pPos,1f,new GameProfile(null, KuzeyCommonConfigs.devastatorSlayer.get())) {
                    @Override
                    public boolean isSpectator() {
                        return false;
                    }

                    @Override
                    public boolean isCreative() {
                        return false;
                    }
                }),8f);
            }
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && pHand == InteractionHand.MAIN_HAND){
            boolean currentState = pState.getValue(TOGGLED);
            pLevel.setBlock(pPos, pState.setValue(TOGGLED, !currentState), 3);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TOGGLED);
    }
}
