package com.nexoner.kuzey.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KuzeyiumEnrichedCoalItem extends Item {
    public KuzeyiumEnrichedCoalItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 12000;
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasAltDown()){
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.kuzeyium_coal.tooltip"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.alt.tooltip"));
        }
}}
