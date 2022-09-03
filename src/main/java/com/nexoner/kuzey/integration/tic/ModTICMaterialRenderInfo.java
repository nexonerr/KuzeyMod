package com.nexoner.kuzey.integration.tic;

import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

public class ModTICMaterialRenderInfo extends AbstractMaterialRenderInfoProvider {
    public ModTICMaterialRenderInfo(DataGenerator gen, @Nullable AbstractMaterialSpriteProvider materialSprites) {
        super(gen, materialSprites);
    }

    @Override
    protected void addMaterialRenderInfo() {
        buildRenderInfo(ModTICMaterials.KUZEYIUM).color(0x8D467B);
        buildRenderInfo(ModTICMaterials.INFUSED_KUZEYIUM).color(0xCD6DB5);
        buildRenderInfo(ModTICMaterials.KUZEYIAN_WOOD).color(0xBB55BE);
    }

    @Override
    public String getName() {
        return "Kuzey Mod Material Render Info Provider";
    }
}
