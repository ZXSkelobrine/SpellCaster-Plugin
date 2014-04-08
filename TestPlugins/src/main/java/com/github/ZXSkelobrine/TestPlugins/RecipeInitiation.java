package com.github.ZXSkelobrine.TestPlugins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;

public class RecipeInitiation {

	public static final String FLAME_BEARER = "Flame Bearer";
	public static final String WIND_BEARER = "Wind Bearer";
	public static final String HEAL_BEARER = "Heal Bearer";
	public static final String ARMOUR_BEARER = "Armour Bearer";

	public RecipeInitiation(Server server) {
		server.addRecipe(getRecipe("wood,red"));
		server.addRecipe(getRecipe("wood,blue"));
		server.addRecipe(getRecipe("wood,green"));
		server.addRecipe(getRecipe("wood,yellow"));
	}

	private Recipe getRecipe(String string) {
		ShapelessRecipe sr = null;
		if (string.toLowerCase().equalsIgnoreCase("wood,red")) {
			ItemStack flame_sword_wood = configureItemStack(Color.RED, Material.WOOD_SWORD);
			sr = new ShapelessRecipe(flame_sword_wood);
			sr.addIngredient(Material.WOOD_SWORD);
			Dye d = new Dye();
			d.setColor(DyeColor.RED);
			sr.addIngredient(d.toItemStack().getData());
		}
		if (string.toLowerCase().equalsIgnoreCase("wood,blue")) {
			ItemStack wind_sword_wood = configureItemStack(Color.BLUE, Material.WOOD_SWORD);
			sr = new ShapelessRecipe(wind_sword_wood);
			sr.addIngredient(Material.WOOD_SWORD);
			Dye d = new Dye();
			d.setColor(DyeColor.BLUE);
			sr.addIngredient(d.toItemStack().getData());
		}
		if (string.toLowerCase().equalsIgnoreCase("wood,green")) {
			ItemStack heal_sword_wood = configureItemStack(Color.GREEN, Material.WOOD_SWORD);
			sr = new ShapelessRecipe(heal_sword_wood);
			sr.addIngredient(Material.WOOD_SWORD);
			Dye d = new Dye();
			d.setColor(DyeColor.GREEN);
			sr.addIngredient(d.toItemStack().getData());
		}
		if (string.toLowerCase().equalsIgnoreCase("wood,yellow")) {
			ItemStack armour_sword_wood = configureItemStack(Color.YELLOW, Material.WOOD_SWORD);
			sr = new ShapelessRecipe(armour_sword_wood);
			sr.addIngredient(Material.WOOD_SWORD);
			Dye d = new Dye();
			d.setColor(DyeColor.YELLOW);
			sr.addIngredient(d.toItemStack().getData());
		}
		return sr;
	}

	private ItemStack configureItemStack(Color dye, Material material) {
		ItemStack is = new ItemStack(material);
		ItemMeta im = is.getItemMeta();
		List<String> lores = new ArrayList<String>();
		if (dye.equals(Color.RED)) {
			lores.add(FLAME_BEARER);
			im.setDisplayName("Weathered Deutscher Zweihänder");
			is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
		}
		if (dye.equals(Color.BLUE)) {
			lores.add(WIND_BEARER);
			im.setDisplayName("Ancient Tachi");
			is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		}
		if (dye.equals(Color.GREEN)) {
			lores.add(HEAL_BEARER);
			im.setDisplayName("Wakizashi");
		}
		if (dye.equals(Color.YELLOW)) {
			lores.add(ARMOUR_BEARER);
			im.setDisplayName("Tanto");
		}
		im.setLore(lores);
		is.setItemMeta(im);
		if (dye.equals(Color.RED)) {
			is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
		}
		if (dye.equals(Color.BLUE)) {
			is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		}
		return is;
	}
}
