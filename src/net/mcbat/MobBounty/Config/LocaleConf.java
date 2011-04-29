package net.mcbat.MobBounty.Config;

import java.io.File;

import net.mcbat.MobBounty.MobBounty;

import org.bukkit.util.config.Configuration;

public class LocaleConf {
	private final MobBounty _plugin;
	
	private final File _localeConfigFile;
	private Configuration localeConfig;
	
	public LocaleConf(MobBounty plugin) {
		_plugin = plugin;

		_localeConfigFile = new File(_plugin.getDataFolder() + File.separator + "Locale.yml");
	}
	
	private void createConfig() {
		localeConfig = new Configuration(_localeConfigFile);
		localeConfig.setProperty("useEnvironmentMultiplier", false);
		localeConfig.setProperty("useTimeMultiplier", false);
		localeConfig.setProperty("useWorldMultiplier", true);
		localeConfig.setProperty("useDepreciativeReturn", false);
		
		this.saveConfig();
	}
	
	public void loadConfig() {
		if (_localeConfigFile.exists()) {			
			localeConfig = new Configuration(_localeConfigFile);
			localeConfig.load();
		}
		else
			this.createConfig();
	}
	
	public void saveConfig() {
		localeConfig.save();
	}
}
