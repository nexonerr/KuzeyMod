package com.nexoner.kuzey.integration.jei;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.block.custom.CombustibleFluidGeneratorBlock;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.recipe.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

@JeiPlugin
public class JEIKuzeyModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(KuzeyMod.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new KuzeyiumPurificationChamberRecipeCategory(registration.getJeiHelpers().getGuiHelper(), KuzeyCommonConfigs.kuzeyiumPurificationChamberMaxReceived.get()));
        registration.addRecipeCategories(new KuzeyiumWorkstationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EmreEssenceExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper(),KuzeyCommonConfigs.emreEssenceExtractorMaxReceived.get()));
        registration.addRecipeCategories(new TransmutationTableRecipeCategory(registration.getJeiHelpers().getGuiHelper(),KuzeyCommonConfigs.transmutationTableMaxReceived.get()));
        registration.addRecipeCategories(new EmreEssenceInfuserRecipeCategory(registration.getJeiHelpers().getGuiHelper(),KuzeyCommonConfigs.emreEssenceInfuserMaxReceived.get()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(new RecipeType<>(KuzeyiumPurificationChamberRecipeCategory.UID, KuzeyiumPurificationChamberRecipe.class), rm.getAllRecipesFor(KuzeyiumPurificationChamberRecipe.Type.INSTANCE));
        registration.addRecipes(new RecipeType<>(KuzeyiumWorkstationRecipeCategory.UID, KuzeyiumWorkstationRecipe.class), rm.getAllRecipesFor(KuzeyiumWorkstationRecipe.Type.INSTANCE));
        registration.addRecipes(new RecipeType<>(EmreEssenceExtractorRecipeCategory.UID, EmreEssenceExtractorRecipe.class), rm.getAllRecipesFor(EmreEssenceExtractorRecipe.Type.INSTANCE));
        registration.addRecipes(new RecipeType<>(TransmutationTableRecipeCategory.UID, TransmutationTableRecipe.class), rm.getAllRecipesFor(TransmutationTableRecipe.Type.INSTANCE));
        registration.addRecipes(new RecipeType<>(EmreEssenceInfuserRecipeCategory.UID, EmreEssenceInfuserRecipe.class), rm.getAllRecipesFor(EmreEssenceInfuserRecipe.Type.INSTANCE));

        addItemDescriptions(registration);

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.KUZEYIUM_PURIFICATION_CHAMBER.get()), KuzeyiumPurificationChamberRecipeCategory.KUZEYIUM_PURIFICATION);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.KUZEYIUM_WORKSTATION.get()), KuzeyiumWorkstationRecipeCategory.KUZEYIUM_SMITHING);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EMRE_ESSENCE_EXTRACTOR.get()), EmreEssenceExtractorRecipeCategory.EMRE_ESSENCE_EXTRACTION);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.TRANSMUTATION_TABLE.get()), TransmutationTableRecipeCategory.TRANSMUTATION);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EMRE_ESSENCE_INFUSER.get()), EmreEssenceInfuserRecipeCategory.EMRE_ESSENCE_INFUSION);
    }

    private void addItemDescriptions(IRecipeRegistration registration){
        addItemDescription(registration, ModBlocks.COMBUSTIBLE_SOLID_GENERATOR.get());
        addItemDescription(registration, ModBlocks.COMBUSTIBLE_FLUID_GENERATOR.get());
    }

    private void addItemDescription(IRecipeRegistration registration, ItemLike itemLike) {
        if (itemLike instanceof Block){
            Block toUse = (Block) itemLike;
            registration.addIngredientInfo(new ItemStack(toUse, 1), VanillaTypes.ITEM_STACK, new TranslatableComponent(toUse.getDescriptionId() + ".guide"));
        } else if (itemLike instanceof Item){
            Item toUse = (Item) itemLike;
            registration.addIngredientInfo(new ItemStack(toUse, 1), VanillaTypes.ITEM_STACK, new TranslatableComponent(toUse.getDescriptionId() + ".guide"));
        } else {
            KuzeyMod.LOGGER.error("Problem classifying an ItemLike while creating JEI descriptions for Kuzey Mod, skipping");
        }
    }
}
