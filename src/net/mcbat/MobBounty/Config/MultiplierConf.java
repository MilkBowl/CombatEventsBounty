package net.mcbat.MobBounty.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.Time;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.util.config.Configuration;

public class MultiplierConf {
	private final MobBounty _plugin;
	
	private final File _multiplierConfigFile;
	private Configuration multiplierConfig;
	
	private Map<Environment, Double> environmentMultiplier;
	private Map<String, Double> timeMultiplier;
	private Map<String, Double> worldMultiplier;
	
	public MultiplierConf(MobBounty plugin) {
		_plugin = plugin;

		_multiplierConfigFile = new File(_plugin.getDataFolder() + File.separator + "Multiplier.yml");
	}
	
	private void createConfig() {
		multiplierConfig = new Configuration(_multiplierConfigFile);
		multiplierConfig.setProperty("Environment.Normal", 1.0);
		multiplierConfig.setProperty("Environment.Nether", 2.0);
		multiplierConfig.setProperty("Time.Day", 0.5);
		multiplierConfig.setProperty("Time.Sunset", 1.0);
		multiplierConfig.setProperty("Time.Night", 2.0);
		multiplierConfig.setProperty("Time.Sunrise", 1.0);

		environmentMultiplier = new HashMap<Environment, Double>();
		environmentMultiplier.put(Environment.NORMAL, new Double(1.0));
		environmentMultiplier.put(Environment.NETHER, new Double(2.0));
		
		timeMultiplier = new HashMap<String, Double>();
		timeMultiplier.put(Time.Day, new Double(0.5));
		timeMultiplier.put(Time.Sunset, new Double(1.0));
		timeMultiplier.put(Time.Night, new Double(2.0));
		timeMultiplier.put(Time.Sunrise, new Double(1.0));
		
		worldMultiplier = new HashMap<String, Double>();
		
		this.saveConfig();
	}
	
	public void loadConfig() {
		if (_multiplierConfigFile.exists()) {
			List<World> worlds = _plugin.getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();
			
			multiplierConfig = new Configuration(_multiplierConfigFile);
			multiplierConfig.load();
			
			environmentMultiplier = new HashMap<Environment, Double>();
			environmentMultiplier.put(Environment.NORMAL, multiplierConfig.getDouble("Environment.Normal", new Double(1.0)));
			environmentMultiplier.put(Environment.NETHER, multiplierConfig.getDouble("Environment.Nether", new Double(2.0)));

			timeMultiplier = new HashMap<String, Double>();
			timeMultiplier.put(Time.Day, multiplierConfig.getDouble("Time.Day", new Double(0.5)));
			timeMultiplier.put(Time.Sunset, multiplierConfig.getDouble("Time.Sunset", new Double(1.0)));
			timeMultiplier.put(Time.Night, multiplierConfig.getDouble("Time.Night", new Double(2.0)));
			timeMultiplier.put(Time.Sunrise, multiplierConfig.getDouble("Time.Sunrise", new Double(1.0)));

			worldMultiplier = new HashMap<String, Double>();
			while (worldIterator.hasNext()) {
				World world = worldIterator.next();
				Double multiplier =  multiplierConfig.getDouble("Worlds."+world.getName(), new Double(1.0));
				
				if (multiplier != 1)
					worldMultiplier.put(world.getName(), multiplier);
			}
		}
		else
			this.createConfig();
	}
	
	public void saveConfig() {
		multiplierConfig.save();
	}
	
	// Environment Multiplier
	
	public double getEnvironmentMultiplier(Environment env) {
		Double environmentMulti = environmentMultiplier.get(env);
		if (environmentMulti == null) environmentMulti = new Double(1.0);

		return environmentMulti;
	}

	public void setEnvironmentMultiplier(Environment env, double amount) {
		if (env == Environment.NORMAL)	multiplierConfig.setProperty("Environment.Normal", amount);
		else							multiplierConfig.setProperty("Environment.Nether", amount);
		
		environmentMultiplier.put(env, new Double(amount));
		multiplierConfig.save();
	}
	
	// Time Multiplier
	
	public double getTimeMultiplier(String time) {
		Double timeMulti = timeMultiplier.get(time);
		if (timeMulti == null) timeMulti = new Double(1.0);

		return timeMulti;
	}

	public void setTimeMultiplier(String time, double amount) {
		if (time == Time.Day)				multiplierConfig.setProperty("Time.Day", amount);
		else if (time == Time.Sunset)		multiplierConfig.setProperty("Time.Sunset", amount);
		else if (time == Time.Night)		multiplierConfig.setProperty("Time.Night", amount);
		else if (time == Time.Sunrise)		multiplierConfig.setProperty("Time.Sunrise", amount);
		
		timeMultiplier.put(time, new Double(amount));
		multiplierConfig.save();
	}
	
	// World Multiplier
	
	public double getWorldMultiplier(String world) {
		Double worldMulti = worldMultiplier.get(world);
		if (worldMulti == null) worldMulti = new Double(1.0);
		
		return worldMulti;
	}
	
	public void setWorldMultiplier(String world, double amount) {
		multiplierConfig.setProperty("Worlds."+world, amount);
		worldMultiplier.put(world, new Double(amount));
		multiplierConfig.save();
	}
}
