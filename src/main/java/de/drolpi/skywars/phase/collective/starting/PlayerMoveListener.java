package de.drolpi.skywars.phase.collective.starting;

import de.drolpi.skywars.SkyWars;
import de.drolpi.skywars.team.TeamRepository;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final TeamRepository teamRepository;

    public PlayerMoveListener(SkyWars skyWars) {
        this.teamRepository = skyWars.getTeamRepository();
    }

    @EventHandler
    public void handle(PlayerMoveEvent event) {
        if(teamRepository.isInTeam(event.getPlayer())) {
            Location from = event.getFrom();
            Location to = new Location(from.getWorld(), from.getX(), from.getY(), from.getZ(), event.getTo().getYaw(), event.getTo().getPitch());

            event.setTo(to);
        }
    }

}
