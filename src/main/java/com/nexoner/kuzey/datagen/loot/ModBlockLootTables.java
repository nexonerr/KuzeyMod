package com.nexoner.kuzey.datagen.loot;

import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.block.custom.BlackCarrotsCropBlock;
import com.nexoner.kuzey.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot {
    private static final float[] KUZEYIAN_LEAVES_SAPLING_CHANCE = new float[]{0.01F, 0.03F, 0.04F, 0.05F};

    @Override
    protected void addTables() {

        this.dropSelf(ModBlocks.DEVASTATOR.get());
        this.dropSelf(ModBlocks.KUZEYIUM_GLASS.get());
        this.dropSelf(ModBlocks.KUZEYIAN_LOG.get());
        this.dropSelf(ModBlocks.KUZEYIAN_LOG_STRIPPED.get());
        this.dropSelf(ModBlocks.KUZEYIAN_WOOD.get());
        this.dropSelf(ModBlocks.KUZEYIAN_WOOD_STRIPPED.get());
        this.dropSelf(ModBlocks.KUZEYIAN_PLANKS.get());
        this.dropSelf(ModBlocks.KUZEYIAN_SAPLING.get());
        this.dropSelf(ModBlocks.KUZEYIUM_PURIFICATION_CHAMBER.get());
        this.dropSelf(ModBlocks.KUZEYIUM_WORKSTATION.get());
        this.dropSelf(ModBlocks.KUZEYIUM_BLOCK.get());
        this.dropSelf(ModBlocks.EMRE_ESSENCE_EXTRACTOR.get());
        this.dropSelf(ModBlocks.WOODEN_MACHINE_FRAME.get());
        this.dropSelf(ModBlocks.IRON_MACHINE_FRAME.get());
        this.dropSelf(ModBlocks.DIAMOND_MACHINE_FRAME.get());
        this.dropSelf(ModBlocks.UNACTIVATED_INFUSED_MACHINE_FRAME.get());
        this.dropSelf(ModBlocks.INFUSED_MACHINE_FRAME.get());
        this.dropSelf(ModBlocks.TRANSMUTATION_TABLE.get());
        this.dropSelf(ModBlocks.EMRE_ESSENCE_INFUSER.get());
        this.dropSelf(ModBlocks.DECONDENSATOR.get());
        this.dropSelf(ModBlocks.RAW_KUZEYIUM_BLOCK.get());
        this.dropSelf(ModBlocks.INFUSED_KUZEYIUM_BLOCK.get());

        this.add(ModBlocks.KUZEYIUM_ORE.get(), (block) -> createOreDrop(ModBlocks.KUZEYIUM_ORE.get(), ModItems.RAW_KUZEYIUM.get()));
        this.add(ModBlocks.EMRE_ESSENCE_ROCK_ORE.get(), (block) -> createOreDrop(ModBlocks.EMRE_ESSENCE_ROCK_ORE.get(), ModItems.EMRE_ESSENCE_ROCK.get()));
        this.add(ModBlocks.DEEPSLATE_EMRE_ESSENCE_ROCK_ORE.get(), (block) -> createOreDrop(ModBlocks.DEEPSLATE_EMRE_ESSENCE_ROCK_ORE.get(), ModItems.EMRE_ESSENCE_ROCK.get()));

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BLACK_CARROTS_PLANT.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlackCarrotsCropBlock.AGE, 3));
        this.add(ModBlocks.BLACK_CARROTS_PLANT.get(), applyExplosionDecay(ModBlocks.BLACK_CARROTS_PLANT.get(), LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.BLACK_CARROTS.get())))
                .withPool(LootPool.lootPool().when(lootitemcondition$builder).add(LootItem.lootTableItem(ModItems.BLACK_CARROTS.get()).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))))));

        this.add(ModBlocks.KUZEYIAN_LEAVES.get(), (block) ->
                createLeavesDrops(block, ModBlocks.KUZEYIAN_SAPLING.get(), KUZEYIAN_LEAVES_SAPLING_CHANCE));


    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
