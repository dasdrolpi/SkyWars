package de.drolpi.skywars.chat;

import org.bukkit.entity.Player;

public class ChatUtil {

    private ChatUtil () {
        throw new UnsupportedOperationException();
    }

    public static String getHealth(Player player) {
        return "§7" + (int) Math.floor(player.getHealth()) + "§4❤";
    }

}
