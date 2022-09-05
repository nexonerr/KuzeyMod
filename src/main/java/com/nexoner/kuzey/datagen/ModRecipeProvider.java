package com.nexoner.kuzey.datagen;

import com.google.common.collect.ImmutableList;
import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.datagen.custom.*;
import com.nexoner.kuzey.fluid.ModFluids;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    protected static final ImmutableList<ItemLike> KUZEYIUM_SMELTABLES = ImmutableList.of(ModBlocks.KUZEYIUM_ORE.get(), ModItems.RAW_KUZEYIUM.get());
    protected static final ImmutableList<ItemLike> EMRE_ESSENCE_SMELTABLES = ImmutableList.of(ModBlocks.EMRE_ESSENCE_ROCK_ORE.get(), ModBlocks.DEEPSLATE_EMRE_ESSENCE_ROCK_ORE.get());

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        ToolRecipes(pFinishedRecipeConsumer);
        SmithingHammerRecipes(pFinishedRecipeConsumer);
        AdvancedItems(pFinishedRecipeConsumer);
        AdvancedBlocks(pFinishedRecipeConsumer);
        ArmorRecipes(pFinishedRecipeConsumer);
        CraftingComponents(pFinishedRecipeConsumer);
        MachineRecipes(pFinishedRecipeConsumer);
        MachineFrames(pFinishedRecipeConsumer);

        UncategorizedRecipes(pFinishedRecipeConsumer);

        OreSmelting(pFinishedRecipeConsumer);
        WoodRecipes(pFinishedRecipeConsumer);
        ConvertableRecipes(pFinishedRecipeConsumer);

        //CUSTOM RECIPE TYPES
        EmreEssenceExtractorRecipes(pFinishedRecipeConsumer);
        EmreEssenceInfuserRecipes(pFinishedRecipeConsumer);
        KuzeyiumPurificationChamberRecipes(pFinishedRecipeConsumer);
        KuzeyiumWorkstationRecipes(pFinishedRecipeConsumer);
        TransmutationTableRecipes(pFinishedRecipeConsumer);
    }
    //-Custom Recipe Types-
    private void EmreEssenceInfuserRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){

        new EmreEssenceInfuserRecipeBuilder(ModItems.INFUSED_KUZEYIUM_GEM.get(), ModItems.ASCENDED_KUZEYIUM_GEM.get(), 1, 440, 22000,
                new FluidStack(ModFluids.EMRE_ESSENCE_FLUID.get(), 800))
                .unlockedBy("has_ascended_kuzeyium_gem", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ASCENDED_KUZEYIUM_GEM.get()).build())).save(pFinishedRecipeConsumer);

    }

    private void EmreEssenceExtractorRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){

        new EmreEssenceExtractorRecipeBuilder(new FluidStack(ModFluids.EMRE_ESSENCE_FLUID.get(), 500), ModItems.EMRE_ESSENCE_ROCK.get(), 300, 22000)
                .unlockedBy("has_emre_essence_rock", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.EMRE_ESSENCE_ROCK.get()).build())).save(pFinishedRecipeConsumer);

        new EmreEssenceExtractorRecipeBuilder(new FluidStack(Fluids.LAVA, 500), Blocks.MAGMA_BLOCK, 40, 800)
                .unlockedBy("has_magma_block", inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.MAGMA_BLOCK).build())).save(pFinishedRecipeConsumer);

        new EmreEssenceExtractorRecipeBuilder(new FluidStack(Fluids.LAVA, 200), Blocks.NETHERRACK, 30, 600)
                .unlockedBy("has_netherrack", inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.NETHERRACK).build())).save(pFinishedRecipeConsumer);

    }

    private void KuzeyiumPurificationChamberRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){

        new KuzeyiumPurificationChamberRecipeBuilder(ModItems.KUZEYIUM_INGOT.get(), 1, 300, 10000, ModItems.KUZEYIUM_CHUNK.get(), Items.NETHER_STAR)
                .unlockedBy("has_kuzeyium_chunk", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.KUZEYIUM_CHUNK.get()).build())).save(pFinishedRecipeConsumer);

        new KuzeyiumPurificationChamberRecipeBuilder(ModBlocks.INFUSED_MACHINE_FRAME.get(), 1, 900, 15000, ModBlocks.UNACTIVATED_INFUSED_MACHINE_FRAME.get(), Items.NETHER_STAR)
                .unlockedBy("has_unactivated_infused_machine_frame", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.UNACTIVATED_INFUSED_MACHINE_FRAME.get()).build())).save(pFinishedRecipeConsumer);

    }

    private void KuzeyiumWorkstationRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){

        new KuzeyiumWorkstationRecipeBuilder(ModItems.KUZEYIUM_INGOT.get(), 1, 600, 4, ModItems.KUZEYIUM_CHUNK.get(), Items.NETHER_STAR, ModItems.KUZEYIUM_CHUNK.get())
                .unlockedBy("has_kuzeyium_chunk", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.KUZEYIUM_CHUNK.get()).build())).save(pFinishedRecipeConsumer);

        new KuzeyiumWorkstationRecipeBuilder(ModItems.KUZEYIUM_PLATE.get(), 1, 300, 9, ModItems.ASCENDED_KUZEYIUM_GEM.get(), ModItems.ASCENDED_KUZEYIUM_GEM.get(), ModItems.ASCENDED_KUZEYIUM_GEM.get())
                .unlockedBy("has_ascended_kuzeyium_gem", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ASCENDED_KUZEYIUM_GEM.get()).build())).save(pFinishedRecipeConsumer);

        new KuzeyiumWorkstationRecipeBuilder(ModItems.REINFORCED_KUZEYIAN_TOOL_HANDLE.get(), 1, 750, 12, ModItems.KUZEYIAN_TOOL_HANDLE.get(), ModItems.ASCENDED_KUZEYIUM_GEM.get(), ModItems.KUZEYIAN_TOOL_HANDLE.get())
                .unlockedBy("has_ascended_kuzeyium_gem", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ASCENDED_KUZEYIUM_GEM.get()).build())).save(pFinishedRecipeConsumer);

        new KuzeyiumWorkstationRecipeBuilder(ModItems.KUZEYIUM_PURIFICATION_CORE.get(), 1, 1500, 250, ModBlocks.KUZEYIUM_BLOCK.get(), Items.NETHER_STAR, ModBlocks.KUZEYIUM_BLOCK.get())
                .unlockedBy("has_kuzeyium_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.KUZEYIUM_BLOCK.get()).build())).save(pFinishedRecipeConsumer);

    }

    private void TransmutationTableRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){

        new TransmutationTableRecipeBuilder(ModItems.BLACK_CARROTS.get(), 1, Items.CARROT, 64, 400, 8000)
                .unlockedBy("has_carrots", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CARROT).build())).save(pFinishedRecipeConsumer);

        new TransmutationTableRecipeBuilder(ModItems.ASCENDED_KUZEYIUM_GEM.get(), 1, ModItems.KUZEYIUM_NUGGET.get(), 10, 560, 12000)
                .unlockedBy("has_kuzeyium_nugget", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.KUZEYIUM_NUGGET.get()).build())).save(pFinishedRecipeConsumer);

        new TransmutationTableRecipeBuilder(Items.DIAMOND, 1, Items.COAL, 18, 100, 3500)
                .unlockedBy("has_coal", inventoryTrigger(ItemPredicate.Builder.item().of(Items.COAL).build())).save(pFinishedRecipeConsumer);

        new TransmutationTableRecipeBuilder(Blocks.MELON, 1, Blocks.PUMPKIN, 1, 20, 500)
                .unlockedBy("has_pumpkin", inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.PUMPKIN).build())).save(pFinishedRecipeConsumer);

        new TransmutationTableRecipeBuilder(Blocks.PUMPKIN, 1, Blocks.MELON, 1, 20, 500)
                .unlockedBy("has_melon", inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.MELON).build())).save(pFinishedRecipeConsumer);

        new TransmutationTableRecipeBuilder(Items.RAW_IRON, 1, Blocks.COBBLESTONE, 32, 15, 1200)
                .unlockedBy("has_cobblestone", inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.COBBLESTONE).build())).save(pFinishedRecipeConsumer);

        new TransmutationTableRecipeBuilder(Items.NETHERITE_SCRAP, 6, ModItems.KUZEYIUM_INGOT.get(), 1, 900, 13000)
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(ModTags.Items.KUZEYIUM_INGOTS).build())).save(pFinishedRecipeConsumer);

        new TransmutationTableRecipeBuilder(Items.BAKED_POTATO, 2, Items.POTATO, 1, 5, 400)
                .unlockedBy("has_potato", inventoryTrigger(ItemPredicate.Builder.item().of(Items.POTATO).build())).save(pFinishedRecipeConsumer);

    }
    //-Custom Recipe Types-

    //-Unshaped Recipes-
    private void OreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        oreSmelting(pFinishedRecipeConsumer, KUZEYIUM_SMELTABLES, ModItems.KUZEYIUM_CHUNK.get(), 1.0F, 300, "kuzey:kuzeyium");
        oreBlasting(pFinishedRecipeConsumer, KUZEYIUM_SMELTABLES, ModItems.KUZEYIUM_CHUNK.get(), 1.0F, 150, "kuzey:kuzeyium");
        oreSmelting(pFinishedRecipeConsumer, EMRE_ESSENCE_SMELTABLES, ModItems.EMRE_ESSENCE_ROCK.get(), 2.0F, 300, "kuzey:emre_essence_rock");
        oreBlasting(pFinishedRecipeConsumer, EMRE_ESSENCE_SMELTABLES, ModItems.EMRE_ESSENCE_ROCK.get(), 2.0F, 150, "kuzey:emre_essence_rock");
    }

    private void WoodRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        planksFromLogs(pFinishedRecipeConsumer,ModBlocks.KUZEYIAN_PLANKS.get(), ModTags.Items.KUZEYIAN_LOGS);
        woodFromLogs(pFinishedRecipeConsumer,ModBlocks.KUZEYIAN_WOOD.get(),ModBlocks.KUZEYIAN_LOG.get());
        woodFromLogs(pFinishedRecipeConsumer,ModBlocks.KUZEYIAN_WOOD_STRIPPED.get(),ModBlocks.KUZEYIAN_LOG_STRIPPED.get());
    }

    private void ConvertableRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        createBlockRecipeReconvertable(pFinishedRecipeConsumer,ModBlocks.KUZEYIUM_BLOCK.get(),ModItems.KUZEYIUM_INGOT.get(),"kuzeyium_ingot");
        createBlockRecipeReconvertable(pFinishedRecipeConsumer,ModItems.KUZEYIUM_INGOT.get(),ModItems.KUZEYIUM_NUGGET.get(),"kuzeyium_ingot");
        createBlockRecipeReconvertable(pFinishedRecipeConsumer,ModBlocks.RAW_KUZEYIUM_BLOCK.get(),ModItems.RAW_KUZEYIUM.get(),"raw_kuzeyium");
        createBlockRecipeReconvertable(pFinishedRecipeConsumer,ModBlocks.INFUSED_KUZEYIUM_BLOCK.get(),ModItems.INFUSED_KUZEYIUM_GEM.get(),"infused_kuzeyium");
    }

    private void SmithingHammerRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        ShapelessRecipeBuilder.shapeless(ModItems.KUZEYIUM_INGOT.get())
                .requires(ModItems.KUZEYIUM_CHUNK.get(),3)
                .requires(Items.NETHER_STAR)
                .requires(ModItems.KUZEYIUM_SMITHING_HAMMER.get())
                .unlockedBy("has_kuzeyium_chunk", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_CHUNK.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModItems.KUZEYIUM_PLATE.get())
                .requires(ModItems.ASCENDED_KUZEYIUM_GEM.get(),4)
                .requires(ModItems.KUZEYIUM_SMITHING_HAMMER.get())
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);
    }
    //-Unshaped Recipes-

    //-Shaped Recipes-
    private void ToolRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_SAW.get())
                .define('K', ModTags.Items.KUZEYIUM_INGOTS)
                .define('S', Items.STRING)
                .define('H', ModItems.KUZEYIAN_TOOL_HANDLE.get())
                .pattern(" KK")
                .pattern("SK ")
                .pattern("H  ")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_SMITHING_HAMMER.get())
                .define('K', ModItems.KUZEYIUM_CHUNK.get())
                .define('S', Items.STICK)
                .pattern("KKK")
                .pattern("KS ")
                .pattern(" S ")
                .unlockedBy("has_kuzeyium_chunk", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_CHUNK.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.INFUSED_KUZEYIUM_PICKAXE.get())
                .define('S', ModItems.REINFORCED_KUZEYIAN_TOOL_HANDLE.get())
                .define('K', ModItems.INFUSED_KUZEYIUM_GEM.get())
                .define('P', ModItems.KUZEYIUM_PICKAXE.get())
                .pattern("KKK")
                .pattern(" P ")
                .pattern(" S ")
                .unlockedBy("has_infused_kuzeyium_gem", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_KUZEYIUM_GEM.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.INFUSED_KUZEYIUM_AXE.get())
                .define('S', ModItems.REINFORCED_KUZEYIAN_TOOL_HANDLE.get())
                .define('K', ModItems.INFUSED_KUZEYIUM_GEM.get())
                .define('A', ModItems.KUZEYIUM_AXE.get())
                .pattern("KK ")
                .pattern("KA ")
                .pattern(" S ")
                .unlockedBy("has_infused_kuzeyium_gem", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_KUZEYIUM_GEM.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_MINING_HAMMER.get())
                .define('S', ModItems.REINFORCED_KUZEYIAN_TOOL_HANDLE.get())
                .define('K', ModItems.INFUSED_KUZEYIUM_GEM.get())
                .define('P', ModItems.KUZEYIUM_PICKAXE.get())
                .define('X', ModItems.KUZEYIUM_SHOVEL.get())
                .pattern("KKK")
                .pattern("KSP")
                .pattern(" SX")
                .unlockedBy("has_infused_kuzeyium_gem", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.INFUSED_KUZEYIUM_GEM.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_SHOVEL.get())
                .define('S', ModItems.KUZEYIAN_TOOL_HANDLE.get())
                .define('K', ModTags.Items.KUZEYIUM_INGOTS)
                .pattern(" K ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_PICKAXE.get())
                .define('S', ModItems.KUZEYIAN_TOOL_HANDLE.get())
                .define('K', ModTags.Items.KUZEYIUM_INGOTS)
                .pattern("KKK")
                .pattern(" K ")
                .pattern(" S ")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_AXE.get())
                .define('S', ModItems.KUZEYIAN_TOOL_HANDLE.get())
                .define('K', ModTags.Items.KUZEYIUM_INGOTS)
                .pattern("KK ")
                .pattern("KS ")
                .pattern(" S ")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_SWORD.get())
                .define('S', ModItems.KUZEYIAN_TOOL_HANDLE.get())
                .define('K', ModTags.Items.KUZEYIUM_INGOTS)
                .pattern(" K ")
                .pattern(" K ")
                .pattern(" S ")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.SPAWNER_IRRITATOR.get())
                .define('H', ModItems.KUZEYIAN_TOOL_HANDLE.get())
                .define('K', ModTags.Items.KUZEYIUM_INGOTS)
                .define('E', Blocks.END_STONE_BRICKS)
                .define('G', ModItems.KUZEYIUM_GLASS_PLATE.get())
                .define('N', ModItems.KUZEYIUM_NUGGET.get())
                .pattern(" K ")
                .pattern("EGN")
                .pattern("HN ")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);
    }

    private void AdvancedItems(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_COAL.get(),4)
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('C', ItemTags.COALS)
                .pattern("CKC")
                .pattern("CCC")
                .unlockedBy("has_coal", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COAL).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.REGENERATOR.get())
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('G', Items.GOLDEN_APPLE)
                .define('L', Blocks.GLASS)
                .define('I', Items.IRON_INGOT)
                .pattern(" G ")
                .pattern("LKL")
                .pattern("IKI")
                .unlockedBy("has_kuzeyium_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_NUGGET.get()).build()))
                .save(pFinishedRecipeConsumer);
    }

    private void AdvancedBlocks(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        ShapedRecipeBuilder.shaped(ModBlocks.DEVASTATOR.get(),12)
                .define('W', ModBlocks.WOODEN_MACHINE_FRAME.get())
                .define('M', Blocks.MAGMA_BLOCK)
                .define('O', Blocks.OBSIDIAN)
                .define('I', Items.IRON_INGOT)
                .pattern("MMM")
                .pattern("IWI")
                .pattern("OOO")
                .unlockedBy("has_wooden_machine_frame", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.WOODEN_MACHINE_FRAME.get()).build()))
                .save(pFinishedRecipeConsumer);
    }

    private void ArmorRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_HELMET.get())
                .define('F', ModItems.KUZEYIUM_FABRIC.get())
                .define('G', ModItems.KUZEYIUM_GLASS_PLATE.get())
                .pattern("FFF")
                .pattern("FGF")
                .unlockedBy("has_kuzeyium_fabric", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_FABRIC.get()).build()))
                .save(pFinishedRecipeConsumer);


        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_CHESTPLATE.get())
                .define('F', ModItems.KUZEYIUM_FABRIC.get())
                .pattern("F F")
                .pattern("FFF")
                .pattern("FFF")
                .unlockedBy("has_kuzeyium_fabric", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_FABRIC.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_LEGGINGS.get())
                .define('F', ModItems.KUZEYIUM_FABRIC.get())
                .pattern("FFF")
                .pattern("F F")
                .pattern("F F")
                .unlockedBy("has_kuzeyium_fabric", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_FABRIC.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_BOOTS.get())
                .define('F', ModItems.KUZEYIUM_FABRIC.get())
                .pattern("F F")
                .pattern("F F")
                .unlockedBy("has_kuzeyium_fabric", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_FABRIC.get()).build()))
                .save(pFinishedRecipeConsumer);
    }

    private void CraftingComponents(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        ShapedRecipeBuilder.shaped(ModItems.STRUCTURE_BINDER.get())
                .define('I', Items.IRON_INGOT)
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('B', Blocks.IRON_BLOCK)
                .pattern("IKI")
                .pattern("KBK")
                .pattern("IKI")
                .unlockedBy("has_kuzeyium_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_NUGGET.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_HEATING_COIL.get())
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .pattern("IRI")
                .pattern("KKK")
                .pattern("IRI")
                .unlockedBy("has_kuzeyium_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_NUGGET.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.HEATING_FIN.get())
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('I', Items.IRON_INGOT)
                .define('C', ItemTags.COALS)
                .define('B', Blocks.IRON_BLOCK)
                .pattern("ICC")
                .pattern("IKB")
                .pattern("ICC")
                .unlockedBy("has_kuzeyium_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_NUGGET.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_FABRIC.get())
                .define('M', ModItems.KUZEYIUM_MESH.get())
                .define('S', Items.STRING)
                .define('T', Items.STICK)
                .pattern("SM")
                .pattern("MT")
                .unlockedBy("has_kuzeyium_mesh", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_MESH.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_MESH.get())
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('S', Items.STRING)
                .pattern("KKS")
                .pattern("KSK")
                .pattern("SKK")
                .unlockedBy("has_kuzeyium_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_NUGGET.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_GLASS_PLATE.get())
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('G', ModBlocks.KUZEYIUM_GLASS.get())
                .define('H', ModItems.KUZEYIUM_SMITHING_HAMMER.get())
                .pattern("KHK")
                .pattern("GGG")
                .pattern("GGG")
                .unlockedBy("has_kuzeyium_glass", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.KUZEYIUM_GLASS.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIAN_TOOL_HANDLE.get())
                .define('S',Items.STRING)
                .define('H', ModItems.KUZEYIUM_SMITHING_HAMMER.get())
                .define('L', ModTags.Items.KUZEYIAN_LOGS)
                .pattern("SLS")
                .pattern("HLS")
                .pattern("SLS")
                .unlockedBy("has_smithing_hammer", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_SMITHING_HAMMER.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModItems.STILL_FLUID_TANK.get())
                .define('G', ModBlocks.KUZEYIUM_GLASS.get())
                .define('P', ModItems.KUZEYIUM_GLASS_PLATE.get())
                .pattern("GPG")
                .pattern("G G")
                .pattern("GGG")
                .unlockedBy("has_kuzeyium_glass", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.KUZEYIUM_GLASS.get()).build()))
                .save(pFinishedRecipeConsumer);
    }

    private void MachineRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        ShapedRecipeBuilder.shaped(ModBlocks.KUZEYIUM_PURIFICATION_CHAMBER.get())
                .define('K', ModItems.KUZEYIUM_INGOT.get())
                .define('L', ModTags.Items.KUZEYIAN_LOGS)
                .define('M', ModBlocks.DIAMOND_MACHINE_FRAME.get())
                .define('G', ModItems.KUZEYIUM_GLASS_PLATE.get())
                .define('C', ModItems.KUZEYIUM_PURIFICATION_CORE.get())
                .pattern("KGK")
                .pattern("LCL")
                .pattern("KMK")
                .unlockedBy("has_kuzeyium_workstation", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.KUZEYIUM_WORKSTATION.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.KUZEYIUM_WORKSTATION.get())
                .define('K', ModItems.KUZEYIUM_INGOT.get())
                .define('G', ModItems.KUZEYIUM_GLASS_PLATE.get())
                .define('E', Items.END_STONE_BRICKS)
                .define('M', ModBlocks.IRON_MACHINE_FRAME.get())
                .pattern("KGK")
                .pattern("KMK")
                .pattern("KEK")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.EMRE_ESSENCE_EXTRACTOR.get())
                .define('S', ModItems.STRUCTURE_BINDER.get())
                .define('I', Blocks.IRON_BLOCK)
                .define('T', ModItems.STILL_FLUID_TANK.get())
                .define('M', ModBlocks.DIAMOND_MACHINE_FRAME.get())
                .define('K', ModItems.KUZEYIUM_PLATE.get())
                .define('E', ModItems.EMRE_ESSENCE_ROCK.get())
                .define('O', Blocks.OBSIDIAN)
                .pattern("SIS")
                .pattern("TMK")
                .pattern("EOK")
                .unlockedBy("has_emre_essence_rock", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.EMRE_ESSENCE_ROCK.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.TRANSMUTATION_TABLE.get())
                .define('G', ModItems.KUZEYIUM_GLASS_PLATE.get())
                .define('E', Blocks.END_STONE_BRICKS)
                .define('M', ModBlocks.IRON_MACHINE_FRAME.get())
                .define('K', ModTags.Items.KUZEYIUM_INGOTS)
                .define('L', ModTags.Items.KUZEYIAN_LOGS)
                .define('D', Blocks.DIAMOND_BLOCK)
                .define('B', ModBlocks.KUZEYIUM_BLOCK.get())
                .pattern("DGB")
                .pattern("LML")
                .pattern("KEK")
                .unlockedBy("has_iron_machine_frame", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.IRON_MACHINE_FRAME.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.EMRE_ESSENCE_INFUSER.get())
                .define('G', ModBlocks.KUZEYIUM_GLASS.get())
                .define('E', ModItems.EMRE_ESSENCE_BUCKET.get())
                .define('M', ModBlocks.DIAMOND_MACHINE_FRAME.get())
                .define('P', ModItems.KUZEYIUM_PLATE.get())
                .define('B', ModItems.STRUCTURE_BINDER.get())
                .define('T', ModItems.STILL_FLUID_TANK.get())
                .define('I', Blocks.IRON_BLOCK)
                .pattern("TEB")
                .pattern("GMP")
                .pattern("IBP")
                .unlockedBy("has_diamond_machine_frame", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.DIAMOND_MACHINE_FRAME.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.DECONDENSATOR.get())
                .define('H', ModItems.HEATING_FIN.get())
                .define('C', ModItems.KUZEYIUM_HEATING_COIL.get())
                .define('M', ModBlocks.IRON_MACHINE_FRAME.get())
                .define('I', Blocks.IRON_BLOCK)
                .pattern("HCH")
                .pattern("IMI")
                .pattern("HCH")
                .unlockedBy("has_iron_machine_frame", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.IRON_MACHINE_FRAME.get()).build()))
                .save(pFinishedRecipeConsumer);
    }

    private void MachineFrames(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        ShapedRecipeBuilder.shaped(ModBlocks.WOODEN_MACHINE_FRAME.get())
                .define('K', ModItems.KUZEYIUM_INGOT.get())
                .define('L', ModTags.Items.KUZEYIAN_LOGS)
                .define('W', ItemTags.LOGS)
                .pattern("LWL")
                .pattern("WKW")
                .pattern("LWL")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.IRON_MACHINE_FRAME.get())
                .define('M', ModBlocks.WOODEN_MACHINE_FRAME.get())
                .define('S', ModItems.STRUCTURE_BINDER.get())
                .define('I', Blocks.IRON_BLOCK)
                .define('K', ModTags.Items.KUZEYIUM_INGOTS)
                .pattern("ISI")
                .pattern("IMI")
                .pattern("IKI")
                .unlockedBy("has_wooden_machine_frame", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.WOODEN_MACHINE_FRAME.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.DIAMOND_MACHINE_FRAME.get())
                .define('M', ModBlocks.IRON_MACHINE_FRAME.get())
                .define('S', ModItems.STRUCTURE_BINDER.get())
                .define('I', Blocks.DIAMOND_BLOCK)
                .define('K', ModItems.KUZEYIUM_PLATE.get())
                .pattern("ISI")
                .pattern("IMI")
                .pattern("IKI")
                .unlockedBy("has_iron_machine_frame", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.IRON_MACHINE_FRAME.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.UNACTIVATED_INFUSED_MACHINE_FRAME.get())
                .define('M', ModBlocks.DIAMOND_MACHINE_FRAME.get())
                .define('O', Blocks.OBSIDIAN)
                .define('H', ModItems.KUZEYIUM_SMITHING_HAMMER.get())
                .pattern("OHO")
                .pattern("OMO")
                .pattern("OOO")
                .unlockedBy("has_diamond_machine_frame", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.DIAMOND_MACHINE_FRAME.get()).build()))
                .save(pFinishedRecipeConsumer);
    }

    private void UncategorizedRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer){
        ShapedRecipeBuilder.shaped(ModBlocks.KUZEYIUM_GLASS.get(),6)
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('G', Blocks.GLASS)
                .pattern("GGG")
                .pattern("KKK")
                .pattern("GGG")
                .unlockedBy("has_kuzeyium_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_NUGGET.get()).build()))
                .save(pFinishedRecipeConsumer);
    }
    //-Shaped Recipes-




    //Method
    protected void createBlockRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pIngredient, String pGroup){
        ShapedRecipeBuilder.shaped(pResult)
                .define('X', pIngredient)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .group(pGroup)
                .unlockedBy(getHasName(pIngredient), inventoryTrigger(ItemPredicate.Builder.item().of(pIngredient).build())).save(pFinishedRecipeConsumer, KuzeyMod.MOD_ID + ":" + pResult.asItem().getRegistryName().getPath() + "_from_" + pIngredient.asItem().getRegistryName().getPath());
    }
    protected void createBlockRecipeReconvertable(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pResult, ItemLike pIngredient, String pGroup){
        createBlockRecipe(pFinishedRecipeConsumer, pResult, pIngredient, pGroup);
        ShapelessRecipeBuilder.shapeless(pIngredient,9).requires(pResult).group(pGroup).unlockedBy(getHasName(pResult), inventoryTrigger(ItemPredicate.Builder.item().of(pResult).build())).save(pFinishedRecipeConsumer,KuzeyMod.MOD_ID + ":" + pIngredient.asItem().getRegistryName().getPath() + "_from_" + pResult.asItem().getRegistryName().getPath());
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, SimpleCookingSerializer<?> pCookingSerializer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), pResult, pExperience, pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer, KuzeyMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }
}
