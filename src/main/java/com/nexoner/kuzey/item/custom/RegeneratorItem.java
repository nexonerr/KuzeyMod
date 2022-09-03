package com.nexoner.kuzey.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RegeneratorItem extends Item {
    public RegeneratorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
       if(!pLevel.isClientSide) {
           float maxhealth = pPlayer.getMaxHealth();
           float currenthealth = pPlayer.getHealth();
           float toheal = maxhealth - currenthealth;
           pPlayer.heal(toheal);
           if (toheal > 0) {
               pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
           }}

       return super.use(pLevel, pPlayer, pUsedHand);
    }


    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasAltDown()){
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.regenerator"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.alt"));
        }
    }
}
