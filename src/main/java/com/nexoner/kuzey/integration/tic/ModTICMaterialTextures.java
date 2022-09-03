package com.nexoner.kuzey.integration.tic;

import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;

public class ModTICMaterialTextures extends AbstractMaterialSpriteProvider {
    @Override
    public String getName() {
        return "Kuzey Mod Material Textures";
    }

    @Override
    protected void addAllMaterials() {
        buildMaterial(ModTICMaterials.KUZEYIUM)
                .meleeHarvest()
                .fallbacks("metal")
                .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63,0xFFA8378B).addARGB(102, 0xFF9C4786).addARGB(140, 0xFF8D467B)
                        .addARGB(178, 0xFF744668).addARGB(216, 0xFF66435D).addARGB(255, 0xFF5A3C52).build());

        buildMaterial(ModTICMaterials.INFUSED_KUZEYIUM)
                .meleeHarvest()
                .fallbacks("crystal")
                .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63,0xFF37DF1F).addARGB(102, 0xFF37CE21).addARGB(140, 0xFF35C221)
                        .addARGB(178, 0xFFDF64BF).addARGB(216, 0xFFCD6DB5).addARGB(255, 0xFFB978A9).build());

        buildMaterial(ModTICMaterials.KUZEYIAN_WOOD)
                .statType(HandleMaterialStats.ID,ExtraMaterialStats.ID)
                .fallbacks("wood")
                .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63,0xFFEB58F0).addARGB(102, 0xFFDD57E1).addARGB(140, 0xFFCB52CF)
                        .addARGB(178, 0xFFBB55BE).addARGB(216, 0xFF8B3F8D).addARGB(255, 0xFF864088).build());
    }
}
