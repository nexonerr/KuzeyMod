package com.nexoner.kuzey.block;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.custom.*;
import com.nexoner.kuzey.item.ModCreativeModeTab;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.world.feature.tree.KuzeyianTreeGrower;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(5f,30f),
                    UniformInt.of(4,8)
                    ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> DEVASTATOR = registerBlock("devastator",
            () -> new DevastatorBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(2f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.devastator");

    public static final RegistryObject<Block> KUZEYIUM_GLASS = registerBlock("kuzeyium_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(0.5f,600f)
            ), ModCreativeModeTab.KUZEY_TAB,"tooltip.kuzey.block.kuzeyium_glass");

    public static final RegistryObject<Block> BLACK_CARROTS_PLANT = registerBlockWithoutBlockItem("black_carrots_plant",
            () -> new BlackCarrotsCropBlock(BlockBehaviour.Properties.copy(Blocks.CARROTS).noOcclusion()));

    public static final RegistryObject<Block> KUZEYIAN_LOG = registerBlock("kuzeyian_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_LOG)
                    .requiresCorrectToolForDrops()
                    .strength(2f,20f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_LOG_STRIPPED = registerBlock("kuzeyian_log_stripped",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_LOG)
                    .requiresCorrectToolForDrops()
                    .strength(1.9f,20f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_WOOD = registerBlock("kuzeyian_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_WOOD)
                    .requiresCorrectToolForDrops()
                    .strength(2f,20f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_WOOD_STRIPPED = registerBlock("kuzeyian_wood_stripped",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_WOOD)
                    .requiresCorrectToolForDrops()
                    .strength(1.9f,20f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_PLANKS = registerBlock("kuzeyian_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_PLANKS)
                    .strength(1.7f,15f)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 16;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 3;
                }
            }, ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_LEAVES = registerBlock("kuzeyian_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_LEAVES)
                    .strength(1.2f,15f)
                    .requiresCorrectToolForDrops()){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 40;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 14;
                }
            }, ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIAN_SAPLING = registerBlock("kuzeyian_sapling",
            () -> new SaplingBlock(new KuzeyianTreeGrower(),BlockBehaviour.Properties.copy(Blocks.DARK_OAK_SAPLING)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> KUZEYIUM_PURIFICATION_CHAMBER = registerBlock("kuzeyium_purification_chamber",
            () -> new KuzeyiumPurificationChamberBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.kuzeyium_purification_chamber");

    public static final RegistryObject<Block> KUZEYIUM_WORKSTATION = registerBlock("kuzeyium_workstation",
            () -> new KuzeyiumWorkstationBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.kuzeyium_workstation");

    public static final RegistryObject<Block> KUZEYIUM_BLOCK = registerBlock("kuzeyium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .strength(5f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> EMRE_ESSENCE_EXTRACTOR = registerBlock("emre_essence_extractor",
            () -> new EmreEssenceExtractorBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.emre_essence_extractor");

    public static final RegistryObject<Block> EMRE_ESSENCE_ROCK_ORE = registerBlock("emre_essence_rock_ore",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(5f,30f),
                    UniformInt.of(4,9)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> DEEPSLATE_EMRE_ESSENCE_ROCK_ORE = registerBlock("deepslate_emre_essence_rock_ore",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(7f,50f),
                    UniformInt.of(4,9)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> WOODEN_MACHINE_FRAME = registerBlock("wooden_machine_frame",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> IRON_MACHINE_FRAME = registerBlock("iron_machine_frame",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> DIAMOND_MACHINE_FRAME = registerBlock("diamond_machine_frame",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> UNACTIVATED_INFUSED_MACHINE_FRAME = registerBlock("unactivated_infused_machine_frame",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> INFUSED_MACHINE_FRAME = registerBlock("infused_machine_frame",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> TRANSMUTATION_TABLE = registerBlock("transmutation_table",
            () -> new TransmutationTableBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.transmutation_table");

    public static final RegistryObject<Block> EMRE_ESSENCE_INFUSER = registerBlock("emre_essence_infuser",
            () -> new EmreEssenceInfuserBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
                    .sound(SoundType.GLASS)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.emre_essence_infuser");

    public static final RegistryObject<Block> DECONDENSATOR = registerBlock("decondensator",
            () -> new DecondensatorBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.decondensator");

    public static final RegistryObject<Block> RAW_KUZEYIUM_BLOCK = registerBlock("raw_kuzeyium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> INFUSED_KUZEYIUM_BLOCK = registerBlock("infused_kuzeyium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> EMRE_ESSENCE_GENERATOR = registerBlock("emre_essence_generator",
            () -> new EmreEssenceGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.emre_essence_generator");

    public static final RegistryObject<Block> STONE_BOUND_GENERATOR_CHASSIS = registerBlock("stone_bound_generator_chassis",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> CHAIN_BOUND_GENERATOR_CHASSIS = registerBlock("chain_bound_generator_chassis",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> LAVA_BOUND_GENERATOR_CHASSIS = registerBlock("lava_bound_generator_chassis",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(3f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> LAVA_INFUSED_COBBLESTONE = registerBlock("lava_infused_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(0.5f,30f)
            ), ModCreativeModeTab.KUZEY_TAB);

    public static final RegistryObject<Block> COMBUSTIBLE_SOLID_GENERATOR = registerBlock("combustible_solid_generator",
            () -> new CombustibleSolidGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.combustible_solid_generator");

    public static final RegistryObject<Block> COMBUSTIBLE_FLUID_GENERATOR = registerBlock("combustible_fluid_generator",
            () -> new CombustibleFluidGeneratorBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.combustible_fluid_generator");

    public static final RegistryObject<Block> WITHER_SKELETON_CONTAINMENT_CHAMBER = registerBlock("wither_skeleton_containment_chamber",
            () -> new WitherSkeletonContainmentChamberBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .noOcclusion()
                    .requiresCorrectToolForDrops()
                    .strength(4f,30f)
            ), ModCreativeModeTab.KUZEY_TAB, "tooltip.kuzey.block.wither_skeleton_containment_chamber");

    //Register Methods
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
                    pTooltip.add(new TranslatableComponent("tooltip.kuzey.alt"));
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
