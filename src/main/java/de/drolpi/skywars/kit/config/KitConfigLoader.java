package de.drolpi.skywars.kit.config;

import de.drolpi.skywars.config.AbstractConfigurationLoader;
import java.nio.file.Paths;

public class KitConfigLoader extends AbstractConfigurationLoader<KitConfig> {

    public KitConfigLoader() {
        super(Paths.get("plugins", "SkyWars", "kits.json"), KitConfig.class);
    }

    @Override
    public KitConfig createObject() {
        return new KitConfig();
    }
}
