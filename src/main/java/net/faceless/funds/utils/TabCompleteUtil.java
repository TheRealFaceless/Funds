package net.faceless.funds.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TabCompleteUtil {

    public static ArrayList<String> printOnlinePlayersToTab (String[] args, int index) {
        ArrayList<String> completions = new ArrayList<>();
        if (args.length == index) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        }
        return completions;
    }

    public static ArrayList<String> printOnlinePlayersToTab () {
        ArrayList<String> completions = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }

        return completions;
    }
}
