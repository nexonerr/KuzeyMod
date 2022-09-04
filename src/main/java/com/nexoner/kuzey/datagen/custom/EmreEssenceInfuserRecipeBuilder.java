package com.nexoner.kuzey.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.recipe.EmreEssenceInfuserRecipe;
import com.nexoner.kuzey.recipe.TransmutationTableRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class EmreEssenceInfuserRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int count;
    private final int recipeTime;
    private final int usageCost;
    private final FluidStack fluidStack;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public EmreEssenceInfuserRecipeBuilder(Item result, ItemLike ingredient, int count, int recipeTime, int usageCost, FluidStack fluidStack) {
        this.ingredient = Ingredient.of(ingredient);
        this.result = result;
        this.count = count;
        this.recipeTime = recipeTime;
        this.usageCost = usageCost;
        this.fluidStack = fluidStack;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_recipe",
                        RecipeUnlockedTrigger.unlocked(pRecipeId))
                .rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.result, this.ingredient, this.count, this.recipeTime, this.usageCost, this.fluidStack, this.advancement,
                new ResourceLocation(pRecipeId.getNamespace(), "recipes/" + "emre_essence_infusion/" + pRecipeId.getPath())));
    }


    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final int count;
        private final int recipeTime;
        private final int usageCost;
        private final FluidStack fluidStack;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, Ingredient ingredient, int pCount, int recipeTime, int usageCost, FluidStack fluidStack, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.count = pCount;
            this.recipeTime = recipeTime;
            this.usageCost = usageCost;
            this.ingredient = ingredient;
            this.fluidStack = fluidStack;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            JsonArray array = new JsonArray();
            array.add(ingredient.toJson());

            pJson.add("ingredients", array);

            JsonObject object = new JsonObject();
            object.addProperty("item", this.result.getRegistryName().toString());
            if (this.count > 1) {
                object.addProperty("count", this.count);
            }
            pJson.add("output", object);
            JsonObject FluidObject = new JsonObject();
            FluidObject.addProperty("fluid", this.fluidStack.getFluid().getRegistryName().toString());
            FluidObject.addProperty("amount", this.fluidStack.getAmount());
            pJson.add("fluid", FluidObject);
            pJson.addProperty("usageCost", this.usageCost);
            pJson.addProperty("recipeTime", this.recipeTime);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(KuzeyMod.MOD_ID, "emre_essence_infusion/" + this.result.getRegistryName().getPath() + "_with_" + this.fluidStack.getFluid().getRegistryName().getPath() + "_from_emre_essence_infusion");
        }

        @Override
        public RecipeSerializer<?> getType() {
            return EmreEssenceInfuserRecipe.Serializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}

