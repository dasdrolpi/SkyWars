package de.drolpi.skywars.world.config;

import de.drolpi.skywars.config.AbstractConfigurationLoader;

import java.nio.file.Paths;

public class WorldConfigLoader extends AbstractConfigurationLoader<WorldConfig> {

    public WorldConfigLoader() {
        super(Paths.get("plugins", "SkyWars", "locations.json"), WorldConfig.class);
    }

    @Override
    public WorldConfig createObject() {
        return new WorldConfig();
    }
}
