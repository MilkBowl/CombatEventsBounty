// MobBounty
// Version 1.01
//
// Created By Steven Mattera

package com.stevenmattera.MobBounty;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.stevenmattera.MobBounty.Commands.mb;
import com.stevenmattera.MobBounty.Commands.mbmulti;
import com.stevenmattera.MobBounty.Commands.mbreward;
import com.stevenmattera.MobBounty.Listeners.DeathListener;
import com.stevenmattera.MobBounty.Listeners.PluginListener;

public class Main extends JavaPlugin {
	private final DeathListener _deathListener = new DeathListener(this);
	private final PluginListener _pluginListener = new PluginListener(this);
	
	private final mb _mb = new mb(this);
	private final mbmulti _mbmulti = new mbmulti(this);
	private final mbreward _mbreward = new mbreward(this);
	
	private final Logger _logger = Logger.getLogger("Minecraft");

	private Config _config;
	
	public iConomy iConomy = null;
	public PermissionHandler Permissions = null;

	// ----
	// ==== Overridden Methods ====
	// ----
	
	@Override
	public void onEnable() {
		_logger.info("[MobBounty] v"+this.getDescription().getVersion()+" (Helium) loaded.");
		_logger.info("[MobBounty] Developed by: [Mattera, Steven (IchigoKyger)].");
		
		_config = new Config(this);
		
		getCommand("mb").setExecutor(_mb);
		getCommand("mbmulti").setExecutor(_mbmulti);
		getCommand("mbreward").setExecutor(_mbreward);
		
		if (iConomy == null) {
			Plugin iConomyPlugin = this.getServer().getPluginManager().getPlugin("iConomy");
		
			if (iConomyPlugin != null) {
				if (iConomyPlugin.isEnabled()) {
					iConomy = (iConomy) iConomyPlugin;
					this.getLogger().info("[MobBounty] hooked into iConomy.");
				}
			}
		}
		
		if (Permissions == null) {
			Plugin PermissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
			
			if (PermissionsPlugin != null) {
				if (PermissionsPlugin.isEnabled()) {
					Permissions = ((Permissions) PermissionsPlugin).getHandler();
					this.getLogger().info("[MobBounty] hooked into Permissions/GroupManager.");
				}
			}
		}
		
		this.getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DAMAGE, _deathListener, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DEATH, _deathListener, Priority.Monitor, this);

		if (iConomy == null || Permissions == null)
			this.getServer().getPluginManager().registerEvent(Event.Type.PLUGIN_ENABLE, _pluginListener, Priority.Monitor, this);
	}

	@Override
	public void onDisable() {
		_config.saveConfig();

		_logger.info("[MobBounty] Plugin disabled.");
	}
	
	// ----
	// ==== Public Methods ====
	// ----
	
	public Config getConfig() {
		return _config;
	}
	
	public Logger getLogger() {
		return _logger;
	}
}
