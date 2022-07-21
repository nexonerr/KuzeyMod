package com.nexoner.kuzey.item.custom;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CraftSoundItem extends Item {
    public CraftSoundItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        pLevel.playSound(pPlayer,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(),SoundEvents.ANVIL_PLACE,SoundSource.PLAYERS,1f,1f);
    }
}
