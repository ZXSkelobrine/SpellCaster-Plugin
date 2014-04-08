package com.github.ZXSkelobrine.TestPlugins;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {
	private Plugin plugin;
	private boolean block = false;
	private Logger log;

	public Commands(Plugin plugin, Logger log) {
		this.plugin = plugin;
		this.log = log;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spellcaster") && args.length == 2) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args[0].equalsIgnoreCase("team")) {
					if (args[1].equalsIgnoreCase("red") && !block) {
						player.setMetadata("team", new FixedMetadataValue(plugin, "red"));
						String meta = player.getMetadata("team").get(0).asString();
						if (player.isOp()) {
							player.setDisplayName(Listeners.formatName("red", player.getName(), true, log));
						} else {
							player.setDisplayName(Listeners.formatName("red", player.getName(), false, log));
						}
						GeneralMethods.broadcast(player.getDisplayName() + " has chosen to be on the " + meta + " team.");
						return true;
					}
					if (args[1].equalsIgnoreCase("blue") && !block) {
						player.setMetadata("team", new FixedMetadataValue(plugin, "blue"));
						String meta = player.getMetadata("team").get(0).asString();
						if (player.isOp()) {
							player.setDisplayName(Listeners.formatName("blue", player.getName(), true, log));
						} else {
							player.setDisplayName(Listeners.formatName("blue", player.getName(), false, log));
						}
						GeneralMethods.broadcast(player.getDisplayName() + " has chosen to be on the " + meta + " team.");
						return true;
					}
					return true;
				}

			}
		}
		if (cmd.getName().equalsIgnoreCase("spellcaster") && args.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args[0].equalsIgnoreCase("start")) {
					block = true;
					GeneralMethods.broadcast("Team changing has been halted by: " + player.getDisplayName() + " - prepare for war!");
					return true;
				}
				if (args[0].equalsIgnoreCase("stop")) {
					block = false;
					GeneralMethods.broadcast("Team changing has been resumed by: " + player.getDisplayName() + " - war is over!");
					return true;
				}
			}
		}
		return false;
	}
}
