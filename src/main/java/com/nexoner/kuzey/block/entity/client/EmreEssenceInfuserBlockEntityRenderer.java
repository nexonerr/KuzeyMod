package com.nexoner.kuzey.block.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.nexoner.kuzey.block.custom.EmreEssenceInfuserBlock;
import com.nexoner.kuzey.block.entity.custom.EmreEssenceInfuserBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

/*
Basic Block Entity Rendering by Kaupenjoe under MIT License
https://github.com/Kaupenjoe/Resource-Slimes
 */


public class EmreEssenceInfuserBlockEntityRenderer implements BlockEntityRenderer<EmreEssenceInfuserBlockEntity> {
    private final BlockEntityRendererProvider.Context providerContext;
    public EmreEssenceInfuserBlockEntityRenderer(BlockEntityRendererProvider.Context context){
        providerContext = context;
    }

    @Override
    public void render(EmreEssenceInfuserBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack itemStack = pBlockEntity.getRenderStack();
        pPoseStack.pushPose();
        pPoseStack.scale(0.2f, 0.2f, 0.2f);
        pPoseStack.translate(1f, 1f, 1f);


        switch (pBlockEntity.getBlockState().getValue(EmreEssenceInfuserBlock.FACING)) {
            case NORTH -> pPoseStack.mulPose(Vector3f.ZN.rotationDegrees(180));
            case SOUTH -> pPoseStack.mulPose(Vector3f.ZN.rotationDegrees(0));
            case EAST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(90));
            case WEST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(270));
        }
        switch (pBlockEntity.getBlockState().getValue(EmreEssenceInfuserBlock.FACING)) {
            case NORTH, SOUTH -> pPoseStack.mulPose(Vector3f.YN.rotationDegrees(0));
            case EAST -> pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
            case WEST -> pPoseStack.mulPose(Vector3f.YN.rotationDegrees(90));
        }
        switch (pBlockEntity.getBlockState().getValue(EmreEssenceInfuserBlock.FACING)) {
            case NORTH -> pPoseStack.mulPose(Vector3f.XP.rotationDegrees(180));
            case SOUTH -> pPoseStack.mulPose(Vector3f.XP.rotationDegrees(0));
            case EAST, WEST -> pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90));
        }
        switch (pBlockEntity.getBlockState().getValue(EmreEssenceInfuserBlock.FACING)) {
            case NORTH -> pPoseStack.translate(-0.55f, 1.2f, -1.6f);
            case EAST -> pPoseStack.translate(-0.55f, 1.2f, 1.6f);
            case SOUTH -> pPoseStack.translate(2.45f, 1.2f, 1.6f);
            case WEST -> pPoseStack.translate(2.45f, 1.2f, -1.6f);
        }


        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
