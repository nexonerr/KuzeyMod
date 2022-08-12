package com.nexoner.kuzey.integration.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.recipe.KuzeyiumPurificationChamberRecipe;
import com.nexoner.kuzey.recipe.KuzeyiumWorkstationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public class KuzeyiumWorkstationRecipeCategory implements IRecipeCategory<KuzeyiumWorkstationRecipe> {
    public static final RecipeType<KuzeyiumWorkstationRecipe> KUZEYIUM_SMITHING =
            RecipeType.create(KuzeyMod.MOD_ID,"kuzeyium_smithing",KuzeyiumWorkstationRecipe.class);
    public final static ResourceLocation UID = new ResourceLocation(KuzeyMod.MOD_ID, "kuzeyium_smithing");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(KuzeyMod.MOD_ID, "textures/gui/jei/kuzeyium_workstation_gui_jei.png");

    private final IDrawableStatic progressStatic;
    private final IDrawableAnimated progressAnim;

    private final IDrawable background;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public KuzeyiumWorkstationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.guiHelper = helper;
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.KUZEYIUM_WORKSTATION.get()));
        this.progressStatic = guiHelper.createDrawable(TEXTURE,176,0, 69, 54);
        this.progressAnim = guiHelper.createAnimatedDrawable(progressStatic, 600, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends KuzeyiumWorkstationRecipe> getRecipeClass() {
        return KuzeyiumWorkstationRecipe.class;
    }

    @Override
    public Component getTitle() {return new TranslatableComponent("block.kuzey.kuzeyium_workstation");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull KuzeyiumWorkstationRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 16).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 35).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 54).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 126, 6).addIngredients(Ingredient.of(ModItems.KUZEYIUM_SAW.get()));
        builder.addSlot(RecipeIngredientRole.INPUT, 149, 6).addIngredients(Ingredient.of(ModItems.KUZEYIUM_SMITHING_HAMMER.get()));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 97, 35).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(KuzeyiumWorkstationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        String recipeTime = (Math.round(recipe.getRecipeTime() / 20)) + " seconds";
        String toolDamage = recipe.getToolDamage() + " durability";

        minecraft.font.draw(stack,recipeTime,113,70,0x404040);
        minecraft.font.draw(stack,toolDamage,104,24,0x404040);

        progressAnim.draw(stack,27,16);
    }
}
