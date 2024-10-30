package io.github.jpmillz.jpChatCustomization;

import io.github.jpmillz.jpChatCustomization.commands.RankCommand;
import io.github.jpmillz.jpChatCustomization.commands.RankCommandTabCompleter;
import io.github.jpmillz.jpChatCustomization.events.PlayerEvents;
import io.github.jpmillz.jpChatCustomization.util.RankUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class JpChatCustomization extends JavaPlugin {
    private static JpChatCustomization plugin;

    public static JpChatCustomization getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        RankUtil.loadRanks();
        RankUtil.createRank("Default", "[Default]");
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getCommand("JpRanks").setExecutor(new RankCommand());
        getCommand("JpRanks").setTabCompleter(new RankCommandTabCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
