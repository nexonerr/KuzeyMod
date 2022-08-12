package com.nexoner.kuzey.integration.jei;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.recipe.EmreEssenceExtractorRecipe;
import com.nexoner.kuzey.recipe.KuzeyiumPurificationChamberRecipe;
import com.nexoner.kuzey.recipe.KuzeyiumWorkstationRecipe;
import com.nexoner.kuzey.recipe.TransmutationTableRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Objects;

@JeiPlugin
public class JEIKuzeyModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(KuzeyMod.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new KuzeyiumPurificationChamberRecipeCategory(registration.getJeiHelpers().getGuiHelper(),80000));
        registration.addRecipeCategories(new KuzeyiumWorkstationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EmreEssenceExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper(),140000));
        registration.addRecipeCategories(new TransmutationTableRecipeCategory(registration.getJeiHelpers().getGuiHelper(),110000));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(new RecipeType<>(KuzeyiumPurificationChamberRecipeCategory.UID, KuzeyiumPurificationChamberRecipe.class), rm.getAllRecipesFor(KuzeyiumPurificationChamberRecipe.Type.INSTANCE));
        registration.addRecipes(new RecipeType<>(KuzeyiumWorkstationRecipeCategory.UID, KuzeyiumWorkstationRecipe.class), rm.getAllRecipesFor(KuzeyiumWorkstationRecipe.Type.INSTANCE));
        registration.addRecipes(new RecipeType<>(EmreEssenceExtractorRecipeCategory.UID, EmreEssenceExtractorRecipe.class), rm.getAllRecipesFor(EmreEssenceExtractorRecipe.Type.INSTANCE));
        registration.addRecipes(new RecipeType<>(TransmutationTableRecipeCategory.UID, TransmutationTableRecipe.class), rm.getAllRecipesFor(TransmutationTableRecipe.Type.INSTANCE));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.KUZEYIUM_PURIFICATION_CHAMBER.get()), KuzeyiumPurificationChamberRecipeCategory.KUZEYIUM_PURIFICATION);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.KUZEYIUM_WORKSTATION.get()), KuzeyiumWorkstationRecipeCategory.KUZEYIUM_SMITHING);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EMRE_ESSENCE_EXTRACTOR.get()), EmreEssenceExtractorRecipeCategory.EMRE_ESSENCE_EXTRACTION);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.TRANSMUTATION_TABLE.get()), TransmutationTableRecipeCategory.TRANSMUTATION);
    }
}
