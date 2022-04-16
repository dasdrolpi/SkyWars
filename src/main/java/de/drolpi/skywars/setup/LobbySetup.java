package de.drolpi.skywars.setup;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.location.SerializableLocation;
import de.drolpi.skywars.runnable.CatchingRunnable;
import de.drolpi.skywars.setup.answer.QuestionAnswerLocation;
import de.drolpi.skywars.setup.answer.QuestionAnswerString;
import de.drolpi.skywars.world.LobbyWorld;
import de.drolpi.skywars.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LobbySetup extends Setup{

    private final SkyWars skyWars;
    private final WorldManager worldManager;

    public LobbySetup(Player player, SkyWars skyWars) {
        super(player, skyWars.getSetupManager());

        this.skyWars = skyWars;
        this.worldManager = skyWars.getWorldManager();
    }

    @Override
    public void configure() {
        addQuestion(new Question<>(
                "worldFolder",
                "Wie heißt der Order der Welt, die geladen werden muss?",
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

        addCompletionListener("worldFolder", object -> {
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
                "spawn",
                "Wo sollen Spieler während einer Lobby-Phase spawnen? Schreibe gesetzt in den Chat",
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
        if(hasResult("worldFolder")) {
            LobbyWorld lobbyWorld = new LobbyWorld(getResult("worldFolder", String.class));

            if(hasResult("spawn")) {
                lobbyWorld.setSpawnLocation(new SerializableLocation(getResult("spawn", Location.class)));
            }

            worldManager.getConfig().setLobbyWorld(lobbyWorld);
            worldManager.getConfigLoader().saveConfig(worldManager.getConfig());
            player.sendMessage("SUCCES");
        }
    }

    @Override
    public void cancel() {

    }
}
