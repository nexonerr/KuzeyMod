package com.nexoner.kuzey.block;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.custom.BlackCarrotsCropBlock;
import com.nexoner.kuzey.block.custom.DevastatorBlock;
import com.nexoner.kuzey.block.custom.ModFlammableBlock;
import com.nexoner.kuzey.block.custom.ModFlammableRotatedPillarBlock;
import com.nexoner.kuzey.item.ModCreativeModTab;
import com.nexoner.kuzey.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
            ), ModCreativeModTab.KUZEY_TAB, "tooltip.kuzey.block.devastator");

    public static final RegistryObject<Block> KUZEYIUM_GLASS = registerBlock("kuzeyium_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(0.5f,600f)
            ),ModCreativeModTab.KUZEY_TAB,"tooltip.kuzey.block.kuzeyium_glass");

    public static final RegistryObject<Block> BLACK_CARROTS_PLANT = registerBlockWithoutBlockItem("black_carrots_plant",
            () -> new BlackCarrotsCropBlock(BlockBehaviour.Properties.copy(Blocks.CARROTS).noOcclusion()));

    public static final RegistryObject<Block> KUZEYIAN_LOG = registerBlock("kuzeyian_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_LOG)
                    .requiresCorrectToolForDrops()
                    .strength(2f,20f)
            ),ModCreativeModTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_LOG_STRIPPED = registerBlock("kuzeyian_log_stripped",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_LOG)
                    .requiresCorrectToolForDrops()
                    .strength(1.9f,20f)
            ),ModCreativeModTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_WOOD = registerBlock("kuzeyian_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_WOOD)
                    .requiresCorrectToolForDrops()
                    .strength(2f,20f)
            ),ModCreativeModTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_WOOD_STRIPPED = registerBlock("kuzeyian_wood_stripped",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_WOOD)
                    .requiresCorrectToolForDrops()
                    .strength(1.9f,20f)
            ),ModCreativeModTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_PLANKS = registerBlock("kuzeyian_planks",
            () -> new ModFlammableBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PLANKS)
                    .requiresCorrectToolForDrops()
                    .strength(1.7f,15f)
            ),ModCreativeModTab.KUZEY_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab, String tooltipKey){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab, tooltipKey);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab, String tooltipKey){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                if (Screen.hasAltDown()){
                    pTooltip.add(new TranslatableComponent(tooltipKey));
                } else {
                    pTooltip.add(new TranslatableComponent("tooltip.kuzey.alt.tooltip"));
                }
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block){
        return BLOCKS.register(name,block);
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }




    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
