package com.nexoner.kuzey.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.nexoner.kuzey.KuzeyMod;
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

public class EmreEssenceInfuserScreen extends AbstractContainerScreen<EmreEssenceInfuserMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(KuzeyMod.MOD_ID, "textures/gui/emre_essence_infuser_gui.png");
    private FluidStackRenderer renderer;
    private FluidStackRenderer horizontalFluidProgressRenderer;
    private FluidStackRenderer verticalFluidProgressRenderer;

    public EmreEssenceInfuserScreen(EmreEssenceInfuserMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignFluidRenderer();
        assignVerticalFluidProgressRenderer();
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(pPoseStack, this.playerInventoryTitle, 122f, 76f, 4210752);

        renderFluidTooltips(pPoseStack,pMouseX,pMouseY,x,y);
        renderEnergyTooltips(pPoseStack,pMouseX,pMouseY,x,y);
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
            blit(pPoseStack,x + 32, y + 51 - menu.getScaledProgress(), 176, 19 - menu.getScaledProgress(),12, menu.getScaledProgress());
        }
            //Energy Bar
            blit(pPoseStack,x + 140, y + 66 - menu.getEnergyBarScale(), 178, 59- menu.getEnergyBarScale(),9, menu.getEnergyBarScale());

            //Decondensator
            if(GeneralUtils.intToBool(menu.data.get(6))){
                blit(pPoseStack,x + 8, y + 69,178,61,25,15);}

            renderer.render(pPoseStack, x + 13, y + 26, menu.getFluid());


            //Fluid Progress
        if (menu.getScaledFluid() > 0 && menu.getFluid() != FluidStack.EMPTY){
            if (menu.getScaledFluid() > 25){
                assignHorizontalFluidProgressRenderer(25);
                horizontalFluidProgressRenderer.render(pPoseStack, x + 29 , y + 63, new FluidStack(menu.getFluid().getFluid(),1));
                verticalFluidProgressRenderer.render(pPoseStack, x + 52, y + 54, new FluidStack(menu.getFluid().getFluid(), menu.getScaledFluid() - 25));
            } else {
                assignHorizontalFluidProgressRenderer(menu.getScaledFluid());
                horizontalFluidProgressRenderer.render(pPoseStack, x + 29 , y + 63, new FluidStack(menu.getFluid().getFluid(), 1));
            }
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack,pMouseX,pMouseY);
    }

    private void assignFluidRenderer(){
        renderer = new FluidStackRenderer(10000, true, 16, 41);
    }

    private void assignHorizontalFluidProgressRenderer(int width){
        horizontalFluidProgressRenderer = new FluidStackRenderer(1, false, width, 2);
    }
    private void assignVerticalFluidProgressRenderer(){
        verticalFluidProgressRenderer = new FluidStackRenderer(9, false, 2, 9);
    }
    private void renderFluidTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y){
        if (GeneralUtils.isMouseOver(pMouseX,pMouseY,x + 13,y + 26,renderer.getWidth(),renderer.getHeight())){
            renderTooltip(pPoseStack,renderer.getTooltip(menu.getFluid(), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }
    private void renderEnergyTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y){
        String energyText = GeneralUtils.formatEnergyText(menu.data.get(2),menu.data.get(3));
        if (GeneralUtils.isMouseOver(pMouseX,pMouseY,x + 140,y + 27,9,39)){
            renderTooltip(pPoseStack, List.of(new TextComponent(energyText)),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

}
