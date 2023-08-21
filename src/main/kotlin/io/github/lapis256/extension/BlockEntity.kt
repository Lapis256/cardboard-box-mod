package io.github.lapis256.extension

import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound


fun BlockEntity?.createNbtSafe() = this?.createNbtWithIdentifyingData() ?: NbtCompound()
