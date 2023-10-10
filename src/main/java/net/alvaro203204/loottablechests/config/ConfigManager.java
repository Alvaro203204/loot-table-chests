package net.alvaro203204.loottablechests.config;

import net.alvaro203204.loottablechests.LootTableChests;
import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    public static final Path FABRIC_CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    public static final String CONFIG_FILE = LootTableChests.MOD_ID + "/config.json";
    public static final String DEFAULT_TEXT =
            """
                {
                    "minecraft:forest": ["minecraft:chests/desert_pyramid", "minecraft:chests/ancient_city"],
                    "minecraft:river": ["minecraft:chests/bastion_treasure", "minecraft:chests/end_city_treasure"],
                    "default": ["minecraft:chests/ruined_portal"]
                }
            """;
    public static final List<Identifier> DEFAULT_LOOT_TABLES = new ArrayList();
    public static final Map<Identifier, List<Identifier>> LOOT_TABLES = new HashMap();
    public static void initializeConfig() {
        createConfigFile();
        readConfigFile();
    }

    public static void createConfigFile() {
        File configFile = FABRIC_CONFIG_DIR.resolve(CONFIG_FILE).toFile();
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                FileWriter configFileWriter = new FileWriter(configFile);

                configFileWriter.write(DEFAULT_TEXT);
                configFileWriter.close();

                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readConfigFile() {
        File configFile = FABRIC_CONFIG_DIR.resolve(CONFIG_FILE).toFile();
        if (configFile.exists()) {
            try {
                Reader configReader = Files.newBufferedReader(configFile.toPath());

                Gson gson = new Gson();

                Map<String, List<String>> configMap = gson.fromJson(configReader, Map.class);

                LOOT_TABLES.clear();
                DEFAULT_LOOT_TABLES.clear();

                for (Map.Entry<String, List<String>> entry : configMap.entrySet()) {
                    String biome = entry.getKey();
                    List<String> tables = entry.getValue();

                    if (!biome.equals("default")) {
                        List<Identifier> tablesIdentifiers = new ArrayList();
                        for (String table: tables) {
                            tablesIdentifiers.add(new Identifier(table));
                        }
                        LOOT_TABLES.put(new Identifier(biome), tablesIdentifiers);
                    } else {
                        for (String table: tables) {
                            DEFAULT_LOOT_TABLES.add(new Identifier(table));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else LootTableChests.LOGGER.error("No configuration file found for Loot Table Chests Mod in: " + configFile.getPath());
    }
}