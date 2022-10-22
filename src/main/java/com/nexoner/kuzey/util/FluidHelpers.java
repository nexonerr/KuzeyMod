package com.nexoner.kuzey.util;

import com.nexoner.kuzey.util.render.FluidRenderMap;
import com.nexoner.kuzey.util.render.Model3D;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraftforge.fluids.FluidStack;

public class FluidHelpers {

    public static final FluidRenderMap<Int2ObjectMap<Model3D>> CACHED_FLUIDS = new FluidRenderMap<>();
    public static final int STAGES = 1400;

    //From Cyclic
    //Gets fluid, if it is cached, and then returns a Model3D with a bound texture for fluid BE rendering
    public static Model3D getFluidModel(FluidStack fluid, int stage, float pSideSpacing, float pBelowSpacing, float pTopSpacing) {
        if (CACHED_FLUIDS.containsKey(fluid) && CACHED_FLUIDS.get(fluid).containsKey(stage)) {
            return CACHED_FLUIDS.get(fluid).get(stage);
        }
        Model3D model = new Model3D();
        model.setTexture(FluidRenderMap.getFluidTexture(fluid, FluidRenderMap.FluidType.STILL));
        if (fluid.getFluid().getAttributes().getStillTexture(fluid) != null) {
            float sideSpacing = pSideSpacing;
            float belowSpacing = pBelowSpacing;
            float topSpacing = pTopSpacing;
            model.minX = sideSpacing;
            model.minY = belowSpacing;
            model.minZ = sideSpacing;
            model.maxX = 1 - sideSpacing;
            model.maxY = 1 - topSpacing;
            model.maxZ = 1 - sideSpacing;
        }
        if (CACHED_FLUIDS.containsKey(fluid)) {
            CACHED_FLUIDS.get(fluid).put(stage, model);
        }
        else {
            Int2ObjectMap<Model3D> map = new Int2ObjectOpenHashMap<>();
            map.put(stage, model);
            CACHED_FLUIDS.put(fluid, map);
        }
        return model;
    }
}
