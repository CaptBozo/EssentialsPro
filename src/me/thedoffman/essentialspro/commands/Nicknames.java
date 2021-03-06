package me.thedoffman.essentialspro.commands;


import me.thedoffman.essentialspro.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Nicknames implements CommandExecutor, Listener {
    private static Main plugin = Main.getPlugin(Main.class);

    public Nicknames(Main plugin) {
        Bukkit.getPluginCommand("nickname").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        plugin.prefix = plugin.prefix.replaceAll("&", "\u00A7");
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("nickname")) {
            if (p.hasPermission("ep.nick.use")) {
                p.sendMessage(plugin.prefix + ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }
            if ((sender instanceof Player)) {
                sender.sendMessage(plugin.prefix + ChatColor.RED + "Error: Only players can see inventories!");
                return true;
            }
            if (args.length == 0) {
                p.sendMessage(plugin.prefix + ChatColor.RED + "Use: /nick <Nickname>");
                return true;
            }
            if (args[0].equalsIgnoreCase("off")) {
                plugin.getplayers().set(p.getName() + ".nickname", null);
                plugin.saveYamls();
                p.sendMessage(plugin.prefix + ChatColor.GREEN + "Nickname turned off!");
                return true;
            }
            for (String key : plugin.getplayers().getKeys(false)) {
                if (!plugin.getplayers().getString(key).equals(args[0])) continue;
                p.sendMessage(plugin.prefix + ChatColor.RED + "Someone else already has this nickname!");
                return true;
            }
            String nick = "";
            String[] arrstring = args;
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String arg = arrstring[n2];
                nick = nick + arg + " ";
                ++n2;
            }
            nick = nick.substring(0, nick.length() - 1);
            nick = nick.replaceAll("&", "\u00A7");
            plugin.getplayers().set(p.getName() + ".nickname", nick);
            plugin.saveYamls();
            p.sendMessage(plugin.prefix + ChatColor.GREEN + "Nickname changed!");
        }
        if (cmd.getName().equalsIgnoreCase("realname")) {
            if (!p.hasPermission("nick.realname")) {
                p.sendMessage(plugin.prefix + ChatColor.RED + "You do not have permission to use this command!");
                return true;
            }
            if (args.length == 0) {
                p.sendMessage(plugin.prefix + ChatColor.RED + "Use: /nick <Nickname>");
                return true;
            }
            for (Player p2 : Bukkit.getOnlinePlayers()) {
                if (!plugin.getplayers().contains(p2.getName() + ".nickname") || !plugin.getplayers().getString(p2.getName() + ".nickname").equals(args[0])) continue;
                p.sendMessage(plugin.prefix + ChatColor.GREEN + args[0] + "'s real name " + ChatColor.DARK_AQUA + p2.getName());
                return true;
            }
            p.sendMessage(plugin.prefix + ChatColor.RED + "Nobody with that nickname found!");
        }
        return true;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (plugin.getplayers().contains(e.getPlayer().getName() + ".nickname")) {
            e.setCancelled(true);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("<" + plugin.getplayers().getString(new StringBuilder(e.getPlayer().getName()).append(".nickname").toString()) + ChatColor.RESET + "> " + e.getMessage());
            }
        }
    }
}

