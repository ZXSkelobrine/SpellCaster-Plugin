package com.github.ZXSkelobrine.TestPlugins;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class Bearer {
	public static void getBearer(Entity damager, Entity damagee, double damage, String bearer) {
		if (bearer.equals(RecipeInitiation.HEAL_BEARER)) healBearer(damager, damagee, damage);
		if (bearer.equals(RecipeInitiation.ARMOUR_BEARER)) armourBearer(damager, damagee, damage);
	}

	public static void healBearer(Entity damager, Entity damagee, double damage) {
		if (damagee instanceof Player && damager instanceof Player) {
			Player attacker = (Player) damager;
			Player attackee = (Player) damagee;
			if (attackee.getMetadata("team").get(0).asString().equals(attacker.getMetadata("team").get(0).asString())) {
				try {
					attackee.setHealth(attackee.getHealth() + damage);
					attackee.setHealth(attackee.getHealth() + damage);
					GeneralMethods.sendMessage("Gave " + (damage + damage) + " health.", attacker);
				} catch (Exception e) {
					attackee.setHealth(20);
					GeneralMethods.sendMessage("Gave " + (damage + damage) + " health.", attacker);
				}
			} else {
				// Attack attacked
				try {
					attackee.setHealth(attackee.getHealth() - damage);
					attackee.setHealth(attackee.getHealth() - damage);
				} catch (Exception e) {
					attackee.setHealth(0);
				}
				// Give health
				try {
					attacker.setHealth(attacker.getHealth() + damage);
					attacker.setHealth(attacker.getHealth() + damage);
				} catch (Exception e) {
					attacker.setHealth(20);
				}
				// Announce
				GeneralMethods.sendMessage("Absorbed " + (damage + damage) + " health.", (Player) damager);
			}
		} else if (damager instanceof Player) {
			if (damagee instanceof Animals) {
				Player player = (Player) damagee;
				Animals animals = (Animals) damagee;
				double acDam = damage + damage;
				try {
					animals.setHealth(animals.getHealth() - acDam);
				} catch (Exception e) {
					animals.setHealth(0);
				}
				try {
					player.setHealth(player.getHealth() + acDam);
				} catch (Exception e) {
					player.setHealth(20);
				}
				GeneralMethods.sendMessage("Absorbed " + acDam + " health.", player);
			}
			if (damagee instanceof Monster) {
				Player player = (Player) damager;
				Monster monster = (Monster) damagee;
				double acDam = damage + damage;
				try {
					monster.setHealth(monster.getHealth() - acDam);
				} catch (Exception e) {
					monster.setHealth(0);
				}
				try {
					player.setHealth(player.getHealth() + acDam);
				} catch (Exception e) {
					player.setHealth(20);
				}
				GeneralMethods.sendMessage("Absorbed " + acDam + " health.", player);
			}
		}
	}

	public static void armourBearer(Entity damager, Entity damagee, double damage) {
		if()
	}
}
