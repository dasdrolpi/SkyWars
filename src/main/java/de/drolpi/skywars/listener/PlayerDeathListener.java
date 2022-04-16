package de.drolpi.skywars.listener;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.chat.ChatUtil;
import de.drolpi.skywars.kills.KillerManager;
import de.drolpi.skywars.kit.KitManager;
import de.drolpi.skywars.localization.MessageProvider;
import de.drolpi.skywars.mapvote.MapManager;
import de.drolpi.skywars.runnable.CatchingRunnable;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.game.GameManager;
import de.drolpi.skywars.world.WorldManager;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class PlayerDeathListener implements Listener {

    private final JavaPlugin plugin;
    private final Server server;
    private final TeamRepository teamRepository;
    private final GameManager gameManager;
    private final MessageProvider messageProvider;
    private final MapManager mapManager;
    private final KillerManager killerManager;
    private final KitManager kitManager;

    public PlayerDeathListener(SkyWars skyWars) {
        this.plugin = skyWars.getPlugin();
        this.server = skyWars.getPlugin().getServer();
        this.teamRepository = skyWars.getTeamRepository();
        this.gameManager = skyWars.getGameManager();
        this.messageProvider = skyWars.getMessageProvider();
        this.mapManager = skyWars.getMapManager();
        this.killerManager = skyWars.getKillerManager();
        this.kitManager = skyWars.getKitManager();
    }

    @EventHandler
    public void handle(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setKeepInventory(true);
        event.setDeathMessage(null);

        if(!teamRepository.isInTeam(player)) {
            player.spigot().respawn();
            player.teleport(mapManager.getCurrentWorld().getSpectatorSpawn().toLocation(mapManager.getCurrentWorldWrapper().getBukkitWorld()));
            return;
        }

        for (final ItemStack item : player.getInventory()) {
            if (item == null) {
                continue;
            }
            player.getLocation().getWorld().dropItem(player.getLocation(), item);
        }

        player.getLocation().getWorld().dropItem(player.getLocation(), player.getInventory().getHelmet());
        player.getLocation().getWorld().dropItem(player.getLocation(), player.getInventory().getChestplate());
        player.getLocation().getWorld().dropItem(player.getLocation(), player.getInventory().getLeggings());
        player.getLocation().getWorld().dropItem(player.getLocation(), player.getInventory().getBoots());

        plugin.getServer().getScheduler().runTaskLater(plugin, new CatchingRunnable(() -> {
            player.spigot().respawn();

            gameManager.removePlayer(player, GameManager.RemoveReason.DEATH);
            gameManager.updateScoreboard();
        }), 5);

        sendMessages(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void handle(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(null);

        if (!teamRepository.isInTeam(player)) {
            gameManager.updateScoreboard();
            return;
        }

        for (final ItemStack item : player.getInventory()) {
            if (item == null) {
                continue;
            }
            player.getLocation().getWorld().dropItem(player.getLocation(), item);
        }

        player.getLocation().getWorld().dropItem(player.getLocation(), player.getInventory().getHelmet());
        player.getLocation().getWorld().dropItem(player.getLocation(), player.getInventory().getChestplate());
        player.getLocation().getWorld().dropItem(player.getLocation(), player.getInventory().getLeggings());
        player.getLocation().getWorld().dropItem(player.getLocation(), player.getInventory().getBoots());

        plugin.getServer().getScheduler().runTaskLater(plugin, new CatchingRunnable(() -> {
            gameManager.removePlayer(player, GameManager.RemoveReason.QUIT);
            gameManager.updateScoreboard();
        }), 5);

        sendMessages(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handle(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        gameManager.addSpectator(player);
        gameManager.updateScoreboard();
    }

    private void sendMessages(Player player) {
        Optional<Player> optional = getKiller(player);

        if(optional.isPresent()) {
            Player killer = optional.get();
            player.sendMessage(
                    messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                            + messageProvider.getString(Locale.GERMAN, "skywars.ingame.death_by_player", killer.getName())
            );
            player.sendMessage(
                    messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                            + messageProvider.getString(Locale.GERMAN, "skywars.ingame.death_by_player_health", killer.getName(), ChatUtil.getHealth(killer))
            );
            player.sendMessage(
                    messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                            + messageProvider.getString(Locale.GERMAN, "skywars.ingame.death_by_player_kit", killer.getName(), kitManager.getKitByPlayer(player).getName())
            );

            killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 1, 1);
            gameManager.addKill(killer);
            gameManager.updateScoreboard(killer);

            for (Player onlinePlayer : server.getOnlinePlayers()) {
                onlinePlayer.sendMessage(
                        messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                                + messageProvider.getString(Locale.GERMAN, "skywars.ingame.death_by_player_brodcast", player.getName(), killer.getName())
                );
            }
        }else {
            player.sendMessage(
                    messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                            + messageProvider.getString(Locale.GERMAN, "skywars.ingame.death")
            );

            for (Player onlinePlayer : server.getOnlinePlayers()) {
                onlinePlayer.sendMessage(
                        messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                                + messageProvider.getString(Locale.GERMAN, "skywars.ingame.death_brodcast", player.getName())
                );
            }
        }
    }

    private Optional<Player> getKiller(Player player) {
        return killerManager.getHitInformation(player).getLastDamager(10, TimeUnit.SECONDS);
    }

}
