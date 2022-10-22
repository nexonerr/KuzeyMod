package com.nexoner.kuzey.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.nexoner.kuzey.block.custom.EmreEssenceInfuserBlock;
import com.nexoner.kuzey.block.entity.custom.EmreEssenceInfuserBlockEntity;
import com.nexoner.kuzey.block.entity.custom.generator.CombustibleFluidGeneratorBlockEntity;
import com.nexoner.kuzey.util.CustomFluidTank;
import com.nexoner.kuzey.util.FluidHelpers;
import com.nexoner.kuzey.util.render.FluidTankRenderType;
import com.nexoner.kuzey.util.render.RenderResizableCuboid;
import com.nexoner.kuzey.util.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;


public class CombustibleFluidGeneratorBlockEntityRenderer implements BlockEntityRenderer<CombustibleFluidGeneratorBlockEntity> {
    public CombustibleFluidGeneratorBlockEntityRenderer(BlockEntityRendererProvider.Context context){}

    @Override
    public void render(CombustibleFluidGeneratorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        IFluidHandler handler = pBlockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
        if (handler != null && handler.getFluidInTank(0) != null){
            FluidStack fluid = handler.getFluidInTank(0);
            if (!fluid.isEmpty()){
                VertexConsumer buffer = pBufferSource.getBuffer(Sheets.translucentCullBlockSheet());
                pPoseStack.scale(1f,(float)handler.getFluidInTank(0).getAmount() / (float)handler.getTankCapacity(0),1f);
                RenderUtils.renderObject(FluidHelpers.getFluidModel(fluid,FluidHelpers.STAGES - 1,0.25f,0.0625f,0.25f),pPoseStack,buffer,RenderUtils.getColorARGB(fluid,1f),
                        RenderUtils.calculateGlowLight(pPackedLight,fluid),OverlayTexture.NO_OVERLAY, RenderResizableCuboid.FaceDisplay.BOTH,true);
            }
        }

    }

}
