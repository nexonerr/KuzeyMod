package com.nexoner.kuzey.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods{
    public static final FoodProperties BLACK_CARROT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.8F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2, 0), 0.4f).fast().build();
}
