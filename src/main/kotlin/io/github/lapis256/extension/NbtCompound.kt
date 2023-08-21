package io.github.lapis256.extension

import net.minecraft.block.BlockState
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.util.math.BlockPos

fun NbtCompound.putBlockState(key: String, blockState: BlockState) = put(key, NbtHelper.fromBlockState(blockState))

fun NbtCompound.getBlockState(key: String): BlockState = NbtHelper.toBlockState(getCompound(key))

fun NbtCompound.putBlockPos(blockPos: BlockPos, key: String? = null) =
    NbtHelper.fromBlockPos(blockPos).let { nbt -> key?.let { put(it, nbt) } ?: copyFrom(nbt); Unit };
