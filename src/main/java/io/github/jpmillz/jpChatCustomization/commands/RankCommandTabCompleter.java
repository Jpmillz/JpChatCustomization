package io.github.jpmillz.jpChatCustomization.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RankCommandTabCompleter implements TabCompleter {

    private static final List<String> COMMANDS = List.of("ranks", "create");

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(strings[0], COMMANDS, completions);
        return completions;
    }
}
