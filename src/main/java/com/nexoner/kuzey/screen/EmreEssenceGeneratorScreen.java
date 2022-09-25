package com.nexoner.kuzey.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.nexoner.kuzey.KuzeyMod;
import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.util.FluidStackRenderer;
import com.nexoner.kuzey.util.GeneralUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public class EmreEssenceGeneratorScreen extends AbstractContainerScreen<EmreEssenceGeneratorMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(KuzeyMod.MOD_ID, "textures/gui/emre_essence_generator_gui.png");
    private FluidStackRenderer renderer;

    public EmreEssenceGeneratorScreen(EmreEssenceGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignFluidRenderer();
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderFluidTooltips(pPoseStack,pMouseX,pMouseY,x,y);
        renderEnergyTooltips(pPoseStack,pMouseX,pMouseY,x,y);

        super.renderLabels(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isBurning()){
            //Progress Bar
            blit(pPoseStack,x + 60,y + 59 - menu.getScaledProgress(),189,51 - menu.getScaledProgress(), 11, menu.getScaledProgress());
        }
            //Energy Bar
            blit(pPoseStack,x + 48, y + 66 - menu.getEnergyBarScale(), 178, 59 - menu.getEnergyBarScale(),9, menu.getEnergyBarScale());

            renderer.render(pPoseStack, x + 82, y + 26, menu.getFluid());
        }
    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack,pMouseX,pMouseY);
    }

    private void assignFluidRenderer(){
        renderer = new FluidStackRenderer(KuzeyCommonConfigs.emreEssenceGeneratorFluidCapacity.get(), true, 16, 41);
    }

    private void renderFluidTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y){
        if (GeneralUtils.isMouseOver(pMouseX,pMouseY,x + 82,y + 26,renderer.getWidth(),renderer.getHeight())){
            renderTooltip(pPoseStack,renderer.getTooltip(menu.getFluid(), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private void renderEnergyTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y){
        String energyText = GeneralUtils.formatEnergyText(menu.data.get(2),menu.data.get(3));
        if (GeneralUtils.isMouseOver(pMouseX,pMouseY,x + 48,y + 27,9,39)){
            renderTooltip(pPoseStack, List.of(new TextComponent(energyText)),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
}
