package me.thedoffman.essentialspro.commands;

import me.thedoffman.essentialspro.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInventory
implements CommandExecutor {
    private Main plugin = (Main)Main.getPlugin(Main.class);

    public ClearInventory(Main plugin) {
        Bukkit.getPluginCommand((String)"ci").setExecutor((CommandExecutor)this);
    }

    @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player target;
        this.plugin.prefix = this.plugin.prefix.replaceAll("&", "�");
        if (cmd.getName().equalsIgnoreCase("ci")) {
            if (!sender.hasPermission("ep.clearinventory")) {
                sender.sendMessage((Object)ChatColor.RED + "You do not have permission to use that command!");
                return true;
            }
            if (args.length == 0 && !(sender instanceof Player)) {
                String eplayer = this.plugin.getlang().getString(String.valueOf(this.plugin.prefix) + "Messages.EPlayer");
                eplayer = eplayer.replaceAll("&", "�");
                sender.sendMessage(eplayer);
                return true;
            }
        }
        if (args.length == 0) {
            target = (Player)sender;
        } else {
            target = Bukkit.getServer().getPlayer(args[0]);
            if (target == null) {
                String nplayer = this.plugin.getlang().getString(String.valueOf(this.plugin.prefix) + "Messages.NPlayer").replaceAll("%player%", args[0]);
                nplayer = nplayer.replaceAll("&", "�");
                sender.sendMessage(nplayer);
                return true;
            }
        }
        target.getInventory().clear();
        String CIS = this.plugin.getlang().getString("Messages.CIS").replaceAll("%targetplayer%", target.getName().toLowerCase());
        CIS = CIS.replaceAll("&", "�");
        String CIT = this.plugin.getlang().getString("Messages.CIT").replaceAll("%senderplayer%", sender.getName().toLowerCase());
        CIT = CIT.replaceAll("&", "�");
        sender.sendMessage(String.valueOf(this.plugin.prefix) + CIS);
        target.sendMessage(String.valueOf(this.plugin.prefix) + CIT);
        return true;
    }
}

