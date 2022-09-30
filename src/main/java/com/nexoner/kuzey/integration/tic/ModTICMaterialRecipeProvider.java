package com.nexoner.kuzey.integration.tic;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.library.data.recipe.ICommonRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;


import java.util.function.Consumer;

public class ModTICMaterialRecipeProvider extends RecipeProvider implements IConditionBuilder, IMaterialRecipeHelper, IToolRecipeHelper, ISmelteryRecipeHelper, ICommonRecipeHelper {
    public ModTICMaterialRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }


    @Override
    public String getModId() {
        return KuzeyMod.MOD_ID;
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        String castingFolder = "smeltery/casting/metal/";
        String meltingFolder = "smeltery/melting/metal/";
        String materialFolder = "tools/materials/";


        //Kuzeyium
        metalCasting(pFinishedRecipeConsumer, ModTICMaterialFluids.MOLTEN_KUZEYIUM_OBJECT, ModBlocks.KUZEYIUM_BLOCK.get(), ModItems.KUZEYIUM_INGOT.get(), ModItems.KUZEYIUM_NUGGET.get(), castingFolder, "kuzeyium");
        metalMelting(pFinishedRecipeConsumer, ModTICMaterialFluids.MOLTEN_KUZEYIUM_FLUID.get(),"kuzeyium",true, meltingFolder,false);
        metalMaterialRecipe(pFinishedRecipeConsumer, ModTICMaterials.KUZEYIUM,materialFolder,"kuzeyium",true);
        materialMeltingCasting(pFinishedRecipeConsumer, ModTICMaterials.KUZEYIUM, ModTICMaterialFluids.MOLTEN_KUZEYIUM_OBJECT, materialFolder);

        //Infused Kuzeyium
        gemCasting(pFinishedRecipeConsumer, ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_OBJECT, ModItems.INFUSED_KUZEYIUM_GEM.get(), castingFolder + ModItems.INFUSED_KUZEYIUM_GEM.get().getRegistryName().getPath() + "/gem");
        gemMelting(pFinishedRecipeConsumer, ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_FLUID.get(), "infused_kuzeyium", false, 9, meltingFolder, true);
        metalMaterialRecipe(pFinishedRecipeConsumer, ModTICMaterials.INFUSED_KUZEYIUM,materialFolder,"infused_kuzeyium",true);
        materialMeltingCasting(pFinishedRecipeConsumer, ModTICMaterials.INFUSED_KUZEYIUM, ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_OBJECT, materialFolder);
        ItemCastingRecipeBuilder.basinRecipe(ModBlocks.INFUSED_KUZEYIUM_BLOCK.get())
                .setFluidAndTime(ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_OBJECT, 900)
                .save(pFinishedRecipeConsumer, modResource(castingFolder + ModItems.INFUSED_KUZEYIUM_GEM.get().getRegistryName().getPath() + "/block"));

        //Kuzeyian Wood
        materialRecipe(pFinishedRecipeConsumer, ModTICMaterials.KUZEYIAN_WOOD, Ingredient.of(ModTICMaterialItems.MELDABLE_KUZEYIAN_WOOD.get()), 4, 1, materialFolder + "kuzeyian_wood");
    }
}
