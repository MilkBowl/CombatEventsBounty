package net.mcbat.MobBounty.Commands;

import java.util.Iterator;
import java.util.List;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.Colors;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mbwm implements CommandExecutor {
	private final MobBounty _plugin;

	public mbwm(MobBounty plugin) {
		_plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)arg0, "MobBounty.Admin.mbwm")) || (_plugin.Permissions == null && arg0.isOp())) {
			this.mbwmCommand(arg0, arg2, arg3);
		}
		else {
			arg0.sendMessage(Colors.Red+"You do no have access to that command.");
		}

		return true;
	}

	private void mbwmCommand(CommandSender sender, String command, String[] args) {
		if (args.length == 2) {
			if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				World world = _plugin.getServer().getWorld(args[0]);
			
				if (world != null) {
					Double amount = Double.parseDouble(args[1]);
					
					_plugin.getConfig().setWorldMultiplier(world.getName(), amount);
					sender.sendMessage(Colors.DarkGreen+"Multiplier for the "+Colors.White+world.getName().toLowerCase()+Colors.DarkGreen+" world has been changed to "+Colors.White+"x"+amount+Colors.DarkGreen+".");
				}
				else 
					this.commandUsage(sender, command);
			}
			else
				this.commandUsage(sender, command);
		}
		else
			this.commandUsage(sender, command);
	}
	
	private void commandUsage(CommandSender sender, String command) {
		List<World> worlds = _plugin.getServer().getWorlds();
		Iterator<World> worldIterator = worlds.iterator();
		
		String worldsStr = Colors.Gray+"Worlds: ";
		
		while (worldIterator.hasNext()) {
			World world = worldIterator.next();
			
			worldsStr += world.getName();
			worldsStr += " ";
		}
		
		sender.sendMessage(Colors.Red+"Usage: /"+command+" [world] <amount>");
		sender.sendMessage(worldsStr);
	}

}
