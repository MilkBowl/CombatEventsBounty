package com.stevenmattera.MobBounty.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

import com.stevenmattera.MobBounty.Main;
import com.stevenmattera.MobBounty.Utils.Colors;


public class mbreward implements CommandExecutor {
	private final Main _plugin;
	
	public mbreward(Main plugin) {
		_plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (_plugin.Permissions != null && _plugin.Permissions.has((Player) sender, "MobBounty.mbreward")) {
			this.mbrewardCommand(sender, label, args);
		}
		else if (_plugin.Permissions == null && sender.isOp()) {
			this.mbrewardCommand(sender, label, args);
		}
		else {
			sender.sendMessage(Colors.Red+"You do no have access to that command.");
		}
		
		return true;
	}

	private void mbrewardCommand(CommandSender sender, String label, String[] args) {
		if (args.length == 2) {
			if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				Double amount = Double.parseDouble(args[1]);
				
				if (amount >= 0) {
					if (args[0].equalsIgnoreCase("chicken")) {
						_plugin.getConfig().setReward(CreatureType.CHICKEN, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("cow")) {
						_plugin.getConfig().setReward(CreatureType.COW, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("creeper")) {
						_plugin.getConfig().setReward(CreatureType.CREEPER, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("ghast")) {
						_plugin.getConfig().setReward(CreatureType.GHAST, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("pig")) {
						_plugin.getConfig().setReward(CreatureType.PIG, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("pigman")) {
						_plugin.getConfig().setReward(CreatureType.PIG_ZOMBIE, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("sheep")) {
						_plugin.getConfig().setReward(CreatureType.SHEEP, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("skeleton")) {
						_plugin.getConfig().setReward(CreatureType.SKELETON, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("slime")) {
						_plugin.getConfig().setReward(CreatureType.SLIME, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("spider")) {
						_plugin.getConfig().setReward(CreatureType.SPIDER, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("squid")) {
						_plugin.getConfig().setReward(CreatureType.SQUID, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else if (args[0].equalsIgnoreCase("zombie")) {
						_plugin.getConfig().setReward(CreatureType.ZOMBIE, amount);
						sender.sendMessage(Colors.DarkGreen+"Mob \""+Colors.White+args[0]+Colors.DarkGreen+"\" has been changed to amount: "+Colors.White+amount);
					}
					else {
						sender.sendMessage(Colors.Red+"Unknown mob \""+Colors.White+args[0]+Colors.Red+"\"");
					}	
				}
				else {
					sender.sendMessage(Colors.Red+"Amount must be greater than or equal to 0.");				
				}
			}
			else {
				sender.sendMessage(Colors.Red+"Unknown amount \""+Colors.White+args[1]+Colors.Red+"\"");				
			}		
		}
		else {
			sender.sendMessage(Colors.Red+"Usage: /"+label+" <mob> <amount>");
		}
	}
}
