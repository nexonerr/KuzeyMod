package com.nexoner.kuzey.block.entity.template;

import com.nexoner.kuzey.block.entity.entityType.IFluidHandlingBlockEntity;
import com.nexoner.kuzey.util.CustomFluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.nexoner.kuzey.util.IKuzeyConstants.BUCKET_VOLUME;

public abstract class AbstractFluidGeneratorEntity extends AbstractGeneratorEntity implements IFluidHandlingBlockEntity {

    private int capacity;
    private int progress;
    private int maxProgress;
    private boolean fluidFiltering;
    private TagKey<Fluid> tag;

    public CustomFluidTank fluidTank;
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public HashMap<Fluid,Integer> generatedMap;
    public HashMap<Fluid,Integer> burnRateMap;
    public HashMap<Fluid,Integer> burnRateMapMB;

    protected final ContainerData data;

    public AbstractFluidGeneratorEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, TranslatableComponent name, int pCapacity, int pMaxExtracted,
                                        int pProduced, int pSlots, int pFluidCapacity, TagKey<Fluid> pTag, boolean pFluidFiltering) {
        super(pType, pPos, pBlockState, name, pCapacity, pMaxExtracted, pProduced, pSlots);

        fluidTank = createFluidTank(pFluidCapacity,pFluidFiltering);

        this.capacity = pFluidCapacity;
        this.fluidFiltering = pFluidFiltering;
        this.tag = pTag;

        this.generatedMap = getFluidProperty(0);
        this.burnRateMap = getFluidProperty(1);
        this.burnRateMapMB = getFluidProperty(2);

        data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                switch (pIndex) {
                    case 0: return progress;
                    case 1: return maxProgress;
                    case 2: return getEnergy();
                    case 3: return getMaxEnergy();
                    case 4: return fluidTank.getFluidAmount();
                    case 5: return fluidTank.getCapacity();
                    default: return 0;
                }
            }

            @Override
            public void set(int pIndex, int pValue) {
                //this is a very useless method for this use case
                int maxEnergy = energyStorage.getMaxEnergyStored();
                int fluidAmount = fluidTank.getFluidAmount();
                int fluidCapacity = fluidTank.getCapacity();
                switch (pIndex) {
                    case 0: progress = pValue;break;
                    case 1: maxProgress = pValue;break;
                    case 2: energyStorage.setEnergy(pValue);break;
                    case 3: maxEnergy = pValue;break;
                    case 4: fluidAmount = pValue;break;
                    case 5: fluidCapacity = pValue;break;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };

    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap,@Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap,side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyFluidHandler = LazyOptional.of(() -> fluidTank);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag = fluidTank.writeToNBT(pTag);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        fluidTank.readFromNBT(pTag);
    }

    //OVERRIDE ALL OF THESE

    //Fluid-RF/t definition
    //It's recommended that this can be divided with the energy capacity
    public void defineFluidGenerated(HashMap<Fluid,Integer> map){
        //Fluids should be defined here
    }

    //Fluid-Burnrate definition, defines how many ticks a defined mb of fluid lasts
    public void defineFluidBurnRate(HashMap<Fluid,Integer> map){
        //Fluids should be defined here
    }

    //Fluid-Burnrate definition, defines defines how many mb will get removed per removed tick
    public void defineFluidBurnRateMB(HashMap<Fluid,Integer> map){
        //Fluids should be defined here
    }

    public HashMap<Fluid,Integer> getFluidProperty(int property){

        switch (property){
            case 0: {
                HashMap<Fluid,Integer> generatedMap = new HashMap<Fluid,Integer>();
                defineFluidGenerated(generatedMap);
                return generatedMap;
            }
            case 1: {
                HashMap<Fluid,Integer> burnRateMap = new HashMap<Fluid,Integer>();
                defineFluidBurnRate(burnRateMap);
                return burnRateMap;
            }
            case 2: {
                HashMap<Fluid,Integer> burnRateMapMB = new HashMap<Fluid,Integer>();
                defineFluidBurnRateMB(burnRateMapMB);
                return burnRateMapMB;
            }
        }
        return new HashMap<Fluid,Integer>();


    }

    private CustomFluidTank createFluidTank(int fluidCapacity, boolean fluidFiltering){
        if (fluidFiltering == false){
            return new CustomFluidTank(fluidCapacity, this);
        }
        return new CustomFluidTank(fluidCapacity, this, tag);
    }

    public boolean shouldProduceEnergy(){
        for (Map.Entry<Fluid,Integer> entry: generatedMap.entrySet()){
            if (entry.getKey() == fluidTank.getFluid().getFluid()){
                return true;
            }
        }
        return false;
    }

    private void transferLiquidFromItem(){
        if (fluidTank.getSpace() > 0){
            ItemStack fluidTankItem = itemHandler.getStackInSlot(0);
            if (fluidTankItem.getItem() instanceof BucketItem && fluidTankItem.getItem() != Items.BUCKET){
                if (fluidTank.getSpace() >= 0){
                    fluidTankItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler ->{
                        Fluid fluidToFill = handler.getFluidInTank(0).getFluid();
                        itemHandler.extractItem(0,1,false);
                        itemHandler.insertItem(0,new ItemStack(Items.BUCKET,1),false);
                        fluidTank.fillExtra(BUCKET_VOLUME,fluidToFill, IFluidHandler.FluidAction.EXECUTE);
                    });
                }
            } else {
                fluidTankItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler->{
                    int toDrain = Math.min(Math.min(handler.getFluidInTank(0).getAmount(), BUCKET_VOLUME), fluidTank.getSpace());
                    if (toDrain > 0){
                        Fluid fluidToFill = handler.getFluidInTank(0).getFluid();
                        handler.drain(toDrain, IFluidHandler.FluidAction.EXECUTE);
                        fluidTank.fillExtra(toDrain, fluidToFill, IFluidHandler.FluidAction.EXECUTE);
                    }
                });
            }}}

    @Override
    public void tickCustom(AbstractGeneratorEntity entity) {
        Fluid fluid = fluidTank.getFluid().getFluid();
        if (shouldProduceEnergy()){
            maxProgress = burnRateMap.get(fluid);
            if (canInsertEnergy(entity)){
                if (fluidTank.getFluidAmount() >= burnRateMapMB.get(fluid)){
                    energyStorage.addEnergy(generatedMap.get(fluid));
                    progress++;
            }}
            if (progress >= maxProgress){
                this.progress = 0;
                fluidTank.setAmount(fluidTank.getFluidAmount() - burnRateMapMB.get(fluid));
            }
        }
        transferLiquidFromItem();
    }
}
