package com.nexoner.kuzey.item;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.item.custom.KuzeyiumEnrichedCoalItem;
import com.nexoner.kuzey.item.custom.RegeneratorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, KuzeyMod.MOD_ID);

    //Item register start

    public static final RegistryObject<Item> KUZEYIUM_INGOT = ITEMS.register("kuzeyium_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_CHUNK = ITEMS.register("kuzeyium_chunk",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> RAW_KUZEYIUM = ITEMS.register("raw_kuzeyium",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> REGENARATOR = ITEMS.register("regenerator",
            () -> new RegeneratorItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB).durability(1).setNoRepair()));

    public static final RegistryObject<Item> KUZEYIUM_NUGGET = ITEMS.register("kuzeyium_nugget",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_COAL = ITEMS.register("kuzeyium_coal",
            () -> new KuzeyiumEnrichedCoalItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
