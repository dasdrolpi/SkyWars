package de.drolpi.skywars.counter.config;

import de.drolpi.skywars.config.AbstractConfigurationLoader;

import java.nio.file.Paths;

public class CounterConfigLoader extends AbstractConfigurationLoader<CounterConfig> {

    public CounterConfigLoader() {
        super(Paths.get("plugins", "SkyWars", "countdowns.json"), CounterConfig.class);
    }

    @Override
    public CounterConfig createObject() {
        return new CounterConfig();
    }
}
