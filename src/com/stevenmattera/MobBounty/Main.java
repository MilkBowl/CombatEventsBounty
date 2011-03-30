// MobBounty
// Version 1.0
//
// Created By Steven Mattera

package com.stevenmattera.MobBounty;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.permissions.PermissionHandler;
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
		
		this.getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DAMAGED, _deathListener, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DEATH, _deathListener, Priority.Monitor, this);
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
