package com.github.ZXSkelobrine.TestPlugins;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugins extends JavaPlugin {
	public Logger log = getLogger();
	public boolean hungerT = false;
	public boolean running = true;
	public Thread hungerThread = new Thread() {
		@Override
		public void run() {
			while (running) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (hungerT && player.getFoodLevel() != 20) {
						player.setFoodLevel(player.getFoodLevel() + 2);
					} else {
						System.out.print("");
					}
					if (player.hasMetadata("inv")) {
						if (player.getMetadata("inv").get(0).asInt() > 0) {
							player.setMetadata("inv", new FixedMetadataValue(TestPlugins.this, player.getMetadata("inv").get(0).asInt() - 1));
						}
					} else {
						player.setMetadata("inv", new FixedMetadataValue(TestPlugins.this, 0));
					}
				}
				try {
					Thread.sleep(1000);
					System.out.print("");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		log.info("Enabled");
		getServer().getPluginManager().registerEvents(new Listeners(log, this), this);
		hungerThread.start();
		new RecipeInitiation(getServer());
		getCommand("spellcaster").setExecutor(new Commands(this, log));
		getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.BRICK, 1), Material.STONE));
	}

	@Override
	public void onDisable() {
		running = false;
		log.info("Disabled");
		hungerThread.interrupt();
	}

	public void sendMessage(String message, CommandSender player) {
		player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[SpellCaster]" + ChatColor.RESET + "" + ChatColor.RED + message);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wand") && args.length == 1) {
			if (sender instanceof Player) {
				if (args[0].equals("name")) {
					if (((Player) sender).getItemInHand().getType().equals(Material.BLAZE_ROD)) {
						GeneralMethods.setName("Blaze Rod", (Player) sender);
						return true;
					} else if (((Player) sender).getItemInHand().getType().equals(Material.BONE)) {
						GeneralMethods.setName("Bone", (Player) sender);
					} else {
						sendMessage("You must be holding a wand rod to do that!", sender);
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("hunger-thread")) {
					if (((Player) sender).isOp()) {
						hungerT = !hungerT;
						if (hungerT) {
							sendMessage("Hunger thread is now on", sender);
							return true;
						} else {
							sendMessage("Hunger thread is now off", sender);
							return true;
						}
					} else {
						sendMessage("You must be op to do that!", sender);
						return true;
					}
				}
			} else {
				sender.sendMessage("You must be a player to do that!");
				return true;
			}
		}
		return false;
	}

}
