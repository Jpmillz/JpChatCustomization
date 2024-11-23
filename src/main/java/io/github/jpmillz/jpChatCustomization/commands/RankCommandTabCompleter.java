package io.github.jpmillz.jpChatCustomization.commands;

import io.github.jpmillz.jpChatCustomization.util.RankUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RankCommandTabCompleter implements TabCompleter {

    //Add list of ranks on assign command

    private static final List<String> COMMANDS = List.of("ranks", "create", "assign", "delete", "unassign");

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        final List<String> completions = new ArrayList<>();
        String initialCommand = strings[0];
        if (strings.length == 1){
            StringUtil.copyPartialMatches(strings[0], COMMANDS, completions);
        }else if (strings.length == 2) {
            completions.clear();
            switch (initialCommand) {
                case "assign":
                    ArrayList<String> namesOfPlayer = new ArrayList<>();
                    Arrays.stream(Bukkit.getOfflinePlayers()).map(offlinePlayer -> offlinePlayer.getName()).forEach(p -> namesOfPlayer.add(p));
                    Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).forEach(p -> namesOfPlayer.add(p));
                    StringUtil.copyPartialMatches(strings[1], namesOfPlayer, completions);
                    break;
                case "delete":
                    ArrayList<String> ranks = RankUtil.getRanks().stream().map(rank -> rank.name()).collect(Collectors.toCollection(ArrayList::new));
                    StringUtil.copyPartialMatches(strings[1], ranks, completions);
                    break;
                case "unassign":
                    ArrayList<String> rankedPlayers = new ArrayList<>();
                    Arrays.stream(Bukkit.getOfflinePlayers()).filter(player -> !(RankUtil.getPlayerRank(player.getUniqueId().toString()) == null)).forEach(player -> rankedPlayers.add(player.getName()));
                    Bukkit.getOnlinePlayers().stream().filter(player -> !(RankUtil.getPlayerRank(player.getUniqueId().toString()) == null)).forEach(player -> rankedPlayers.add(player.getName()));
                    StringUtil.copyPartialMatches(strings[1], rankedPlayers, completions);
                    break;
            }
        }
        return completions;
    }
}
