package io.github.jpmillz.jpChatCustomization.commands;


import io.github.jpmillz.jpChatCustomization.ranks.Rank;
import io.github.jpmillz.jpChatCustomization.util.RankUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.*;
import java.util.stream.Collectors;

public class RankCommand implements CommandExecutor {

    //add a change prefix command

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String initialCommand = strings[0];
        if (strings.length == 0 || initialCommand.equalsIgnoreCase("help")) {
            commandSender.sendMessage("help executed");
            return true;
        }
        if (initialCommand.equalsIgnoreCase("ranks")) {
            StringBuilder builder = new StringBuilder();
            RankUtil.getRanks().stream().map(Rank::name).forEach(v -> builder.append(v + ", "));
            commandSender.sendMessage(RankUtil.getRanks().toString());
            return true;
        }
        if (initialCommand.equalsIgnoreCase("create")) {
            createRankCommand(commandSender, strings);
            return true;
        }
        if (initialCommand.equalsIgnoreCase("assign")) {
            assignRankCommand(commandSender, strings);
            return true;
        }
        if (initialCommand.equalsIgnoreCase("delete")) {
            deleteRankCommand(commandSender, strings);
            return true;
        }
        if (initialCommand.equalsIgnoreCase("unassign")) {
            unassignRankCommand(commandSender, strings);
            return true;
        }
        return false;
    }

    private void unassignRankCommand(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Map<String, String> playerInfo = getPlayerInfoMap();
            String playerName = args[1];
            if (playerInfo.containsKey(playerName)) {
                String playerUUID = playerInfo.get(playerName);
                Rank playerRank = RankUtil.getPlayerRank(playerUUID);
                if (playerRank == null) {
                    Component notInRankComp = Component.text(playerName + " is not in a Rank!", NamedTextColor.RED);
                    sender.sendMessage(notInRankComp);
                } else {
                    boolean removedRank = RankUtil.removePlayerRank(playerUUID);
                    if (removedRank){
                        Component successComp = Component.text("Successfully removed " + playerName + " from rank " + playerRank.name(), NamedTextColor.GREEN);
                        sender.sendMessage(successComp);
                    }else {
                        Component errorComp = Component.text("Unable to remove " + playerName + " from " + playerRank.name(), NamedTextColor.RED);
                        sender.sendMessage(errorComp);
                    }
                }
            }else{
                Component playerNotFoundComp = Component.text(playerName + " not found.", NamedTextColor.RED);
                sender.sendMessage(playerNotFoundComp);
            }
        }
    }

    private void deleteRankCommand(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String rankToDelete = args[1];
            boolean deleteRank = RankUtil.deleteRank(rankToDelete);
            if (deleteRank) {
                sender.sendMessage(Component.text("Successfully deleted: " + rankToDelete, NamedTextColor.GREEN));
            } else {
                sender.sendMessage(Component.text("Deletion of " + rankToDelete + " was unsuccessful!", NamedTextColor.RED));
            }
        }
    }

    private void assignRankCommand(CommandSender sender, String[] args) {
        Map<String, String> playerInfo = getPlayerInfoMap();
        if (args.length == 3) {
            String playerName = args[1];
            String rankToAssign = args[2];
            String playerUUID = playerInfo.get(playerName);
            if (playerExistInMemory(playerName) && !(playerUUID == null)) {
                boolean assignedToRank = RankUtil.assignRank(playerUUID, rankToAssign);
                //add a check to make sure that the rank to be assigned actually exists
                if (assignedToRank) {
                    sender.sendMessage(playerName + " has been assigned to " + rankToAssign);
                } else {
                    sender.sendMessage(playerName + " is already in the rank: " + RankUtil.getPlayerRank(playerUUID));
                }
            } else {
                sender.sendMessage("Unable to assign rank to " + playerName);
            }
        }
    }

    private Map<String, String> getPlayerInfoMap() {
        Map<String, String> map = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            map.put(player.getName(), player.getUniqueId().toString());
        }
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            map.put(offlinePlayer.getName(), offlinePlayer.getUniqueId().toString());
        }
        return map;
    }

    private boolean playerExistInMemory(String playerName) {
        String nameToCheck = playerName.toLowerCase().trim();
        ArrayList<String> offlinePlayer = Arrays.stream(Bukkit.getOfflinePlayers())
                .map(p -> p.getName().toLowerCase())
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> onlinePlayers = Bukkit.getOnlinePlayers().stream()
                .map(p -> p.getName().toLowerCase())
                .collect(Collectors.toCollection(ArrayList::new));
        return offlinePlayer.contains(nameToCheck) ||
                onlinePlayers.contains(nameToCheck);
    }

    private void createRankCommand(CommandSender sender, String[] args) {
        String nameOfRank = args[1];
        String identifier = args[2];
        boolean createRankBool = RankUtil.createRank(nameOfRank, identifier);
        if (createRankBool) {
            sender.sendMessage(nameOfRank + " Has Been Created");
        } else {
            sender.sendMessage(nameOfRank + " Already exists!");
        }
    }
}
