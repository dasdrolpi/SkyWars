package de.drolpi.skywars.command;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.game.GameManager;
import de.drolpi.skywars.kit.KitManager;
import de.drolpi.skywars.scoreboard.ScoreboardManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitSelectCommand implements CommandExecutor {

    private final KitManager kitManager;
    private final GameManager gameManager;

    public KitSelectCommand(SkyWars skyWars) {
        this.kitManager = skyWars.getKitManager();
        this.gameManager = skyWars.getGameManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if(args.length != 1) {
            player.sendMessage("ERROR");
            return false;
        }

        String kitName = args[0];

        if(kitManager.getKitByName(kitName) == null) {
            player.sendMessage("ERROR");
            return false;
        }

        kitManager.selectKit(player, kitName);
        gameManager.updateScoreboard(player);
        player.sendMessage("SUCCES");
        return true;
    }
}
