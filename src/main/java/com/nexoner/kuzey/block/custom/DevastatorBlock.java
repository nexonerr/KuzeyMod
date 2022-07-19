package com.nexoner.kuzey.block.custom;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;




public class DevastatorBlock extends Block {
    public DevastatorBlock(Properties properties) {
        super(properties);
    }

    String Slayer = "KUZEY";

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(!pLevel.isClientSide){
            if(pEntity instanceof LivingEntity){
                LivingEntity livingEntity = ((LivingEntity) pEntity);
                livingEntity.hurt(DamageSource.playerAttack(new Player(pLevel,pPos,1f,new GameProfile(null,Slayer)) {
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
}
