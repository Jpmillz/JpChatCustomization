package io.github.jpmillz.jpChatCustomization.listeners;

import io.github.jpmillz.jpChatCustomization.ranks.Rank;
import io.github.jpmillz.jpChatCustomization.util.RankUtil;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener, ChatRenderer {

    private static ChatListener listener;

    @EventHandler
    public void onChat(AsyncChatEvent event){
        event.renderer(this);
    }

    @Override
    public Component render(Player player, Component displayName, Component message, Audience viewer) {
        Rank rank = RankUtil.getPlayerRank(player.getUniqueId().toString());
        Component nameComp = displayName.color(TextColor.color(0, 255, 255));
        Component semiColonComp = Component.text(": ", NamedTextColor.WHITE);
        Component messageComp = message.color(TextColor.color(255, 255, 255));
        if (rank == null){
            return nameComp.append(semiColonComp).append(messageComp);
        }
        Component rankPrefixPlusName = Component.text(ChatColor.translateAlternateColorCodes('&', rank.identifier()) + " " + player.getName());
        return rankPrefixPlusName.append(semiColonComp).append(message);
    }

    public static ChatListener getInstance(){
        if (listener == null){
            return new ChatListener();
        }
        return listener;
    }
}
