package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.Main;
import net.mcbat.MobBounty.Utils.Colors;
import net.mcbat.MobBounty.Utils.Time;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mbtm implements CommandExecutor {
	private final Main _plugin;
	
	public mbtm(Main plugin) {
		_plugin = plugin;		
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)arg0, "MobBounty.Admin.mbtm")) || (_plugin.Permissions == null && arg0.isOp())) {
			this.mbtmCommand(arg0, arg2, arg3);
		}
		else {
			arg0.sendMessage(Colors.Red+"You do no have access to that command.");
		}

		return true;
	}

	private void mbtmCommand(CommandSender sender, String command, String[] args) {
		if (args.length == 2) {
			if (args[1].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
				Double amount = Double.parseDouble(args[1]);
				
				if (args[0].equalsIgnoreCase("day")) {
					_plugin.getConfig().setTimeMultiplier(Time.Day, amount);
					sender.sendMessage(Colors.DarkGreen+"Multiplier during the "+Colors.White+"day"+Colors.DarkGreen+" has been changed to "+Colors.White+"x"+amount+Colors.DarkGreen+".");
				}
				else if (args[0].equalsIgnoreCase("sunset")) {
					_plugin.getConfig().setTimeMultiplier(Time.Sunset, amount);
					sender.sendMessage(Colors.DarkGreen+"Multiplier during the "+Colors.White+"sunset"+Colors.DarkGreen+" has been changed to "+Colors.White+"x"+amount+Colors.DarkGreen+".");
				}
				else if (args[0].equalsIgnoreCase("night")) {
					_plugin.getConfig().setTimeMultiplier(Time.Night, amount);
					sender.sendMessage(Colors.DarkGreen+"Multiplier during the "+Colors.White+"night"+Colors.DarkGreen+" has been changed to "+Colors.White+"x"+amount+Colors.DarkGreen+".");
				}
				else if (args[0].equalsIgnoreCase("sunrise")) {
					_plugin.getConfig().setTimeMultiplier(Time.Sunrise, amount);
					sender.sendMessage(Colors.DarkGreen+"Multiplier during the "+Colors.White+"sunrise"+Colors.DarkGreen+" has been changed to "+Colors.White+"x"+amount+Colors.DarkGreen+".");
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
		sender.sendMessage(Colors.Red+"Usage: /"+command+" [day|sunset|night|sunrise] <amount>");
	}
	
}
