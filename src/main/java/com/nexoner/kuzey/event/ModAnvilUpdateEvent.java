package com.nexoner.kuzey.event;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.item.ModItems;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;


@Mod.EventBusSubscriber(modid = KuzeyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModAnvilUpdateEvent {


    @SubscribeEvent
    public static void anvilUpdateEvent(AnvilUpdateEvent event){
        HashMap<ItemLike, List<Enchantment>> disabledEnchants = getDisabledEnchants();

        //OH MY GOD IT IS SO CONVOLUTED

        for (Map.Entry<ItemLike, List<Enchantment>> mainEntry : disabledEnchants.entrySet()){
            if (mainEntry.getKey() == event.getLeft().getItem() && event.getRight().getItem() instanceof EnchantedBookItem){
                ItemStack enchantedBook = event.getRight();
                if (!EnchantedBookItem.getEnchantments(enchantedBook).isEmpty()){
                    mainEntry.getValue().forEach(enchantment -> {
                        Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(enchantedBook);
                        for (Map.Entry<Enchantment,Integer> enchantmentIntegerEntry : map.entrySet()) {
                            if (enchantmentIntegerEntry.getKey() == enchantment) {
                                event.setCanceled(true);
                            }
                        }
                    });
                    }
                }
            }
        }

    private static HashMap<ItemLike, List<Enchantment>> getDisabledEnchants(){
        HashMap<ItemLike, List<Enchantment>> disabledEnchants = new HashMap<ItemLike, List<Enchantment>>();

        put(disabledEnchants,ModItems.REGENERATOR.get(),Enchantments.MENDING,Enchantments.UNBREAKING);
        put(disabledEnchants,ModItems.KUZEYIUM_SAW.get(), Enchantments.MENDING, Enchantments.UNBREAKING);
        put(disabledEnchants,ModItems.KUZEYIUM_SMITHING_HAMMER.get(), Enchantments.MENDING, Enchantments.UNBREAKING);
        put(disabledEnchants,ModItems.SPAWNER_IRRITATOR.get(), Enchantments.MENDING, Enchantments.UNBREAKING);

        return disabledEnchants;
    }

    private static void put(HashMap<ItemLike, List<Enchantment>> hashMap, ItemLike item, Enchantment... enchantments){
        List<Enchantment> enchantmentList = Arrays.stream(enchantments).toList();
        hashMap.put(item, enchantmentList);
    }

}