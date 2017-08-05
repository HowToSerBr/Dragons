package org.dragons.devarka.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.dragons.devarka.utils.Status;

public class Cancels implements Listener {

		
	@EventHandler
	public void morrer (EntityDamageEvent event1){//TU TA ESQUECENDO DE CHECAR O STATUS 
		if(Status.getStatus().equals(Status.AGUARDANDO)){
		event1.setCancelled(true);
		}
	}
	
	@EventHandler
	public void pvp (EntityDamageByEntityEvent event2){
		if(Status.getStatus().equals(Status.AGUARDANDO)){
		event2.setCancelled(true);
		} else if (event2.getDamager() instanceof Player && event2.getEntity() instanceof Player) {
			event2.setCancelled(true);
		}
	}
	
	@EventHandler
	public void quebrar1 (BlockBreakEvent event3){
			event3.setCancelled(true);
	}
	
	@EventHandler
	public void  queimar(EntityCombustEvent event4){
	    event4.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		event.setBuild(true);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(Status.getStatus().equals(Status.AGUARDANDO)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBed(PlayerBedEnterEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		event.setCancelled(true);
	}

}

	
	
	
	
