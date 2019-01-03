package me.thedoffman.essentialspro.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thedoffman.essentialspro.main.Main;

public class Mute implements CommandExecutor {

	private Main plugin = Main.getPlugin(Main.class);
	
    public Mute(Main plugin) {
        Bukkit.getPluginCommand("mute").setExecutor(this);
        Bukkit.getPluginCommand("unmute").setExecutor(this);
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		plugin.prefix = plugin.prefix.replaceAll("&", "\u00A7");

        if (label.equalsIgnoreCase("mute")) {
            if (args.length == 0) {
                sender.sendMessage(plugin.prefix + ChatColor.RED +"Please specify a player!");
            }
             else {
                 @SuppressWarnings("deprecation")
				Player target = Bukkit.getPlayer(args[0]);
                 
                 if (target == null) {
                     sender.sendMessage(plugin.prefix + ChatColor.RED + args[0]+" is not online!");
                     return true;
                 }
                 
                Bukkit.broadcastMessage(plugin.prefix + ChatColor.GREEN + target.getName() + ChatColor.RED + " was muted!");
                
                plugin.getplayers().set(args[0] + ".mute", true);
                plugin.saveYamls();
            }

    }
        if (label.equalsIgnoreCase("unmute")) {
            if (args.length == 0) {
                sender.sendMessage(plugin.prefix + ChatColor.RED +"Please specify a player!");
            }
             else {
                 @SuppressWarnings("deprecation")
				Player target = Bukkit.getPlayer(args[0]);
                 
                 if (target == null) {
                     sender.sendMessage(plugin.prefix + ChatColor.RED + args[0]+" is not online!");
                     return true;
                 }
                 
                Bukkit.broadcastMessage(plugin.prefix + ChatColor.GREEN + target.getName() + ChatColor.RED + " was unmuted!");
                
                plugin.getplayers().set(args[0] + ".mute", false);
                plugin.saveYamls();
            }

    }
		return false;
	}
}
