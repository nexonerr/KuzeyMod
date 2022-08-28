package com.nexoner.kuzey.integration.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.recipe.EmreEssenceInfuserRecipe;
import com.nexoner.kuzey.util.FluidStackRenderer;
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
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

public class EmreEssenceInfuserRecipeCategory implements IRecipeCategory<EmreEssenceInfuserRecipe> {
    public static final RecipeType<EmreEssenceInfuserRecipe> EMRE_ESSENCE_INFUSION =
            RecipeType.create(KuzeyMod.MOD_ID,"emre_essence_infusion",EmreEssenceInfuserRecipe.class);
    public final static ResourceLocation UID = new ResourceLocation(KuzeyMod.MOD_ID, "emre_essence_infusion");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(KuzeyMod.MOD_ID, "textures/gui/jei/emre_essence_infuser_gui_jei.png");
    private IDrawableStatic energyStatic;
    //IDrawableAnimated energyAnim;

    private final IDrawableStatic progressStatic;
    private final IDrawableAnimated progressAnim;

    private final IDrawable background;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;
    private final int maxEnergy;

    private final int BAR_SIZE = 39;

    public EmreEssenceInfuserRecipeCategory(IGuiHelper helper, int maxEnergy) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.EMRE_ESSENCE_INFUSER.get()));
        this.guiHelper = helper;
        this.maxEnergy = maxEnergy;
        this.energyStatic = this.guiHelper.createDrawable(TEXTURE, 178, 20, 9, 39);
        this.progressStatic = this.guiHelper.createDrawable(TEXTURE,176,0,12,19);
        this.progressAnim = this.guiHelper.createAnimatedDrawable(progressStatic, 440, IDrawableAnimated.StartDirection.BOTTOM,false);
    }

    //We have to call this method because the constructor can't take recipes therefore we can't get the usage cost before we initialize our energy bar
    private int setupEnergyBar(EmreEssenceInfuserRecipe recipe){
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
    public Class<? extends EmreEssenceInfuserRecipe> getRecipeClass() {
        return EmreEssenceInfuserRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block.kuzey.emre_essence_infuser");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull EmreEssenceInfuserRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 46, 37).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 13, 26)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(recipe.getFluidInput()))
                .setFluidRenderer(10000, false, 16,41);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 82, 37).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(EmreEssenceInfuserRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        FluidStackRenderer verticalFluidStackRenderer = new FluidStackRenderer(1,false,2,9);
        FluidStackRenderer horizontalFluidStackRenderer = new FluidStackRenderer(1,false,22,2);
        FluidStack renderStack = new FluidStack(recipe.getFluidInput().getFluid(),1);

        String recipeTime = Math.round(recipe.getRecipeTime() / 20) + " seconds";
        String usageCost = recipe.getUsageCost() + " FE/t";

        minecraft.font.draw(stack,recipeTime,40,78,0x404040);
        minecraft.font.draw(stack,usageCost,120,15,0x404040);

        horizontalFluidStackRenderer.render(stack,32,63,renderStack);
        verticalFluidStackRenderer.render(stack,52,54,renderStack);

        int scaledBar = setupEnergyBar(recipe);
        energyStatic.draw(stack,140,66 - scaledBar);

        progressAnim.draw(stack,32,32);
    }
}
