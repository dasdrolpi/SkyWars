package de.drolpi.skywars;

import de.drolpi.skywars.bootstrap.SkyWarsPlugin;
import de.drolpi.skywars.bootstrap.task.LifeCycle;
import de.drolpi.skywars.bootstrap.task.Task;
import de.drolpi.skywars.chest.ChestManager;
import de.drolpi.skywars.command.KitSelectCommand;
import de.drolpi.skywars.command.SetupCommand;
import de.drolpi.skywars.command.TeleportCommand;
import de.drolpi.skywars.config.SkyWarsConfig;
import de.drolpi.skywars.config.SkyWarsConfigLoader;
import de.drolpi.skywars.counter.config.CounterConfig;
import de.drolpi.skywars.counter.config.CounterConfigLoader;
import de.drolpi.skywars.kills.KillerManager;
import de.drolpi.skywars.kit.KitManager;
import de.drolpi.skywars.listener.PingListener;
import de.drolpi.skywars.listener.PlayerDeathListener;
import de.drolpi.skywars.listener.PlayerJoinListener;
import de.drolpi.skywars.listener.PlayerQuitListener;
import de.drolpi.skywars.listener.protection.DefaultProtectionListener;
import de.drolpi.skywars.listener.protection.SpectatorProtectionListener;
import de.drolpi.skywars.localization.MessageProvider;
import de.drolpi.skywars.localization.bundle.JsonResourceLoader;
import de.drolpi.skywars.mapvote.MapManager;
import de.drolpi.skywars.phase.PhasenManager;
import de.drolpi.skywars.phase.collective.lobby.LobbyPhase;
import de.drolpi.skywars.setup.SetupManager;
import de.drolpi.skywars.team.TeamRepository;
import de.drolpi.skywars.game.GameManager;
import de.drolpi.skywars.util.FileUtil;
import de.drolpi.skywars.world.WorldManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

@Getter
public class SkyWars {

    private static final List<Locale> LOCALES = Arrays.asList(Locale.ENGLISH, Locale.GERMAN, Locale.FRENCH, Locale.ITALY, Locale.CHINESE);

    private final SkyWarsPlugin plugin;
    private final PhasenManager phasenManager;
    private final WorldManager worldManager;
    private final TeamRepository teamRepository;
    private final GameManager gameManager;
    private final ChestManager chestManager;
    private final KitManager kitManager;
    private final KillerManager killerManager;
    private final SetupManager setupManager;
    private final MapManager mapManager;
    private final PlayerDeathListener deathListener;
    private final SpectatorProtectionListener spectatorListener;
    private final CounterConfig counterConfig;
    private final SkyWarsConfig skyWarsConfig;
    private MessageProvider messageProvider;

    private boolean enabled;

    public SkyWars(SkyWarsPlugin plugin) {
        this.plugin = plugin;

        this.loadLanguages();

        this.phasenManager = new PhasenManager(this);
        this.teamRepository = new TeamRepository(this);
        this.worldManager = new WorldManager(this);
        this.mapManager = new MapManager(this);
        this.kitManager = new KitManager(this);
        this.gameManager = new GameManager(this);
        this.chestManager = new ChestManager(this);
        this.killerManager = new KillerManager(this);
        this.deathListener = new PlayerDeathListener(this);
        this.spectatorListener = new SpectatorProtectionListener(this);
        this.setupManager = new SetupManager(this);

        CounterConfigLoader counterConfigLoader = new CounterConfigLoader();
        this.counterConfig = counterConfigLoader.loadOrCreateFile();

        SkyWarsConfigLoader skyWarsConfigLoader = new SkyWarsConfigLoader();
        this.skyWarsConfig = skyWarsConfigLoader.loadOrCreateFile();

        this.enabled = true;
    }

    public void loadLanguages() {
        MessageProvider.Builder builder = MessageProvider.builder();

        for (Locale locale : LOCALES) {
            builder.addBundle(ResourceBundle.getBundle("skywars", locale, new JsonResourceLoader()));
        }

        this.messageProvider = builder.build();
    }

    @Task(event = LifeCycle.STARTED, order = 1)
    public void loadTeams() {
        teamRepository.loadTeams();
        gameManager.createTeam();
        gameManager.loadScoreboard();
    }

    @Task(event = LifeCycle.STARTED, order = 2)
    public void loadWorlds() {
        for (World world : Bukkit.getWorlds()) {
            if(!world.getName().contains("world")) {
                Bukkit.unloadWorld(world, false);
            }
        }

        checkEnabled(worldManager.loadWorlds());
    }

    @Task(event = LifeCycle.STARTED, order = 3)
    public void loadKits() {
        kitManager.loadKits();
    }

    @Task(event = LifeCycle.STARTED, order = 4)
    public void loadKillerManager() {
        killerManager.load();
    }

    @Task(event = LifeCycle.STARTED, order = 5)
    public void loadSetupManager() {
        setupManager.load();
    }

    @Task(event = LifeCycle.STARTED, order = 10)
    public void registerListener() {
        PluginManager pluginManager = getPlugin().getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoinListener(this), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(this), plugin);
        pluginManager.registerEvents(new PingListener(this), plugin);
        pluginManager.registerEvents(new DefaultProtectionListener(), plugin);

        getPlugin().getCommand("setup").setExecutor(new SetupCommand(this));
        getPlugin().getCommand("wtp").setExecutor(new TeleportCommand(this));
        getPlugin().getCommand("select").setExecutor(new KitSelectCommand(this));
    }

    @Task(event = LifeCycle.STARTED, order = 100)
    public void enable() {
        if(enabled) {
            phasenManager.start(new LobbyPhase(this));
        }

        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        List<String> lines = Arrays.asList(
                "§8---------------[§aSkyWars§8]---------------",
                "§7",
                "§aSkyWars§8: §7Version §8-> §e" + plugin.getDescription().getVersion(),
                "§aSkyWars§8: §7Developer §8-> §e" + plugin.getDescription().getAuthors().get(0),
                "§aSkyWars§8: §7Gamesize §8-> §e" +  teamRepository.getTeams().size() + "x" + teamRepository.getTeams().stream().mapToInt(value -> value.getPlayers().max()).findAny().getAsInt(),
                "§7",
                "§8---------------[§aSkyWars§8]---------------"
        );

        for (String line : lines) {
            sender.sendMessage(line);
        }
    }

    @Task(event = LifeCycle.STOPPED, order = 1)
    public void disable() {
        phasenManager.setPhase(null);
        worldManager.unloadWorlds();
    }

    private void checkEnabled(boolean enabled) {
        if(this.enabled) {
            this.enabled = enabled;
        }
    }

}
