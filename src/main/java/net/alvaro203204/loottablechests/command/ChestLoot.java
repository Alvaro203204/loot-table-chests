package net.alvaro203204.loottablechests.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.alvaro203204.loottablechests.config.ConfigManager;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ChestLoot {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("chestloot")
                .then(CommandManager.literal("reload").executes(ChestLoot::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) {
        PlayerEntity player = context.getSource().getPlayer();

        player.sendMessage(Text.literal("Loading data..."));

        ConfigManager.readConfigFile();

        player.sendMessage(Text.literal("Data loaded"));

        return 1;
    }
}
