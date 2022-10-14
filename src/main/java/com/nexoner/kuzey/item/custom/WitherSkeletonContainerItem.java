package com.nexoner.kuzey.item.custom;

import com.nexoner.kuzey.fluid.ModFluids;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.util.FluidHandlerCapabilityStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WitherSkeletonContainerItem extends Item {

    public WitherSkeletonContainerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if (pInteractionTarget instanceof WitherSkeleton entity && entity.isAlive()){
            if (!pPlayer.getCooldowns().isOnCooldown(ModItems.WITHER_SKELETON_CONTAINER.get())) {

                //This is stupid, slow, overengineered and possibly the most unsafe implementation of an item like this that could ever be done
                //But I spent way too long on a stupid issue and I refuse to spend more time on this

                if (pPlayer.level.isClientSide) {
                    spawnParticles(pPlayer.getLevel(), entity.getOnPos());
                    return InteractionResult.SUCCESS;
                }
                if (!pPlayer.level.isClientSide) {

                    IFluidHandler handler = pStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);

                    if (handler.getFluidInTank(0).getAmount() + 500 <= handler.getTankCapacity(0)) {
                        handler.fill(new FluidStack(ModFluids.EMRE_ESSENCE_FLUID.get(), 500), IFluidHandler.FluidAction.EXECUTE);
                        CompoundTag tag = new CompoundTag();
                        handler.getFluidInTank(0).writeToNBT(tag);

                        ItemStack toUse = pStack.copy();

                        toUse.getTag().put(FluidHandlerCapabilityStack.FLUID_NBT_KEY, tag);

                        pPlayer.getInventory().removeItem(pPlayer.getInventory().selected, 1);
                        pPlayer.getInventory().add(pPlayer.getInventory().selected, toUse);

                        entity.remove(Entity.RemovalReason.KILLED);

                        pPlayer.playSound(SoundEvents.ENDER_DRAGON_GROWL, 1, 0.5f);

                        pPlayer.getCooldowns().addCooldown(ModItems.WITHER_SKELETON_CONTAINER.get(), 75);

                        return InteractionResult.SUCCESS;
                    } else {
                        pPlayer.playSound(SoundEvents.VILLAGER_NO, 1, 1);

                        pPlayer.displayClientMessage(new TranslatableComponent("tooltip.kuzey.no_capacity"), true);
                    }
                }
            }
        }
        return InteractionResult.FAIL;

    }

    private void spawnParticles(Level pLevel, BlockPos pPos){
        for (int i = 0; i < 360; i++){
            if (i % 10 == 0){
                pLevel.addParticle(ParticleTypes.DRAGON_BREATH, pPos.getX() + 0.5d + Math.cos(i) * 0.5d, pPos.getY() + 1, pPos.getZ() + 0.5d + Math.sin(i) * 0.5, Math.cos(i) * -0.2, 0.3d, Math.sin(i) * -0.2);
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandlerCapabilityStack(stack,6000, ModFluids.EMRE_ESSENCE_FLUID.get());
    }


    //Also from Cyclic
    public static FluidStack copyFluidFromStack(ItemStack stack) {
        if (stack.getTag() != null) {
            FluidHandlerCapabilityStack handler = new FluidHandlerCapabilityStack(stack, 6000, ModFluids.EMRE_ESSENCE_FLUID.get());
            return handler.getFluid();
        }
        return null;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        FluidStack stack = copyFluidFromStack(pStack);
        float amount = stack.getAmount();
        float max = 6000;
        return Math.round(13F * amount / max);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return 0xFF24C8BD;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        FluidStack fluid = copyFluidFromStack(pStack);
        return fluid != null && fluid.getAmount() > 0;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasAltDown()){
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.wither_skeleton_container"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.kuzey.alt"));
        }
            IFluidHandler handler = pStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).orElse(null);
            if (handler != null) {
                TranslatableComponent toUse = new TranslatableComponent(handler.getFluidInTank(0).getAmount() + " / " + handler.getTankCapacity(0) + "mB");
                toUse.withStyle(ChatFormatting.AQUA);
                pTooltipComponents.add(toUse);
            }
    }
}
