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

	public static void setDisplayName(boolean left, PlayerInteractEvent event) {
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
		check(event.getPlayer().getItemInHand().getItemMeta().getDisplayName(), event.getPlayer());
	}

	public static void check(String displayName, Player player) {
		if (displayName.split("-").length == 3) {
			getSpell(displayName.split("-"), player);
			ItemMeta im = player.getItemInHand().getItemMeta();
			im.setDisplayName("Bone");
			player.getItemInHand().setItemMeta(im);
		}
	}

	@SuppressWarnings("deprecation")
	public static void getSpell(String[] split, final Player player) {
		if (split[0].contains("Left") && split[1].contains("Left") && split[2].contains("Left")) {
			GeneralMethods.broadcast(player.getDisplayName() + " has cast Soul Suck");
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
					player.setHealth(player.getHealth() + entities.size());
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
