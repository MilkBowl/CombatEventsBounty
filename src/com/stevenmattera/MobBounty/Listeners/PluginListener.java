package com.stevenmattera.MobBounty.Listeners;

import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.stevenmattera.MobBounty.Main;

public class PluginListener extends ServerListener {
	private static Main _plugin = null;
	
	public PluginListener(Main plugin) {
		if (_plugin == null) _plugin = plugin;
	}
	
	public void onPluginEnabled(PluginEvent event) {
		if (_plugin != null) {
			if (_plugin.iConomy == null) {
				Plugin iConomy = _plugin.getServer().getPluginManager().getPlugin("iConomy");
			
				if (iConomy != null) {
					if (iConomy.isEnabled()) {
						_plugin.iConomy = (iConomy) iConomy;
						_plugin.getLogger().info("[MobBounty] hooked into iConomy.");
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
