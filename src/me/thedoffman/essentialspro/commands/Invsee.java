package me.thedoffman.essentialspro.commands;

import me.thedoffman.essentialspro.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Invsee
implements CommandExecutor {
    private Inventory inv;
    private Main plugin = (Main)Main.getPlugin(Main.class);

    public Invsee(Main plugin) {
        Bukkit.getPluginCommand((String)"invsee").setExecutor((CommandExecutor)this);
    }

    @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(String.valueOf(this.plugin.prefix) + (Object)ChatColor.RED + "Error: Only players can see inventories!");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("invsee")) {
            if (!sender.hasPermission("ep.invsee")) {
                sender.sendMessage((Object)ChatColor.RED + "You do not have permission to use that command!");
                return true;
            }
            Player p = (Player)sender;
            if (args.length == 0) {
                p.sendMessage(String.valueOf(this.plugin.prefix) + (Object)ChatColor.RED + "Invalid Syntax! Usage: /invsee <player...>");
                return false;
            }
            if (args.length == 1) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(String.valueOf(this.plugin.prefix) + (Object)ChatColor.RED + "That player is not online");
                    return false;
                }
                this.inv = target.getInventory();
                p.openInventory(this.inv);
                return true;
            }
        }
        return false;
    }
}

