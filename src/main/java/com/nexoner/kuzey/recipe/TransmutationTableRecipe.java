
package com.nexoner.kuzey.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nexoner.kuzey.KuzeyMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class TransmutationTableRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int usageCost;
    private final int recipeTime;
    private final int inputAmount;

    public TransmutationTableRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int usageCost, int recipeTime, int inputAmount) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.usageCost = usageCost;
        this.recipeTime = recipeTime;
        this.inputAmount = inputAmount;
    }

    public int getUsageCost(){
        return usageCost;
    }
    public int getRecipeTime(){
        return recipeTime;
    }
    public int getInputAmount(){
        return inputAmount;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return recipeItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<TransmutationTableRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "transmutation";
    }
    public static class Serializer implements RecipeSerializer<TransmutationTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(KuzeyMod.MOD_ID,"transmutation");

        @Override
        public TransmutationTableRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            int usageCost = GsonHelper.getAsInt(json, "usageCost");
            int recipeTime = GsonHelper.getAsInt(json,"recipeTime");
            int inputAmount = GsonHelper.getAsInt(json,"inputAmount");

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new TransmutationTableRecipe(id, output, inputs,usageCost,recipeTime,inputAmount);
        }

        @Override
        public TransmutationTableRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            int recipeTime = buf.readInt();
            int usageCost = buf.readInt();
            int inputAmount = buf.readInt();
            return new TransmutationTableRecipe(id, output, inputs,usageCost,recipeTime,inputAmount);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, TransmutationTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
            buf.writeInt(recipe.recipeTime);
            buf.writeInt(recipe.usageCost);
            buf.writeInt(recipe.inputAmount);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}