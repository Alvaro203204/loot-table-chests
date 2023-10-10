package net.alvaro203204.loottablechests;

import net.alvaro203204.loottablechests.command.ChestLoot;
import net.alvaro203204.loottablechests.config.ConfigManager;
import net.alvaro203204.loottablechests.event.ChestOpenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LootTableChests implements ModInitializer {
	public static final String MOD_ID = "loottablechests";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ConfigManager.initializeConfig();
		CommandRegistrationCallback.EVENT.register(ChestLoot::register);
		UseBlockCallback.EVENT.register(new ChestOpenHandler());
	}
}
