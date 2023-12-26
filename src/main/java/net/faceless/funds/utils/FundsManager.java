package net.faceless.funds.utils;

import net.faceless.funds.Funds;
import net.faceless.funds.database.FundsDatabase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FundsManager {

    private static final Map<UUID, BigDecimal> fundsMap = new HashMap<>();
    private static final FundsDatabase database = Funds.getDatabase();

    public static void loadFunds (Player player) {
        UUID uuid = player.getUniqueId();

        if(database.playerExists(player)) {
            BigDecimal balance = database.getBalance(uuid);
            fundsMap.put(uuid, balance);
        }else {
            database.set(uuid, BigDecimal.ZERO);
            fundsMap.put(uuid, BigDecimal.ZERO);
        }

    }

    public static void saveFunds (Player player) {
        UUID pUUID = player.getUniqueId();
        BigDecimal funds = fundsMap.get(pUUID);

        if(fundsMap.containsKey(pUUID)) {
            database.set(pUUID, funds);
            fundsMap.remove(pUUID);
        }

    }

    public static void addFunds (Player player, BigDecimal val) {
        UUID pUUID = player.getUniqueId();

        if(fundsMap.containsKey(pUUID)) {
            BigDecimal bal = fundsMap.get(pUUID);
            BigDecimal r = val.add(bal);
            fundsMap.put(pUUID, r);

        }else {
            loadFunds(player);
            fundsMap.put(pUUID, BigDecimal.ZERO);
        }
    }

    public static BigDecimal getFunds (Player player) {
        UUID pUUID = player.getUniqueId();
        if(fundsMap.containsKey(pUUID)) {
            return fundsMap.get(pUUID);
        }
        loadFunds(player);
        return BigDecimal.ZERO;
    }

    public static void saveOnQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        new BukkitRunnable(){
            @Override
            public void run() {
                FundsManager.saveFunds(player);

            }
        }.runTaskLater(Funds.getPlugin(), 6000);
    }

    public static void loadOnJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        loadFunds(player);
    }

    public static void saveOnServerClose() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            saveFunds(player);
        }
        for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            saveFunds((Player) player);
        }

    }

    public static  boolean isLoaded(Player player) {
        return fundsMap.containsKey(player.getUniqueId());
    }

    public static void resetFunds(Player player) {
        if(fundsMap.containsKey(player.getUniqueId())) {
            fundsMap.put(player.getUniqueId(), BigDecimal.ZERO);
        }
    }
}
