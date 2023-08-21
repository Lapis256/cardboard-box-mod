package io.github.lapis256.tile

import io.github.lapis256.CardboardBoxMod
import io.github.lapis256.block.Blocks
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.util.registry.Registry

object Tiles {
    val CARDBOARD_BOX = FabricBlockEntityTypeBuilder.create(::CardboardBoxTile, Blocks.CARDBOARD_BOX).build(null)!!

    object Ids {
        val CARDBOARD_BOX = CardboardBoxMod.id("cardboard_box")
    }

    fun init() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, Ids.CARDBOARD_BOX, CARDBOARD_BOX)
    }
}
