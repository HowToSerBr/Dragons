package org.dragons.devarka.eventos;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.dragons.devarka.Main;
import org.dragons.devarka.utils.ArrayL;
import org.dragons.devarka.utils.Status;


public class JoinEvent implements Listener {

	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
	if(Status.getStatus().equals(Status.AGUARDANDO)) {
		event.getPlayer().setGameMode(GameMode.SURVIVAL);
		event.getPlayer().setFoodLevel(20);
		event.getPlayer().setHealth(20.0D);
		event.getPlayer().setLevel(0);
		event.getPlayer().getInventory().clear();
		Bukkit.broadcastMessage("§eO jogador §c"+event.getPlayer().getName() + " §eentrou na partida! §c(§c"+ Bukkit.getOnlinePlayers().size() +"/"+ Bukkit.getMaxPlayers()+"§c)");
		event.setJoinMessage(null);
		ArrayL.player.add(event.getPlayer().getName());
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 2));

        File file = new File(Main.plugin.getDataFolder().getPath(), "lobby.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
        if(cfg.get("X") != null) {
        double x = cfg.getDouble("X");
        double y = cfg.getDouble("Y");
        double z = cfg.getDouble("Z");
        double p = cfg.getDouble("P");
        double a = cfg.getDouble("A");
        
        Location localizacao = new Location(Bukkit.getWorld("world"), x, y, z);
        localizacao.setPitch((float)p);
        localizacao.setYaw((float)a);
        
        event.getPlayer().teleport(localizacao);
        }

	
	}else{
		if(event.getPlayer().hasPermission("dragons.admin")){
			event.getPlayer().sendMessage("§cVocê entrou como espectador!");
			event.setJoinMessage(null);
		

		}else{
			event.getPlayer().kickPlayer("§cDesculpe-nos, a sala que você tentou entrar está com a partida em andamento.");
			event.setJoinMessage(null);
		}
	}
}
	@EventHandler
	public void onLeft(PlayerQuitEvent event){
	       Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){

	            @Override
	            public void run() {
	                if (StartEvent.finalizada) {
	                    for (Player p : Bukkit.getOnlinePlayers()) {
	                        ByteArrayOutputStream b = new ByteArrayOutputStream();
	                        DataOutputStream out = new DataOutputStream(b);
	                        try {
	                            out.writeUTF("Connect");
	                            out.writeUTF(Main.plugin.getConfig().getString("lobby"));
	                        }
	                        catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                        p.sendPluginMessage(Main.plugin, "BungeeCord", b.toByteArray());
	                    }
	                }
	            }
	        }, 200L);
	        
	        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){

	            @Override
	            public void run() {
	                if (StartEvent.finalizada) {
	                    Bukkit.shutdown();
	                }
	            }
	        }, 260);
		if(Status.getStatus().equals(Status.AGUARDANDO)){
		Bukkit.broadcastMessage("§c"+ event.getPlayer().getName()+" §edesconectou-se da partida! §c(§c"+Bukkit.getOnlinePlayers().size() + "/"+ Bukkit.getMaxPlayers()+"§c)") ;
		event.setQuitMessage(null);
		ArrayL.player.remove(event.getPlayer().getName());
		
	}else{
		Bukkit.broadcastMessage("§c "+event.getPlayer().getName() + " §enão resistiu a fúria do dragão e desconectou!");
		event.setQuitMessage(null);
		ArrayL.player.remove(event.getPlayer().getName());
	}
}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Bukkit.broadcastMessage("§c"+ event.getPlayer().getName() + " §emorreu!");
        File file = new File(Main.plugin.getDataFolder().getPath(), "spawn.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
        if(cfg.get("X") != null) {
        double x = cfg.getDouble("X");
        double y = cfg.getDouble("Y");
        double z = cfg.getDouble("Z");
        double p = cfg.getDouble("P");
        double a = cfg.getDouble("A");
        
        Location localizacao = new Location(Bukkit.getWorld("world"), x, y, z);
        localizacao.setPitch((float)p);
        localizacao.setYaw((float)a);
        
        event.setRespawnLocation(localizacao);
	}
}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Bukkit.broadcastMessage("§7[g] "+event.getPlayer().getName()+": §f"+event.getMessage());
		event.setCancelled(true);
	}
}