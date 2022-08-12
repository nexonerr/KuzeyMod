package com.nexoner.kuzey.integration.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.recipe.TransmutationTableRecipe;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class TransmutationTableRecipeCategory implements IRecipeCategory<TransmutationTableRecipe> {
    public static final RecipeType<TransmutationTableRecipe> TRANSMUTATION =
            RecipeType.create(KuzeyMod.MOD_ID,"transmutation",TransmutationTableRecipe.class);
    public final static ResourceLocation UID = new ResourceLocation(KuzeyMod.MOD_ID, "transmutation");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(KuzeyMod.MOD_ID, "textures/gui/jei/transmutation_table_gui_jei.png");
    private IDrawableStatic energyStatic;
    //IDrawableAnimated energyAnim;

    private final IDrawableStatic progressStatic;
    private final IDrawableAnimated progressAnim;

    private final IDrawable background;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;
    private final int maxEnergy;

    private final int BAR_SIZE = 39;

    public TransmutationTableRecipeCategory(IGuiHelper helper, int maxEnergy) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.TRANSMUTATION_TABLE.get()));
        this.guiHelper = helper;
        this.maxEnergy = maxEnergy;
        this.energyStatic = this.guiHelper.createDrawable(TEXTURE, 177, 55, 9, 39);
        this.progressStatic = this.guiHelper.createDrawable(TEXTURE,176,1,16,7);
        this.progressAnim = this.guiHelper.createAnimatedDrawable(progressStatic, 80, IDrawableAnimated.StartDirection.BOTTOM,false);
    }

    //We have to call this method because the constructor can't take recipes therefore we can't get the usage cost before we initialize our energy bar
    private int setupEnergyBar(TransmutationTableRecipe recipe){
            int usageCost = recipe.getUsageCost();
            int scaledBar = usageCost * BAR_SIZE / maxEnergy;

            energyStatic = this.guiHelper.createDrawable(TEXTURE, 177, 94 - scaledBar, 9, scaledBar);
            return scaledBar;
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends TransmutationTableRecipe> getRecipeClass() {
        return TransmutationTableRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block.kuzey.transmutation_table");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull TransmutationTableRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 74,54).addItemStack(new ItemStack(recipe.getIngredients().get(0).getItems()[0].getItem(), recipe.getInputAmount()));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 74, 15).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(TransmutationTableRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();

        String recipeTime = Math.round(recipe.getRecipeTime() / 20) + " seconds";
        String usageCost = recipe.getUsageCost() + "FE/t";

        minecraft.font.draw(stack,recipeTime,11,36,0x404040);
        minecraft.font.draw(stack,usageCost,120,15,0x404040);

        int scaledBar = setupEnergyBar(recipe);
        energyStatic.draw(stack,140,66 - scaledBar);

        progressAnim.draw(stack,74,38);
    }
}
