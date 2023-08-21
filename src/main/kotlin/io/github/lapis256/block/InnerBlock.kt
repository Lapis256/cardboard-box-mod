package io.github.lapis256.block

import io.github.lapis256.extension.createNbtSafe
import io.github.lapis256.extension.getBlockState
import io.github.lapis256.extension.putBlockPos
import io.github.lapis256.extension.putBlockState
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.MutableText
import net.minecraft.util.math.BlockPos

data class InnerBlock(private var _blockState: BlockState, private var _data: NbtCompound) {
    val blockState: BlockState
        get() = _blockState
    val data: NbtCompound
        get() = _data

    companion object {
        private const val BLOCK_STATE_TAG_KEY = "blockState"
        private const val BLOCK_DATA_TAG_KEY = "data"
        private const val BLOCK_ITEM_DATA_TAG_KEY = "BlockEntityTag"

        fun hasInnerBlock(itemStack: ItemStack) = itemStack.nbt?.contains(BLOCK_ITEM_DATA_TAG_KEY) ?: false

        fun fromItemStack(itemStack: ItemStack) =
            itemStack.nbt?.getCompound(BLOCK_ITEM_DATA_TAG_KEY)?.let { fromNbt(it) }

        fun fromNbt(nbt: NbtCompound) =
            InnerBlock(nbt.getBlockState(BLOCK_STATE_TAG_KEY), nbt.getCompound(BLOCK_DATA_TAG_KEY).copy())
    }

    fun writeToBlockEntityNbt(nbt: NbtCompound) {
        nbt.putBlockState(BLOCK_STATE_TAG_KEY, blockState)
        nbt.put(BLOCK_DATA_TAG_KEY, data)
    }

    fun updateBlockPos(blockPos: BlockPos) = data.putBlockPos(blockPos)

    fun updateFromNbt(nbt: NbtCompound) {
        _blockState = nbt.getBlockState(BLOCK_STATE_TAG_KEY)
        _data = nbt.getCompound(BLOCK_DATA_TAG_KEY).copy()
    }

    fun updateFromBlock(blockState: BlockState, blockEntity: BlockEntity?) {
        _blockState = blockState
        _data = blockEntity.createNbtSafe()
    }

    val blockName: MutableText
        get() = blockState.block.name
}
