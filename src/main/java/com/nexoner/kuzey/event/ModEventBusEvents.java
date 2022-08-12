package com.nexoner.kuzey.event;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.recipe.EmreEssenceExtractorRecipe;
import com.nexoner.kuzey.recipe.KuzeyiumPurificationChamberRecipe;
import com.nexoner.kuzey.recipe.KuzeyiumWorkstationRecipe;
import com.nexoner.kuzey.recipe.TransmutationTableRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KuzeyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, KuzeyiumPurificationChamberRecipe.Type.ID, KuzeyiumPurificationChamberRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, KuzeyiumWorkstationRecipe.Type.ID, KuzeyiumWorkstationRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, EmreEssenceExtractorRecipe.Type.ID, EmreEssenceExtractorRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, TransmutationTableRecipe.Type.ID, TransmutationTableRecipe.Type.INSTANCE);
    }
}
