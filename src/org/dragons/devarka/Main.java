package org.dragons.devarka;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.dragons.devarka.comandos.Comando;
import org.dragons.devarka.comandos.setDragon;
import org.dragons.devarka.comandos.setLobby;
import org.dragons.devarka.comandos.setPlayersSpawn;
import org.dragons.devarka.eventos.Cancels;
import org.dragons.devarka.eventos.JoinEvent;
import org.dragons.devarka.eventos.Machado;
import org.dragons.devarka.eventos.Motd;
import org.dragons.devarka.eventos.StartEvent;
import org.dragons.devarka.utils.ArrayL;
import org.dragons.devarka.utils.ScoreboardUtil;
import org.dragons.devarka.utils.Status;
import org.dragons.devarka.utils.Timer;

public class Main extends JavaPlugin {

	public static Main plugin;
	public static int tempo_game;
	public static int tempo_start;
	
	public void onEnable(){	
		plugin = this;
		
		
		saveDefaultConfig();
		
		Bukkit.getPluginManager().registerEvents(new StartEvent(), this);
		Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
		Bukkit.getPluginManager().registerEvents(new Cancels(), this);
		Bukkit.getPluginManager().registerEvents(new Machado(), this);
		Bukkit.getPluginManager().registerEvents(new Motd(), this);

		Status.setStatus(Status.AGUARDANDO);
		
		tempo_start = getConfig().getInt("Tempo_Lobby");
		tempo_game = getConfig().getInt("Tempo_Jogo");
		
		Timer.startTimer();
		
		System.out.println("Plugin desenvolvido por Arkasher e DevBlocks ®");
		System.out.println("Todos os direitos reservados!");
		
		getCommand("start").setExecutor(new Comando());
		getCommand("setdragon").setExecutor(new setDragon());
		getCommand("setspawn").setExecutor(new setPlayersSpawn());
		getCommand("setlobby").setExecutor(new setLobby());
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(Status.getStatus().equals(Status.EM_JOGO)) {
					for(Player all : Bukkit.getOnlinePlayers()) {
					ScoreboardUtil.unrankedSidebarDisplay(all, new String[]{"§e§lDRAGONS",
					"§1",
					"§fDragões: §a"+StartEvent.dragoes,
					"§2",
					"§fTempo: §a"+convertMinutos(tempo_game),
					"§3",
					"§fStatus: §a"+getStatus(),
					"§4",
					"§fMapa: §a"+getConfig().getString("Mapa"),
					"§5",
					"§6dragons.com.br"});
					}
				} else {
					for(Player all : Bukkit.getOnlinePlayers()) {
					ScoreboardUtil.unrankedSidebarDisplay(all, new String[]{"§e§lDRAGONS",
					"§2",
					"§fTempo: §a"+String.valueOf(tempo_start) + " segundos",
					"§1",
					"§fPlayers: §a"+ArrayL.player.size(),
					"§3",
					"§fStatus: §a"+getStatus(),
					"§4",
					"§fMapa: §a"+getConfig().getString("Mapa"),
					"§5",
					"§6dragons.com.br"});
					}
				}
				
			}
		}.runTaskTimer(this, 0L, 20L);
	 
	
	}
	
	public static String getStatus() {
		if(Status.getStatus().equals(Status.EM_JOGO)) {
			return "Em Jogo";
		} else if (Status.getStatus().equals(Status.REINICIANDO)) {
			return "Reiniciando";
		} else if (Status.getStatus().equals(Status.AGUARDANDO)) {
			return "Aguardando";
		} else {
			return "Desconhecido";
		}
	}
	
	public static String convertMinutos(int i) {
		int ms = i/60;
		String minutos = String.valueOf(ms);
		String fin = null;
		if(ms >= 2) {
		fin = minutos + " minutos";
		} else if (ms == 1) {
		fin = minutos + " minuto";
		} else if (ms == 0) {
		fin = String.valueOf(i) + " segundos";
		} else {
		return "Erro";
		}
		return fin;
	}

	public void onDisable(){
        World w = Bukkit.getWorld("world");
        Bukkit.unloadWorld((World)w, false);
        File msw = new File(w.getName());
        deleteWorld(msw);
        generateWorld();	
	}
	
    private void generateWorld() {
        Bukkit.createWorld(new WorldCreator("world_b"));
        World schematic = Bukkit.getWorld("world_b");
        if (schematic == null) {
            return;
        }
        copyWorld(schematic.getWorldFolder(), Bukkit.getWorld("world").getWorldFolder());
    }

    public void copyWorld(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    @SuppressWarnings("unused")
					String[] files;
                    if (!target.exists()) {
                        target.mkdirs();
                    }
                    String[] arrayOfString1 = files = source.list();
                    int j = arrayOfString1.length;
                    int i = 0;
                    while (i < j) {
                        String file = arrayOfString1[i];
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                        ++i;
                    }
                } else {
                    int length;
                    FileInputStream in = new FileInputStream(source);
                    FileOutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[167];
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                    in.close();
                    out.close();
                }
            }
        }
        catch (IOException ignore) {}
    }

    private boolean deleteWorld(File file) {
        if (file.exists() && !file.getName().equals("session.lock")) {
            File[] files = file.listFiles();
            int i = 0;
            while (i < files.length) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
                ++i;
            }
        }
        return file.delete();
    }

}