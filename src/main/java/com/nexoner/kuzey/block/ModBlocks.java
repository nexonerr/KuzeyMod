package com.nexoner.kuzey.block;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.custom.DevastatorBlock;
import com.nexoner.kuzey.item.ModCreativeModTab;
import com.nexoner.kuzey.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, KuzeyMod.MOD_ID);

    public static final RegistryObject<Block> KUZEYIUM_ORE = registerBlock("kuzeyium_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(5f,30f)
            ), ModCreativeModTab.KUZEY_TAB);
    public static final RegistryObject<Block> DEVASTATOR = registerBlock("devastator",
            () -> new DevastatorBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(2f,30f)
            ), ModCreativeModTab.KUZEY_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }




    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
