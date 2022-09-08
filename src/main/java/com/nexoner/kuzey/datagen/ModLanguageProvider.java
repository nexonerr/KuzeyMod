package com.nexoner.kuzey.datagen;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.fluid.ModFluids;
import com.nexoner.kuzey.integration.tic.ModTICMaterialFluids;
import com.nexoner.kuzey.integration.tic.ModTICMaterialItems;
import com.nexoner.kuzey.integration.tic.ModTICMaterials;
import com.nexoner.kuzey.item.ModCreativeModeTab;
import com.nexoner.kuzey.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.LanguageProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.util.function.Supplier;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        //Simple Items
        addItem(ModItems.KUZEYIUM_INGOT,"Kuzeyium Ingot");
        addItem(ModItems.KUZEYIUM_CHUNK,"Kuzeyium Chunk");
        addItem(ModItems.RAW_KUZEYIUM,"Raw Kuzeyium");
        addItem(ModItems.REGENERATOR,"Kuzeyium Regenerator");
        addItem(ModItems.KUZEYIUM_NUGGET,"Kuzeyium Nugget");
        addItem(ModItems.KUZEYIUM_COAL,"Kuzeyium Enriched Coal");
        addItem(ModItems.KUZEYIUM_MESH,"Kuzeyium Woven Mesh");
        addItem(ModItems.KUZEYIUM_FABRIC,"Kuzeyium Woven Fabric");
        addItem(ModItems.KUZEYIUM_SMITHING_HAMMER,"Kuzeyium Smithing Hammer");
        addItem(ModItems.KUZEYIUM_GLASS_PLATE,"Kuzeyium Infused Glass Plate");
        addItem(ModItems.BLACK_CARROTS,"Black Carrots");
        addItem(ModItems.KUZEYIUM_SAW,"Kuzeyium Saw");
        addItem(ModItems.KUZEYIUM_PURIFICATION_CORE,"Kuzeyium Purification Core");
        addItem(ModItems.KUZEYIAN_TOOL_HANDLE,"Kuzeyian Tool Handle");
        addItem(ModItems.SPAWNER_IRRITATOR,"Kuzeyium Spawner Irritator");
        addItem(ModItems.EMRE_ESSENCE_BUCKET,"Emre Essence Bucket");
        addItem(ModItems.STILL_FLUID_TANK,"Still Fluid Tank");
        addItem(ModItems.STRUCTURE_BINDER,"Structure Binder");
        addItem(ModItems.KUZEYIUM_PLATE,"Kuzeyium Plate");
        addItem(ModItems.ASCENDED_KUZEYIUM_GEM,"Ascended Kuzeyium Gem");
        addItem(ModItems.KUZEYIUM_HEATING_COIL,"Kuzeyium Heating Coil");
        addItem(ModItems.HEATING_FIN,"Heating Fin");
        addItem(ModItems.INFUSED_KUZEYIUM_GEM,"Infused Kuzeyium Gem");
        addItem(ModItems.REINFORCED_KUZEYIAN_TOOL_HANDLE,"Reinforced Kuzeyian Tool Handle");
        addItem(ModItems.EMRE_ESSENCE_ROCK,"Emre Essence Rock");

        //Tools
        addItem(ModItems.KUZEYIUM_SWORD,"Kuzeyium Murderer");
        addItem(ModItems.KUZEYIUM_AXE,"Kuzeyium Deforester");
        addItem(ModItems.KUZEYIUM_SHOVEL,"Kuzeyium Excavator");
        addItem(ModItems.KUZEYIUM_PICKAXE,"Kuzeyium Miner");
        addItem(ModItems.INFUSED_KUZEYIUM_PICKAXE,"Infused Kuzeyium Miner");
        addItem(ModItems.INFUSED_KUZEYIUM_AXE,"Infused Kuzeyium Deforester");
        addItem(ModItems.KUZEYIUM_MINING_HAMMER,"Kuzeyium Mining Hammer");

        //Armor
        addItem(ModItems.KUZEYIUM_HELMET,"Kuzeyium Head Cover");
        addItem(ModItems.KUZEYIUM_CHESTPLATE,"Kuzeyium Chest Piece");
        addItem(ModItems.KUZEYIUM_LEGGINGS,"Kuzeyium Leg Covers");
        addItem(ModItems.KUZEYIUM_BOOTS,"Kuzeyium Boots");

        //Simple Blocks
        addBlock(ModBlocks.KUZEYIUM_ORE,"Kuzeyium Ore");
        addBlock(ModBlocks.KUZEYIUM_GLASS,"Kuzeyium Infused Glass");
        addBlock(ModBlocks.BLACK_CARROTS_PLANT,"Black Carrots");
        addBlock(ModBlocks.KUZEYIAN_LOG,"Kuzeyian Log");
        addBlock(ModBlocks.KUZEYIAN_LOG_STRIPPED,"Stripped Kuzeyian Log");
        addBlock(ModBlocks.KUZEYIAN_WOOD,"Kuzeyian Wood");
        addBlock(ModBlocks.KUZEYIAN_WOOD_STRIPPED,"Stripped Kuzeyian Wood");
        addBlock(ModBlocks.KUZEYIAN_PLANKS,"Kuzeyian Planks");
        addBlock(ModBlocks.KUZEYIAN_LEAVES,"Kuzeyian Leaves");
        addBlock(ModBlocks.KUZEYIAN_SAPLING,"Kuzeyian Sapling");
        addBlock(ModBlocks.KUZEYIUM_BLOCK,"Kuzeyium Block");
        addBlock(ModBlocks.EMRE_ESSENCE_ROCK_ORE,"Emre Essence Rock Ore");
        addBlock(ModBlocks.DEEPSLATE_EMRE_ESSENCE_ROCK_ORE,"Deepslate Emre Essence Rock Ore");
        addBlock(ModBlocks.RAW_KUZEYIUM_BLOCK,"Raw Kuzeyium Block");
        addBlock(ModBlocks.INFUSED_KUZEYIUM_BLOCK,"Infused Kuzeyium Block");

        //Advanced Blocks
        addBlock(ModBlocks.DEVASTATOR,"Kuzeyium Devastator");

        //Machine Frames
        addBlock(ModBlocks.WOODEN_MACHINE_FRAME,"Wooden Machine Frame");
        addBlock(ModBlocks.IRON_MACHINE_FRAME,"Iron Machine Frame");
        addBlock(ModBlocks.DIAMOND_MACHINE_FRAME,"Diamond Machine Frame");
        addBlock(ModBlocks.UNACTIVATED_INFUSED_MACHINE_FRAME,"Unactivated Infused Machine Frame");
        addBlock(ModBlocks.INFUSED_MACHINE_FRAME,"Infused Machine Frame");

        //Machines
        addBlock(ModBlocks.KUZEYIUM_PURIFICATION_CHAMBER,"Kuzeyium Purification Chamber");
        addBlock(ModBlocks.KUZEYIUM_WORKSTATION,"Kuzeyium Workstation");
        addBlock(ModBlocks.EMRE_ESSENCE_EXTRACTOR,"Emre Essence Extractor");
        addBlock(ModBlocks.TRANSMUTATION_TABLE,"Transmutation Table");
        addBlock(ModBlocks.EMRE_ESSENCE_INFUSER,"Emre Essence Infuser");
        addBlock(ModBlocks.DECONDENSATOR,"Decondensator"); //Is it really a machine if it doesn't have BE?

        //Fluid(s)
        addFluid(ModFluids.EMRE_ESSENCE_FLUID,"Emre Essence");

        //Creative Mode Tab(s)
        addCreativeModeTab(ModCreativeModeTab.KUZEY_TAB,"Kuzey Mod");

        //Item Tooltips
        addItemTooltip(ModItems.REGENERATOR,"\u00A74Heals\u00A7r you to \u00A74FULL HEALTH \u00A7eINSTANTLY\u00A7r, only has \u00A7eONE\u00A7r use!");
        addItemTooltip(ModItems.KUZEYIUM_COAL,"Very strong fuel capable of smelting \u00A7e60\u00A7r items.");
        addItemTooltip(ModItems.KUZEYIUM_SWORD,"Steals \u00A74HEALTH\u00A7r from foes, the less health a foe has, the more health will be taken.");
        addItemTooltip(ModItems.KUZEYIUM_AXE,"Strong axe capable of taking down \u00A7eMULTIPLE LOGS\u00A7r at at time.");
        addItemTooltip(ModItems.SPAWNER_IRRITATOR,"Makes a spawner spawn its respective mob type.");
        addItemTooltip(ModItems.INFUSED_KUZEYIUM_PICKAXE,"A strong pickaxe capable of mining veins of blocks.");
        addItemTooltip(ModItems.KUZEYIUM_MINING_HAMMER,"Mining hammer capable of breaking a radius of blocks, press \u00A7e[SHIFT]\u00A7r to mine normally.");
        addItemTooltip(ModItems.INFUSED_KUZEYIUM_AXE,"Very strong axe capable of taking down a radius of logs.");
        
        //Block Tooltips
        addBlockTooltip(ModBlocks.DEVASTATOR,"Deals damage cast by \u00A74KUZEY\u00A7r to those standing above it. Toggleable via right clicking it.");
        addBlockTooltip(ModBlocks.KUZEYIUM_GLASS,"Infusing glass with \u00A75kuzeyium\u00A7r seems to make it incredibly durable: is resistant to explosions.");
        addBlockTooltip(ModBlocks.KUZEYIUM_PURIFICATION_CHAMBER,"Purifies \u00A75kuzeyium\u00A7r more efficiently than the workstation, also doesn't require tools.");
        addBlockTooltip(ModBlocks.KUZEYIUM_WORKSTATION,"Purifies \u00A75kuzeyium\u00A7r more efficiently than crude smithing using more precise machinery.");
        addBlockTooltip(ModBlocks.EMRE_ESSENCE_EXTRACTOR,"Extracts \u00A7bfluids\u00A7r from certain minerals");
        addBlockTooltip(ModBlocks.TRANSMUTATION_TABLE,"Can turn one \u00A7e-or several-\u00A7r items into another");
        addBlockTooltip(ModBlocks.EMRE_ESSENCE_INFUSER,"Infuses items with \u00A7bfluids\u00A7r. Requires a \u00A7eDECONDENSATOR\u00A7r in order to function");
        addBlockTooltip(ModBlocks.DECONDENSATOR,"\u00A7cHEATS\u00A7r stuff up, required for the \u00A7aEmre Essence Infuser\u00A7r to function");

        //Ability Tooltips
        addItemAbilityTooltip(ModItems.INFUSED_KUZEYIUM_PICKAXE,"Vein-mine: %s");
        addItemAbilityTooltip(ModItems.INFUSED_KUZEYIUM_AXE,"Lumber: %s");

        //Ability Toggled Tooltips
        addItemAbilityToggledTooltip(ModItems.INFUSED_KUZEYIUM_PICKAXE, "Vein-mine toggled %s");
        addItemAbilityToggledTooltip(ModItems.INFUSED_KUZEYIUM_AXE, "Lumber toggled %s");

        //Misc Tooltips
        addTooltip("alt","Press \u00A7e[ALT]\u00A7r for more information!");
        addTooltip("ability_activate","Press \u00A7e[SHIFT]\u00A7r + \u00A7e[RMB]\u00A7r to activate ability!");/*This will *probably* never be used*/
        addTooltip("on","\u00A7aON");
        addTooltip("off","\u00A7cOFF");

        /*Integration*/
        
        //JEI
        addTooltip("integration.jei.liquid_amount_with_capacity","%s / %s mB");
        addTooltip("integration.jei.liquid_amount","%s mB");
        
        //TIC

        //Materials
        addMaterial(ModTICMaterials.KUZEYIUM,"\u00A75Kuzeyium", "", "Adds some more slots, or durability");
        addMaterial(ModTICMaterials.INFUSED_KUZEYIUM,"\u00A7aInfused \u00A75Kuzeyium", "", "Very good for fighting, not so much for mining");
        addMaterial(ModTICMaterials.KUZEYIAN_WOOD,"\u00A7dKuzeyian Wooden", "", "Extremely durable");

        //Items
        addItem(ModTICMaterialItems.MOLTEN_KUZEYIUM_BUCKET,"Molten Kuzeyium Bucket");
        addItem(ModTICMaterialItems.MOLTEN_INFUSED_KUZEYIUM_BUCKET,"Molten Infused Kuzeyium Bucket");

        //Fluids
        addFluid(ModTICMaterialFluids.MOLTEN_KUZEYIUM_FLUID,"Molten Kuzeyium");
        addFluid(ModTICMaterialFluids.MOLTEN_INFUSED_KUZEYIUM_FLUID,"Molten Infused Kuzeyium");
        


    }
    private void addFluid(Supplier<? extends Fluid> key, String translation){
        add("fluid." + KuzeyMod.MOD_ID + "." + key.get().getRegistryName().getPath(), translation);
    }
    private void addCreativeModeTab(CreativeModeTab key, String translation){
        add(key.getDisplayName().getString(), translation);
    }
    private void addTooltip(String key, String translation){
        add("tooltip." + KuzeyMod.MOD_ID + "." + key, translation);
    }
    private void addItemTooltip(Supplier<? extends Item> key, String translation){
        addTooltip(key.get().getRegistryName().getPath(), translation);
    }
    private void addBlockTooltip(Supplier<? extends Block> key, String translation){
        addTooltip("block." + key.get().getRegistryName().getPath(), translation);
    }
    private void addItemAbilityTooltip(Supplier<? extends Item> key, String translation){
        addTooltip(key.get().getRegistryName().getPath() + "_ability", translation);
    }
    private void addItemAbilityToggledTooltip(Supplier<? extends Item> key, String translation){
        addTooltip(key.get().getRegistryName().getPath() + "_toggled", translation);
    }

    //From Materialis
    //https://github.com/RCXcrafter/Materialis
    private void addMaterial(MaterialId material, String name, String flavour, String desc) {
        String id = material.getPath();
        add("material.kuzey." + id, name);
        if (!flavour.equals(""))
            add("material.kuzey." + id + ".flavor", flavour);
        if (!desc.equals(""))
            add("material.kuzey." + id + ".encyclopedia", desc);
    }

}
