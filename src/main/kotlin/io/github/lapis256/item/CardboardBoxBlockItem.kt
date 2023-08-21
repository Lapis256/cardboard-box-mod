package io.github.lapis256.item

import io.github.lapis256.block.Blocks
import io.github.lapis256.block.InnerBlock
import io.github.lapis256.extension.getBlockEntity
import io.github.lapis256.tile.CardboardBoxTile
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.world.World

class CardboardBoxBlockItem : BlockItem(Blocks.CARDBOARD_BOX, FabricItemSettings().maxCount(16)) {

    init {
        UseBlockCallback.EVENT.register(::useOnBlockHandler)
    }

    override fun appendTooltip(
        stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext
    ) {
        super.appendTooltip(stack, world, tooltip, context)

        InnerBlock.fromItemStack(stack)?.let {
            tooltip.add(Text.translatable("tooltip.cardboard-box.cardboard_box.inner_block", it.blockName))
        }
    }

    private fun useOnBlockHandler(
        player: PlayerEntity, world: World, hand: Hand, hitResult: BlockHitResult
    ): ActionResult {
        val itemStack = player.getStackInHand(hand)
        if (itemStack.item != this) {
            return ActionResult.PASS
        }
        if (InnerBlock.hasInnerBlock(itemStack)) {
            return ActionResult.PASS
        }

        val blockPos = hitResult.blockPos
        val blockState = world.getBlockState(blockPos)
        if (blockState.block.asItem() == this) {
            return ActionResult.FAIL
        }
        if (world.isClient) {
            return ActionResult.SUCCESS
        }

        val blockEntity = world.getBlockEntity(blockPos)
        world.removeBlockEntity(blockPos)
        world.setBlockState(blockPos, block.defaultState)
        world.getBlockEntity<CardboardBoxTile>(blockPos)?.let {
            it.innerBlockData.updateFromBlock(blockState, blockEntity)
        } ?: return ActionResult.FAIL

        if (!player.isCreative) {
            itemStack.decrement(1)
        }

        return ActionResult.SUCCESS
    }
}
