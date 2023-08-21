package io.github.lapis256.extension

import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World


inline fun <reified T : BlockEntity> World.getBlockEntity(pos: BlockPos): T? = getBlockEntity(pos) as? T

fun World.spawnItemStack(blockPos: BlockPos, itemStack: ItemStack) {
    val pos = Vec3d.ofCenter(blockPos)
    val itemEntity = ItemEntity(this, pos.x, pos.y, pos.z, itemStack)
    this.spawnEntity(itemEntity)
}
