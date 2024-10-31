package io.github.jpmillz.jpChatCustomization.commands;


import io.github.jpmillz.jpChatCustomization.ranks.Rank;
import io.github.jpmillz.jpChatCustomization.util.RankUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String initialCommand = strings[0];
        if (strings.length == 0 || initialCommand.equalsIgnoreCase("help")){
            commandSender.sendMessage("help executed");
            return true;
        }
        if (initialCommand.equalsIgnoreCase("ranks")){
            StringBuilder builder = new StringBuilder();
            RankUtil.getRanks().stream().map(Rank::name).forEach(v-> builder.append(v + ", "));
            commandSender.sendMessage(RankUtil.getRanks().toString());
            return true;
        }
        if (initialCommand.equalsIgnoreCase("create")){
            createRankCommand(commandSender, strings);
            return true;
        }
        return false;
    }

    public void createRankCommand(CommandSender sender, String[] args){
        String nameOfRank = args[1];
        String identifier = args[2];
        boolean createRankBool = RankUtil.createRank(nameOfRank, identifier);
        if (createRankBool){
        sender.sendMessage(nameOfRank + " Has Been Created");
        } else{
            sender.sendMessage(nameOfRank + " Already exists!");
        }
    }
}
