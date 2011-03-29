package com.stevenmattera.MobBounty.Commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.stevenmattera.MobBounty.Main;
import com.stevenmattera.MobBounty.Utils.Colors;


public class mbmulti implements CommandExecutor {
	private final Main _plugin;
	
	public mbmulti(Main plugin) {
		_plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (_plugin.Permissions != null && _plugin.Permissions.has((Player) sender, "MobBounty.mbmulti")) {
			this.mbmultiCommand(sender, args);
		}
		else if (_plugin.Permissions == null && sender.isOp()) {
			this.mbmultiCommand(sender, args);
		}
		else {
			sender.sendMessage(Colors.Red+"You do no have access to that command.");
		}
		
		return true;
	}
	
	private void mbmultiCommand(CommandSender sender, String[] args) {
		if (args.length == 2) {
			if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				Double amount = Double.parseDouble(args[1]);
				
				if (amount >= 0) {
					World world = _plugin.getServer().getWorld(args[0]);
					
					if (world != null) {
						_plugin.getConfig().setWorldMultiplier(world.getName(), amount);
					}
					else {
						sender.sendMessage(Colors.Red+"Unknown world \""+Colors.White+args[0]+Colors.Red+"\".");				
					}
				}
				else {
					sender.sendMessage(Colors.Red+"Amount must be greater than or equal to 0.");				
				}
			}
		}
		else {
			sender.sendMessage(Colors.Red+"Usage: /mbmulti <world> <amount>");
		}
	}
}
