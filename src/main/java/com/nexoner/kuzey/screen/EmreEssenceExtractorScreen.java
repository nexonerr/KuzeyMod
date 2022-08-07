package com.nexoner.kuzey.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.util.FluidStackRenderer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EmreEssenceExtractorScreen extends AbstractContainerScreen<EmreEssenceExtractorMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(KuzeyMod.MOD_ID, "textures/gui/emre_essence_extractor_gui.png");
    private FluidStackRenderer renderer;

    public EmreEssenceExtractorScreen(EmreEssenceExtractorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignFluidRenderer();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()){
            //Progress Bar
            blit(pPoseStack,x + 46,y + 37,176,5, menu.getScaledProgress(), 14);
        }
            //Energy Bar
            blit(pPoseStack,x + 140, y + 66 - menu.getEnergyBarScale(), 178, 59- menu.getEnergyBarScale(),9, menu.getEnergyBarScale());

            renderer.render(pPoseStack, x+81, y+26, menu.getFluid());
        }
    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack,pMouseX,pMouseY);
        drawCenteredString(pPoseStack,this.font, this.menu.getEnergyText(), x + 230, y + 40,0xFFFFFF);
        drawCenteredString(pPoseStack,this.font, this.menu.getFluidText(), x + 230, y + 50,0xFFFFFF);
        drawCenteredString(pPoseStack,this.font, menu.getFluid().getFluid().getRegistryName().toString(), x + 230, y + 60,0xFFFFFF);
    }

    private void assignFluidRenderer(){
        renderer = new FluidStackRenderer(30000, false, 16, 41);
    }
}
