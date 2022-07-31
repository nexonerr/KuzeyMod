package com.nexoner.kuzey.datagen;

import com.google.common.collect.ImmutableList;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    protected static final ImmutableList<ItemLike> KUZEYIUM_SMELTABLES = ImmutableList.of(ModBlocks.KUZEYIUM_ORE.get(), ModItems.RAW_KUZEYIUM.get());

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

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

        ShapedRecipeBuilder.shaped(ModBlocks.DEVASTATOR.get(),12)
                .define('K', ModTags.Items.KUZEYIUM_INGOTS)
                .define('M', Blocks.MAGMA_BLOCK)
                .define('O', Blocks.OBSIDIAN)
                .define('I', Items.IRON_INGOT)
                .pattern("MMM")
                .pattern("IKI")
                .pattern("OOO")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
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

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_HELMET.get())
                .define('F', ModItems.KUZEYIUM_FABRIC.get())
                .pattern("FFF")
                .pattern("F F")
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

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_COAL.get(),4)
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('C', ItemTags.COALS)
                .pattern("CKC")
                .pattern("CCC")
                .unlockedBy("has_coal", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COAL).build()))
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

        ShapedRecipeBuilder.shaped(ModBlocks.KUZEYIUM_GLASS.get(),6)
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('G', Blocks.GLASS)
                .pattern("GGG")
                .pattern("KKK")
                .pattern("GGG")
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

        ShapedRecipeBuilder.shaped(ModItems.KUZEYIUM_MESH.get())
                .define('K', ModItems.KUZEYIUM_NUGGET.get())
                .define('S', Items.STRING)
                .pattern("KKS")
                .pattern("KSK")
                .pattern("SKK")
                .unlockedBy("has_kuzeyium_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_NUGGET.get()).build()))
                .save(pFinishedRecipeConsumer);

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

        ShapedRecipeBuilder.shaped(ModBlocks.KUZEYIUM_WORKSTATION.get())
                .define('K', ModItems.KUZEYIUM_INGOT.get())
                .define('G', ModItems.KUZEYIUM_GLASS_PLATE.get())
                .define('E', Items.END_STONE_BRICKS)
                .define('L', ModTags.Items.KUZEYIAN_LOGS)
                .pattern("KGK")
                .pattern("KLK")
                .pattern("KEK")
                .unlockedBy("has_kuzeyium_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.KUZEYIUM_INGOTS).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModItems.KUZEYIUM_INGOT.get())
                .requires(ModItems.KUZEYIUM_CHUNK.get(),3)
                .requires(Items.NETHER_STAR)
                .unlockedBy("has_kuzeyium_chunk", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.KUZEYIUM_CHUNK.get()).build()))
                .save(pFinishedRecipeConsumer);

        oreSmelting(pFinishedRecipeConsumer, KUZEYIUM_SMELTABLES, ModItems.KUZEYIUM_CHUNK.get(), 1.0F, 300, "kuzeyium");
        oreBlasting(pFinishedRecipeConsumer, KUZEYIUM_SMELTABLES, ModItems.KUZEYIUM_CHUNK.get(), 1.0F, 150, "kuzeyium");

        planksFromLogs(pFinishedRecipeConsumer,ModBlocks.KUZEYIAN_PLANKS.get(), ModTags.Items.KUZEYIAN_LOGS);
        woodFromLogs(pFinishedRecipeConsumer,ModBlocks.KUZEYIAN_WOOD.get(),ModBlocks.KUZEYIAN_LOG.get());
        woodFromLogs(pFinishedRecipeConsumer,ModBlocks.KUZEYIAN_WOOD_STRIPPED.get(),ModBlocks.KUZEYIAN_LOG_STRIPPED.get());

        nineBlockStorageRecipesWithCustomPacking(pFinishedRecipeConsumer, ModItems.KUZEYIUM_NUGGET.get(), ModItems.KUZEYIUM_INGOT.get(), "kuzeyium_ingot_from_nuggets", "kuzeyium_ingot");
    }
}
