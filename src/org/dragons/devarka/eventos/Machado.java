package org.dragons.devarka.eventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;
import org.dragons.devarka.Main;
import org.dragons.devarka.utils.Status;

public class Machado implements Listener {
	
	public static ArrayList<String> unfall = new ArrayList<>();
	public static HashMap<String, Long> delay = new HashMap<>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getItem() != null) {
			if(event.getItem().hasItemMeta()) {
				if(event.getItem().getItemMeta().hasDisplayName()) {
					if(event.getItem().getType().equals(Material.IRON_AXE)) {
						if(!delay.containsKey(event.getPlayer().getName()) || delay.get(event.getPlayer().getName()).longValue() <= System.currentTimeMillis()) {
						
						delay.put(event.getPlayer().getName(), Long.valueOf(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10L)));
						Vector forca = event.getPlayer().getLocation().getDirection().multiply(0).setY(2.0D);
						
						
						unfall.add(event.getPlayer().getName());
						
						event.getPlayer().setVelocity(forca);
						
						event.setCancelled(true);
					} else {
						event.setCancelled(true);
						event.getPlayer().sendMessage("§cAguarde §f"+TimeUnit.MILLISECONDS.toSeconds(delay.get(event.getPlayer().getName()) - System.currentTimeMillis())+" §csegundos para usar novamente!");
					}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			if(event.getCause().equals(DamageCause.FALL)) {
				if(unfall.contains(event.getEntity().getName())) {
					event.setCancelled(true);
					unfall.remove(event.getEntity().getName());
				}
			}
		}
	}
	
	public static HashMap<String, Integer> doublejumps = new HashMap<>();
	public static ArrayList<String> delaydj = new ArrayList<>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if((player.getGameMode() != GameMode.CREATIVE)  && (player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR)
		&& (!player.isFlying())) {
			if(Status.getStatus().equals(Status.EM_JOGO)) {
			if(player.hasPermission("dragons.double_jump")) {
				if(!delaydj.contains(player.getName())) {
					delaydj.add(player.getName());
 		if(!doublejumps.containsKey(player.getName())) {
			doublejumps.put(player.getName(), 1);
		} else {
			doublejumps.put(player.getName(), doublejumps.get(player.getName()) + 1);
		}
		if(doublejumps.get(player.getName()) <= 2) {
			player.setAllowFlight(true);
			player.playSound(player.getLocation(), Sound.BLAZE_BREATH, 5.0f, 1.0f);
	        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
	            @Override
	            public void run() {
	               delaydj.remove(player.getName());
	            }//exporta
	        }, 40L);
		}
		}
			}
		}
		}
	}
	
	@EventHandler
	public void onFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE)
		return;
		event.setCancelled(true);
		player.setAllowFlight(false);
		player.setFlying(false); 
		player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
	}

}
