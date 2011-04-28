package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.Colors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mbl implements CommandExecutor {
	private final MobBounty _plugin;
	
	public mbl(MobBounty plugin) {
		_plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player)arg0, "MobBounty.Admin.mbl")) || (_plugin.Permissions == null && arg0.isOp())) {
			_plugin.getConfig().loadConfig();
			arg0.sendMessage(Colors.DarkGreen+"MobBounty config has been loaded.");
		}
		else {
			arg0.sendMessage(Colors.Red+"You do no have access to that command.");
		}

		return true;
	}
}
