package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.Colors;
import net.mcbat.MobBounty.Utils.CreatureID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mbr implements CommandExecutor {
	private final MobBounty _plugin;

	public mbr(MobBounty plugin) {
		_plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)arg0, "MobBounty.Admin.mbr")) || (_plugin.Permissions == null && arg0.isOp())) {
			this.mbrCommand(arg0, arg2, arg3);
		}
		else {
			arg0.sendMessage(Colors.Red+"You do no have access to that command.");
		}

		return true;
	}

	private void mbrCommand(CommandSender sender, String command, String[] args) {
		if (args.length == 2) {
			if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				CreatureID mob = CreatureID.fromName(args[0]);
			
				if (mob != null) {
					Double amount = Double.parseDouble(args[1]);
					_plugin.getConfig().setDefaultReward(mob, amount);
					sender.sendMessage(Colors.DarkGreen+"Default reward/fine for mob "+Colors.White+mob.getName().toLowerCase()+Colors.DarkGreen+" has been changed to "+Colors.White+amount+Colors.DarkGreen+".");
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
		sender.sendMessage(Colors.Red+"Usage: /"+command+" [mob] <amount>");
		sender.sendMessage(Colors.Gray+"Mobs: Zombie PigZombie Skeleton Slime Chicken Pig Monster Spider Creeper ElectrifiedCreeper Ghast Squid Giant Cow Sheep Wolf TamedWolf");
	}
}
