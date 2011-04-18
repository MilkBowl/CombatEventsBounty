package net.mcbat.MobBounty.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.mcbat.MobBounty.Main;

import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.util.config.Configuration;

public class RewardConf {
	private final Main _plugin;
	
	private final File _rewardConfigFile;
	private Configuration rewardConfig;
	
	private Map<CreatureType, Double> defaultReward;
	private Map<String, Map<CreatureType, Double>> worldReward;
	
	public RewardConf(Main plugin) {
		_plugin = plugin;

		_rewardConfigFile = new File(_plugin.getDataFolder() + File.separator + "Reward.yml");
	}
	
	private void createConfig() {
		rewardConfig = new Configuration(_rewardConfigFile);
		rewardConfig.setProperty("Default.Chicken", new Double(0.0));
		rewardConfig.setProperty("Default.Cow", new Double(0.0));
		rewardConfig.setProperty("Default.Creeper", new Double(57.0));
		rewardConfig.setProperty("Default.Ghast", new Double(69.0));
		rewardConfig.setProperty("Default.Pig", new Double(0.0));
		rewardConfig.setProperty("Default.PigZombie", new Double(28.5));
		rewardConfig.setProperty("Default.Sheep", new Double(0.0));
		rewardConfig.setProperty("Default.Skeleton", new Double(33.0));
		rewardConfig.setProperty("Default.Slime", new Double(0.0));
		rewardConfig.setProperty("Default.Spider", new Double(28.5));
		rewardConfig.setProperty("Default.Squid", new Double(0.0));
		rewardConfig.setProperty("Default.Wolf", new Double(28.5));
		rewardConfig.setProperty("Default.Zombie", new Double(21.0));
		
		defaultReward = new HashMap<CreatureType, Double>();
		defaultReward.put(CreatureType.CHICKEN, new Double(0.0));
		defaultReward.put(CreatureType.COW, new Double(0.0));
		defaultReward.put(CreatureType.CREEPER, new Double(57.0));
		defaultReward.put(CreatureType.GHAST, new Double(69.0));
		defaultReward.put(CreatureType.PIG, new Double(0.0));
		defaultReward.put(CreatureType.PIG_ZOMBIE, new Double(28.5));
		defaultReward.put(CreatureType.SHEEP, new Double(0.0));
		defaultReward.put(CreatureType.SKELETON, new Double(33.0));
		defaultReward.put(CreatureType.SLIME, new Double(0.0));
		defaultReward.put(CreatureType.SPIDER, new Double(28.5));
		defaultReward.put(CreatureType.SQUID, new Double(0.0));
		defaultReward.put(CreatureType.WOLF, new Double(28.5));
		defaultReward.put(CreatureType.ZOMBIE, new Double(21.0));
		
		worldReward = new HashMap<String, Map<CreatureType, Double>>();

		this.saveConfig();
	}
	
	public void loadConfig() {
		if (_rewardConfigFile.exists()) {
			List<World> worlds = _plugin.getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();
			
			rewardConfig = new Configuration(_rewardConfigFile);
			rewardConfig.load();
			
			defaultReward = new HashMap<CreatureType, Double>();
			for (CreatureType creature : CreatureType.values()) {
				defaultReward.put(creature, rewardConfig.getDouble("Default."+creature.getName(), 0.0));
			}
			
			worldReward = new HashMap<String, Map<CreatureType, Double>>();
			while (worldIterator.hasNext()) {
				World world = worldIterator.next();
				
				Map<CreatureType, Double> thisWorldReward = new HashMap<CreatureType, Double>();
				
				for (CreatureType creature : CreatureType.values()) {
					double reward = rewardConfig.getDouble("Worlds."+world.getName()+"."+creature.getName(), -9001.0);
					
					if (reward != -9001) {
						thisWorldReward.put(creature, new Double(reward));
					}
				}
				
				worldReward.put(world.getName(), thisWorldReward);
			}
		}
		else
			this.createConfig();
	}
	
	public void saveConfig() {
		List<World> worlds = _plugin.getServer().getWorlds();
		Iterator<World> worldIterator = worlds.iterator();

		while (worldIterator.hasNext()) {
			World world = worldIterator.next();
			for (CreatureType creature : CreatureType.values()) {
				if (rewardConfig.getDouble("Worlds."+world.getName()+"."+creature.getName(), -9001.0) == -9001.0)
					rewardConfig.removeProperty("Worlds."+world.getName()+"."+creature.getName());
			}
		}
		
		rewardConfig.save();
	}
	
	public double getReward(String name, CreatureType creature) {
		Double reward = new Double(0.0);
		Map<CreatureType, Double> worldRwd = worldReward.get(name);
		
		if (worldRwd == null) {
			reward = defaultReward.get(creature);
			if (reward == null)	reward = new Double(0.0);
		}
		else {
			reward = worldRwd.get(creature);
			if (reward == null) {
				reward = defaultReward.get(creature);
				if(reward == null)	reward = new Double(0.0);
			}
		}
		
		return reward.doubleValue();
	}
	
	public void setDefaultReward(CreatureType creature, double amount) {
		rewardConfig.setProperty("Default."+creature.getName(), amount);
		defaultReward.put(creature, new Double(amount));
		
		rewardConfig.save();
	}
	
	public void setReward(String name, CreatureType creature, double amount) {
		rewardConfig.setProperty("Worlds."+name+"."+creature.getName(), amount);
		
		Map<CreatureType, Double> thisWorldReward = worldReward.get(name);
		
		if (thisWorldReward == null)
			thisWorldReward = new HashMap<CreatureType, Double>();
		
		thisWorldReward.put(creature, new Double(amount));
		
		worldReward.put(name, thisWorldReward);
		
		rewardConfig.save();
	}
	
	public void removeReward(String name, CreatureType creature) {
		rewardConfig.removeProperty("Worlds."+name+"."+creature.getName());
		
		Map<CreatureType, Double> worldRwd = worldReward.get(name);
		
		if (worldRwd != null) {
			worldRwd.remove(creature);
			worldReward.put(name, worldRwd);
		}
	}
}
