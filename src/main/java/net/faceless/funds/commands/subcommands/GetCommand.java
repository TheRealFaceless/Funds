package net.faceless.funds.commands.subcommands;

import net.faceless.funds.commands.SubCommand;
import net.faceless.funds.utils.ChatUtils;
import net.faceless.funds.utils.FundsManager;
import net.faceless.funds.utils.TabCompleteUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GetCommand extends SubCommand {
    @Override
    public String getName() {
        return "get";
    }

    @Override
    public String getDescription() {
        return "Check your balance";
    }

    @Override
    public String getSyntax() {
        return "/balance get <name>";
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length == 1) {
            String bal = FundsManager.getFunds(player).toString();
            ChatUtils.sendMessage(player, bal);
        } else if (args.length == 2) {
            String targetPlayerName = args[1];
            Player targetPlayer = Bukkit.getPlayerExact(targetPlayerName);

            if(targetPlayer != null) {
                String bal = FundsManager.getFunds(targetPlayer).toString();
                ChatUtils.sendMessage(player, bal);
            }else {
                ChatUtils.sendMessage(player, "&cPlayer does not Exist");
            }
        }else {
            ChatUtils.sendMessage(player, "&cInvalid Argument!");
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull String[] strings) {
        return TabCompleteUtil.printOnlinePlayersToTab(strings, 2);

    }
}
