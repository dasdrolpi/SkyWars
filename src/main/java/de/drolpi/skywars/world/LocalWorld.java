package de.drolpi.skywars.world;

import de.drolpi.skywars.location.SerializableLocation;
import de.drolpi.skywars.team.GameTeam;
import de.drolpi.skywars.team.TeamColor;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.world.island.Island;
import de.drolpi.skywars.world.wrapper.WorldWrapper;
import org.bukkit.Location;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class LocalWorld {

    private final String worldName;
    private String name;
    private String builder;

    private final Set<Island> islands;
    private SerializableLocation spectatorSpawn;

    public LocalWorld(String worldName) {
        this.worldName = worldName;
        this.islands = new HashSet<>();
    }

    public String getWorldName() {
        return worldName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBuilder(String builder) {
        this.builder = builder;
    }

    public String getBuilder() {
        return builder;
    }

    public void addIsland(Island island) {
        islands.add(island);
    }

    public Set<Island> getIslands() {
        return islands;
    }

    public void setSpectatorSpawn(Location spectatorSpawn) {
        this.spectatorSpawn = new SerializableLocation(spectatorSpawn);
    }

    public SerializableLocation getSpectatorSpawn() {
        return spectatorSpawn;
    }

    public void setSpectatorSpawn(SerializableLocation spectatorSpawn) {
        this.spectatorSpawn = spectatorSpawn;
    }

    public SerializableLocation getSpawnForTeam(GameTeam team) {
        Optional<Island> optional = islands.stream().filter(island -> island.getTeamColor().equals(team.getColor())).findFirst();
        return optional.map(Island::getSpawnLocation).orElse(null);
    }
}
