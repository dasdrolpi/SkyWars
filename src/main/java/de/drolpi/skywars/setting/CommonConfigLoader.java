package de.drolpi.skywars.setting;

import de.drolpi.skywars.config.AbstractConfigurationLoader;

import java.nio.file.Paths;

public class CommonConfigLoader extends AbstractConfigurationLoader<CommonConfig> {

    public CommonConfigLoader() {
        super(Paths.get("plugins", "SkyWars", "config.json"), CommonConfig.class);
    }

    @Override
    public CommonConfig createObject() {
        return new CommonConfig();
    }
}
