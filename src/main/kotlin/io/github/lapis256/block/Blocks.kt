package io.github.lapis256.block

import io.github.lapis256.CardboardBoxMod
import net.minecraft.util.registry.Registry

object Blocks {
    val CARDBOARD_BOX = CardboardBoxBlock()

    object Ids {
        val CARDBOARD_BOX = CardboardBoxMod.id("cardboard_box")
    }

    fun init() {
        Registry.register(Registry.BLOCK, Ids.CARDBOARD_BOX, CARDBOARD_BOX)
    }
}
