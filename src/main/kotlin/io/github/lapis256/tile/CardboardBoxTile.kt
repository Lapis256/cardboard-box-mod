package io.github.lapis256.tile

import io.github.lapis256.block.Blocks
import io.github.lapis256.block.InnerBlock
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.Packet
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.util.math.BlockPos

class CardboardBoxTile(pos: BlockPos, state: BlockState) :
    BlockEntity(Tiles.CARDBOARD_BOX, pos, state) {

    val innerBlockData = InnerBlock(Blocks.CARDBOARD_BOX.defaultState, NbtCompound())

    override fun writeNbt(nbt: NbtCompound) = innerBlockData.writeToBlockEntityNbt(nbt)

    override fun readNbt(nbt: NbtCompound) = innerBlockData.updateFromNbt(nbt)
}
