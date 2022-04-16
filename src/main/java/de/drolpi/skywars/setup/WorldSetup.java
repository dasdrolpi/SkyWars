package de.drolpi.skywars.setup;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.location.SerializableLocation;
import de.drolpi.skywars.runnable.CatchingRunnable;
import de.drolpi.skywars.setup.answer.QuestionAnswerLocation;
import de.drolpi.skywars.setup.answer.QuestionAnswerString;
import de.drolpi.skywars.team.GameTeam;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.world.LocalWorld;
import de.drolpi.skywars.world.WorldManager;
import de.drolpi.skywars.world.island.Island;
import de.drolpi.skywars.world.wrapper.LocalWorldWrapper;
import de.drolpi.skywars.world.wrapper.WorldWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class WorldSetup extends Setup {

    private final SkyWars skyWars;
    private final WorldManager worldManager;
    private final TeamRepository teamRepository;

    public WorldSetup(Player player, SkyWars skyWars) {
        super(player, skyWars.getSetupManager());
        this.skyWars = skyWars;
        this.worldManager = skyWars.getWorldManager();
        this.teamRepository = skyWars.getTeamRepository();
    }

    @Override
    public void configure() {
        addQuestion(new Question<>(
                "mapFolder",
                "§aWie heißt der Ordner der Welt, die geladen werden muss?",
                new QuestionAnswerString() {
                    @Override
                    public boolean isValidInput(@NotNull String input) {
                        return input.length() > 0;
                    }

                    @Override
                    public String getInvalidInputMessage(@NotNull String input) {
                        return "null";
                    }
                }
        ));

        addCompletionListener("mapFolder", object -> {
            if(object instanceof String) {
                Bukkit.getScheduler().runTask(skyWars.getPlugin(), new CatchingRunnable(() -> {
                    String worldName = (String) object;
                    World world = Bukkit.getWorld(worldName);
                    if(world == null) {
                        world = Bukkit.createWorld(new WorldCreator(worldName));
                    }
                    player.teleport(world.getSpawnLocation());
                }));
            }
        });

        addQuestion(new Question<>(
                "mapName",
                "§aSchreibe einen Namen für die Map in den Chat",
                new QuestionAnswerString() {
                    @Override
                    public boolean isValidInput(@NotNull String input) {
                        return input.length() > 0 && worldManager.getConfig().getGameWorlds().stream().noneMatch(world -> world.getName().equals(input));
                    }

                    @Override
                    public String getInvalidInputMessage(@NotNull String input) {
                        return "null";
                    }
                }
        ));

        addQuestion(new Question<>(
                "mapBuilder",
                "§aWie heißt der Erbauer der Welt?",
                new QuestionAnswerString() {
                    @Override
                    public boolean isValidInput(@NotNull String input) {
                        return input.length() > 0;
                    }

                    @Override
                    public String getInvalidInputMessage(@NotNull String input) {
                        return "null";
                    }
                }
        ));

        for (GameTeam team : teamRepository.getTeams()) {
            addQuestion(new Question<>(
                    "spawn" + team.getColor(),
                    MessageFormat.format("§aWo soll der Spawnpunkt von Team {0} sein? Schreibe gesetzt in den Chat", team.getName()),
                    new QuestionAnswerLocation() {
                        @Override
                        public boolean isValidInput(String input) {
                            return true;
                        }

                        @Override
                        public String getInvalidInputMessage(@NotNull String input) {
                            return "null";
                        }
                    }
            ));
        }

        addQuestion(new Question<>(
                "spectatorSpawn",
                "§aWo soll der Spawnpunkt füpr Spectator sein? Schreibe gesetzt in den Chat",
                new QuestionAnswerLocation() {
                    @Override
                    public boolean isValidInput(String input) {
                        return true;
                    }

                    @Override
                    public String getInvalidInputMessage(@NotNull String input) {
                        return "null";
                    }
                }
        ));
    }

    @Override
    public void finish() {
        if(hasResult("mapName")) {
            LocalWorld world = new LocalWorld(getResult("mapFolder", String.class));
            WorldWrapper worldWrapper = new LocalWorldWrapper(world, skyWars, false);

            if(hasResult("mapFolder")) {
                world.setName(getResult("mapName", String.class));
            }

            if(hasResult("mapBuilder")) {
                world.setBuilder(getResult("mapBuilder", String.class));
            }

            for (GameTeam team : teamRepository.getTeams()) {
                if(hasResult("spawn" + team.getColor())) {
                    world.addIsland(new Island(team.getColor(), new SerializableLocation(getResult("spawn" + team.getColor(), Location.class))));
                }
            }

            if(hasResult("spectatorSpawn")) {
                world.setSpectatorSpawn(getResult("spectatorSpawn", Location.class));
            }

            worldWrapper.store();

            worldManager.getConfig().getGameWorlds().add(world);
            worldManager.getConfigLoader().saveConfig(worldManager.getConfig());
            player.sendMessage("SUCCES");
        }
    }

    @Override
    public void cancel() {

    }
}
