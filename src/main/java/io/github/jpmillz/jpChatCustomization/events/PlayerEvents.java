package io.github.jpmillz.jpChatCustomization.events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import io.github.jpmillz.jpChatCustomization.util.RankUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        String playerUUID = event.getPlayer().getUniqueId().toString();
        if (RankUtil.getPlayerRank(playerUUID) == null){
            RankUtil.assignRank(playerUUID, "default");
        }else{
            RankUtil.assignRank(playerUUID, RankUtil.getPlayerRank(playerUUID).name().toLowerCase().trim());
        }
    }
}
