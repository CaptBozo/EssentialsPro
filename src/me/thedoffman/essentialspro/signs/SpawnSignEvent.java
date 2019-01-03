package me.thedoffman.essentialspro.signs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.thedoffman.essentialspro.main.Main;

public class SpawnSignEvent implements Listener {

	private  Main plugin = Main.getPlugin(Main.class);
	
    public SpawnSignEvent(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
   
    @EventHandler
    public void onSignChange(SignChangeEvent e) {
    	if(e.getPlayer().hasPermission("ep.signspawn")){

        if (e.getLine(0).equalsIgnoreCase("[Spawn]")) {
            e.setLine(0, ChatColor.BLUE+ChatColor.BOLD.toString()+"[Spawn]");
            e.setLine(1, ChatColor.GREEN + "Click here");
            e.setLine(2, ChatColor.GREEN + "to go to spawn!");
            e.getPlayer().sendMessage(ChatColor.BLUE+"Successfully created a new spawn sign!");
            }
    	}
        }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
    	if(e.getPlayer().hasPermission("ep.singspawnuse")){
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getClickedBlock().getState();

                if (sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE+ChatColor.BOLD.toString()+"[Spawn]")) {
                    World w = Bukkit.getServer().getWorld(plugin.getconfigs().getString("Spawn.world"));
                    double x = plugin.getconfigs().getDouble("Spawn.x");
                    double y = plugin.getconfigs().getDouble("Spawn.y");
                    double z = plugin.getconfigs().getDouble("Spawn.z");
                    e.getPlayer().teleport(new Location(w, x, y, z));
                    e.getPlayer().sendMessage(ChatColor.BLUE + "You successfully teleported to spawn!");
                }

            }
        }
    	}
    }

}
