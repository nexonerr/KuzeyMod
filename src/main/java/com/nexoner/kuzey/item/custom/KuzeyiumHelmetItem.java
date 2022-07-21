package com.nexoner.kuzey.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class KuzeyiumHelmetItem extends ArmorItem {
    public KuzeyiumHelmetItem(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
            if (!world.isClientSide()) {

                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 10, 0));
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 10, 0));

            }


        super.onArmorTick(stack, world, player);
    }

}
