package io.github.lapis256.item

import io.github.lapis256.CardboardBoxMod
import net.minecraft.util.registry.Registry

object Items {

    val CARDBOARD_BOX = CardboardBoxBlockItem()

    object Ids {
        val CARDBOARD_BOX = CardboardBoxMod.id("cardboard_box")
    }

    fun init() {
        Registry.register(Registry.ITEM, Ids.CARDBOARD_BOX, CARDBOARD_BOX)
    }
}
