package net.faceless.funds;

import net.faceless.funds.commands.CommandManager;
import net.faceless.funds.database.FundsDatabase;
import net.faceless.funds.listeners.FundLoadListener;
import net.faceless.funds.listeners.FundSaveListener;
import net.faceless.funds.utils.FundsManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Funds extends JavaPlugin {

    private static FundsDatabase database;
    private static Funds plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        database = new FundsDatabase(this);
        plugin = this;
        // Listeners
        getServer().getPluginManager().registerEvents(new FundLoadListener(), plugin);
        getServer().getPluginManager().registerEvents(new FundSaveListener(), plugin);
        // Commands
        getCommand("balance").setExecutor(new CommandManager());




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        database.closeConnection();
        FundsManager.saveOnServerClose();
    }

    public static FundsDatabase getDatabase() {
        return database;
    }

    public static Funds getPlugin() {
        return plugin;
    }
}
