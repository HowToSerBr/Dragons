package org.dragons.devarka.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private int playersJogando;
	private int playersMaximos;
	private String mapa;
	
	public GameStartEvent(int playersJogando, int playersMaximos, String mapa) {
		this.playersJogando = playersJogando;
		this.playersMaximos = playersMaximos;
		this.mapa = mapa;
	}
	
	public int getPlayers() {
		return this.playersJogando;
	}
	
	public int getMaxPlayers() {
		return this.playersMaximos;
	}
	
	public String getMapa() {
		return this.mapa;
	}

	public HandlerList getHandlers() {
	return handlers;
	}
	
	public static HandlerList getHandlerList() {
	return handlers;
	}

}

