package me.Mammothskier.Giants.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.Mammothskier.Giants.Giants;
import me.Mammothskier.Giants.files.Files;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DropsManager implements Listener {
	
	private Giants _giants;
	
	public DropsManager(Giants giants) {
		_giants = giants;
		_giants.getServer().getPluginManager().registerEvents(this, giants);
	}
	
	@EventHandler
	public void onGiantDrops(EntityDeathEvent event) throws IOException{
		Entity entity = event.getEntity();
		

		if (Entities.isGiantZombie(entity)) {
			String string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Stats.Experience.Giant Zombie");
			int exp;

			try {
				exp = Integer.parseInt(string);
			} catch (Exception e) {
				exp = 5;
			}
			if (Giants.getProperty(Files.CONFIG, "Giants Configuration.Sounds").equalsIgnoreCase("true")) {
				entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 0);
			}
			event.setDroppedExp(exp);
			List<String> newDrop = Giants.getPropertyList(Files.ENTITIES, "Entities Configuration.Stats.Drops.Giant Zombie");
			if (newDrop == null || newDrop.contains("") || newDrop.toString().equalsIgnoreCase("[]")) {
				return;
			}
			
			List<ItemStack> drops = new ArrayList<ItemStack>();
			drops = setDrop(entity, newDrop);

			event.getDrops().addAll(drops);
		}
		if (Entities.isGiantSlime(entity)) {
			String string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Stats.Experience.Giant Slime");
			int exp;

			try {
				exp = Integer.parseInt(string);
			} catch (Exception e) {
				exp = 5;
			} 
			if (Giants.getProperty(Files.CONFIG, "Giants Configuration.Sounds").equalsIgnoreCase("true")) {
				entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENDERDRAGON_DEATH, 1, 0);
			}
			event.setDroppedExp(exp);
			List<String> newDrop = Giants.getPropertyList(Files.ENTITIES, "Entities Configuration.Stats.Drops.Giant Slime");
			if (newDrop == null || newDrop.contains("") || newDrop.toString().equalsIgnoreCase("[]")) {
				return;
			}
			
			List<ItemStack> drops = new ArrayList<ItemStack>();

			drops = setDrop(entity, newDrop);

			event.getDrops().addAll(drops);
		}
		

		if (Entities.isGiantLavaSlime(entity)) {
			String string = Giants.getProperty(Files.ENTITIES, "Entities Configuration.Stats.Experience.Giant Lava Slime");
			int exp;

			try {
				exp = Integer.parseInt(string);
			} catch (Exception e) {
				exp = 5;
			}
			if (Giants.getProperty(Files.CONFIG, "Giants Configuration.Sounds").equalsIgnoreCase("true")) {
				entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 0);
			}
			event.setDroppedExp(exp);
			List<String> newDrop = Giants.getPropertyList(Files.ENTITIES, "Entities Configuration.Stats.Drops.Giant Lava Slime");
			if (newDrop == null || newDrop.contains("") || newDrop.toString().equalsIgnoreCase("[]")) {
				return;
			}

			List<ItemStack> drops = new ArrayList<ItemStack>();

			drops = setDrop(entity, newDrop);

			event.getDrops().addAll(drops);
		}
	}
	
	
	public static List<ItemStack> setDrop(Entity entity, List<String> newDrop) {
		List<ItemStack> drops = new ArrayList<ItemStack>();
		
		switch (entity.getType()) {
			case GIANT:
				for (String dropList : newDrop) {
					Random rand = new Random();
					String[] s = dropList.split(";");			
					
					if (s.length == 5) {
						String item = s[0];
						String style= "";
						String effect = "";
						String effectLevel= "";
						String amount = s[1];
						String rate = s[2];
						String name = s[3];
						String lore = s[4];
						List<String> itemLore;
						int id = 0;
						int num = 100;
						int amt = 1;
						int den = 100;
						short color = 0;
						int effectID = 0;
						int effectLevelID = 0;
					
						if (item.contains("-")){
							String[] split = item.split("-");
							if (split.length == 3){
								item = split[0];
								effect = split[1];
								effectLevel = split[2];
							}
						} 
						if (item.contains(":")){
							String[] split = item.split(":");
							if (split.length == 2){
								item = split[0];
								style = split[1];
							}
						}
						if (amount.contains("-")){
							int lowerAmount;
							int upperAmount;
							String[] split = amount.split("-");
							String lAmount = split[0];
							String uAmount = split[1];
							try {
								lowerAmount = Integer.parseInt(lAmount);
								upperAmount = Integer.parseInt(uAmount);
							} catch (Exception e) {
								lowerAmount = 1;
								upperAmount = 1;
							}
							amt = rand.nextInt(upperAmount - lowerAmount + 1) + lowerAmount - 1;
							amount = String.valueOf(amt);
						}
						if (rate.contains("/")){
							String[] split = rate.split("/");
							if (split.length == 2){
								try {
									num = Integer.parseInt(split[0]);
									den = Integer.parseInt(split[1]);
								} catch (Exception e) {
									num = 100;
									den = 100;
								}
							}
						}

						try {
							id = Integer.parseInt(item);
							effectID = Integer.parseInt(effect);
							effectLevelID = Integer.parseInt(effectLevel);
							color = Short.parseShort(style);
							amt = Integer.parseInt(amount);
						} catch (Exception e) {
							
						}
						int randNum = rand.nextInt(den);
						
						if (name != null) {
							name = ChatColor.translateAlternateColorCodes('&', name);
						}
						
						if (lore != null) {
							lore = ChatColor.translateAlternateColorCodes('&', lore);
						}
						
						itemLore = Arrays.asList(lore);
						
						
						if (num >= randNum){
							ItemStack newItem = new ItemStack(id, amt, color);
							ItemMeta itemMeta = newItem.getItemMeta();
							itemMeta.setDisplayName(name);
							itemMeta.setLore(itemLore);
							newItem.setItemMeta(itemMeta);
							
							newItem.setDurability(color);
							if ((effectID == 0) || (effectLevelID == 0)) {
								
							} else if ((effectLevelID >= Enchantment.getById(effectID).getStartLevel()) && (effectLevelID <= Enchantment.getById(effectID).getMaxLevel())) {
								Enchantment enchantment = new EnchantmentWrapper(effectID);
								newItem.addEnchantment(enchantment, effectLevelID);
							} else {
								
							}
							
							drops.add(newItem);
						}
					}
					else {
						return null;
					}
				}
				return drops;
			
			case SLIME:
				for (String dropList : newDrop) {
					Random rand = new Random();
					String[] s = dropList.split(";");
					
					if (s.length == 6) {
						String item = s[0];
						String style= "";
						String effect = "";
						String effectLevel= "";
						String amount = s[1];
						String rate = s[2];
						String sizeRange = s[3];
						String name = s[4];
						String lore = s[5];
						List<String> itemLore;
						int id = 0;
						int num = 100;
						int den = 100;
						int amt = 1;
						short color = 0;
						int effectID = 0;
						int effectLevelID = 0;
						int lsize = 4;
						int usize = 12;

						Slime slime = (Slime) entity;
						int size = slime.getSize(); 
					
						if (item.contains("-")){
							String[] split = item.split("-");
							if (split.length == 3){
								item = split[0];
								effect = split[1];
								effectLevel = split[2];
							}
						} 
						if (item.contains(":")){
							String[] split = item.split(":");
							if (split.length == 2){
								item = split[0];
								style = split[1];
							}
						}
						if (amount.contains("-")){
							int lowerAmount;
							int upperAmount;
							String[] split = amount.split("-");
							String lAmount = split[0];
							String uAmount = split[1];
							try {
								lowerAmount = Integer.parseInt(lAmount);
								upperAmount = Integer.parseInt(uAmount);
							} catch (Exception e) {
								lowerAmount = 1;
								upperAmount = 1;
							}
							amt = rand.nextInt(upperAmount - lowerAmount + 1) + lowerAmount - 1;
							amount = String.valueOf(amt);
						}
						if (rate.contains("/")){
							String[] split = rate.split("/");
							if (split.length == 2){
								try {
									num = Integer.parseInt(split[0]);
									den = Integer.parseInt(split[1]);
								} catch (Exception e) {
									num = 100;
									den = 100;
								}
							}
						}
						
						if (sizeRange.contains("-")) {
							String[] split = sizeRange.split("-");
							if (split.length == 2){
								try {
									lsize = Integer.parseInt(split[0]);
									usize = Integer.parseInt(split[1]);
								} catch (Exception e) {
									num = 5;
									den = 12;
								}
							}
						} else {
							try {
								lsize = Integer.parseInt(sizeRange);
								usize = Integer.parseInt(sizeRange);
							} catch (Exception e) {
								lsize = 5;
								usize = 5;
							}
						}

						try {
							id = Integer.parseInt(item);
							effectID = Integer.parseInt(effect);
							effectLevelID = Integer.parseInt(effectLevel);
							color = Short.parseShort(style);
							amt = Integer.parseInt(amount);
						} catch (Exception e) {
							
						}
						int randNum = rand.nextInt(den);
						
						if (name != null) {
							name = ChatColor.translateAlternateColorCodes('&', name);
						}
						
						if (lore != null) {
							lore = ChatColor.translateAlternateColorCodes('&', lore);
						}
						
						itemLore = Arrays.asList(lore);
						
						if ((lsize <= size) && (size <= usize)) {
							if (num >= randNum){
								ItemStack newItem = new ItemStack(id, amt, color);
								ItemMeta itemMeta = newItem.getItemMeta();
								
								itemMeta.setDisplayName(name);
								itemMeta.setLore(itemLore);
								newItem.setItemMeta(itemMeta);
								
								newItem.setDurability(color);
								if ((effectID == 0) || (effectLevelID == 0)) {
									
								} else if ((effectLevelID >= Enchantment.getById(effectID).getStartLevel()) && (effectLevelID <= Enchantment.getById(effectID).getMaxLevel())) {
									Enchantment enchantment = new EnchantmentWrapper(effectID);
									newItem.addEnchantment(enchantment, effectLevelID);
								} else {
								
								}
								
								drops.add(newItem);
							}
						}
					} else {
						return null;
					}
				}
				return drops;
				
			case MAGMA_CUBE:
				for (String dropList : newDrop) {
					Random rand = new Random();
					String[] s = dropList.split(";");
					
					if (s.length == 6) {
						String item = s[0];
						String style= "";
						String effect = "";
						String effectLevel= "";
						String amount = s[1];
						String rate = s[2];
						String sizeRange = s[3];
						String name = s[4];
						String lore = s[5];
						List<String> itemLore;
						int id = 0;
						int num = 100;
						int den = 100;
						int amt = 1;
						short color = 0;
						int effectID = 0;
						int effectLevelID = 0;
						int lsize = 4;
						int usize = 12;
						
						MagmaCube magmacube = (MagmaCube) entity;
						int size = magmacube.getSize();
					
						if (item.contains("-")){
							String[] split = item.split("-");
							if (split.length == 3){
								item = split[0];
								effect = split[1];
								effectLevel = split[2];
							}
						} 
						if (item.contains(":")){
							String[] split = item.split(":");
							if (split.length == 2){
								item = split[0];
								style = split[1];
							}
						}
						if (amount.contains("-")){
							int lowerAmount;
							int upperAmount;
							String[] split = amount.split("-");
							String lAmount = split[0];
							String uAmount = split[1];
							try {
								lowerAmount = Integer.parseInt(lAmount);
								upperAmount = Integer.parseInt(uAmount);
							} catch (Exception e) {
								lowerAmount = 1;
								upperAmount = 1;
							}
							amt = rand.nextInt(upperAmount - lowerAmount + 1) + lowerAmount - 1;
							amount = String.valueOf(amt);
						}
						if (rate.contains("/")){
							String[] split = rate.split("/");
							if (split.length == 2){
								try {
									num = Integer.parseInt(split[0]);
									den = Integer.parseInt(split[1]);
								} catch (Exception e) {
									num = 100;
									den = 100;
								}
							}
						}
						
						if (sizeRange.contains("-")) {
							String[] split = sizeRange.split("-");
							if (split.length == 2){
								try {
									lsize = Integer.parseInt(split[0]);
									usize = Integer.parseInt(split[1]);
								} catch (Exception e) {
									num = 5;
									den = 12;
								}
							}
						} else {
							try {
								lsize = Integer.parseInt(sizeRange);
								usize = Integer.parseInt(sizeRange);
							} catch (Exception e) {
								lsize = 5;
								usize = 5;
							}
						}

						try {
							id = Integer.parseInt(item);
							effectID = Integer.parseInt(effect);
							effectLevelID = Integer.parseInt(effectLevel);
							color = Short.parseShort(style);
							amt = Integer.parseInt(amount);
						} catch (Exception e) {
							
						}
						int randNum = rand.nextInt(den);
						
						if (name != null) {
							name = ChatColor.translateAlternateColorCodes('&', name);
						}
						
						if (lore != null) {
							lore = ChatColor.translateAlternateColorCodes('&', lore);
						}
						
						itemLore = Arrays.asList(lore);
						
						if ((lsize <= size) && (size <= usize)) {
							if (num >= randNum){
								ItemStack newItem = new ItemStack(id, amt, color);
								ItemMeta itemMeta = newItem.getItemMeta();
								
								itemMeta.setDisplayName(name);
								itemMeta.setLore(itemLore);
								newItem.setItemMeta(itemMeta);
								
								newItem.setDurability(color);
								if ((effectID == 0) || (effectLevelID == 0)) {
									
								} else if ((effectLevelID >= Enchantment.getById(effectID).getStartLevel()) && (effectLevelID <= Enchantment.getById(effectID).getMaxLevel())) {
									Enchantment enchantment = new EnchantmentWrapper(effectID);
									newItem.addEnchantment(enchantment, effectLevelID);
								} else {
									
								}
								
								drops.add(newItem);
							}
						}
					} else {
						return null;
					}
				}
				return drops;
				
			default:
				break;
		}
		return drops;
	}
}
