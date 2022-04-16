package de.drolpi.skywars.world;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.world.config.WorldConfig;
import de.drolpi.skywars.world.config.WorldConfigLoader;
import de.drolpi.skywars.world.wrapper.LocalWorldWrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class WorldManager {

    private final static Path WORLD_STORAGE = Paths.get("plugins", "SkyWars", "worlds");

    private final SkyWars skyWars;
    private final Map<String, LocalWorldWrapper> gameWorlds;
    private final WorldConfigLoader configLoader;
    private final WorldConfig config;
    private LobbyWorld lobbyWorld;

    public WorldManager(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.gameWorlds = new HashMap<>();
        this.configLoader = new WorldConfigLoader();
        this.config = configLoader.loadOrCreateFile();

        try {
            Files.createDirectories(WORLD_STORAGE);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void unloadWorlds() {
        lobbyWorld.unload();
        for (LocalWorldWrapper world : gameWorlds.values()) {
            world.unload();
        }
    }

    public boolean loadWorlds() {
        lobbyWorld = config.getLobbyWorld();
        lobbyWorld.load();

        if (lobbyWorld == null) {
            return false;
        }

        Set<LocalWorldWrapper> loadedWorlds = config.getGameWorlds()
                .stream()
                .map(localWorld -> new LocalWorldWrapper(localWorld, skyWars, false))
                .collect(Collectors.toSet());

        for (LocalWorldWrapper localWorld : loadedWorlds) {
            if(localWorld.restoreFromSource()) {
                gameWorlds.put(localWorld.getWorldName(), localWorld);
            }
        }

        return gameWorlds.size() > 0;
    }

    public LocalWorldWrapper randomWorld() {
        return new ArrayList<>(gameWorlds.values()).get(ThreadLocalRandom.current().nextInt(gameWorlds.size()));
    }

    public LobbyWorld getLobbyWorld() {
        return lobbyWorld;
    }

    public Map<String, LocalWorldWrapper> getGameWorlds() {
        return gameWorlds;
    }

    public WorldConfig getConfig() {
        return config;
    }

    public WorldConfigLoader getConfigLoader() {
        return configLoader;
    }
}
