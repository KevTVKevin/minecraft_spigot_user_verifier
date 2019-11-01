package de.kevtv.kevin.minecraft_spigot_user_verifier.Events;

import de.kevtv.kevin.minecraft_spigot_user_verifier.Data.MySQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int playerStatus = getPlayerStatus(player);

        switch (playerStatus) {
            case 0: player.sendMessage("Du bist noch nicht verifiziert!"); break;
            case 1: player.sendMessage("Deine Verifizierung ist noch nicht abgeschlossen!"); break;
        }

    }

    private int getPlayerStatus(Player player) {
        UUID playerUUID = player.getUniqueId();

        if(MySQL.getColumnValues("minecraft_uuid").contains(playerUUID.toString())) {
            if(MySQL.getSpecificValue("verified", "minecraft_uuid", playerUUID.toString()).equals("0")) {
                return 1;
            }
            return 2;
        }
        return 0;
    }

}
