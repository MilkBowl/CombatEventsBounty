package net.mcbat.MobBounty.Commands;

import java.util.Iterator;
import java.util.List;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.Colors;
import net.mcbat.MobBounty.Utils.CreatureID;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mbwr implements CommandExecutor {
	private final MobBounty _plugin;

	public mbwr(MobBounty plugin) {
		_plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)arg0, "MobBounty.Admin.mbwr")) || (_plugin.Permissions == null && arg0.isOp())) {
			this.mbwrCommand(arg0, arg2, arg3);
		}
		else {
			arg0.sendMessage(Colors.Red+"You do no have access to that command.");
		}

		return true;
	}

	private void mbwrCommand(CommandSender sender, String command, String[] args) {
		if (args.length == 3) {
			if (args[2].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				World world = _plugin.getServer().getWorld(args[0]);
		
				if (world != null) {
					CreatureID mob = CreatureID.fromName(args[1]);
		
					if (mob != null) {
						Double amount = Double.parseDouble(args[2]);
						_plugin.getConfig().setReward(world.getName(), mob, amount);
						sender.sendMessage(Colors.DarkGreen+"Reward/Fine for mob "+Colors.White+mob.getName().toLowerCase()+Colors.DarkGreen+" in world "+Colors.White+world.getName().toLowerCase()+Colors.DarkGreen+" has been changed to "+Colors.White+amount+Colors.DarkGreen+".");
					}
					else
						this.commandUsage(sender, command);
				}
				else 
					this.commandUsage(sender, command);
			}
			else if (args[2].equalsIgnoreCase("default")) {
				World world = _plugin.getServer().getWorld(args[0]);
				
				if (world != null) {
					CreatureID mob = CreatureID.fromName(args[1]);
		
					if (mob != null) {
						_plugin.getConfig().removeReward(world.getName(), mob);
						sender.sendMessage(Colors.DarkGreen+"Reward/Fine for mob "+Colors.White+mob.getName().toLowerCase()+Colors.DarkGreen+" in world "+Colors.White+world.getName().toLowerCase()+Colors.DarkGreen+" has been reset to default.");
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
		
		sender.sendMessage(Colors.Red+"Usage: /"+command+" [world] [mob] <amount|default>");
		sender.sendMessage(worldsStr);
		sender.sendMessage(Colors.Gray+"Mobs: Zombie PigZombie Skeleton Slime Chicken Pig Monster Spider Creeper ElectrifiedCreeper Ghast Squid Giant Cow Sheep Wolf TamedWolf");
	}
}
