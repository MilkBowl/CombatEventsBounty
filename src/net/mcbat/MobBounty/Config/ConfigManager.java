package net.mcbat.MobBounty.Config;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.CreatureID;

import org.bukkit.World.Environment;

public class ConfigManager {
	private final MobBounty _plugin;
	
	private final GeneralConf _generalConf;
	//private final LocaleConf _localeConf;
	private final MultiplierConf _multiplierConf;
	private final RewardConf _rewardConf;

	public ConfigManager(MobBounty plugin) {
		_plugin = plugin;
		
		_generalConf = new GeneralConf(plugin);
		//_localeConf = new LocaleConf(plugin);
		_multiplierConf = new MultiplierConf(plugin);
		_rewardConf = new RewardConf(plugin);
	}
	
	public void loadConfig() {
		_generalConf.loadConfig();
		//_localeConf.loadConfig();
		_multiplierConf.loadConfig();
		_rewardConf.loadConfig();
		
		_plugin.getLogger().info("[MobBounty] Config loaded.");
	}
	
	public void saveConf() {
		_generalConf.saveConfig();
		//_localeConf.saveConfig();
		_multiplierConf.saveConfig();
		_rewardConf.saveConfig();

		_plugin.getLogger().info("[MobBounty] Config saved.");
	}
	
	// General Settings
	
	public Object getGeneralSetting(String property) {
		return _generalConf.getProperty(property);
	}
	
	public void setGeneralSetting(String property, Object value) {
		_generalConf.setProperty(property, value);
	}
	
	// Environment Multiplier
	
	public double getEnvironmentMultiplier(Environment env) {
		return _multiplierConf.getEnvironmentMultiplier(env);
	}
	
	public void setEnvironmentMultiplier(Environment env, double amount) {
		_multiplierConf.setEnvironmentMultiplier(env, amount);
	}

	// Time Multiplier
	
	public double getTimeMultiplier(String time) {
		return _multiplierConf.getTimeMultiplier(time);
	}
	
	public void setTimeMultiplier(String time, double amount) {
		_multiplierConf.setTimeMultiplier(time, amount);
	}

	// World Multiplier
	
	public double getWorldMultiplier(String name) {
		return _multiplierConf.getWorldMultiplier(name);
	}

	public void setWorldMultiplier(String name, double amount) {
		_multiplierConf.setWorldMultiplier(name, amount);
	}

	// Rewards
	
	public double getReward(String name, CreatureID creature) {
		return _rewardConf.getReward(name, creature);
	}
		
	public void setReward(String name, CreatureID creature, double amount) {
		_rewardConf.setReward(name, creature, amount);
	}
	
	public void setDefaultReward(CreatureID creature, double amount) {
		_rewardConf.setDefaultReward(creature, amount);
	}
	
	public void removeReward(String name, CreatureID creature) {
		_rewardConf.removeReward(name, creature);
	}
}
