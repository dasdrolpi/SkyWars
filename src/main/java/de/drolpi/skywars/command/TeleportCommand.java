package de.drolpi.skywars.command;

import de.drolpi.skywars.SkyWars;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    private final Server server;

    public TeleportCommand(SkyWars skyWars) {
        this.server = skyWars.getPlugin().getServer();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if(args.length != 4) return false;

        String worldName = args[0];
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);

        World world = server.getWorld(worldName);

        if(world == null) {
            WorldCreator creator = new WorldCreator(worldName);
            world = creator.createWorld();
        }

        player.teleport(new Location(world, x, y, z));

        return true;
    }
}
