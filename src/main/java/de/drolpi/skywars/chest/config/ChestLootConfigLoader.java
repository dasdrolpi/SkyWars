package de.drolpi.skywars.chest.config;

import de.drolpi.skywars.config.AbstractConfigurationLoader;

import java.nio.file.Paths;

public class ChestLootConfigLoader extends AbstractConfigurationLoader<ChestLootConfig> {

    public ChestLootConfigLoader() {
        super(Paths.get("plugins", "SkyWars", "chestloot.json"), ChestLootConfig.class);
    }

    @Override
    public ChestLootConfig createObject() {
        return new ChestLootConfig();
    }
}
