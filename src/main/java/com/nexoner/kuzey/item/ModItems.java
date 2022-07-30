package com.nexoner.kuzey.item;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.item.custom.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, KuzeyMod.MOD_ID);

    //Item register start

    public static final RegistryObject<Item> KUZEYIUM_INGOT = ITEMS.register("kuzeyium_ingot",
            () -> new CraftSoundItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_CHUNK = ITEMS.register("kuzeyium_chunk",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> RAW_KUZEYIUM = ITEMS.register("raw_kuzeyium",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> REGENERATOR = ITEMS.register("regenerator",
            () -> new RegeneratorItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB).durability(1).setNoRepair()));

    public static final RegistryObject<Item> KUZEYIUM_NUGGET = ITEMS.register("kuzeyium_nugget",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_COAL = ITEMS.register("kuzeyium_coal",
            () -> new KuzeyiumEnrichedCoalItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));


    public static final RegistryObject<Item> KUZEYIUM_SWORD = ITEMS.register("kuzeyium_sword",
            () -> new KuzeyiumSwordItem(ModTiers.KUZEYIUM, 6, -2.1f, new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_AXE = ITEMS.register("kuzeyium_axe",
            () -> new KuzeyiumAxeItem(ModTiers.KUZEYIUM, 9, -3.3f, new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_PICKAXE = ITEMS.register("kuzeyium_pickaxe",
            () -> new PickaxeItem(ModTiers.KUZEYIUM, 1, -3.1f, new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_SHOVEL = ITEMS.register("kuzeyium_shovel",
            () -> new ShovelItem(ModTiers.KUZEYIUM, 1, -3.1f, new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));


    public static final RegistryObject<Item> KUZEYIUM_HELMET = ITEMS.register("kuzeyium_helmet",
            () -> new KuzeyiumHelmetItem(ModArmorMaterials.KUZEYIUM, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_CHESTPLATE = ITEMS.register("kuzeyium_chestplate",
            () -> new KuzeyiumChestplateItem(ModArmorMaterials.KUZEYIUM, EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_LEGGINGS = ITEMS.register("kuzeyium_leggings",
            () -> new KuzeyiumLeggingsItem(ModArmorMaterials.KUZEYIUM, EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_BOOTS = ITEMS.register("kuzeyium_boots",
            () -> new KuzeyiumBootsItem(ModArmorMaterials.KUZEYIUM, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));


    public static final RegistryObject<Item> KUZEYIUM_MESH = ITEMS.register("kuzeyium_mesh",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_FABRIC = ITEMS.register("kuzeyium_fabric",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_SMITHING_HAMMER = ITEMS.register("kuzeyium_smithing_hammer",
            () -> new SmithingHammerItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB).durability(850)));

    public static final RegistryObject<Item> KUZEYIUM_GLASS_PLATE = ITEMS.register("kuzeyium_glass_plate",
            () -> new CraftSoundItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> BLACK_CARROTS = ITEMS.register("black_carrots",
            () -> new ItemNameBlockItem(ModBlocks.BLACK_CARROTS_PLANT.get(), new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB).food(ModFoods.BLACK_CARROT)));

    public static final RegistryObject<Item> KUZEYIUM_SAW = ITEMS.register("kuzeyium_saw",
            () -> new SmithingHammerItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB).durability(600)));

    public static final RegistryObject<Item> KUZEYIAN_TOOL_HANDLE = ITEMS.register("kuzeyian_tool_handle",
            () -> new CraftSoundItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> KUZEYIUM_PURIFICATION_CORE = ITEMS.register("kuzeyium_purification_core",
            () -> new Item(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB)));

    public static final RegistryObject<Item> SPAWNER_IRRITATOR = ITEMS.register("spawner_irritator",
            () -> new SpawnerIrritatorItem(new Item.Properties().tab(ModCreativeModTab.KUZEY_TAB).durability(42)));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
