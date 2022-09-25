package com.nexoner.kuzey.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class KuzeyCommonConfigs {

    public static final ForgeConfigSpec GENERAL_SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    //Blocks
    public static ForgeConfigSpec.ConfigValue<String> devastatorSlayer;

    //Block entities
    public static ForgeConfigSpec.IntValue emreEssenceExtractorCapacity;
    public static ForgeConfigSpec.IntValue emreEssenceExtractorMaxReceived;
    public static ForgeConfigSpec.IntValue emreEssenceExtractorFluidCapacity;
    public static ForgeConfigSpec.IntValue emreEssenceExtractorDefaultRecipeTime;
    public static ForgeConfigSpec.IntValue emreEssenceExtractorDefaultUsageCost;
    public static ForgeConfigSpec.BooleanValue emreEssenceExtractorFluidFiltering;

    public static ForgeConfigSpec.IntValue emreEssenceInfuserCapacity;
    public static ForgeConfigSpec.IntValue emreEssenceInfuserMaxReceived;
    public static ForgeConfigSpec.IntValue emreEssenceInfuserFluidCapacity;
    public static ForgeConfigSpec.IntValue emreEssenceInfuserDefaultRecipeTime;
    public static ForgeConfigSpec.IntValue emreEssenceInfuserDefaultUsageCost;
    public static ForgeConfigSpec.BooleanValue emreEssenceInfuserFluidFiltering;

    public static ForgeConfigSpec.IntValue kuzeyiumPurificationChamberCapacity;
    public static ForgeConfigSpec.IntValue kuzeyiumPurificationChamberMaxReceived;
    public static ForgeConfigSpec.IntValue kuzeyiumPurificationChamberDefaultRecipeTime;
    public static ForgeConfigSpec.IntValue kuzeyiumPurificationChamberDefaultUsageCost;

    public static ForgeConfigSpec.IntValue kuzeyiumWorkstationDefaultRecipeTime;
    public static ForgeConfigSpec.IntValue kuzeyiumWorkstationDefaultToolDamage;

    public static ForgeConfigSpec.IntValue transmutationTableCapacity;
    public static ForgeConfigSpec.IntValue transmutationTableMaxReceived;
    public static ForgeConfigSpec.IntValue transmutationTableDefaultRecipeTime;
    public static ForgeConfigSpec.IntValue transmutationTableDefaultUsageCost;

    public static ForgeConfigSpec.IntValue emreEssenceGeneratorCapacity;
    public static ForgeConfigSpec.IntValue emreEssenceGeneratorMaxExtracted;
    public static ForgeConfigSpec.IntValue emreEssenceGeneratorProduced;
    public static ForgeConfigSpec.IntValue emreEssenceGeneratorFluidCapacity;
    public static ForgeConfigSpec.BooleanValue emreEssenceGeneratorFluidFiltering;

    //Ores
    public static ForgeConfigSpec.IntValue kuzeyiumOreRate;
    public static ForgeConfigSpec.IntValue kuzeyiumOreVeinSize;
    public static ForgeConfigSpec.IntValue kuzeyiumOreMinSpawn;
    public static ForgeConfigSpec.IntValue kuzeyiumOreMaxSpawn;
    public static ForgeConfigSpec.IntValue kuzeyiumOreInner;

    public static ForgeConfigSpec.IntValue emreEssenceRockOreRate;
    public static ForgeConfigSpec.IntValue emreEssenceRockOreVeinSize;
    public static ForgeConfigSpec.IntValue emreEssenceRockOreMinSpawn;
    public static ForgeConfigSpec.IntValue emreEssenceRockOreMaxSpawn;

    public static ForgeConfigSpec.IntValue emreEssenceRockOreMegaRate;
    public static ForgeConfigSpec.IntValue emreEssenceRockOreMegaVeinSize;
    public static ForgeConfigSpec.IntValue emreEssenceRockOreMegaMinSpawn;
    public static ForgeConfigSpec.IntValue emreEssenceRockOreMegaMaxSpawn;
    public static ForgeConfigSpec.IntValue emreEssenceRockOreMegaInner;

    //Trees
    public static ForgeConfigSpec.IntValue kuzeyianTreeRate;
    public static ForgeConfigSpec.IntValue kuzeyianTreeSpawnChance;

    //Items

    //Tools

    public static ForgeConfigSpec.IntValue kuzeyiumAxeLumberMax;
    public static ForgeConfigSpec.IntValue miningHammerRadius;
    public static ForgeConfigSpec.IntValue miningHammerDepth;
    public static ForgeConfigSpec.IntValue infusedKuzeyiumAxeRadius;
    public static ForgeConfigSpec.IntValue infusedKuzeyiumPickaxeMaxBlocks;
    public static ForgeConfigSpec.BooleanValue infusedKuzeyiumPickaxeAbundantMining;


    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Kuzey Mod COMMON CONFIGS");
        builder.comment("Configs for blocks");
        builder.push("Block Configs");
        devastatorSlayer = builder
                .comment("Defines under which player name the devastator deals damage, this will affect kill messages")
                .define("devastator_slayer", "KUZEY");

        builder.comment("Configs for block entities");
        builder.push("Block Entity Configs");

        //Emre Essence Extractor
        emreEssenceExtractorCapacity = builder
                .comment("Energy Capacity")
                .defineInRange("emre_essence_extractor_capacity", 140000, 40000, 800000);
        emreEssenceExtractorMaxReceived = builder
                .comment("Maximum FE/t Received")
                .defineInRange("emre_essence_extractor_max_received", 32000, 4000, 80000);
        emreEssenceExtractorFluidCapacity = builder
                .comment("Fluid Storage")
                .defineInRange("emre_essence_extractor_fluid_capacity", 30000, 5000, 60000);
        emreEssenceExtractorDefaultRecipeTime = builder
                .comment("Default Recipe Time, This is almost always NEVER used")
                .defineInRange("emre_essence_extractor_default_recipe_time", 400, 20, 40000);
        emreEssenceExtractorDefaultUsageCost = builder
                .comment("Default Usage Cost, This is almost always NEVER used")
                .defineInRange("emre_essence_extractor_default_usage_cost", 22000, 100, 60000);
        emreEssenceExtractorFluidFiltering = builder
                .comment("Toggled fluid filtering, if this is turned on this machine will only accept fluids from a certain tag. This is redundant for machines that can only output fluids, so it's better disable it in those cases.")
                .define("emre_essence_extractor_fluid_filtering", false);

        //Emre Essence Infuser
        emreEssenceInfuserCapacity = builder
                .comment("Energy Capacity")
                .defineInRange("emre_essence_infuser_capacity", 160000, 40000, 900000);
        emreEssenceInfuserMaxReceived = builder
                .comment("Maximum FE/t Received")
                .defineInRange("emre_essence_infuser_max_received", 32000, 4000, 80000);
        emreEssenceInfuserFluidCapacity = builder
                .comment("Fluid Storage")
                .defineInRange("emre_essence_infuser_fluid_capacity", 10000, 1500, 35000);
        emreEssenceInfuserDefaultRecipeTime = builder
                .comment("Default Recipe Time, This is almost always NEVER used")
                .defineInRange("emre_essence_infuser_default_recipe_time", 440, 20, 44000);
        emreEssenceInfuserDefaultUsageCost = builder
                .comment("Default Usage Cost, This is almost always NEVER used")
                .defineInRange("emre_essence_infuser_default_usage_cost", 25000, 100, 70000);
        emreEssenceInfuserFluidFiltering = builder
                .comment("Toggled fluid filtering, if this is turned on this machine will only accept fluids from a certain tag. This is redundant for machines that can only output fluids, so it's better disable it in those cases.")
                .define("emre_essence_infuser_fluid_filtering", true);

        //Kuzeyium Purification Chamber
        kuzeyiumPurificationChamberCapacity = builder
                .comment("Energy Capacity")
                .defineInRange("kuzeyium_purification_chamber_capacity", 80000, 30000, 400000);
        kuzeyiumPurificationChamberMaxReceived = builder
                .comment("Maximum FE/t Received")
                .defineInRange("kuzeyium_purification_chamber_max_received", 20000, 2000, 75000);
        kuzeyiumPurificationChamberDefaultRecipeTime = builder
                .comment("Default Recipe Time, This is almost always NEVER used")
                .defineInRange("kuzeyium_purification_chamber_default_recipe_time", 300, 20, 44000);
        kuzeyiumPurificationChamberDefaultUsageCost = builder
                .comment("Default Usage Cost, This is almost always NEVER used")
                .defineInRange("kuzeyium_purification_chamber_default_usage_cost", 10000, 100, 70000);

        //Kuzeyium Workstation
        kuzeyiumWorkstationDefaultRecipeTime = builder
                .comment("Default Recipe Time, This is almost always NEVER used")
                .defineInRange("kuzeyium_workstation_default_recipe_time", 600, 20, 88000);
        kuzeyiumWorkstationDefaultToolDamage = builder
                .comment("Default Tool Damage, This is almost always NEVER used")
                .defineInRange("kuzeyium_workstation_default_usage_cost", 4, 1, 500);

        //Transmutation Table
        transmutationTableCapacity = builder
                .comment("Energy Capacity")
                .defineInRange("transmutation_table_capacity", 110000, 25000, 600000);
        transmutationTableMaxReceived = builder
                .comment("Maximum FE/t Received")
                .defineInRange("transmutation_table_max_received", 28000, 2000, 90000);
        transmutationTableDefaultRecipeTime = builder
                .comment("Default Recipe Time, This is almost always NEVER used")
                .defineInRange("transmutation_table_default_recipe_time", 800, 20, 44000);
        transmutationTableDefaultUsageCost = builder
                .comment("Default Usage Cost, This is almost always NEVER used")
                .defineInRange("transmutation_table_default_usage_cost", 8000, 100, 70000);

        //Emre Essence Generator
        emreEssenceGeneratorCapacity = builder
                .comment("Energy Capacity")
                .defineInRange("emre_essence_generator_capacity", 200000, 40000, 1200000);
        emreEssenceGeneratorMaxExtracted = builder
                .comment("Maximum FE/t Extracted")
                .defineInRange("emre_essence_generator_max_extracted", 40000, 4000, 100000);
        emreEssenceGeneratorFluidCapacity = builder
                .comment("Fluid Storage")
                .defineInRange("emre_essence_generator_fluid_capacity", 15000, 1500, 40000);
        emreEssenceGeneratorProduced = builder
                .comment("Amount of energy that is produced per tick, on default, this is affected by the fuels that are used, this is the base amount")
                .defineInRange("emre_essence_generator_produced", 20000, 2000, 100000);
        emreEssenceGeneratorFluidFiltering = builder
                .comment("Toggled fluid filtering, if this is turned on this machine will only accept fluids from a certain tag. This is redundant for machines that can only output fluids, so it's better disable it in those cases.")
                .define("emre_essence_generator_fluid_filtering", true);

        builder.pop();
        builder.pop();

        builder.comment("Configs for world gen");
        builder.push("World Gen Configs");
        builder.comment("Configs for ore gen");
        builder.push("Ore gen configs");
        //Kuzeyium Ore
        builder.comment("Kuzeyium Ore Spawns are very biased toward the bottom");
        kuzeyiumOreRate = builder
                .comment("How many ore veins spawn per chunk")
                .defineInRange("kuzeyium_ore_rate", 5, 1, 30);
        kuzeyiumOreVeinSize = builder
                .comment("How many ores are in a single vein")
                .defineInRange("kuzeyium_ore_vein_size", 3, 1, 8);
        kuzeyiumOreMinSpawn = builder
                .comment("Minimum Y level ores will spawn at")
                .defineInRange("kuzeyium_ore_min_spawn", 15, 0, 200);
        kuzeyiumOreMaxSpawn = builder
                .comment("Maximum Y level ores will spawn at")
                .defineInRange("kuzeyium_ore_max_spawn", 50, 5, 200);
        kuzeyiumOreInner = builder
                .comment("Inner value between the max and min spawn levels")
                .defineInRange("kuzeyium_ore_inner", 25, 0, 200);

        //Emre Essence Rock Ore
        builder.comment("Emre Essence Rock Ore Spawns are in a triangle");
        emreEssenceRockOreRate = builder
                .comment("How many ore veins spawn per chunk")
                .defineInRange("emre_essence_rock_ore_rate", 1, 1, 30);
        emreEssenceRockOreVeinSize = builder
                .comment("How many ores are in a single vein")
                .defineInRange("emre_essence_rock_ore_vein_size", 18, 1, 32);
        emreEssenceRockOreMinSpawn = builder
                .comment("Minimum Y level ores will spawn at")
                .defineInRange("emre_essence_rock_ore_min_spawn", -70, -80, 200);
        emreEssenceRockOreMaxSpawn = builder
                .comment("Maximum Y level ores will spawn at")
                .defineInRange("emre_essence_rock_ore_max_spawn", 40, -75, 200);

        //Mega Emre Essence Vein
        builder.comment("Mega Emre Essence Rock Ore Spawns are very biased toward the bottom");
        emreEssenceRockOreMegaRate = builder
                .comment("How many chunks it take to spawn one vein")
                .defineInRange("mega_emre_essence_rock_ore_rate", 12, 1, 50);
        emreEssenceRockOreMegaVeinSize = builder
                .comment("How many ores are in a single vein")
                .defineInRange("mega_emre_essence_rock_ore_vein_size", 36, 1, 120);
        emreEssenceRockOreMegaMinSpawn = builder
                .comment("Minimum Y level ores will spawn at")
                .defineInRange("mega_emre_essence_rock_ore_min_spawn", -50, -80, 200);
        emreEssenceRockOreMegaMaxSpawn = builder
                .comment("Maximum Y level ores will spawn at")
                .defineInRange("mega_emre_essence_rock_ore_max_spawn", -10, -75, 200);
        emreEssenceRockOreMegaInner = builder
                .comment("Inner value between the max and min spawn levels")
                .defineInRange("mega_emre_essence_rock_ore_inner", -40, -80, 200);

        builder.pop();
        builder.comment("Configs for tree generation");
        builder.push("Tree Configs");
        kuzeyianTreeRate = builder
                .comment("How many chunks it takes to spawn a single tree")
                .defineInRange("kuzeyian_tree_rate", 200, 1, 1000);
        kuzeyianTreeSpawnChance = builder
                .comment("Chance for a tree to spawn after it has decided to spawn, out of 10")
                .defineInRange("kuzeyian_tree_chance", 5, 1, 10);

        builder.pop();
        builder.pop();

        builder.comment("Configs for Items");
        builder.push("Item Configs");

        builder.comment("Configs for Tools");
        builder.push("Tool Configs");

        kuzeyiumAxeLumberMax = builder
                .comment("Defines how long the line of blocks the kuzeyium deforester will break")
                .defineInRange("kuzeyium_axe_lumber_max", 5, 0, 100);
        miningHammerRadius = builder
                .comment("Defines the diameter of the hole created by the kuzeyium mining hammer")
                .defineInRange("mining_hammer_radius", 3, 0, 8);
        miningHammerDepth = builder
                .comment("Defines the depth of the hole created by the kuzeyium mining hammer")
                .defineInRange("mining_hammer_depth", 0, 0, 8);
        infusedKuzeyiumAxeRadius = builder
                .comment("Defines the radius of the logs the infused kuzeyium deforester breaks")
                .defineInRange("infused_kuzeyium_axe_radius", 1, 0, 8);
        infusedKuzeyiumPickaxeMaxBlocks = builder
                .comment("Defines the maximum amount of blocks the vein-mine in the infused kuzeyium pickaxe will break. DONT SET TOO HIGH IF YOUR COMPUTER CANT HANDLE IT")
                .defineInRange("infused_kuzeyium_pickaxe_max_blocks", 192, 0, 1024);
        infusedKuzeyiumPickaxeAbundantMining = builder
                .comment("Toggles abundant mining, if abundant mining is on, blocks like stone or deepslate will be able to be vein-mined. The blocks that are considered abundant are defined in a tag")
                .define("infused_kuzeyium_pickaxe_abundant_mining", false);

        builder.pop();
        builder.pop();
    }

}
