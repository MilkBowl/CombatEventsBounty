// MobBounty
// Version 1.0
//
// Created By Steven Mattera

package com.stevenmattera.MobBounty.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

import com.nijiko.coelho.iConomy.iConomy;
import com.stevenmattera.MobBounty.Main;
import com.stevenmattera.MobBounty.Utils.Colors;

public class mb implements CommandExecutor {
	private final Main _plugin;
	
	// ----
	// ==== Constructor Method ====
	// ----
	
	public mb(Main plugin) {
		_plugin = plugin;
	}
	
	// ----
	// ==== CommandExecutor Methods ====
	// ----
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (_plugin.Permissions != null && _plugin.Permissions.has((Player) sender, "MobBounty.mb")) {
			this.mbCommand(sender);
		}
		else if (_plugin.Permissions == null) {
			this.mbCommand(sender);
		}
		else {
			sender.sendMessage(Colors.Red+"You do no have access to that command.");
		}
		
		return true;
	}
	
	// ----
	// ==== Private Methods ====
	// ----
	
	private void mbCommand(CommandSender sender) {
		double multiplier = _plugin.getConfig().getWorldMultiplier(((Player) sender).getWorld());
		
		if (_plugin.getConfig().getReward(CreatureType.CHICKEN) > 0)
			sender.sendMessage("\u00A7f"+"Chicken"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.CHICKEN)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");

		if (_plugin.getConfig().getReward(CreatureType.COW) > 0)
			sender.sendMessage("\u00A7f"+"Cow"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.COW)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");

		if (_plugin.getConfig().getReward(CreatureType.CREEPER) > 0)
			sender.sendMessage("\u00A7f"+"Creeper"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.CREEPER)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");

		if (_plugin.getConfig().getReward(CreatureType.GHAST) > 0)
			sender.sendMessage("\u00A7f"+"Ghast"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.GHAST)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");
		
		if (_plugin.getConfig().getReward(CreatureType.PIG) > 0)
			sender.sendMessage("\u00A7f"+"Pig"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.PIG)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");

		if (_plugin.getConfig().getReward(CreatureType.PIG_ZOMBIE) > 0)
			sender.sendMessage("\u00A7f"+"Pigman"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.PIG_ZOMBIE)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");
		
		if (_plugin.getConfig().getReward(CreatureType.SHEEP) > 0)
			sender.sendMessage("\u00A7f"+"Sheep"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.SHEEP)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");
		
		if (_plugin.getConfig().getReward(CreatureType.SKELETON) > 0)
			sender.sendMessage("\u00A7f"+"Skeleton"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.SKELETON)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");

		if (_plugin.getConfig().getReward(CreatureType.SLIME) > 0)
			sender.sendMessage("\u00A7f"+"Slime"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.SLIME)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");

		if (_plugin.getConfig().getReward(CreatureType.SPIDER) > 0)
			sender.sendMessage("\u00A7f"+"Spider"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.SPIDER)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");

		if (_plugin.getConfig().getReward(CreatureType.SQUID) > 0)
			sender.sendMessage("\u00A7f"+"Squid"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.SQUID)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");

		if (_plugin.getConfig().getReward(CreatureType.ZOMBIE) > 0)
			sender.sendMessage("\u00A7f"+"Zombie"+"\u00A72"+" - "+_plugin.getConfig().getReward(CreatureType.ZOMBIE)*multiplier+" "+iConomy.getBank().getCurrency()+"(s)");
	}
}
