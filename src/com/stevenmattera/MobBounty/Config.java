package com.stevenmattera.MobBounty;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Zombie;
import org.bukkit.util.config.Configuration;

public class Config {
	private static Main _plugin = null;
	
	private File _configFile = null;
	private Configuration _config = null;
	
	private double _defaultMultiplier = 1;
	private Map<String, Double> _worldMultiplier = null;
	private Map<String, Double> _rewards = null;
	
	public Config(Main plugin) {
		if (_plugin == null) _plugin = plugin;
		
		_configFile = new File(_plugin.getDataFolder() + File.separator + "config.yml");

		_worldMultiplier = new HashMap<String, Double>();
		_rewards = new HashMap<String, Double>();
		_config = new Configuration(_configFile);
		
		if (!_configFile.exists())	this.createConfig();
		else						this.loadConfig();
	}
	
	private void createConfig() {
		if (_config != null) {
			List<World> worlds = _plugin.getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();
		
			_config.setProperty("Multipliers.Default", 1);
			_defaultMultiplier = 1;
		
			while (worldIterator.hasNext()) {
				World world = worldIterator.next();

				if (world.getEnvironment() == World.Environment.NETHER) {
					_config.setProperty("Multipliers."+world.getName(), 2);
					_worldMultiplier.put(world.getName(), new Double(2));
				}
				else {
					_config.setProperty("Multipliers."+world.getName(), 1);
					_worldMultiplier.put(world.getName(), new Double(1));
				}
			}

			_config.setProperty("Pig", 0);
			_rewards.put(CreatureType.PIG.getName(), new Double(0));
			
			_config.setProperty("Sheep", 0);
			_rewards.put(CreatureType.SHEEP.getName(), new Double(0));

			_config.setProperty("Cow", 0);
			_rewards.put(CreatureType.COW.getName(), new Double(0));

			_config.setProperty("Chicken", 0);
			_rewards.put(CreatureType.CHICKEN.getName(), new Double(0));

			_config.setProperty("Squid", 0);
			_rewards.put(CreatureType.SQUID.getName(), new Double(0));


			_config.setProperty("Spider", 5);
			_rewards.put(CreatureType.SPIDER.getName(), new Double(5));

			_config.setProperty("Slime", 5);
			_rewards.put(CreatureType.SLIME.getName(), new Double(5));

			_config.setProperty("Zombie", 10);
			_rewards.put(CreatureType.ZOMBIE.getName(), new Double(10));

			_config.setProperty("Skeleton", 15);
			_rewards.put(CreatureType.SKELETON.getName(), new Double(15));

			_config.setProperty("Pigman", 15);
			_rewards.put(CreatureType.PIG_ZOMBIE.getName(), new Double(15));

			_config.setProperty("Creeper", 25);
			_rewards.put(CreatureType.CREEPER.getName(), new Double(25));

			_config.setProperty("Ghast", 50);
			_rewards.put(CreatureType.GHAST.getName(), new Double(50));
		
			this.saveConfig();
		}
	}
	
	public void loadConfig() {
		if (_config != null) {
			_config.load();
		
			List<World> worlds = _plugin.getServer().getWorlds();
			Iterator<World> worldIterator = worlds.iterator();
		
			_defaultMultiplier = _config.getDouble("Multipliers.Default", new Double(1));

			while (worldIterator.hasNext()) {
				World world = worldIterator.next();

				_worldMultiplier.put(world.getName(), _config.getDouble("Multipliers."+world.getName(), _defaultMultiplier));
			}
		
			_rewards.put(CreatureType.PIG.getName(), new Double(_config.getDouble("Pig", 0)));
			_rewards.put(CreatureType.SHEEP.getName(), new Double(_config.getDouble("Sheep", 0)));
			_rewards.put(CreatureType.COW.getName(), new Double(_config.getDouble("Cow", 0)));
			_rewards.put(CreatureType.CHICKEN.getName(), new Double(_config.getDouble("Chicken", 0)));
			_rewards.put(CreatureType.SQUID.getName(), new Double(_config.getDouble("Squid", 0)));
			
			_rewards.put(CreatureType.SPIDER.getName(), new Double(_config.getDouble("Spider", 5)));
			_rewards.put(CreatureType.SLIME.getName(), new Double(_config.getDouble("Slime", 5)));
			_rewards.put(CreatureType.ZOMBIE.getName(), new Double(_config.getDouble("Zombie", 10)));
			_rewards.put(CreatureType.SKELETON.getName(), new Double(_config.getDouble("Skeleton", 15)));
			_rewards.put(CreatureType.PIG_ZOMBIE.getName(), new Double(_config.getDouble("Pigman", 15)));
			_rewards.put(CreatureType.CREEPER.getName(), new Double(_config.getDouble("Creeper", 25)));
			_rewards.put(CreatureType.GHAST.getName(), new Double(_config.getDouble("Ghast", 50)));
		}
	}

	public void saveConfig() {
		if (_config != null) {
			_config.save();
		}
	}
	
	public double getWorldMultiplier(World world) {
		return this.getWorldMultiplier(world.getName());
	}

	public double getWorldMultiplier(String worldName) {
		Double multi = _worldMultiplier.get(worldName);

		if (multi == null) multi = new Double(_defaultMultiplier);
		
		return multi.doubleValue();
	}
	
	public double getReward(Entity entity) {
		if (entity instanceof Pig) 				return getReward(CreatureType.PIG).doubleValue();
		else if (entity instanceof Sheep) 		return getReward(CreatureType.SHEEP).doubleValue();
		else if (entity instanceof Cow)			return getReward(CreatureType.COW).doubleValue();
		else if (entity instanceof Chicken)		return getReward(CreatureType.CHICKEN).doubleValue();
		else if (entity instanceof Squid)		return getReward(CreatureType.SQUID).doubleValue();
		else if (entity instanceof Spider)		return getReward(CreatureType.SPIDER).doubleValue();
		else if (entity instanceof Slime)		return getReward(CreatureType.SLIME).doubleValue();
		else if (entity instanceof Zombie)		return getReward(CreatureType.ZOMBIE).doubleValue();
		else if (entity instanceof Skeleton)	return getReward(CreatureType.SKELETON).doubleValue();
		else if (entity instanceof PigZombie)	return getReward(CreatureType.PIG_ZOMBIE).doubleValue();
		else if (entity instanceof Creeper)		return getReward(CreatureType.CREEPER).doubleValue();
		else if (entity instanceof Ghast)		return getReward(CreatureType.GHAST).doubleValue();
		
		return 0.0;
	}
	
	public Double getReward(CreatureType creature) {
		return _rewards.get(creature.getName());
	}
	
	public void setWorldMultiplier(String worldName, Double amount) {
		_worldMultiplier.put(worldName, amount);
		_config.setProperty("Multipliers."+worldName, amount);
		this.saveConfig();
	}
	
	public void setReward(CreatureType creature, Double amount) {
		_rewards.put(creature.getName(), amount);
		
		switch (creature) {
			case PIG:
				_config.setProperty("Pig", amount);
				break;
			case SHEEP:
				_config.setProperty("Sheep", amount);
				break;
			case COW:
				_config.setProperty("Cow", amount);
				break;
			case CHICKEN:
				_config.setProperty("Chicken", amount);
				break;
			case SQUID:
				_config.setProperty("Squid", amount);
				break;
			case SPIDER:
				_config.setProperty("Spider", amount);
				break;
			case SLIME:
				_config.setProperty("Slime", amount);
				break;
			case ZOMBIE:
				_config.setProperty("Zombie", amount);
				break;
			case SKELETON:
				_config.setProperty("Skeleton", amount);
				break;
			case PIG_ZOMBIE:
				_config.setProperty("Pigman", amount);
				break;
			case CREEPER:
				_config.setProperty("Creeper", amount);
				break;
			case GHAST:
				_config.setProperty("Ghast", amount);
				break;
		}
		
		this.saveConfig();
	}
	
}