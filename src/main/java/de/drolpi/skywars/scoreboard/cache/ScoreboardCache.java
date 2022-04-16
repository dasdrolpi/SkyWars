package de.drolpi.skywars.scoreboard.cache;

import de.drolpi.skywars.scoreboard.ScoreboardImpl;
import lombok.Data;
import org.bukkit.entity.Player;

/**
 * @author drolpi / Lars Nippert
 * @project Natrox-API
 * @date Date: 06.08.2021
 * @time Time: 19:08
 */

@Data
public class ScoreboardCache {

    private final String scoreboardName;
    private final Player player;
    private final ScoreboardImpl scoreboard;

}