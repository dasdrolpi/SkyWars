package de.drolpi.skywars.team.config;

import de.drolpi.skywars.config.AbstractConfigurationLoader;
import java.nio.file.Paths;

public class TeamConfigLoader extends AbstractConfigurationLoader<TeamConfig> {

    public TeamConfigLoader() {
        super(Paths.get("plugins", "SkyWars", "teams.json"), TeamConfig.class);
    }

    @Override
    public TeamConfig createObject() {
        return new TeamConfig();
    }

}
