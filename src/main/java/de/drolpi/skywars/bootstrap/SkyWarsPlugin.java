package de.drolpi.skywars.bootstrap;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.bootstrap.task.LifeCycle;
import de.drolpi.skywars.bootstrap.task.TaskManager;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyWarsPlugin extends JavaPlugin {

    private TaskManager taskManager;

    @Override
    public void onLoad() {
        this.taskManager = new TaskManager(new SkyWars(this));
        this.taskManager.loadMethods();
        this.taskManager.fireTasks(LifeCycle.LOADED);
    }

    @Override
    public void onEnable() {
        taskManager.fireTasks(LifeCycle.STARTED);
    }

    @Override
    public void onDisable() {
        taskManager.fireTasks(LifeCycle.STOPPED);

        for (Player player : getServer().getOnlinePlayers()) {
            player.kickPlayer("CLOSE");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
