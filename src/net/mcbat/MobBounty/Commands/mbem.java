package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.Colors;

import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mbem implements CommandExecutor {
	private final MobBounty _plugin;

	public mbem(MobBounty plugin) {
		_plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)arg0, "MobBounty.Admin.mbem")) || (_plugin.Permissions == null && arg0.isOp())) {
			this.mbemCommand(arg0, arg2, arg3);
		}
		else {
			arg0.sendMessage(Colors.Red+"You do no have access to that command.");
		}

		return true;
	}

	private void mbemCommand(CommandSender sender, String command, String[] args) {
		if (args.length == 2) {
			if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				Double amount = Double.parseDouble(args[1]);
				
				if (args[0].equalsIgnoreCase("nether")) {
					_plugin.getConfig().setEnvironmentMultiplier(Environment.NETHER, amount);
					sender.sendMessage(Colors.DarkGreen+"Multiplier for the "+Colors.White+"nether"+Colors.DarkGreen+" environment has been changed to "+Colors.White+"x"+amount+Colors.DarkGreen+".");
				}
				else if (args[0].equalsIgnoreCase("normal")) {
					_plugin.getConfig().setEnvironmentMultiplier(Environment.NORMAL, amount);
					sender.sendMessage(Colors.DarkGreen+"Multiplier for the "+Colors.White+"normal"+Colors.DarkGreen+" environment has been changed to "+Colors.White+"x"+amount+Colors.DarkGreen+".");
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
		sender.sendMessage(Colors.Red+"Usage: /"+command+" [nether|normal] <amount>");
	}
}
