package me.thedoffman.essentialspro.commands;

import me.thedoffman.essentialspro.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpList
implements CommandExecutor {
    private Main plugin = Main.getPlugin(Main.class);

    public WarpList(Main plugin) {
        Bukkit.getPluginCommand("warplist").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        plugin.prefix = plugin.prefix.replaceAll("&", "\u00A7");
        if (cmd.getName().equalsIgnoreCase("warplist")) {
            if (!sender.hasPermission("ep.warplist")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
                return true;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.prefix + ChatColor.RED + "Error: Only players can set a home!");
                return true;
            }

            String CE = plugin.getlang().getString("Messages.ConsoleE");
            CE = CE.replaceAll("&", "\u00A7");
            if (sender instanceof Player) {
                Player p = (Player)sender;
                String warps = "";
                for (String s : plugin.getwarps().getConfigurationSection("warps").getKeys(false)) {
                    warps = warps + "\n" + s;
                }
                p.sendMessage(ChatColor.DARK_BLUE + "---- Here is the list of current warps---- " + ChatColor.DARK_GREEN + warps);
            } 
            }
            return false;

    }
}

