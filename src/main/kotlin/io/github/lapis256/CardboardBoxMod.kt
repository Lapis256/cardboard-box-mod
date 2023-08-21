package io.github.lapis256

import io.github.lapis256.block.Blocks
import io.github.lapis256.item.Items
import io.github.lapis256.tile.Tiles
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object CardboardBoxMod : ModInitializer {
    const val MOD_ID = "cardboard-box"

    fun id(name: String) = Identifier(MOD_ID, name)

    private val logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        Blocks.init()
        Tiles.init()
        Items.init()
    }
}
