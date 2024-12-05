package me.Mastervrunner.MoveEnchantClean;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p;
		if (sender instanceof Player) {
			p = (Player) sender;

			if (label.equalsIgnoreCase("moveenchant") && p.getInventory().getItemInMainHand().getType() != Material.ENCHANTED_BOOK) {
				if (sender instanceof Player) {
					ItemStack heldItems = p.getInventory().getItemInMainHand();
					ItemStack offHeldItem = p.getInventory().getItemInOffHand();
					ItemStack EnchBook = new ItemStack(Material.ENCHANTED_BOOK);
					if(p.getInventory().getItemInOffHand().getType() == Material.BOOK) {
						
						for (Enchantment enchantment : heldItems.getEnchantments().keySet()) {
							ArrayList<Enchantment> Enchants = new ArrayList<Enchantment>();
							Enchants.add(enchantment);
						
						}
						
						ArrayList<Enchantment> Enchants = new ArrayList<Enchantment>();
						for (Enchantment enchantment : heldItems.getEnchantments().keySet()) {
							Enchants.add(enchantment); //heldItem.removeEnchantment(enchantment);
							
						}
						
						for (int i = 0; i < Enchants.size(); i++) {
							if(offHeldItem.containsEnchantment(Enchants.get(i))) {
								//offHeldItem.removeEnchantment(Enchants.get(i));
							}
						}
						
					}
					
					Enchantment enchToRemove = Enchantment.DIG_SPEED;
					
					if(args.length == 0) {
						if(p.getInventory().getItemInOffHand().getType() == Material.BOOK) {
							
							
		
							for (Enchantment enchantment : p.getInventory().getItemInMainHand().getEnchantments().keySet()) {
								ArrayList<Enchantment> Enchants = new ArrayList<Enchantment>();
								Enchants.add(enchantment);

								ArrayList<Enchantment> Enchants2 = new ArrayList<Enchantment>();
								
								for (int i = 0; i < Enchants.size(); i++) {
									int enchLevel = p.getInventory().getItemInMainHand().getEnchantmentLevel(enchantment);
								
									EnchantmentStorageMeta meta = (EnchantmentStorageMeta) EnchBook.getItemMeta();
									meta.addStoredEnchant(enchantment, enchLevel, true);
		
									EnchBook.setItemMeta(meta);
		
								}
							}
							p.getInventory().setItemInOffHand(EnchBook);
							
						}else {
							//If any enchant in getiteminoffhand is the same as any enchantment in getiteminmainhand
							//Add the unsafe enchantments normally
							
							//If the enchant level is at least 1 below or just less than the max normall enchantment level, then continue, else stop.
							
							//Add a variable with the enchantment name/type, and the enchantment level
							//Then remove the enchantment that is the same in both hands
							//Then set the variable with the enchantment name/type to have +1 enchant level
							//Then add the enchantment.
							
							p.getInventory().getItemInOffHand().addUnsafeEnchantments(p.getInventory().getItemInMainHand().getEnchantments());
							
							ArrayList<Enchantment> OffHandEnchants = new ArrayList<Enchantment>();
							ArrayList<Enchantment> OnHandEnchants = new ArrayList<Enchantment>();
							
							for (Enchantment enchantment1 : p.getInventory().getItemInOffHand().getEnchantments().keySet()) {
								OffHandEnchants.add(enchantment1);
							}
							
							for (Enchantment enchantment2 : p.getInventory().getItemInMainHand().getEnchantments().keySet()) {
								OnHandEnchants.add(enchantment2);
							}
							
							for(int OffEnchant = 0; OffEnchant < OffHandEnchants.size(); OffEnchant++) {
								//Test every off enchant, and see if it matches with anything in on enchant
								for(int OnEnchant = 0; OnEnchant < OnHandEnchants.size(); OnEnchant++) {
									for (Enchantment enchantment2 : p.getInventory().getItemInMainHand().getEnchantments().keySet()) {
										if(p.getInventory().getItemInOffHand().containsEnchantment(enchantment2)) {//;//'' '
											//Set to OnHandEnchants instead of OnEnchant. Oops!
											Enchantment MutualEnchant = OffHandEnchants.get(OffEnchant);
											int MutualEnchantLevel = p.getInventory().getItemInOffHand().getEnchantmentLevel(MutualEnchant);
											if(MutualEnchantLevel < MutualEnchant.getMaxLevel()) {
												p.getInventory().getItemInOffHand().removeEnchantment(MutualEnchant);
												int MutualEnchantLevelPlus1 = MutualEnchantLevel + 1;
												p.getInventory().getItemInOffHand().addUnsafeEnchantment(MutualEnchant, MutualEnchantLevelPlus1);
											}
											
										}
									}
									
								}
							}
						}
					}
					
					ItemStack heldItem = p.getInventory().getItemInMainHand();
					
					if(args.length == 0) {
						for (Enchantment enchantment : heldItem.getEnchantments().keySet()) {
				            heldItem.removeEnchantment(enchantment);
				        }
					} else if(args.length == 1 && heldItem.containsEnchantment(enchToRemove)) {
						heldItem.removeEnchantment(enchToRemove);
					}
	
				} else {
					sender.sendMessage(ChatColor.RED + "You have to be a player to run this, lol");
					return true;
				}
				
			} else {
				if(p.getInventory().getItemInMainHand().getType() == Material.ENCHANTED_BOOK && label.equalsIgnoreCase("moveenchant") || p.getInventory().getItemInMainHand().getType() == Material.ENCHANTED_BOOK && label.equalsIgnoreCase("tranferEnchant")) {
					p.sendMessage(ChatColor.DARK_RED + "You have to add enchantments from books with an anvil!");
					p.sendMessage(ChatColor.GOLD + "Tip: To save XP, instead of adding the enchanted books enchantment(s) to a weapon that already has enchantments, add the enchant to a weapon with no enchantments, then use the move enchant command to move the enchantment from the weapon you just enchanted, to a weapon that already has enchantments on it!");
				}
			}
		}
		return false;
		
	}
	

}
