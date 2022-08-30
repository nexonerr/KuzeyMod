package com.nexoner.kuzey.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.nexoner.kuzey.KuzeyMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class EmreEssenceInfuserRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final FluidStack fluidInput;
    private final int usageCost;
    private final int recipeTime;

    public EmreEssenceInfuserRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int usageCost, int recipeTime, FluidStack fluidInput) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.usageCost = usageCost;
        this.recipeTime = recipeTime;
        this.fluidInput = fluidInput;
    }

    public int getUsageCost(){
        return usageCost;
    }
    public int getRecipeTime(){
        return recipeTime;
    }
    public FluidStack getFluidInput(){return fluidInput;}


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return recipeItems.get(0).test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {return output.copy();}

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {return output;}

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

    public static class Type implements RecipeType<EmreEssenceInfuserRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "emre_essence_infusion";
    }
    public static class Serializer implements RecipeSerializer<EmreEssenceInfuserRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(KuzeyMod.MOD_ID,"emre_essence_infusion");

        @Override
        public EmreEssenceInfuserRecipe fromJson(ResourceLocation id, JsonObject json) {
            JsonObject fluidJson = GsonHelper.getAsJsonObject(json,"fluid");
            String fluidId = fluidJson.get("fluid").getAsString();
            ResourceLocation fluidResource = new ResourceLocation(fluidId);
            int fluidAmount = fluidJson.get("amount").getAsInt();
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidResource);
            if (fluid == null || fluid == Fluids.EMPTY) {
                throw new JsonSyntaxException("Invalid fluid type '" + fluidResource + "'");
            }
            FluidStack fluidInput = new FluidStack(fluid,fluidAmount);

            int usageCost = GsonHelper.getAsInt(json, "usageCost");
            int recipeTime = GsonHelper.getAsInt(json,"recipeTime");

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new EmreEssenceInfuserRecipe(id, output, inputs,usageCost,recipeTime,fluidInput);
        }

        @Override
        public EmreEssenceInfuserRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            int recipeTime = buf.readInt();
            int usageCost = buf.readInt();
            FluidStack fluid = buf.readFluidStack();
            return new EmreEssenceInfuserRecipe(id, output, inputs,usageCost,recipeTime,fluid);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, EmreEssenceInfuserRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItem(recipe.output);
            buf.writeInt(recipe.recipeTime);
            buf.writeInt(recipe.usageCost);
            buf.writeFluidStack(recipe.fluidInput);
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