package ru.kochkaev.stopraid

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.server.command.CommandManager
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.text.TextColor
import net.minecraft.util.Colors

class StopRaid : ModInitializer {

    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                CommandManager.literal("stopraid")
                    .executes {
                        val player = it.source.player
                        if (player!=null) {
                            val raid = player.serverWorld.getRaidAt(player.blockPos)
                            if (raid != null) {
                                raid.invalidate()
                                it.source.sendFeedback({
                                    Text.of("Рейд был принудительно остановлен.")
                                        .getWithStyle(Style.EMPTY.withColor(Colors.LIGHT_GRAY).withItalic(true)).first()
                                }, false)
                            } else it.source.sendFeedback({
                                Text.of("Рейд не найден!")
                                    .getWithStyle(Style.EMPTY.withColor(Colors.LIGHT_GRAY).withItalic(true)).first()
                            }, false)
                        }
                        else it.source.sendFeedback({
                            Text.of("Эту команду может выполнять только игрок!")
                                .getWithStyle(Style.EMPTY.withColor(Colors.LIGHT_GRAY).withItalic(true)).first()
                        }, false)
                        0
                    }
            )
        }
    }
}
