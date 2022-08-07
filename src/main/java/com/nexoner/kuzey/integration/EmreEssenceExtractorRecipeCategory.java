package com.nexoner.kuzey.integration;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.recipe.EmreEssenceExtractorRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class EmreEssenceExtractorRecipeCategory implements IRecipeCategory<EmreEssenceExtractorRecipe> {
    public static final RecipeType<EmreEssenceExtractorRecipe> EMRE_ESSENCE_EXTRACTION =
            RecipeType.create(KuzeyMod.MOD_ID,"emre_essence_extraction",EmreEssenceExtractorRecipe.class);
    public final static ResourceLocation UID = new ResourceLocation(KuzeyMod.MOD_ID, "emre_essence_extraction");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(KuzeyMod.MOD_ID, "textures/gui/jei/emre_essence_extractor_gui_jei.png");
    private IDrawableStatic energyStatic;
    //IDrawableAnimated energyAnim;

    private final IDrawableStatic progressStatic;
    private final IDrawableAnimated progressAnim;

    private final IDrawable background;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;
    private final int maxEnergy;

    private final int BAR_SIZE = 39;

    public EmreEssenceExtractorRecipeCategory(IGuiHelper helper, int maxEnergy) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.EMRE_ESSENCE_EXTRACTOR.get()));
        this.guiHelper = helper;
        this.maxEnergy = maxEnergy;
        this.energyStatic = this.guiHelper.createDrawable(TEXTURE, 178, 20, 9, 39);
        this.progressStatic = this.guiHelper.createDrawable(TEXTURE,176,5,32,14);
        this.progressAnim = this.guiHelper.createAnimatedDrawable(progressStatic, 300, IDrawableAnimated.StartDirection.LEFT,false);
    }

    //We have to call this method because the constructor can't take recipes therefore we can't get the usage cost before we initialize our energy bar
    private int setupEnergyBar(EmreEssenceExtractorRecipe recipe){
            int usageCost = recipe.getUsageCost();
            int scaledBar = usageCost * BAR_SIZE / maxEnergy;

            energyStatic = this.guiHelper.createDrawable(TEXTURE, 178, 58 - scaledBar, 9, scaledBar);
            return scaledBar;
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends EmreEssenceExtractorRecipe> getRecipeClass() {
        return EmreEssenceExtractorRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block.kuzey.emre_essence_extractor");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull EmreEssenceExtractorRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 46, 37).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 26)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(recipe.getResultFluid()))
                .setFluidRenderer(30000, false, 16,41);
    }

    @Override
    public void draw(EmreEssenceExtractorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();

        String recipeTime = Math.round(recipe.getRecipeTime() / 20) + " seconds";
        String usageCost = recipe.getUsageCost() + "FE";
        String fluid = recipe.getResultFluid().getAmount() + "mb of " + recipe.getResultFluid().getFluid().getRegistryName().toString();

        minecraft.font.draw(stack,recipeTime,15,56,0x404040);
        minecraft.font.draw(stack,usageCost,120,15,0x404040);
        minecraft.font.draw(stack,fluid,15,72,0x404040);

        int scaledBar = setupEnergyBar(recipe);
        energyStatic.draw(stack,140,66 - scaledBar);

        progressAnim.draw(stack,46,37);
    }
}
