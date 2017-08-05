package org.dragons.devarka.comandos;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.dragons.devarka.Main;

public class setPlayersSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command cmnd, String arg2, String[] args) {
		if(cs instanceof Player) {
			if(cmnd.getName().equalsIgnoreCase("setspawn")) {
				if(cs.hasPermission("dragons.setspawn")) {
					cs.sendMessage(" ");
					cs.sendMessage("§eVocê setou o spawn dos players!");
					cs.sendMessage(" ");
				File file = new File(Main.plugin.getDataFolder().getPath(), "spawn.yml");
				FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				
				Player player = (Player)cs;
				
				cfg.set("X", player.getLocation().getX());
				cfg.set("Y", player.getLocation().getY());
				cfg.set("Z", player.getLocation().getZ());
				cfg.set("P", player.getLocation().getPitch());
				cfg.set("A", player.getLocation().getYaw());
				
				try {
					cfg.save(file);
				} catch (IOException ev) {
					ev.printStackTrace();
				}
				}
			}
		}
		return false;
	}

}

