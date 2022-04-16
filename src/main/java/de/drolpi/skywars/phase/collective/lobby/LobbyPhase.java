package de.drolpi.skywars.phase.collective.lobby;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.command.ForceStartCommand;
import de.drolpi.skywars.counter.config.lobby.LobbyCounterInformation;
import de.drolpi.skywars.game.GameManager;
import de.drolpi.skywars.kit.KitManager;
import de.drolpi.skywars.listener.protection.DamageProtectionListener;
import de.drolpi.skywars.listener.protection.ProtectionListener;
import de.drolpi.skywars.localization.MessageProvider;
import de.drolpi.skywars.phase.GamePhase;
import de.drolpi.skywars.phase.collective.starting.StartingPhase;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.world.WorldManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LobbyPhase extends GamePhase {

    private final SkyWars skyWars;
    private final Server server;
    private final LobbyCountdown countdown;
    private final MessageProvider messageProvider;
    private final WorldManager worldManager;
    private final TeamRepository teamRepository;
    private final GameManager gameManager;
    private final KitManager kitManager;

    public LobbyPhase(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.server = skyWars.getPlugin().getServer();
        this.messageProvider = skyWars.getMessageProvider();
        this.worldManager = skyWars.getWorldManager();
        this.teamRepository = skyWars.getTeamRepository();
        this.gameManager = skyWars.getGameManager();
        this.kitManager = skyWars.getKitManager();

        this.countdown = new LobbyCountdown(skyWars, skyWars.getCounterConfig().getLobbyCounterInformation());
    }

    @Override
    public void enable() {
        server.getPluginCommand("forcestart").setExecutor(new ForceStartCommand(skyWars, countdown));
    }

    @Override
    public void disable() {
        countdown.stop();
        teamRepository.fillTeams(server.getOnlinePlayers());
        kitManager.selectKits();
        for (Player player : server.getOnlinePlayers()) {
            player.getInventory().clear();
        }
    }

    @Override
    public void playerJoin(Player player) {
        countdown.handleJoin(server.getOnlinePlayers());
        for (Player onlinePlayer : server.getOnlinePlayers()) {
            onlinePlayer.sendMessage(
                    messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                            + messageProvider.getString(Locale.GERMAN, "skywars.lobby.player_join", player.getName())
            );
        }

        gameManager.showScoreboard(player);
        gameManager.updateScoreboard();

        player.teleport(worldManager.getLobbyWorld().getSpawnLocation());

        if(countdown.isRunning()) {
            if(countdown.isLevelCount()) {
                player.setLevel(countdown.getCurrentTime());
            }
            if(countdown.isLevelProgress()) {
                player.setExp(countdown.getCurrentTime() / (countdown.getStartTime() + 0.0F));
            }
        }else {
            if(countdown.isLevelCount()) {
                player.setLevel(countdown.getStartTime());
            }
            if(countdown.isLevelProgress()) {
                player.setExp(1);
            }
        }
    }

    @Override
    public void playerQuit(Player player) {
        countdown.handleQuit(server.getOnlinePlayers());
        for (Player onlinePlayer : server.getOnlinePlayers()) {
            onlinePlayer.sendMessage(
                    messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                            + messageProvider.getString(Locale.GERMAN, "skywars.lobby.player_quit", player.getName())
            );
        }
        skyWars.getPlugin().getServer().getScheduler().runTaskLater(skyWars.getPlugin(), gameManager::updateScoreboard, 5);
    }

    @Override
    public GamePhase next() {
        return new StartingPhase(skyWars);
    }

    @Override
    public List<Listener> provideListener() {
        return Arrays.asList(new ProtectionListener(), new DamageProtectionListener());
    }
}
