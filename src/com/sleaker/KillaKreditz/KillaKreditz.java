/**
 * 
 */
package com.sleaker.KillaKreditz;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

/**
 * @author sleaker
 *
 */
public class KillaKreditz extends JavaPlugin {
    private static final String plugName = "[MobMoney]";
    public static Map<String, KillaKreditzConfig> worldConfig = Collections.synchronizedMap(new HashMap<String, KillaKreditzConfig>());
    private final KillaKreditzWorldLoadEvent worldLoadListener = new KillaKreditzWorldLoadEvent(this);
    
    public static Logger log = Logger.getLogger("Minecraft");

    static Configuration config;

    @Override
    public void onDisable() {

        log.info(plugName  + " Disabled");
    }

    @Override
    public void onEnable() {
        //Get the information from the plugin.yml file.
        PluginDescriptionFile pdfFile = this.getDescription();

        //Check to see if there is a configuration file.
        File yml = new File(getDataFolder()+"/config.yml");

        if (!yml.exists()) {
            new File(getDataFolder().toString()).mkdir();
            try {
                yml.createNewFile();
            }
            catch (IOException ex) {
                log.info(plugName + " - Cannot create configuration file. And none to load, using defaults.");
            }
        }   

        config = getConfiguration();
        List<World> worlds = getServer().getWorlds();

        for ( World world : worlds)
            setupWorld(world.getName());  

        //Create the pluginmanager pm and instantiate our listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.WORLD_LOAD, worldLoadListener, Priority.Monitor, this);
        
        
        //Print that the plugin was successfully enabled!
        log.info(plugName + " - " + pdfFile.getVersion() + " by Sleaker is enabled!");
    }

    public static void setupWorld (String worldName) {

        worldConfig.put(worldName, new KillaKreditzConfig());
        if ( !config.getKeys(null).contains(worldName) ) {  
            setConfigDefaults(worldName);
            log.info(plugName + " " + worldName + " - Generating defaults.");   
        }        

        KillaKreditzConfig conf = worldConfig.get(worldName);

        for (CreatureType creature : CreatureType.values() ) {
            String cName = creature.name();
            if (config.getNode(worldName + "." + cName) == null) {
                config.setProperty(worldName + "." + cName + ".minReward", 0.0);
                config.setProperty(worldName + "." + cName + ".maxReward", 0.0);
                config.setProperty(worldName + "." + cName + ".rewardChance", 0.0);
            } else {
                double minReward = config.getDouble(worldName + "." + cName + ".minReward", 0.0);
                double maxReward = config.getDouble(worldName + "." + cName + ".maxReward", 0.0);
                double chance = config.getDouble(worldName + "." + cName + ".rewardChance", 0.0);
                conf.set(creature, minReward, maxReward, chance);
            }
        }
    }

    public static void setConfigDefaults (String worldName) {

        for (CreatureType creature : CreatureType.values() ) {
            config.setProperty(worldName + "." + creature.getName().toLowerCase() + ".minReward", 0.0);
            config.setProperty(worldName + "." + creature.getName().toLowerCase() + ".maxReward", 0.0);
            config.setProperty(worldName + "." + creature.getName().toLowerCase() + ".rewardChance", 0.0);
        }
        config.save();
        return;
    }
}
