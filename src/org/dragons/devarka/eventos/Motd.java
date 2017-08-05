package org.dragons.devarka.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.dragons.devarka.Main;
import org.dragons.devarka.utils.Status;

public class Motd implements Listener {
@EventHandler
	public void titulo (ServerListPingEvent event){
		if(Status.getStatus().equals(Status.AGUARDANDO)){
			event.setMotd("§aAguardando. Tempo para iniciar: "+ Main.tempo_start + " segundos");
	} else if(Status.getStatus().equals(Status.EM_JOGO)){
			event.setMotd("§cEm Jogo \nTempo:"+ Main.tempo_game);
	} else if(Status.getStatus().equals(Status.REINICIANDO)){
			event.setMotd("§4§lReiniciando");
	} else{
		event.setMotd("§7§lERRO!!");
		event.setMaxPlayers(0);
	}
}
	
}
