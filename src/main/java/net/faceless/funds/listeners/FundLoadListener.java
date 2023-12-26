package net.faceless.funds.listeners;


import net.faceless.funds.utils.FundsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class FundLoadListener implements Listener {

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent e) {
        FundsManager.loadOnJoin(e);
    }
}
