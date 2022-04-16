package de.drolpi.skywars.command;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.counter.Countdown;
import de.drolpi.skywars.localization.MessageProvider;
import de.drolpi.skywars.phase.PhasenManager;
import de.drolpi.skywars.phase.collective.lobby.LobbyPhase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class ForceStartCommand implements CommandExecutor {

    private final PhasenManager phasenManager;
    private final MessageProvider messageProvider;
    private final Countdown counter;

    public ForceStartCommand(SkyWars skyWars, Countdown counter) {
        this.phasenManager = skyWars.getPhasenManager();
        this.messageProvider = skyWars.getMessageProvider();
        this.counter = counter;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if(!player.hasPermission("skywars.forcestart")) return false;

        if(!counter.isRunning()) {
            if(phasenManager.getCurrentPhase() instanceof LobbyPhase) {
                player.sendMessage(
                        messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                                + messageProvider.getString(Locale.GERMAN, "skywars.command.start_notenough")
                );
            }else {
                player.sendMessage(
                        messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                                + messageProvider.getString(Locale.GERMAN, "skywars.command.start_running"));
            }
            return false;
        }

        if(counter.getCurrentTime() <= 11) {
            player.sendMessage(
                    messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                        + messageProvider.getString(Locale.GERMAN, "skywars.command.start_already")
            );
            return false;
        }

        counter.setCurrentTime(11);
        player.sendMessage(
                messageProvider.getString(Locale.GERMAN, "skywars.prefix")
                    + messageProvider.getString(Locale.GERMAN, "skywars.command.start_succes")
        );

        return true;
    }
}
