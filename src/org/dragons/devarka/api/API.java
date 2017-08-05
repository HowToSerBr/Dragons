package org.dragons.devarka.api;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.dragons.devarka.Main;

public class API {
	
	public static void tpPlayer(Player player) {
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
        
        player.teleport(localizacao);
	}
	}

}
