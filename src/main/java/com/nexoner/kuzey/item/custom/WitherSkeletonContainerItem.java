package com.nexoner.kuzey.item.custom;

import com.nexoner.kuzey.config.KuzeyCommonConfigs;
import com.nexoner.kuzey.fluid.ModFluids;
import com.nexoner.kuzey.item.ModItems;
import com.nexoner.kuzey.util.FluidHandlerCapabilityStack;
import com.nexoner.kuzey.util.GeneralUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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

                pPlayer.getItemInHand(pUsedHand).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler -> {
                    if (handler.getFluidInTank(0).getAmount() + KuzeyCommonConfigs.witherSkeletonContainerFluidProduced.get() <= handler.getTankCapacity(0)) {
                        handler.fill(new FluidStack(ModFluids.WITHER_ESSENCE.FLUID.get(), KuzeyCommonConfigs.witherSkeletonContainerFluidProduced.get()), IFluidHandler.FluidAction.EXECUTE);
                        spawnParticles(pPlayer.getLevel(), pInteractionTarget.getOnPos());
                        pPlayer.playSound(SoundEvents.WITHER_SKELETON_DEATH, 0.8f, 0.2f);
                        pInteractionTarget.remove(Entity.RemovalReason.KILLED);
                        pPlayer.getCooldowns().addCooldown(this,KuzeyCommonConfigs.witherSkeletonContainerCooldown.get());
                    } else {
                        pPlayer.playSound(SoundEvents.VILLAGER_NO, 1, 1);

                        pPlayer.displayClientMessage(new TranslatableComponent("tooltip.kuzey.no_capacity"), true);
                    }
                });
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
        return new FluidHandlerCapabilityStack(stack,KuzeyCommonConfigs.witherSkeletonContainerFluidCapacity.get(), ModFluids.WITHER_ESSENCE.FLUID.get());
    }


    //Also from Cyclic
    public static FluidStack copyFluidFromStack(ItemStack stack) {
        if (stack.getTag() != null) {
            FluidHandlerCapabilityStack handler = new FluidHandlerCapabilityStack(stack, KuzeyCommonConfigs.witherSkeletonContainerFluidCapacity.get(), ModFluids.WITHER_ESSENCE.FLUID.get());
            return handler.getFluid();
        }
        return null;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        FluidStack stack = copyFluidFromStack(pStack);
        float amount = stack.getAmount();
        float max = KuzeyCommonConfigs.witherSkeletonContainerFluidCapacity.get();
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
                TranslatableComponent toUse = new TranslatableComponent("tooltip.kuzey.integration.jei.liquid_amount_with_capacity",handler.getFluidInTank(0).getAmount(),handler.getTankCapacity(0));
                toUse.withStyle(ChatFormatting.AQUA);
                pTooltipComponents.add(toUse);
            }
    }
}
