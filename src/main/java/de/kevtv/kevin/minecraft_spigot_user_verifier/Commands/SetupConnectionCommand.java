package de.kevtv.kevin.minecraft_spigot_user_verifier.Commands;

import de.kevtv.kevin.minecraft_spigot_user_verifier.Data.MySQL;
import de.kevtv.kevin.minecraft_spigot_user_verifier.Helper.Helper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetupConnectionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();
            if(!MySQL.getColumnValues("minecraft_uuid").contains(playerUUID.toString())) {
                if(args.length > 0) {
                    if(!MySQL.getColumnValues("ts_uuid").contains(args[0])) {
                        String verifyCode = Helper.getVerifyCode();
                        MySQL.insertValuesToTable(playerUUID.toString(), args[0], verifyCode);
                        sender.sendMessage(verifyCode);
                    } else {
                        sender.sendMessage("Diese Eindeutige Teamspeak ID ist bereits verifiziert oder momentan in einem Prozess einer Verifizierung!");
                    }
                } else {
                    sender.sendMessage("Der Command heißt /setupConnection <Eindeutige TS3 ID>!");
                }
            } else {
                sender.sendMessage("Du bist bereits verifiziert oder bist momentan in einem Prozess einer Verifizierung!");
            }
        } else {
            sender.sendMessage("Dieser Command soll nur von Spielern ausgeführt werden!");
        }

        return true;
    }
}
