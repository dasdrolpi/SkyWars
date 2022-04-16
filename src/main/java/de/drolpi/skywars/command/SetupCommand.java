package de.drolpi.skywars.command;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.setup.KitSetup;
import de.drolpi.skywars.setup.LobbySetup;
import de.drolpi.skywars.setup.SetupManager;
import de.drolpi.skywars.setup.WorldSetup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {

    private final SkyWars skyWars;
    private final SetupManager setupManager;

    public SetupCommand(SkyWars skyWars) {
        this.skyWars = skyWars;
        this.setupManager = skyWars.getSetupManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if(!player.hasPermission("skywars.setup")) return false;

        if(args.length > 0) {

            String kind = args[0];

            if(kind.equalsIgnoreCase("lobby")) {
                setupManager.startSetup(player, new LobbySetup(player, skyWars));
                return true;
            }

            if(kind.equalsIgnoreCase("addmap")) {
                setupManager.startSetup(player, new WorldSetup(player, skyWars));
                return true;
            }

            if(kind.equalsIgnoreCase("addkit")) {
                setupManager.startSetup(player, new KitSetup(player, skyWars));
                return true;
            }
        }

        sendUsage(player);

        return true;
    }

    private void sendUsage(Player player) {
        player.sendMessage("ERROR");
    }
}
