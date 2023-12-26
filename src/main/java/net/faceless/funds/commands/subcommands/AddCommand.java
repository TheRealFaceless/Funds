package net.faceless.funds.commands.subcommands;

import net.faceless.funds.commands.SubCommand;
import net.faceless.funds.utils.ChatUtil;
import net.faceless.funds.utils.FundsManager;
import net.faceless.funds.utils.TabCompleteUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class AddCommand extends SubCommand {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Add balance";
    }

    @Override
    public String getSyntax() {
        return "/balance add <amount> <name>";
    }

    @Override
    public void execute(Player player, String[] args) {
        switch (args.length) {
            case (1) :
            ChatUtil.sendMessage(player, "&7Please provide an amount.");
            break;

            case (2) :
                try {
                    int v = Integer.parseInt(args[1]);
                    BigDecimal val = BigDecimal.valueOf(v);
                    FundsManager.addFunds(player, val);
                    ChatUtil.sendMessage(player, "&aAmount Deposited");
                }catch (NumberFormatException e) {
                    ChatUtil.sendMessage(player, "&cInvalid Value");
                }
                break;

            case (3) :
                try {
                    int v = Integer.parseInt(args[1]);
                    BigDecimal val = BigDecimal.valueOf(v);
                    String targetPlayerName = args[2];
                    Player targetPlayer = Bukkit.getPlayerExact(targetPlayerName);

                    if(targetPlayer != null) {
                        FundsManager.addFunds(targetPlayer, val);
                        ChatUtil.sendMessage(player, "&aAmount Deposited");
                    }else {
                        ChatUtil.sendMessage(player, "&cPlayer does not Exist");
                    }
                }catch (NumberFormatException e) {
                    ChatUtil.sendMessage(player, "&cInvalid Value");
                }
                break;
        }

    }

    @Override
    public List<String> onTabComplete(@NotNull String[] strings) {
        return TabCompleteUtil.printOnlinePlayersToTab(strings, 3);
    }
}
