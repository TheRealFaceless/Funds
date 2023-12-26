package net.faceless.funds.listeners;

import net.faceless.funds.utils.FundsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class FundSaveListener implements Listener {

    @EventHandler
    public void onQuit (PlayerQuitEvent e) {
        FundsManager.saveOnQuit(e);


    }
}
