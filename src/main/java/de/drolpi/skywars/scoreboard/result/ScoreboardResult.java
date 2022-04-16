package de.drolpi.skywars.scoreboard.result;

import de.drolpi.skywars.scoreboard.CustomScoreboard;
import lombok.Data;
import org.bukkit.entity.Player;

/**
 * @author drolpi / Lars Nippert
 * @project Natrox-API
 * @date Date: 04.08.2021
 * @time Time: 19:09
 */

@Data
public class ScoreboardResult {

    private final CustomScoreboard scoreboard;
    private final Player player;

}