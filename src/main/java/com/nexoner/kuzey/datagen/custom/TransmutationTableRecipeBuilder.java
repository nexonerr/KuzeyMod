package com.nexoner.kuzey.datagen.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.recipe.KuzeyiumWorkstationRecipe;
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
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TransmutationTableRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient ingredient;
    private final int count;
    private final int recipeTime;
    private final int usageCost;
    private final int inputAmount;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public TransmutationTableRecipeBuilder(ItemLike result, int count, ItemLike ingredient, int inputAmount, int recipeTime, int usageCost ) {
        this.ingredient = Ingredient.of(ingredient);
        this.result = result.asItem();
        this.count = count;
        this.recipeTime = recipeTime;
        this.usageCost = usageCost;
        this.inputAmount = inputAmount;
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

        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.result, this.count, this.ingredient, this.inputAmount, this.recipeTime, this.usageCost, this.advancement,
                new ResourceLocation(pRecipeId.getNamespace(), "recipes/" + "transmutation/" + pRecipeId.getPath())));
    }


    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final Ingredient ingredient;
        private final int count;
        private final int recipeTime;
        private final int usageCost;
        private final int inputAmount;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation pId, Item pResult, int pCount, Ingredient ingredient, int inputAmount, int recipeTime, int usageCost, Advancement.Builder pAdvancement,
                      ResourceLocation pAdvancementId) {
            this.id = pId;
            this.result = pResult;
            this.count = pCount;
            this.recipeTime = recipeTime;
            this.usageCost = usageCost;
            this.inputAmount = inputAmount;
            this.ingredient = ingredient;
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
            pJson.addProperty("inputAmount", this.inputAmount);
            pJson.addProperty("usageCost", this.usageCost);
            pJson.addProperty("recipeTime", this.recipeTime);
        }

        @Override
        public ResourceLocation getId() {
            return new ResourceLocation(KuzeyMod.MOD_ID, "transmutation/" + this.result.getRegistryName().getPath() + "_from_transmutation");
        }

        @Override
        public RecipeSerializer<?> getType() {
            return TransmutationTableRecipe.Serializer.INSTANCE;
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

