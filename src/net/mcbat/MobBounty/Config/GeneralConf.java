package net.mcbat.MobBounty.Config;

import java.io.File;

import net.mcbat.MobBounty.Main;

import org.bukkit.util.config.Configuration;

public class GeneralConf {
	private final Main _plugin;
	
	private final File _generalConfigFile;
	private Configuration generalConfig;
	
	public GeneralConf(Main plugin) {
		_plugin = plugin;

		_generalConfigFile = new File(_plugin.getDataFolder() + File.separator + "General.yml");
	}
	
	private void createConfig() {
		generalConfig = new Configuration(_generalConfigFile);
		generalConfig.setProperty("useEnvironmentMultiplier", false);
		generalConfig.setProperty("useTimeMultiplier", false);
		generalConfig.setProperty("useWorldMultiplier", true);
		
		this.saveConfig();
	}
	
	public void loadConfig() {
		if (_generalConfigFile.exists()) {			
			generalConfig = new Configuration(_generalConfigFile);
			generalConfig.load();
		}
		else
			this.createConfig();
	}
	
	public void saveConfig() {
		generalConfig.save();
	}
	
	public boolean getProperty(String prop) {
		return generalConfig.getBoolean(prop, false);
	}
	
	public void setProperty(String prop, boolean value) {
		_plugin.getLogger().info(prop);
		
		generalConfig.setProperty(prop, value);
		this.saveConfig();
	}
}
