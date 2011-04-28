package net.mcbat.MobBounty.Commands;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.Colors;
import net.mcbat.MobBounty.Utils.CreatureID;
import net.mcbat.MobBounty.Utils.Time;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nijiko.coelho.iConomy.iConomy;

public class mb implements CommandExecutor {
	private final MobBounty _plugin;
	
	public mb(MobBounty plugin) {
		_plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if ((_plugin.Permissions != null && _plugin.Permissions.has((Player) sender, "MobBounty.mb")) || (_plugin.Permissions == null)) {
			this.mbCommand(sender);
		}
		else {
			sender.sendMessage(Colors.Red+"You do no have access to that command.");
		}

		return true;
	}
	
	private void mbCommand(CommandSender sender) {
		if (!(sender instanceof Player))
			return;
		
		Player player = (Player)sender;
		World world = player.getWorld();
		
		double multiplier = 1.0;
		
		if (_plugin.getConfig().getGeneralSetting("useEnvironmentMultiplier"))
			multiplier *= _plugin.getConfig().getEnvironmentMultiplier(world.getEnvironment());
		if (_plugin.getConfig().getGeneralSetting("useTimeMultiplier"))
			multiplier *= _plugin.getConfig().getTimeMultiplier(Time.getTimeOfDay(world.getTime()));			
		if (_plugin.getConfig().getGeneralSetting("useWorldMultiplier"))
			multiplier *= _plugin.getConfig().getWorldMultiplier(world.getName());
		
		for (CreatureID creature : CreatureID.values()) {
			double reward = _plugin.getConfig().getReward(world.getName(), creature) * multiplier;
			
			if (reward > 0.0)
				player.sendMessage(Colors.DarkGreen+creature.getName()+" : "+Colors.White+(reward)+" "+iConomy.getBank().getCurrency()+"(s)");
			else if (reward < 0.0)
				player.sendMessage(Colors.DarkRed+creature.getName()+" : "+Colors.White+(reward)+" "+iConomy.getBank().getCurrency()+"(s)");
		}
	}
}
