package com.nexoner.kuzey.recipe;

import com.nexoner.kuzey.KuzeyMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, KuzeyMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<KuzeyiumPurificationChamberRecipe>> KUZEYIUM_PURIFICATION_SERIALIZER =
            SERIALIZERS.register("kuzeyium_purification",() -> KuzeyiumPurificationChamberRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<KuzeyiumWorkstationRecipe>> KUZEYIUM_SMITHING_SERIALIZER =
            SERIALIZERS.register("kuzeyium_smithing",() -> KuzeyiumWorkstationRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<EmreEssenceExtractorRecipe>> EMRE_ESSENCE_EXTRACTION =
            SERIALIZERS.register("emre_essence_extraction",() -> EmreEssenceExtractorRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<TransmutationTableRecipe>> TRANSMUTATION =
            SERIALIZERS.register("transmutation",() -> TransmutationTableRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<EmreEssenceInfuserRecipe>> EMRE_ESSENCE_INFUSION =
            SERIALIZERS.register("emre_essence_infusion",() -> EmreEssenceInfuserRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
