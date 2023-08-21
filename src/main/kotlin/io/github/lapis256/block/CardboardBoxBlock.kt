package io.github.lapis256.block

import io.github.lapis256.extension.createNbtSafe
import io.github.lapis256.extension.getBlockEntity
import io.github.lapis256.extension.spawnItemStack
import io.github.lapis256.tile.CardboardBoxTile
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CardboardBoxBlock : Block(
    FabricBlockSettings.of(Material.WOOD).strength(0.5f)
), BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = CardboardBoxTile(pos, state)

    override fun onUse(
        state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult
    ): ActionResult {
        if (player.getStackInHand(hand).item == this.asItem()) {
            return ActionResult.PASS
        }
        if (world.isClient) {
            return ActionResult.SUCCESS
        }

        val blockEntity = world.getBlockEntity<CardboardBoxTile>(pos) ?: return ActionResult.PASS
        val innerBlock = InnerBlock.fromNbt(blockEntity.createNbtSafe())
        innerBlock.updateBlockPos(pos)
        world.setBlockState(pos, innerBlock.blockState)
        world.getBlockEntity(pos)?.let {
            it.readNbt(innerBlock.data)
            it.markDirty()
        }

        if (!player.isCreative) {
            world.spawnItemStack(pos, ItemStack(this))
        }

        return ActionResult.SUCCESS
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        if (world.isClient || !player.isCreative) {
            return
        }

        val blockEntity = world.getBlockEntity<CardboardBoxTile>(pos)
        getDroppedStacks(state, world as ServerWorld, pos, blockEntity).forEach {
            world.spawnItemStack(pos, it)
        }
    }
}
