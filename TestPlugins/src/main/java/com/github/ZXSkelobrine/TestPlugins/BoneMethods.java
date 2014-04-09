package com.github.ZXSkelobrine.TestPlugins;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class BoneMethods {
	static Thread thread;

	/**
	 * This either casts the entity to an animal or casts it to a monster then
	 * sets the health to 0 effectively killing it.
	 * 
	 * @param entity
	 *            - Entity - The entity to kill
	 * @param animal
	 *            - Boolean - whether it is an animal.
	 */
	private static void kill(Entity entity, boolean animal) {
		if (animal) {
			((Animals) entity).setHealth(0);
		} else {
			((Monster) entity).setHealth(0);
		}
	}

	private static void damage(Entity entity) {
		try {
			((Player) entity).setHealth(((Player) entity).getHealth() - 10);
		} catch (Exception e) {
			((Player) entity).setHealth(0);
		}
	}

	public static void setDisplayName(boolean left, PlayerInteractEvent event, Plugin plugin) {
		String currentDisplay = event.getPlayer().getItemInHand().getItemMeta().getDisplayName();
		if (left) {
			if (currentDisplay == null || currentDisplay.equals("Bone")) {
				GeneralMethods.setName("Left ", event.getPlayer());
			} else {
				GeneralMethods.setName(GeneralMethods.getName(event.getPlayer()) + " - Left ", event.getPlayer());
			}
		} else {
			if (currentDisplay == null || currentDisplay.equals("Bone")) {
				GeneralMethods.setName("Right ", event.getPlayer());
			} else {
				GeneralMethods.setName(GeneralMethods.getName(event.getPlayer()) + " - Right ", event.getPlayer());
			}
		}
		check(event.getPlayer().getItemInHand().getItemMeta().getDisplayName(), event.getPlayer(), plugin);
	}

	public static void check(String displayName, Player player, Plugin plugin) {
		if (displayName.split("-").length == 3) {
			getSpell(displayName.split("-"), player, plugin);
			ItemMeta im = player.getItemInHand().getItemMeta();
			im.setDisplayName("Bone");
			player.getItemInHand().setItemMeta(im);
		}
	}

	@SuppressWarnings("deprecation")
	public static void getSpell(String[] split, final Player player, Plugin plugin) {
		if (split[0].contains("Left") && split[1].contains("Left") && split[2].contains("Left")) {

			thread = new Thread() {
				@Override
				public void run() {
					int radius = 10;
					double radiusSquared = radius * radius;
					List<Entity> entities = player.getNearbyEntities(radius, radius, radius);
					for (Entity entity : entities) {
						if (entity.getLocation().distanceSquared(player.getLocation()) > radiusSquared) continue;
						if (entity instanceof Player) {
							damage(entity);
						}
						if (entity instanceof Animals) {
							kill(entity, true);
						}
						if (entity instanceof Monster) {
							kill(entity, false);
						}
					}
					try {
						player.setHealth(player.getHealth() + entities.size());
					} catch (Exception e) {
						player.setHealth(20);
					}
					try {
						player.setFoodLevel(player.getFoodLevel() - entities.size());
					} catch (Exception e) {// Should be
											// java.lang.IllegalArgumentException
											// but I want to make sure I get all
											// of them if any more a called.
						player.setFoodLevel(0);
					}
					if (entities.size() != 0) {
						GeneralMethods.broadcast(player.getDisplayName() + " has cast Soul Suck");
					} else {
						GeneralMethods.broadcast(player.getDisplayName() + " has failed to cast Soul Suck");
					}
					try {
						thread.join();
					} catch (InterruptedException e) {
						thread.interrupt();
						e.printStackTrace();
					}
				}
			};
			thread.start();
		}
		if (split[0].contains("Left") && split[1].contains("Left") && split[2].contains("Right")) {
			GeneralMethods.broadcast(player.getDisplayName() + " has cast Swarm");
			spawnMobs(3, player.getTargetBlock(null, 20), player.getWorld());
		}
		if (split[0].contains("Left") && split[1].contains("Right") && split[2].contains("Right")) {
			player.setMetadata("sc-invun", new FixedMetadataValue(plugin, plugin.getConfig().getInt("inv")));
		}
	}

	public static void spawnMobs(int amount, Block block, World world) {
		for (Entity e : block.getChunk().getEntities()) {
			if (e instanceof Player && block.getLocation().distance(e.getLocation()) < 2) {
				for (int i = 0; i < amount; i++) {
					Location loc = e.getLocation();
					loc.setX(loc.getX() - 5);
					Entity entity = world.spawnEntity(loc, EntityType.SKELETON);
					((Monster) entity).setTarget((LivingEntity) e);
				}
			}
		}
	}
}
