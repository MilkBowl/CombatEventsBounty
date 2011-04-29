package net.mcbat.MobBounty.Config;

import java.io.File;

import net.mcbat.MobBounty.MobBounty;

import org.bukkit.util.config.Configuration;

public class GeneralConf {
	private final MobBounty _plugin;
	
	private final File _generalConfigFile;
	private Configuration generalConfig;
	
	public GeneralConf(MobBounty plugin) {
		_plugin = plugin;

		_generalConfigFile = new File(_plugin.getDataFolder() + File.separator + "General.yml");
	}
	
	private void createConfig() {
		generalConfig = new Configuration(_generalConfigFile);
		generalConfig.setProperty("useEnvironmentMultiplier", false);
		generalConfig.setProperty("useTimeMultiplier", false);
		generalConfig.setProperty("useWorldMultiplier", true);
		generalConfig.setProperty("useDepreciativeReturn", false);
		generalConfig.setProperty("depreciativeReturnRate", new Double(0.1));
		
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
	
	public Object getProperty(String prop) {
		if (prop.equalsIgnoreCase("depreciativeReturnRate"))
			return generalConfig.getDouble(prop, 0.1);
		
		return generalConfig.getBoolean(prop, false);
	}

	public void setProperty(String prop, Object value) {
		generalConfig.setProperty(prop, value);
		this.saveConfig();
	}
}
