package com.github.ZXSkelobrine.TestPlugins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class Listeners implements Listener {
	Logger log;
	Plugin plugin;
	List<PlayerLog> player = new ArrayList<PlayerLog>();

	public Listeners(Logger log, Plugin plugin) {
		this.log = log;
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if (event.getPlayer().getItemInHand().getType().equals(Material.BLAZE_ROD)) {
			if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				BlazeMethods.setDisplayName(true, event);
			} else if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				BlazeMethods.setDisplayName(false, event);
			}
		}
		if (event.getPlayer().getItemInHand().getType().equals(Material.BONE)) {
			if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				BoneMethods.setDisplayName(true, event, plugin);
			} else if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				BoneMethods.setDisplayName(false, event, plugin);
			}
		}
	}

	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
		log.info("Player Interact Entity Event");
		log.info("Player:" + event.getPlayer());
		log.info("Name" + event.getEventName());
		log.info("Right: " + event.getRightClicked());
	}

	@EventHandler
	public void onEntityDamagedByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			ItemStack hand = damager.getItemInHand();
			ItemMeta data = hand.getItemMeta();
			if (data.hasLore()) {
				if (data.getLore().get(0).contains(RecipeInitiation.HEAL_BEARER)) {
					Bearer.getBearer(damager, event.getEntity(), event.getDamage(), RecipeInitiation.HEAL_BEARER);
				}
				if (data.getLore().get(0).contains(RecipeInitiation.ARMOUR_BEARER)) {
					GeneralMethods.sendMessage("You are an " + RecipeInitiation.ARMOUR_BEARER, damager);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		player.add(new PlayerLog(false, event.getPlayer()));
		Location loc = event.getPlayer().getLocation();
		loc.setX(loc.getX() + 0.2);
		event.getPlayer().teleport(loc);
	}

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		for (int i = 0; i < player.size(); i++) {
			if (player.get(i).getPlayer().equals(event.getPlayer())) {
				if (!player.get(i).isDone()) {
					try {
						File file = new File(new File("").getAbsolutePath() + "/plugins/SpellCaster/" + event.getPlayer().getName() + ".txt");
						FileReader reader = new FileReader(file);
						char[] cbuf = new char[(int) file.length()];
						reader.read(cbuf);
						String name = new String(cbuf);
						event.getPlayer().setDisplayName(name);
						log.info("Name: " + name + "\tSet: " + event.getPlayer().getName());
						reader.close();
					} catch (IOException e) {
						event.getPlayer().setDisplayName(formatName("unchosen", event.getPlayer().getName(), event.getPlayer().isOp(), log));
						log.info("Name: " + formatName("unchosen", event.getPlayer().getName(), event.getPlayer().isOp(), log) + "\tSet: " + event.getPlayer().getName());
					}
					player.get(i).setDone(true);
				}
			}
		}
	}

	public static String formatName(String team, String displayName, boolean op, Logger log) {
		if (op) {
			if (team.equalsIgnoreCase("red")) {
				log.info(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[RED]" + ChatColor.RESET + "" + ChatColor.RED + displayName);
				return ChatColor.DARK_RED + "" + ChatColor.BOLD + "[RED]" + ChatColor.RESET + "" + ChatColor.RED + displayName;
			}
			if (team.equalsIgnoreCase("blue")) {
				log.info(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[RED]" + ChatColor.RESET + "" + ChatColor.RED + displayName);
				return ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "[BLUE]" + ChatColor.RESET + "" + ChatColor.RED + displayName;
			}
			if (team.equalsIgnoreCase("unchosen")) {
				log.info(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[RED]" + ChatColor.RESET + "" + ChatColor.RED + displayName);
				return ChatColor.BLACK + "" + ChatColor.BOLD + "[UNCHOSEN]" + ChatColor.RESET + "" + ChatColor.RED + displayName;
			}
		} else {
			if (team.equalsIgnoreCase("red")) {
				log.info(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[RED]" + ChatColor.RESET + "" + ChatColor.RED + displayName);
				return ChatColor.DARK_RED + "" + ChatColor.BOLD + "[RED]" + ChatColor.RESET + displayName;
			}
			if (team.equalsIgnoreCase("blue")) {
				log.info(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[RED]" + ChatColor.RESET + "" + ChatColor.RED + displayName);
				return ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "[BLUE]" + ChatColor.RESET + displayName;
			}
			if (team.equalsIgnoreCase("unchosen")) {
				log.info(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[RED]" + ChatColor.RESET + "" + ChatColor.RED + displayName);
				return ChatColor.BLACK + "" + ChatColor.BOLD + "[UNCHOSEN]" + ChatColor.RESET + displayName;
			}
		}
		return "test";
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		player.remove(event.getPlayer());
		File file = new File(new File("").getAbsolutePath() + "/plugins/SpellCaster/" + event.getPlayer().getName() + ".txt");
		System.out.println(file.getPath());
		if (file.exists()) file.delete();
		PrintWriter pw = null;
		try {
			new File(file.getParent()).mkdirs();
			file.createNewFile();
			pw = new PrintWriter(file);
			pw.print(event.getPlayer().getDisplayName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.close();
	}

	@EventHandler
	public void onEntityTargetEvent(EntityTargetEvent event) {
		if (event.getTarget() instanceof Player) {
			Player player = (Player) event.getTarget();
			if (player.hasMetadata("inv")) {
				if (player.getMetadata("inv").get(0).asInt() > 0) {
					if (event.getEntity() instanceof Skeleton) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
