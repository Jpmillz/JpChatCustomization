package io.github.jpmillz.jpChatCustomization.commands;


import io.github.jpmillz.jpChatCustomization.ranks.Rank;
import io.github.jpmillz.jpChatCustomization.util.RankUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0 || strings[0].equalsIgnoreCase("help")){
            commandSender.sendMessage("help executed");
            return true;
        }
        if (strings[0].equalsIgnoreCase("ranks")){
            StringBuilder builder = new StringBuilder();
            RankUtil.getRanks().stream().map(Rank::name).forEach(v-> builder.append(v + ", "));
            commandSender.sendMessage(RankUtil.getRanks().toString());
        }
        return false;
    }
}
