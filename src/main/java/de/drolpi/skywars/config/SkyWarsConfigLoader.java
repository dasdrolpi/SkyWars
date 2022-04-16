package de.drolpi.skywars.config;

import java.nio.file.Paths;

public class SkyWarsConfigLoader extends AbstractConfigurationLoader<SkyWarsConfig> {

    public SkyWarsConfigLoader() {
        super(Paths.get("plugins", "SkyWars", "config.json"), SkyWarsConfig.class);
    }

    @Override
    public SkyWarsConfig createObject() {
        return new SkyWarsConfig();
    }
}
