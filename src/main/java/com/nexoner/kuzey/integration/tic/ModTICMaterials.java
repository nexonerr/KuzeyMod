package com.nexoner.kuzey.integration.tic;

import com.nexoner.kuzey.KuzeyMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tiers;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModTICMaterials extends AbstractMaterialDataProvider {
    public ModTICMaterials(DataGenerator gen) {
        super(gen);
    }

    public static final MaterialId KUZEYIUM = createMaterial("kuzeyium");
    public static final MaterialId INFUSED_KUZEYIUM = createMaterial("infused_kuzeyium");
    public static final MaterialId KUZEYIAN_WOOD = createMaterial("kuzeyian_wood");

    @Override
    protected void addMaterials() {
        addCompatMetalMaterial(KUZEYIUM,4,ORDER_GENERAL + ORDER_COMPAT + ORDER_END);
        addMaterial(INFUSED_KUZEYIUM,4,ORDER_GENERAL + ORDER_COMPAT + ORDER_END, false);
        addMaterial(KUZEYIAN_WOOD,4,ORDER_GENERAL + ORDER_COMPAT, true);
    }

    @Override
    public String getName() {
        return "Kuzey Mod Materials";
    }

    private static MaterialId createMaterial(String name) {
        return new MaterialId(new ResourceLocation(KuzeyMod.MOD_ID, name));
    }

    public static class ModTICMaterialTraits extends AbstractMaterialTraitDataProvider{

        public ModTICMaterialTraits(DataGenerator gen, AbstractMaterialDataProvider materials) {
            super(gen, materials);
        }

        @Override
        protected void addMaterialTraits() {
            //Kuzeyium
            addTraits(KUZEYIUM, HeadMaterialStats.ID, new ModifierEntry(TinkerModifiers.creativeSlot.getId(),2));
            addTraits(KUZEYIUM, ExtraMaterialStats.ID, new ModifierEntry(TinkerModifiers.creativeSlot.getId(),1));
            addTraits(KUZEYIUM, HandleMaterialStats.ID, new ModifierEntry(TinkerModifiers.overlord.getId(),1));
            //Infused Kuzeyium
            addTraits(INFUSED_KUZEYIUM, HeadMaterialStats.ID, new ModifierEntry(TinkerModifiers.raging.getId(),4));
            addTraits(INFUSED_KUZEYIUM, ExtraMaterialStats.ID, new ModifierEntry(TinkerModifiers.solarPowered.getId(),3));
            addTraits(INFUSED_KUZEYIUM, HandleMaterialStats.ID, new ModifierEntry(TinkerModifiers.overlord.getId(),2));
            //Kuzeyian Wood
            addTraits(KUZEYIAN_WOOD, ExtraMaterialStats.ID, new ModifierEntry(TinkerModifiers.momentum.getId(),2));
            addTraits(KUZEYIAN_WOOD, HandleMaterialStats.ID, new ModifierEntry(TinkerModifiers.maintained.getId(),3));
        }

        @Override
        public String getName() {
            return "Kuzey Mod Material Traits";
        }

    }

    public static class ModTICMaterialStats extends AbstractMaterialStatsDataProvider{

        public ModTICMaterialStats(DataGenerator gen, AbstractMaterialDataProvider materials) {
            super(gen, materials);
        }

        @Override
        protected void addMaterialStats() {
            addMaterialStats(KUZEYIUM, new HeadMaterialStats(2333, 14f, Tiers.NETHERITE, 8f), new ExtraMaterialStats(), new HandleMaterialStats(1.1f,1.6f,0.8f,0.9f));
            addMaterialStats(INFUSED_KUZEYIUM, new HeadMaterialStats(2888, 15f, Tiers.NETHERITE, 10.5f), new ExtraMaterialStats(), new HandleMaterialStats(1.3f,0.7f,1.4f,1.8f));
            addMaterialStats(KUZEYIAN_WOOD, new ExtraMaterialStats(), new HandleMaterialStats(1.9f,1.1f,1f,1f));
        }

        @Override
        public String getName() {
            return "Kuzey Mod Material Stats";
        }
    }

}
