package net.faceless.funds.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SubCommand{

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void execute(Player player, String[] args);

    public abstract List<String> onTabComplete(@NotNull String[] strings);

}

