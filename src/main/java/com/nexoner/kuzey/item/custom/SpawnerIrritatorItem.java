package com.nexoner.kuzey.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpawnerIrritatorItem extends Item{
    public SpawnerIrritatorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide || pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof SpawnerBlockEntity){
            SpawnerBlockEntity entity = (SpawnerBlockEntity) pContext.getLevel().getBlockEntity(pContext.getClickedPos());
            BaseSpawner spawner = entity.getSpawner();
            InteractionHand usedHand = pContext.getHand();
            Entity displayEntity = spawner.getOrCreateDisplayEntity(pContext.getLevel());
            EntityType<?> entityType = displayEntity.getType();
            Entity toSpawn = entityType.create(pContext.getLevel());
            toSpawn.moveTo(pContext.getClickedPos().getX(),pContext.getClickedPos().getY() + 2, pContext.getClickedPos().getZ());
            pContext.getLevel().addFreshEntity(toSpawn);
            pContext.getLevel().playSound(pContext.getPlayer(),pContext.getPlayer().getX(),pContext.getPlayer().getY(),pContext.getPlayer().getZ(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS,1f,0.6f);
            pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(), (player) -> player.broadcastBreakEvent(usedHand));
            return InteractionResult.PASS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasAltDown()){
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.spawner_irritator"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.alt"));
        }
    }
}
