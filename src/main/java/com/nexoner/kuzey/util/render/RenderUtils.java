package com.nexoner.kuzey.util.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderUtils {
    public static final int FULL_LIGHT = 0xF000F0;

    //From Mekanism
    public static void renderObject(@Nullable Model3D object, @Nonnull PoseStack matrix, VertexConsumer buffer, int argb, int light, int overlay,
                                    RenderResizableCuboid.FaceDisplay faceDisplay, boolean fakeDisableDiffuse) {
        if (object != null) {
            RenderResizableCuboid.renderCube(object, matrix, buffer, argb, light, overlay, faceDisplay, fakeDisableDiffuse);
        }
    }

    public static float getRed(int color) {
        return (color >> 16 & 0xFF) / 255.0F;
    }

    public static float getGreen(int color) {
        return (color >> 8 & 0xFF) / 255.0F;
    }

    public static float getBlue(int color) {
        return (color & 0xFF) / 255.0F;
    }

    public static float getAlpha(int color) {
        return (color >> 24 & 0xFF) / 255.0F;
    }

    public static int getColorARGB(FluidStack fluidStack, float fluidScale) {
        if (fluidStack.isEmpty()) {
            return -1;
        }
        return getColorARGB(fluidStack);
    }
    private static int getColorARGB(FluidStack fluidStack) {
        return fluidStack.getFluid().getAttributes().getColor(fluidStack);
    }

    public static int calculateGlowLight(int light, FluidStack fluid) {
        return fluid.isEmpty() ? light : calculateGlowLight(light, fluid.getFluid().getAttributes().getLuminosity(fluid));
    }

    public static int calculateGlowLight(int light, int glow) {
        if (glow >= 15) {
            return FULL_LIGHT;
        }
        int blockLight = LightTexture.block(light);
        int skyLight = LightTexture.sky(light);
        return LightTexture.pack(Math.max(blockLight, glow), Math.max(skyLight, glow));
    }
}
