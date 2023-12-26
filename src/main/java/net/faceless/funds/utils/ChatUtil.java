package net.faceless.funds.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatUtil {

    public static void sendMessage (@NotNull Player player, String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        String format = ChatColor.translateAlternateColorCodes('&', "&7[&6Funds&7] ");
        String formattedMessage = format + message;
        player.sendMessage(formattedMessage);

    }


}
