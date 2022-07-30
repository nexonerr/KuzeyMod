package com.nexoner.kuzey.block.entity;

import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.block.ModBlocks;
import com.nexoner.kuzey.block.entity.custom.KuzeyiumPurificationChamberBlockEntity;
import com.nexoner.kuzey.block.entity.custom.KuzeyiumWorkstationBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, KuzeyMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<KuzeyiumPurificationChamberBlockEntity>> KUZEYIUM_PURIFICATION_CHAMBER_BLOCK_ENTITY=
            BLOCK_ENTITIES.register("kuzeyium_purification_chamber_block_entity",() -> BlockEntityType.Builder.of(KuzeyiumPurificationChamberBlockEntity::new,
                    ModBlocks.KUZEYIUM_PURIFICATION_CHAMBER.get()).build(null));

    public static final RegistryObject<BlockEntityType<KuzeyiumWorkstationBlockEntity>> KUZEYIUM_WORKSTATION_BLOCK_ENTITY=
            BLOCK_ENTITIES.register("kuzeyium_workstation_block_entity",() -> BlockEntityType.Builder.of(KuzeyiumWorkstationBlockEntity::new,
                    ModBlocks.KUZEYIUM_WORKSTATION.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
