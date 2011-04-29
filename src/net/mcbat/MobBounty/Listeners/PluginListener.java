package net.mcbat.MobBounty.Listeners;

import net.mcbat.MobBounty.MobBounty;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import cosine.boseconomy.BOSEconomy;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijikokun.bukkit.Permissions.Permissions;

public class PluginListener extends ServerListener {
	private static MobBounty _plugin = null;
		
	public PluginListener(MobBounty plugin) {
		if (_plugin == null) _plugin = plugin;
	}
	
	public void onPluginEnable(PluginEnableEvent event) {
		if (_plugin != null) {
			if (_plugin.iConomy == null && _plugin.BOSEconomy == null) {
				Plugin iConomyPlugin = _plugin.getServer().getPluginManager().getPlugin("iConomy");
				Plugin BOSEconomyPlugin = _plugin.getServer().getPluginManager().getPlugin("BOSEconomy");
			
				if (iConomyPlugin != null) {
					if (iConomyPlugin.isEnabled()) {
						_plugin.iConomy = (iConomy) iConomyPlugin;
						_plugin.getLogger().info("[MobBounty] hooked into iConomy.");
					}
				}
				else if (BOSEconomyPlugin != null) {
					if (BOSEconomyPlugin.isEnabled()) {
						_plugin.BOSEconomy = (BOSEconomy) BOSEconomyPlugin;
						_plugin.getLogger().info("[MobBounty] hooked into BOSEconomy.");
					}
				}
			}
			
			if (_plugin.Permissions == null) {
				Plugin Permissions = _plugin.getServer().getPluginManager().getPlugin("Permissions");
				
				if (Permissions != null) {
					if (Permissions.isEnabled()) {
						_plugin.Permissions = ((Permissions) Permissions).getHandler();
						_plugin.getLogger().info("[MobBounty] hooked into Permissions/GroupManager.");
					}
				}
			}
		}
	}
}
