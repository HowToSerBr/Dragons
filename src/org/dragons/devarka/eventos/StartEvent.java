package org.dragons.devarka.eventos;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.dragons.devarka.Main;
import org.dragons.devarka.api.API;
import org.dragons.devarka.listeners.GameStartEvent;
import org.dragons.devarka.utils.ArrayL;
import org.dragons.devarka.utils.Status;

import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;


public class StartEvent implements Listener {
	
	public static boolean finalizada = false;
	public static int dragoes = 0;
	
	@EventHandler
	public void onStart(GameStartEvent event) {
		Status.setStatus(Status.EM_JOGO);
		Main.tempo_start = 0;
		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage("§eO jogo iniciou!");
		Bukkit.broadcastMessage("§eObjetivo: Ser o último sobrevivente \n ou viver até o fim do jogo!");
        Bukkit.broadcastMessage(" ");
        
        ItemStack machado = new ItemStack(Material.IRON_AXE);
        ItemMeta machadom = machado.getItemMeta();
        machadom.setDisplayName("§eBoost - Habilidade");
        machado.setItemMeta(machadom);
        
        for(Player all : Bukkit.getOnlinePlayers()) {
        API.tpPlayer(all);
        all.getInventory().addItem(machado);
        all.setLevel(0);
        }
        
        File file = new File(Main.plugin.getDataFolder().getPath(), "dragao.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        
        if(cfg.get("X") != null) {
        double x = cfg.getDouble("X");
        double y = cfg.getDouble("Y");
        double z = cfg.getDouble("Z");
        
        new BukkitRunnable() {
			@Override//Testa
			public void run() {
				if(dragoes < Main.plugin.getConfig().getInt("Dragoes_Maximos")){
				if(!finalizada) {
				EnderDragon ed = (EnderDragon)Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), x, y, z), EntityType.ENDER_DRAGON);
		        ed.setCustomName("§c§lThanos");
		        ed.setCustomNameVisible(true);
		        ed.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 40));
		        ed.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 40));
		        dragoes ++;
			}
				} else {
					this.cancel();
				}
			}
		}.runTaskTimer(Main.plugin, 0L, 20 * 30);
        }
		
		new BukkitRunnable() {			
			@Override
			public void run() {
				Main.tempo_game --;
				
				if(!finalizada) {
				if(Main.tempo_game == 0) {
					Status.setStatus(Status.REINICIANDO);
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage("§cSEM VENCEDORES!");
					Bukkit.broadcastMessage("§cmais sorte da próxima vez!");
					Bukkit.broadcastMessage(" ");
					finalizada = true;
					
			        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){

			            @Override
			            public void run() {
			                if (finalizada) {
			                    for (Player p : Bukkit.getOnlinePlayers()) {
			                        ByteArrayOutputStream b = new ByteArrayOutputStream();
			                        DataOutputStream out = new DataOutputStream(b);
			                        try {
			                            out.writeUTF("Connect");
			                            out.writeUTF(Main.plugin.getConfig().getString("Servidor_De_FallBack"));
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
			                if (finalizada) {
			                    Bukkit.shutdown();
			                }
			            }
			        }, 260);
					
				} else if (ArrayL.player.size() == 1) {
					Status.setStatus(Status.REINICIANDO);
					Bukkit.broadcastMessage(" ");
					for(String s : ArrayL.player) {
					Bukkit.broadcastMessage("§cVENCEDOR "+Bukkit.getPlayer(s).getName());
					Bukkit.getPlayer(s).setAllowFlight(true);
					Bukkit.getPlayer(s).setFlying(true);
					}
					Bukkit.broadcastMessage("§cParabéns ao vencedor!");
					Bukkit.broadcastMessage(" ");
					finalizada = true;
					
			        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){

			            @Override
			            public void run() {
			                if (finalizada) {
			                    for (Player p : Bukkit.getOnlinePlayers()) {
			                        ByteArrayOutputStream b = new ByteArrayOutputStream();
			                        DataOutputStream out = new DataOutputStream(b);
			                        try {
			                            out.writeUTF("Connect");
			                            out.writeUTF(Main.plugin.getConfig().getString("Servidor_De_FallBack"));
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
			                if (finalizada) {
			                    Bukkit.shutdown();
			                }
			            }
			        }, 260);
					
				} else if (ArrayL.player.size() == 0) {
					Status.setStatus(Status.REINICIANDO);
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage("§cSEM VENCEDORES!");
					Bukkit.broadcastMessage("§eTodos players deslogaram ou morreram!");
					Bukkit.broadcastMessage(" ");
						finalizada = true;
					}
				}
				if(!finalizada) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(all.getLocation().getBlock().getType().equals(Material.WATER_LILY) ||all.getLocation().getBlock().equals(Material.STATIONARY_WATER)) {
						all.damage(1.0D);
						break;
					}
					}
				}
			}
		}.runTaskTimer(Main.plugin, 0L, 20L);
				
			}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		
		if(e.getEntity() instanceof Player) {
		ArrayL.player.remove(e.getEntity().getName());
		Player player = e.getEntity();
		player.setGameMode(GameMode.SPECTATOR);
		for(Player all : Bukkit.getOnlinePlayers()) {
			all.hidePlayer(player);
		}
        Main.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
            @Override
            public void run() {
                if (player.isDead()) {
                    ((CraftPlayer)player).getHandle().playerConnection.a(new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN));
                }
            }
        });
		}
		
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){

            @Override
            public void run() {
                if (finalizada) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        DataOutputStream out = new DataOutputStream(b);
                        try {
                            out.writeUTF("Connect");
                            out.writeUTF(Main.plugin.getConfig().getString("Servidor_De_FallBack"));
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
                if (finalizada) {
                    Bukkit.shutdown();
                }
            }
        }, 260);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(Status.getStatus().equals(Status.EM_JOGO)) {
		ArrayList<String> dano = new ArrayList<>();
		if(event.getPlayer().getLocation().getBlock().getType() == Material.WATER || event.getPlayer().getLocation().getBlock().getType() == Material.STATIONARY_WATER) {
			if(!dano.contains(event.getPlayer().getName())) {
				dano.add(event.getPlayer().getName());
				event.getPlayer().damage(1.0D);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
				@Override
				public void run() {
					dano.remove(event.getPlayer().getName());
	            }
	        }, 30L);
			}
		}
		}
	}
}
