package io.github.jpmillz.jpChatCustomization.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.jpmillz.jpChatCustomization.JpChatCustomization;
import io.github.jpmillz.jpChatCustomization.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RankUtil {

    private static List<Rank> ranks = new ArrayList<>();
    private static File jsonFile = new File(JpChatCustomization.getPlugin().getDataFolder().getAbsolutePath() + "/ranks.json");


    public static void loadRanks() {
        if (jsonFile.exists()) {
            try {
                Gson gson = new Gson();
                Reader reader = new FileReader(jsonFile);
                ranks = gson.fromJson(reader, new TypeToken<List<Rank>>() {
                }.getType());
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&2JpRanks: &3Ranks been been loaded!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cJpRanks: Ranks unable to load, or do not exist!"));
        }
    }

    public static boolean assignRank(String uuid, String rankName) {
        Rank playerRank = getPlayerRank(uuid);
        if (playerRank == null) {
            for (Rank rank : ranks) {
                String name = rank.name().toLowerCase().trim();
                if (rankName.trim().toLowerCase().equals(name)) {
                    rank.playerUUIDs().add(uuid);
                    saveRanks();
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean removePlayerRank(String uuid){
        Rank playerRank = getPlayerRank(uuid);
        if (!(playerRank == null)){
            playerRank.playerUUIDs().remove(uuid);
            saveRanks();
            return true;
        }
        return false;
    }

    public static Rank getPlayerRank(String uuid) {
        for (Rank rank : ranks) {
            List<String> playersInRank = rank.playerUUIDs();
            if (playersInRank.contains(uuid)) {
                return rank;
            }
        }
        return null;
    }

    public static boolean createRank(String name, String ingameIdentifier) {
        Rank rank = new Rank(name.toLowerCase().trim(), ingameIdentifier, new ArrayList<>());
        if (ranks.contains(rank)) {
            return false;
        }
        ranks.add(rank);
        saveRanks();
        return true;
    }

    public static boolean deleteRank(String rankName) {
        if (!ranks.isEmpty()) {
            for (int i = 0; i < ranks.size(); i++) {
                if (ranks.get(i).name().equalsIgnoreCase(rankName)) {
                    ranks.remove(i);
                    saveRanks();
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean removePlayerFromRank(String uuidOfPlayer, String rankName) {
        if (!ranks.isEmpty() && ranks.contains(rankName)) {
            for (Rank rank : ranks) {
                if (rank.name().equalsIgnoreCase(rankName)) {
                    List<String> playerUUIDInRank = rank.playerUUIDs();
                    if (playerUUIDInRank.contains(uuidOfPlayer)) {
                        playerUUIDInRank.remove(uuidOfPlayer);
                        saveRanks();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<Rank> getRanks() {
        return ranks;
    }

    public static void saveRanks() {
        try {
            Gson gson = new Gson();
            jsonFile.getParentFile().mkdir();
            jsonFile.createNewFile();
            FileWriter writer = new FileWriter(jsonFile, false);
            gson.toJson(ranks, writer);
            writer.flush();
            writer.close();
            System.out.println("JpRanks: Saved Ranks");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
