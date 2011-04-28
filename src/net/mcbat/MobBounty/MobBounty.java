package net.mcbat.MobBounty;

import java.util.logging.Logger;

import net.mcbat.MobBounty.Commands.mb;
import net.mcbat.MobBounty.Commands.mbem;
import net.mcbat.MobBounty.Commands.mbg;
import net.mcbat.MobBounty.Commands.mbl;
import net.mcbat.MobBounty.Commands.mbr;
import net.mcbat.MobBounty.Commands.mbs;
import net.mcbat.MobBounty.Commands.mbtm;
import net.mcbat.MobBounty.Commands.mbwm;
import net.mcbat.MobBounty.Commands.mbwr;
import net.mcbat.MobBounty.Config.ConfigManager;
import net.mcbat.MobBounty.Listeners.DeathListener;
import net.mcbat.MobBounty.Listeners.PluginListener;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import com.spikensbror.bukkit.mineconomy.MineConomy;

import BOSEconomy.cosine.boseconomy.BOSEconomy;


public class MobBounty extends JavaPlugin {
	private final DeathListener _deathListener = new DeathListener(this);
	private final PluginListener _pluginListener = new PluginListener(this);
	
	private final mb _mb = new mb(this);
	private final mbg _mbg = new mbg(this);
	private final mbl _mbl = new mbl(this);
	private final mbs _mbs = new mbs(this);
	private final mbr _mbr = new mbr(this);
	private final mbwr _mbwr = new mbwr(this);
	private final mbem _mbem = new mbem(this);
	private final mbtm _mbtm = new mbtm(this);
	private final mbwm _mbwm = new mbwm(this);
	
	private final Logger _logger = Logger.getLogger("Minecraft");

	private ConfigManager _config;
	
	public iConomy iConomy = null;
	public BOSEconomy BOSEconomy = null;
	public MineConomy MineConomy = null;
	public PermissionHandler Permissions = null;

	@Override
	public void onEnable() {
		_logger.info("[MobBounty] v"+this.getDescription().getVersion()+" (Carbon) loaded.");
		_logger.info("[MobBounty] Developed by: [Mattera, Steven (IchigoKyger)].");
		
		_config = new ConfigManager(this);
		_config.loadConfig();
		
		getCommand("mb").setExecutor(_mb);
		getCommand("mbg").setExecutor(_mbg);
		getCommand("mbl").setExecutor(_mbl);
		getCommand("mbs").setExecutor(_mbs);		
		getCommand("mbr").setExecutor(_mbr);
		getCommand("mbwr").setExecutor(_mbwr);
		getCommand("mbem").setExecutor(_mbem);
		getCommand("mbtm").setExecutor(_mbtm);
		getCommand("mbwm").setExecutor(_mbwm);
		
		if (iConomy == null && BOSEconomy == null && MineConomy == null) {
			Plugin iConomyPlugin = this.getServer().getPluginManager().getPlugin("iConomy");
					
			if (iConomyPlugin != null) {
				if (iConomyPlugin.isEnabled()) {
					iConomy = (iConomy) iConomyPlugin;
					this.getLogger().info("[MobBounty] hooked into iConomy.");
				}
			}
			else {
				Plugin BOSEconomyPlugin = this.getServer().getPluginManager().getPlugin("BOSEconomy");

				if (BOSEconomyPlugin != null) {
					if (BOSEconomyPlugin.isEnabled()) {
						BOSEconomy = (BOSEconomy) BOSEconomyPlugin;
						this.getLogger().info("[MobBounty] hooked into BOSEconomy.");
					}
				}
				else {
					Plugin MineConomyPlugin = this.getServer().getPluginManager().getPlugin("BOSEconomy");

					if (MineConomyPlugin != null) {
						if (MineConomyPlugin.isEnabled()) {
							MineConomy = (MineConomy) MineConomyPlugin;
							this.getLogger().info("[MobBounty] hooked into MineConomy.");
						}
					}
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

		if ((iConomy == null && BOSEconomy == null && MineConomy == null) || Permissions == null)
			this.getServer().getPluginManager().registerEvent(Event.Type.PLUGIN_ENABLE, _pluginListener, Priority.Monitor, this);
	}

	@Override
	public void onDisable() {
		_config.saveConf();
		
		_logger.info("[MobBounty] Plugin disabled.");
	}
	
	public ConfigManager getConfig() {
		return _config;
	}
	
	public Logger getLogger() {
		return _logger;
	}
}
