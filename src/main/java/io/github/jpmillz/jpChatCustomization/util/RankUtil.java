package io.github.jpmillz.jpChatCustomization.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.jpmillz.jpChatCustomization.JpChatCustomization;
import io.github.jpmillz.jpChatCustomization.ranks.Rank;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RankUtil {

    private static List<Rank> ranks = new ArrayList<>();
    private static File jsonFile = new File(JpChatCustomization.getPlugin().getDataFolder().getAbsolutePath() + "/ranks.json");


    public static void loadRanks() {
        try {
            Gson gson = new Gson();
            if (jsonFile.exists()) {
                Reader reader = new FileReader(jsonFile);
                ranks = gson.fromJson(reader, new TypeToken<List<Rank>>(){}.getType());
                System.out.println("Ranks have been loaded!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean assignRank(String uuid, String rankName){
        for (Rank rank : ranks){
            String name = rank.name().toLowerCase().trim();
            if (rankName.trim().toLowerCase().equals(name)){
                if (!rank.playerUUIDs().contains(uuid)){
                    rank.playerUUIDs().add(uuid);
                }
                return true;
            }
        }
        return false;
    }

    public static Rank getPlayerRank(String uuid){
        for (Rank rank : ranks){
            List<String> playersInRank = rank.playerUUIDs();
            if (playersInRank.contains(uuid)){
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
            System.out.println("Saved Ranks");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
