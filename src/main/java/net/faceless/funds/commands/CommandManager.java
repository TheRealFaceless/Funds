package net.faceless.funds.commands;

import net.faceless.funds.commands.subcommands.GetCommand;
import net.faceless.funds.commands.subcommands.ResetCommand;
import net.faceless.funds.commands.subcommands.AddCommand;
import net.faceless.funds.utils.TabCompleteUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(){
        subcommands.add(new AddCommand());
        subcommands.add(new ResetCommand());
        subcommands.add(new GetCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length > 0){
                for (int i = 0; i < getSubcommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).execute(p, args);

                    }
                }
            }else {
                p.sendMessage(ChatColor.GRAY + "--------------------------------");
                for (int i = 0; i < getSubcommands().size(); i++){
                    p.sendMessage(getSubcommands().get(i).getSyntax() + " - " + getSubcommands().get(i).getDescription());
                }
                p.sendMessage(ChatColor.GRAY + "--------------------------------");
            }

        }
        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (SubCommand subCommand : subcommands) {
                completions.add(subCommand.getName());
            }
        } else if (args.length > 1) {
            String subCommandName = args[0];

            for (SubCommand subCommand : subcommands) {
                if (subCommand.getName().equalsIgnoreCase(subCommandName)) {
                    return subCommand.onTabComplete(args);
                }
            }
            return TabCompleteUtil.printOnlinePlayersToTab();
        }

        return completions;
    }

}
