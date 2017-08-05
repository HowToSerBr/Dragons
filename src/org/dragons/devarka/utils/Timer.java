package org.dragons.devarka.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.dragons.devarka.Main;
import org.dragons.devarka.listeners.GameStartEvent;

public class Timer {
	
	public static void startTimer() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(Status.getStatus().equals(Status.AGUARDANDO)) {
					if(Bukkit.getOnlinePlayers().size() >= Main.plugin.getConfig().getInt("Players_Para_Iniciar")) {
					Main.tempo_start -=1;
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.setLevel(Main.tempo_start);
					      	    
					}
					if(Main.tempo_start == 20) {
						Bukkit.broadcastMessage("§eJogo iniciando em §c20 segundos!");
					} else if(Main.tempo_start == 15) {
						Bukkit.broadcastMessage("§eJogo iniciando em §c15 segundos!");
					} else if(Main.tempo_start == 10) {
						Bukkit.broadcastMessage("§eJogo iniciando em §c10 segundos!");
					} else if(Main.tempo_start == 5) {
						Bukkit.broadcastMessage("§eJogo iniciando em §c5 segundos!");
					} else if(Main.tempo_start == 4) {
						Bukkit.broadcastMessage("§eJogo iniciando em §c4 segundos!");
					} else if(Main.tempo_start == 3) {
						Bukkit.broadcastMessage("§eJogo iniciando em §c3 segundos!");
					} else if(Main.tempo_start == 2) {
						Bukkit.broadcastMessage("§eJogo iniciando em §c2 segundos!");
					} else if(Main.tempo_start == 1) {
						Bukkit.broadcastMessage("§eJogo iniciando em §c1 segundo!");
					} else if(Main.tempo_start == 0) {
						Bukkit.getPluginManager().callEvent(new GameStartEvent(Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers(), Main.plugin.getConfig().getString("Mapa")));
					}
					}
				}
			}
		}.runTaskTimer(Main.plugin, 6L, 20L);
	}

}
