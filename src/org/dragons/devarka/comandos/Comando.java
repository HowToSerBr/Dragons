package org.dragons.devarka.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dragons.devarka.Main;
import org.dragons.devarka.listeners.GameStartEvent;
import org.dragons.devarka.utils.Status;

public class Comando implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender instanceof Player) {
			if(cmd.getName().equalsIgnoreCase("start")){
				if(sender.hasPermission("dragons.start")){
					if(Status.getStatus().equals(Status.AGUARDANDO)) {
					if(args.length == 0) {
						Bukkit.getPluginManager().callEvent(new GameStartEvent(Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers(), Main.plugin.getConfig().getString("Mapa")));
						sender.sendMessage("§2Você iniciou o jogo!");
					} else {
						sender.sendMessage("§cUse: /start");
					}
					} else {
						sender.sendMessage("§cJogo já iniciou.");
					}
				}else{
					sender.sendMessage("§cVocê não tem permissão!");
				}
			}

		
		
	
	
	}else{
		sender.sendMessage("§cComando exclusivo para players");
	}
		return false;
	
}
}
