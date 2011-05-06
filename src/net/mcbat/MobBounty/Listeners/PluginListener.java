package net.mcbat.MobBounty.Listeners;

import net.mcbat.MobBounty.MobBounty;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import cosine.boseconomy.BOSEconomy;

import com.iConomy.iConomy;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.spikensbror.bukkit.mineconomy.MineConomy;

public class PluginListener extends ServerListener {
	private final MobBounty _plugin;
		
	public PluginListener(MobBounty plugin) {
		_plugin = plugin;
	}
	
	public void onPluginEnable(PluginEnableEvent event) {
		if (_plugin.iConomy == null && event.getPlugin().getDescription().getName().equals("iConomy")) {
			if (event.getPlugin().getDescription().getMain().equals("com.nijiko.coelho.iConomy.iConomy")) {
				_plugin.getLogger().severe("[MobBounty] you must upgrade to iConomy 5.0! (forums.bukkit.org/threads/40/");
			}
			else {
				_plugin.iConomy = (iConomy) event.getPlugin();
				_plugin.getLogger().info("[MobBounty] hooked into iConomy.");
			}
		}
		else if (_plugin.BOSEconomy == null && event.getPlugin().getDescription().getName().equals("BOSEconomy")) {
			_plugin.BOSEconomy = (BOSEconomy) event.getPlugin();
			_plugin.getLogger().info("[MobBounty] hooked into BOSEconomy.");
		}
		else if (_plugin.MineConomy == null && event.getPlugin().getDescription().getName().equals("MineConomy")) {
			_plugin.MineConomy = (MineConomy) event.getPlugin();
			_plugin.getLogger().info("[MobBounty] hooked into MineConomy.");
		}
		else if (_plugin.Permissions == null && event.getPlugin().getDescription().getName().equals("Permissions")) {
			_plugin.Permissions = ((Permissions)event.getPlugin()).getHandler();
			_plugin.getLogger().info("[MobBounty] hooked into Permissions/GroupManager.");
		}
	}
	
	public void onPluginDisable(PluginDisableEvent event) {
		if (_plugin.iConomy != null && event.getPlugin().getDescription().getName().equals("iConomy")) {
			_plugin.iConomy = null;
			_plugin.getLogger().info("[MobBounty] un-hooked from iConomy");
		}
		else if (_plugin.BOSEconomy != null && event.getPlugin().getDescription().getName().equals("BOSEconomy")) {
			_plugin.BOSEconomy = null;
			_plugin.getLogger().info("[MobBounty] un-hooked from BOSEconomy");
		}
		else if (_plugin.MineConomy != null && event.getPlugin().getDescription().getName().equals("MineConomy")) {
			_plugin.MineConomy = null;
			_plugin.getLogger().info("[MobBounty] un-hooked from MineConomy");
		}
		else if (_plugin.Permissions != null && event.getPlugin().getDescription().getName().equals("Permissions")) {
			_plugin.Permissions = null;
			_plugin.getLogger().info("[MobBounty] un-hooked from Permissions/GroupManager");
		}
	}
}
