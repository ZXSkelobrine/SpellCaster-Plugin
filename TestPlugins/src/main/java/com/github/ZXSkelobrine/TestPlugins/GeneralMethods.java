package com.github.ZXSkelobrine.TestPlugins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class GeneralMethods {
	public static String getName(Player player) {
		return player.getItemInHand().getItemMeta().getDisplayName();
	}

	public static void broadcast(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[SpellCaster]" + ChatColor.RESET + "" + ChatColor.RED + message);
		}
	}

	public static void sendMessage(String message, CommandSender player) {
		player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[SpellCaster]" + ChatColor.RESET + "" + ChatColor.RED + message);
	}

	public static void setName(String name, Player player) {
		ItemMeta im = player.getItemInHand().getItemMeta();
		im.setDisplayName(name);
		player.getItemInHand().setItemMeta(im);
	}
}
