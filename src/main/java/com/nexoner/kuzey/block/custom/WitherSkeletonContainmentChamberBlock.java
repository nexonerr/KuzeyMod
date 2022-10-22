package com.nexoner.kuzey.block.custom;


import com.nexoner.kuzey.block.entity.custom.WitherSkeletonContainmentChamberBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class WitherSkeletonContainmentChamberBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public WitherSkeletonContainmentChamberBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return makeShape();
    }

    //facing

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    public VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.0625, 0.9375, 0.5625, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.0625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0.4375, 0.9375, 1, 0.875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0.4375, 0, 1, 0.875, 0.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0, 0.125, 0.875, 0.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.9375, 0.125, 0.875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.875, 0.0625, 0.875, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.4375, 0.0625, 0.0625, 0.875, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.9375, 0.4375, 0.0625, 1, 0.875, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.9375, 0.4375, 0.875, 1, 0.875, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.0625, 0.125, 1.125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0.75, 0.0625, 0.9375, 1.125, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0.75, 0.875, 0.9375, 1.125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.75, 0.875, 0.125, 1.125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.8125, 0.9375, 0.5625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0.1875, 0.1875, 0.5625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.0625, 0.1875, 0.9375, 0.5625, 0.8125), BooleanOp.OR);

        return shape;
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    //BLOCK ENTITY

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        //from Cyclic
        if (!pLevel.isClientSide) {
            BlockEntity tankHere = pLevel.getBlockEntity(pPos);
            if (tankHere != null) {
                IFluidHandler handler = tankHere.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, pHit.getDirection()).orElse(null);
                if (handler != null) {
                    if (FluidUtil.interactWithFluidHandler(pPlayer, pHand, handler)) {
                        if (pPlayer instanceof ServerPlayer) {
                            pLevel.playSound(pPlayer, pPos, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 0.7f, 1f);
                        }
                        ItemStack tankItem = pPlayer.getItemInHand(pHand);
                            if (tankItem != null) {
                                IFluidHandler handlerItem = tankItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
                                if (handler.getFluidInTank(0) != null) {
                                    TranslatableComponent toUse = new TranslatableComponent("tooltip.kuzey.integration.jei.liquid_amount_with_capacity", handlerItem.getFluidInTank(0).getAmount(), handlerItem.getTankCapacity(0));
                                    toUse.withStyle(ChatFormatting.AQUA);
                                    pPlayer.displayClientMessage(toUse, true);
                                }
                             else {
                                TranslatableComponent toUse = new TranslatableComponent("tooltip.kuzey.integration.jei.liquid_amount_with_capacity", handlerItem.getFluidInTank(0).getAmount(), handlerItem.getTankCapacity(0));
                                toUse.withStyle(ChatFormatting.AQUA);
                                pPlayer.displayClientMessage(toUse, true);
                            }
                        }
                    }
                }
            }
        }
        if (FluidUtil.getFluidHandler(pPlayer.getItemInHand(pHand)).isPresent()) {
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WitherSkeletonContainmentChamberBlockEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((WitherSkeletonContainmentChamberBlockEntity) blockEntity).tick(level,pos,state);
    }

}
