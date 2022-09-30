package com.nexoner.kuzey.integration.tic;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.item.ModCreativeModeTab;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTICMaterialItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, KuzeyMod.MOD_ID);

    public static final RegistryObject<Item> MOLTEN_KUZEYIUM_BUCKET = ITEMS.register("molten_kuzeyium_bucket",
            () -> new BucketItem(ModTICMaterialFluids.MOLTEN_KUZEYIUM_FLUID, new Item.Properties().tab(ModCreativeModeTab.KUZEY_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MOLTEN_INFUSED_KUZEYIUM_BUCKET = ITEMS.register("molten_infused_kuzeyium_bucket",
            () -> new BucketItem(ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_FLUID, new Item.Properties().tab(ModCreativeModeTab.KUZEY_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MELDABLE_KUZEYIAN_WOOD = ITEMS.register("meldable_kuzeyian_wood",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.KUZEY_TAB)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
