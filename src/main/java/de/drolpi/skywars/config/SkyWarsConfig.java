package de.drolpi.skywars.config;

import lombok.Data;
import org.bukkit.Server;

@Data
public class SkyWarsConfig {

    private final RestartMethod restartMethod;

    public SkyWarsConfig() {
        this.restartMethod = RestartMethod.RELOAD;
    }

    public enum RestartMethod {

        RELOAD,
        RESTART;

        public void run(Server server) {
            if (this.equals(RestartMethod.RELOAD)) {
                server.reload();
            } else {
                server.shutdown();
            }
        }

    }

}
