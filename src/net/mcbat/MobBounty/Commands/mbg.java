package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.Main;
import net.mcbat.MobBounty.Utils.Colors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mbg implements CommandExecutor {
	private final Main _plugin;

	public mbg(Main plugin) {
		_plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)arg0, "MobBounty.Admin.mbg")) || (_plugin.Permissions == null && arg0.isOp())) {
			this.mbgCommand(arg0, arg2, arg3);
		}
		else {
			arg0.sendMessage(Colors.Red+"You do no have access to that command.");
		}

		return true;
	}

	private void mbgCommand(CommandSender sender, String command, String[] args) {
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("true")) {
				if (args[0].equalsIgnoreCase("envmulti")) {
					_plugin.getConfig().setGeneralSetting("useEnvironmentMultiplier", true);
					sender.sendMessage(Colors.DarkGreen+"General setting "+Colors.White+"useEnvironmentMultiplier"+Colors.DarkGreen+" has been changed to "+Colors.White+"True"+Colors.DarkGreen+".");
				}
				else if (args[0].equalsIgnoreCase("timemulti")) {
					_plugin.getConfig().setGeneralSetting("useTimeMultiplier", true);
					sender.sendMessage(Colors.DarkGreen+"General setting "+Colors.White+"useTimeMultiplier"+Colors.DarkGreen+" has been changed to "+Colors.White+"True"+Colors.DarkGreen+".");
				}
				else if (args[0].equalsIgnoreCase("worldmulti")) {
					_plugin.getConfig().setGeneralSetting("useWorldMultiplier", true);
					sender.sendMessage(Colors.DarkGreen+"General setting "+Colors.White+"useWorldMultiplier"+Colors.DarkGreen+" has been changed to "+Colors.White+"True"+Colors.DarkGreen+".");
				}
				else
					this.commandUsage(sender, command);
			}
			else if (args[1].equalsIgnoreCase("false")) {
				if (args[0].equalsIgnoreCase("envmulti")) {
					_plugin.getConfig().setGeneralSetting("useEnvironmentMultiplier", false);
					sender.sendMessage(Colors.DarkGreen+"General setting "+Colors.White+"useEnvironmentMultiplier"+Colors.DarkGreen+" has been changed to "+Colors.White+"False"+Colors.DarkGreen+".");
				}
				else if (args[0].equalsIgnoreCase("timemulti")) {
					_plugin.getConfig().setGeneralSetting("useTimeMultiplier", false);
					sender.sendMessage(Colors.DarkGreen+"General setting "+Colors.White+"useTimeMultiplier"+Colors.DarkGreen+" has been changed to "+Colors.White+"False"+Colors.DarkGreen+".");
				}
				else if (args[0].equalsIgnoreCase("worldmulti")) {
					_plugin.getConfig().setGeneralSetting("useWorldMultiplier", false);
					sender.sendMessage(Colors.DarkGreen+"General setting "+Colors.White+"useWorldMultiplier"+Colors.DarkGreen+" has been changed to "+Colors.White+"False"+Colors.DarkGreen+".");
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
		sender.sendMessage(Colors.Red+"Usage: /"+command+" [property] <true|false>");
		sender.sendMessage(Colors.Gray+"Property: EnvMulti TimeMulti WorldMulti");
	}
}
