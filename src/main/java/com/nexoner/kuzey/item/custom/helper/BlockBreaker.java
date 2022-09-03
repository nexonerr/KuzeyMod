package com.nexoner.kuzey.item.custom.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;
import java.util.stream.Stream;

public class BlockBreaker {

    /**
     * BlockBreaker class by Melanx
     * https://github.com/MelanX/MoreVanillaLib
     *
     * Under Apache 2.0 license
     * https://www.apache.org/licenses/LICENSE-2.0.txt
     *
     * Class was modified to remove requirements
     * for other classes available under the MoreVanillaLib
     * and add in an extra method
     */

    private BlockBreaker() {
        // NO-OP
    }

    /**
     * Breaks blocks within the given radius on the axis the player is facing.
     * If the player is facing the X axis and the radius is 1, a 3x3 area will be destroyed on the X axis.
     *
     * @param level          world the player is in
     * @param player         the player
     * @param radius         radius to break
     * @param depth          depth to break
     */
    public static void breakInRadius(Level level, Player player, int radius, int depth, BlockPos originPos) {
        if (!level.isClientSide) {
            Stream<BlockPos> brokenBlocks = getBreakBlocks(level, player, radius, depth, originPos);
            ItemStack heldItem = player.getMainHandItem();
            brokenBlocks.forEach(pos -> {
                if (!pos.equals(originPos)) {
                    BlockState state = level.getBlockState(pos);
                    if (heldItem.isCorrectToolForDrops(state)) {
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        if (player.getAbilities().instabuild) {
                            if (state.onDestroyedByPlayer(level, pos, player, true, state.getFluidState()))
                                state.getBlock().destroy(level, pos, state);
                        } else {
                            BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, pos, state, player);
                            MinecraftForge.EVENT_BUS.post(event);

                            if (event.isCanceled()) {
                                // Forge copy
                                serverPlayer.connection.send(new ClientboundBlockUpdatePacket(level, pos));
                                BlockEntity tile = level.getBlockEntity(pos);
                                if (tile != null) {
                                    Packet<?> packet = tile.getUpdatePacket();
                                    if (packet != null) {
                                        serverPlayer.connection.send(packet);
                                    }
                                }
                            } else {
                                heldItem.getItem().mineBlock(heldItem, level, state, pos, player);
                                BlockEntity blockEntity = level.getBlockEntity(pos);
                                state.getBlock().destroy(level, pos, state);
                                state.getBlock().playerDestroy(level, player, pos, state, blockEntity, heldItem);
                                state.getBlock().popExperience((ServerLevel) level, pos, event.getExpToDrop());

                                level.removeBlock(pos, false);
                                level.levelEvent(2001, pos, Block.getId(state));
                                serverPlayer.connection.send(new ClientboundBlockUpdatePacket(level, pos));
                            }
                        }
                    }
                }
            });
        }
    }

    public static Stream<BlockPos> getBreakBlocks(Level level, Player player, int radius, BlockPos originPosition) {
        return getBreakBlocks(level, player, radius, 0, originPosition);
    }

    /**
     * Returns a list of the blocks that would be broken in breakInRadius, but doesn't break them.
     *
     * @param level  world of player
     * @param player player breaking
     * @param radius radius to break in
     * @param depth  depth to break in
     * @return a list of blocks that would be broken with the given radius and tool
     */
    public static Stream<BlockPos> getBreakBlocks(Level level, Player player, int radius, int depth, BlockPos originPosition) {
        Stream<BlockPos> potentialBrokenBlocks = Stream.of();

        Vec3 eyePosition = player.getEyePosition();
        Vec3 rotation = player.getViewVector(1);
        AttributeInstance attribute = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
        //noinspection ConstantConditions
        double reach = attribute.getValue();
        Vec3 combined = eyePosition.add(rotation.x * reach, rotation.y * reach, rotation.z * reach);

        BlockHitResult rayTraceResult = level.clip(new ClipContext(player.getEyePosition(), combined, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

        if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
            Direction side = rayTraceResult.getDirection();

            boolean doX = side.getStepX() == 0;
            boolean doY = side.getStepY() == 0;
            boolean doZ = side.getStepZ() == 0;

            BlockPos begin = new BlockPos(doX ? -radius : 0, doY ? -radius : 0, doZ ? -radius : 0);
            BlockPos end = new BlockPos(doX ? radius : depth * -side.getStepX(), doY ? radius : depth * -side.getStepY(), doZ ? radius : depth * -side.getStepZ());

            return BlockPos.betweenClosedStream(originPosition.offset(begin), originPosition.offset(end));
        }

        return potentialBrokenBlocks;
    }

    public static List<ItemStack> breakBlockAndReturnDrops(BlockPos blockPos, Level pLevel, BlockPos spawnPos, BlockPos originPos, ItemStack pTool, LivingEntity pEntity){
        Player player = (Player) pEntity;
        LootContext.Builder lootContextBuilder =
                (new LootContext.Builder((ServerLevel)pLevel)).withRandom(pLevel.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(spawnPos))
                        .withParameter(LootContextParams.TOOL, pTool).withOptionalParameter(LootContextParams.THIS_ENTITY, pEntity);
        List<ItemStack> list = pLevel.getBlockState(blockPos).getDrops(lootContextBuilder);
        BlockState state = pLevel.getBlockState(blockPos);
        if (!blockPos.equals(originPos)) {
            if (pTool.isCorrectToolForDrops(state)) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                if (player.getAbilities().instabuild) {
                    if (state.onDestroyedByPlayer(pLevel, blockPos, player, true, state.getFluidState()))
                        state.getBlock().destroy(pLevel, blockPos, state);
                } else {
                    BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(pLevel, blockPos, state, player);
                    MinecraftForge.EVENT_BUS.post(event);

                    if (event.isCanceled()) {
                        // Forge copy
                        serverPlayer.connection.send(new ClientboundBlockUpdatePacket(pLevel, blockPos));
                        BlockEntity tile = pLevel.getBlockEntity(blockPos);
                        if (tile != null) {
                            Packet<?> packet = tile.getUpdatePacket();
                            if (packet != null) {
                                serverPlayer.connection.send(packet);
                            }
                        }
                    } else {
                        pTool.getItem().mineBlock(pTool, pLevel, state, blockPos, player);
                        state.getBlock().destroy(pLevel, blockPos, state);
                        state.getBlock().popExperience((ServerLevel) pLevel, blockPos, event.getExpToDrop());

                        pLevel.removeBlock(blockPos, false);
                        pLevel.levelEvent(2001, blockPos, Block.getId(state));
                        serverPlayer.connection.send(new ClientboundBlockUpdatePacket(pLevel, blockPos));
                    }
                }
            }
        }
        return list;
    }
}
