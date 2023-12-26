package net.faceless.funds.commands.subcommands;

import net.faceless.funds.commands.SubCommand;
import net.faceless.funds.utils.ChatUtil;
import net.faceless.funds.utils.FundsManager;
import net.faceless.funds.utils.TabCompleteUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResetCommand extends SubCommand {
    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public String getDescription() {
        return "Resets Your Balance";
    }

    @Override
    public String getSyntax() {
        return "/balance reset <name>";
    }

    @Override
    public void execute(Player player, String[] args) {
        if(args.length == 1) {
            FundsManager.resetFunds(player);
            ChatUtil.sendMessage(player, "&7Balance Reset!");
        } else if (args.length == 2) {
            String targetPlayerName = args[1];
            Player targetPlayer = Bukkit.getPlayerExact(targetPlayerName);

            if(targetPlayer != null) {
                FundsManager.resetFunds(targetPlayer);
                ChatUtil.sendMessage(player, "&7Balance reset for " + targetPlayer.getName());
            }else{
                ChatUtil.sendMessage(player, "&cPlayer does not Exist");
            }
        }else {
            ChatUtil.sendMessage(player, "&cInvalid Argument!");
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull String[] strings) {
        return TabCompleteUtil.printOnlinePlayersToTab(strings, 2);

    }
}
