package de.drolpi.skywars.kit;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.kit.config.KitConfig;
import de.drolpi.skywars.kit.config.KitConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class KitManager {

    private final KitConfigLoader kitConfigLoader;
    private final KitConfig config;
    private final List<Kit> kits;
    private final Map<Player, String> selectedKits;
    private String defaultKitName;

    public KitManager(SkyWars skyWars) {
        this.kitConfigLoader = new KitConfigLoader();
        this.kits = new ArrayList<>();
        this.selectedKits = new WeakHashMap<>();
        this.config = kitConfigLoader.loadOrCreateFile();
    }

    public void loadKits() {
        kits.addAll(config.getKits());
        defaultKitName = config.getDefaultKitName();
    }

    public Kit getKitByName(String name) {
        return kits.stream().filter(kit -> kit.getName().equals(name)).findFirst().orElse(null);
    }

    public Kit getKitByPlayer(Player player) {
        if(!selectedKits.containsKey(player)) {
            selectKit(player, defaultKitName);
        }
        
        return getKitByName(selectedKits.get(player));
    }

    public boolean hasKitSelected(Player player) {
        return selectedKits.containsKey(player);
    }

    public void selectKits() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(!hasKitSelected(player)) {
                selectKit(player, defaultKitName);
            }
        }
    }

    public void selectKit(Player player, Kit kit) {
        selectedKits.put(player, kit.getName());
    }

    public void selectKit(Player player, String kitName) {
        selectedKits.put(player, kitName);
    }

    public void giveKit(Player player) {
        Kit kit = getKitByPlayer(player);
        kit.giveKit(player);
    }

    public void giveKits() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            giveKit(onlinePlayer);
        }
    }

    public KitConfig getConfig() {
        return config;
    }

    public KitConfigLoader getConfigLoader() {
        return kitConfigLoader;
    }
}
