package de.drolpi.skywars.phase.collective.starting;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.chest.ChestManager;
import de.drolpi.skywars.game.GameManager;
import de.drolpi.skywars.kills.KillerManager;
import de.drolpi.skywars.kit.KitManager;
import de.drolpi.skywars.listener.protection.DamageProtectionListener;
import de.drolpi.skywars.listener.protection.ProtectionListener;
import de.drolpi.skywars.mapvote.MapManager;
import de.drolpi.skywars.phase.GamePhase;
import de.drolpi.skywars.phase.collective.protection.ProtectionPhase;
import de.drolpi.skywars.runnable.CatchingRunnable;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class StartingPhase extends GamePhase {

    private final SkyWars skyWars;
    private final Server server;
    private final StartingCountdown countdown;
    private final TeamRepository teamRepository;
    private final MapManager mapManager;
    private final ChestManager chestManager;
    private final KitManager kitManager;
    private final KillerManager killerManager;
    private final GameManager gameManager;

    public StartingPhase(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.server = skyWars.getPlugin().getServer();
        this.teamRepository = skyWars.getTeamRepository();
        this.mapManager = skyWars.getMapManager();
        this.countdown = new StartingCountdown(skyWars, skyWars.getCounterConfig().getStartingCounterInformation());
        this.chestManager = skyWars.getChestManager();
        this.kitManager = skyWars.getKitManager();
        this.killerManager = skyWars.getKillerManager();
        this.gameManager = skyWars.getGameManager();
    }

    @Override
    public void enable() {
        countdown.start();
        kitManager.giveKits();
        killerManager.createPlayers();
        gameManager.updateScoreboard();
        mapManager.teleportTeams(teamRepository.getTeams());

        chestManager.loadChests();
    }

    @Override
    public void disable() {
        countdown.stop();
        Bukkit.getScheduler().runTask(skyWars.getPlugin(), new CatchingRunnable(() -> {
            for (Player player : server.getOnlinePlayers()) {
                player.setWalkSpeed(0.2f);
            }
        }));
    }

    @Override
    public void playerJoin(Player player) {

    }

    @Override
    public void playerQuit(Player player) {

    }

    @Override
    public GamePhase next() {
        return new ProtectionPhase(skyWars);
    }

    @Override
    public List<Listener> provideListener() {
        return Arrays.asList(new ProtectionListener(), new DamageProtectionListener(), new PlayerMoveListener(skyWars), skyWars.getDeathListener(), skyWars.getSpectatorListener());
    }
}
