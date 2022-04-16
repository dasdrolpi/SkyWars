package de.drolpi.skywars.world.config;

import de.drolpi.skywars.location.SerializableLocation;
import de.drolpi.skywars.world.LobbyWorld;
import de.drolpi.skywars.world.LocalWorld;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class WorldConfig {

    private LobbyWorld lobbyWorld;
    private final Set<LocalWorld> gameWorlds;

    public WorldConfig() {
        this.lobbyWorld = new LobbyWorld("world");
        this.gameWorlds = new HashSet<>();

        lobbyWorld.setSpawnLocation(new SerializableLocation("world", 0, 100, 0));

        /*
        lobbyWorld.setSpawnLocation(new SerializableLocation("world", 258.5, 33, 184.5, 90, 0));

        SkyWarsWorld world = new SkyWarsWorld();

        world.setName("Trees");
        world.setWorldName("SWTrees");
        world.setBuilder("DROLPI");
        world.setSpectatorSpawn(new SerializableLocation("SWTrees", 0.5, 80, 0.5));

        Island redIsland = new Island(TeamColor.RED, new SerializableLocation("SWTrees", 20.5, 64, 49.5, 158, 0));
        Island blueIsland = new Island(TeamColor.BLUE, new SerializableLocation("SWTrees", -19.5, 64, -48.5, -22, 0));
        Island greenIsland = new Island(TeamColor.GREEN, new SerializableLocation("SWTrees", -48.5, 64, 20.5, -112, 0));
        Island yellowIsland = new Island(TeamColor.YELLOW, new SerializableLocation("SWTrees", 49.5, 64, -19.5, 68, 0));
        Island pinkIsland = new Island(TeamColor.PINK, new SerializableLocation("SWTrees", -19.5, 64, 49.5, -158, 0));
        Island lightBlueIsland = new Island(TeamColor.LIGHT_BLUE, new SerializableLocation("SWTrees", 20.5, 64, -48.5, 22, 0));
        Island lightGreenIsland = new Island(TeamColor.LIGHT_GREEN, new SerializableLocation("SWTrees", -48.5, 64, -19.5, -68, 0));
        Island orangeIsland = new Island(TeamColor.ORANGE, new SerializableLocation("SWTrees", 49.5, 64, 20.5, 112, 0));

        world.addIsland(redIsland);
        world.addIsland(blueIsland);
        world.addIsland(yellowIsland);
        world.addIsland(greenIsland);
        world.addIsland(pinkIsland);
        world.addIsland(lightBlueIsland);
        world.addIsland(lightGreenIsland);
        world.addIsland(orangeIsland);


        this.gameWorlds.add(world);

         */
    }
}
