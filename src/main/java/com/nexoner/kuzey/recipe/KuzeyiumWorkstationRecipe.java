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

public class KuzeyiumWorkstationRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int recipeTime;
    private final int toolDamage;

    public KuzeyiumWorkstationRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int recipeTime, int toolDamage) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.recipeTime = recipeTime;
        this.toolDamage = toolDamage;
    }

    public int getRecipeTime(){return recipeTime;}
    public int getToolDamage(){return toolDamage;}

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return recipeItems.get(0).test(pContainer.getItem(2)) && recipeItems.get(1).test(pContainer.getItem(3)) && recipeItems.get(2).test(pContainer.getItem(4));
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

    public static class Type implements RecipeType<KuzeyiumWorkstationRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "kuzeyium_smithing";
    }
    public static class Serializer implements RecipeSerializer<KuzeyiumWorkstationRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(KuzeyMod.MOD_ID,"kuzeyium_smithing");

        @Override
        public KuzeyiumWorkstationRecipe fromJson(ResourceLocation id, JsonObject json) {
            int recipeTime = GsonHelper.getAsInt(json,"recipeTime");
            int toolDamage = GsonHelper.getAsInt(json,"toolDamage");

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new KuzeyiumWorkstationRecipe(id, output, inputs,recipeTime,toolDamage);
        }

        @Override
        public KuzeyiumWorkstationRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            int recipeTime = buf.readInt();
            int toolDamage = buf.readInt();
            return new KuzeyiumWorkstationRecipe(id, output, inputs,recipeTime,toolDamage);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, KuzeyiumWorkstationRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            buf.writeItemStack(recipe.getResultItem(), false);
            buf.writeInt(recipe.recipeTime);
            buf.writeInt(recipe.toolDamage);
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