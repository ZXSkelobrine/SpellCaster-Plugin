package com.github.ZXSkelobrine.TestPlugins;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlazeMethods {
	public static void setDisplayName(boolean left, PlayerInteractEvent event) {
		String currentDisplay = event.getPlayer().getItemInHand().getItemMeta().getDisplayName();
		if (left) {
			if (currentDisplay == null || currentDisplay.equals("Blaze Rod")) {
				GeneralMethods.setName("Left ", event.getPlayer());
			} else {
				GeneralMethods.setName(GeneralMethods.getName(event.getPlayer()) + " - Left ", event.getPlayer());
			}
		} else {
			if (currentDisplay == null || currentDisplay.equals("Blaze Rod")) {
				GeneralMethods.setName("Right ", event.getPlayer());
			} else {
				GeneralMethods.setName(GeneralMethods.getName(event.getPlayer()) + " - Right ", event.getPlayer());
			}
		}
		check(event.getPlayer().getItemInHand().getItemMeta().getDisplayName(), event.getPlayer());
	}

	public static void check(String displayName, Player player) {
		if (displayName.split("-").length == 3) {
			getSpell(displayName.split("-"), player);
			ItemMeta im = player.getItemInHand().getItemMeta();
			im.setDisplayName("Blaze Rod");
			player.getItemInHand().setItemMeta(im);
		}
	}

	@SuppressWarnings("deprecation")
	public static void getSpell(String[] split, Player player) {
		if (split[0].contains("Left") && split[1].contains("Left") && split[2].contains("Right")) {
			player.getWorld().spawnArrow(player.getLocation(), player.getLocation().getDirection(), 1F, 30F).setKnockbackStrength(1);
			GeneralMethods.broadcast(player.getDisplayName() + " has cast phantom arrow!");
			player.setFoodLevel(player.getFoodLevel() - 3);
		}
		if (split[0].contains("Right") && split[1].contains("Left") && split[2].contains("Right")) {
			player.getWorld().strikeLightning(player.getTargetBlock(null, 200).getLocation());
			GeneralMethods.broadcast(player.getDisplayName() + " has cast lightning!");
			player.setFoodLevel(player.getFoodLevel() - 3);
		}
		if (split[0].contains("Right") && split[1].contains("Left") && split[2].contains("Left")) {
			createExplosion(player.getTargetBlock(null, 20).getLocation(), 3, false, false, player);
			GeneralMethods.broadcast(player.getDisplayName() + " has cast explosion!");
			player.setFoodLevel(player.getFoodLevel() - 3);
		}
		if (split[0].contains("Left") && split[1].contains("Left") && split[2].contains("Left")) {
			if (!(player.getHealth() >= 20)) {
				try {
					player.setHealth(player.getHealth() + 7);
					player.setFoodLevel(player.getFoodLevel() - 7);
					GeneralMethods.broadcast(player.getDisplayName() + " has cast heal!");
				} catch (Exception e) {
					player.setHealth(20);
				}
			}
		}
		if (split[0].contains("Right") && split[1].contains("Right") && split[2].contains("Right")) {
			if (!(player.getFoodLevel() >= 20)) {
				try {
					player.setFoodLevel(player.getFoodLevel() + 7);
					player.setHealth(player.getHealth() - 7);
					GeneralMethods.broadcast(player.getDisplayName() + " has cast heal!");
				} catch (Exception e) {
					player.setHealth(20);
				}
			}
		}
		if (split[0].contains("Left") && split[1].contains("Right") && split[2].contains("Left")) {
			player.teleport(player.getTargetBlock(null, 20).getLocation());
			GeneralMethods.broadcast(player.getDisplayName() + " has cast blink!");
			player.setFoodLevel(player.getFoodLevel() - 3);
		}
		if (split[0].contains("Left") && split[1].contains("Right") && split[2].contains("Right")) {
			Location location = player.getLocation();
			location.setY(location.getY() - 1);
			if (player.getWorld().getBlockAt(location).getType().equals(Material.EMERALD_BLOCK)) {
				player.setHealth(20);
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 3));
			}
		}
	}

	public static void createExplosion(Location location, int i, boolean b, boolean c, Player player) {
		player.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), i, b, c);
	}

}
