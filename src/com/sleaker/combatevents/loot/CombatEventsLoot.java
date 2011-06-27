/**
 * 
 */
package com.sleaker.combatevents.loot;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.sleaker.combatevents.CombatEventsCore;


/**
 * @author sleaker
 *
 */
public class CombatEventsLoot extends JavaPlugin {
    static final String plugName = "[CombatEventsLoot]";
    public static Map<String, LootWorldConfig> worldConfig = Collections.synchronizedMap(new HashMap<String, LootWorldConfig>());
    private CombatEventsCore ceCore = null;
    
    private final LootWorldLoadEvent worldLoadListener = new LootWorldLoadEvent(this);
    private final LootEvent customListener = new LootEvent();

    public static Logger log = Logger.getLogger("Minecraft");

    //Handles the per-world Settings
    static Configuration wConfig;
    //Handles the server-wide Settings
    static Configuration mConfig;
    
    @Override
    public void onDisable() {

        log.info(plugName  + " Disabled");
    }

    @Override
    public void onEnable() {
        //Get the information from the plugin.yml file.
        PluginDescriptionFile pdfFile = this.getDescription();

        //Check to see if there is a worlds configuration file.
        File worldsYml = new File(getDataFolder()+"/worlds.yml");
        File mainYml = new File(getDataFolder()+"/config.yml");
        setupFile(worldsYml);
        setupFile(mainYml);
        
        setupDependencies();
        
        wConfig = new Configuration(worldsYml);
        wConfig.load();
        mConfig = getConfiguration();
        
        List<World> worlds = getServer().getWorlds();

        for ( World world : worlds)
            setupWorld(world.getName());  

        //Create the pluginmanager pm and instantiate our listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.WORLD_LOAD, worldLoadListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.CUSTOM_EVENT, customListener, Priority.Monitor, this);
        
        //Load up our permissions
        LootPermissions.initialize(getServer());  
        LootEconHandler.initialize(getServer());
        //Print that the plugin was successfully enabled!
        log.info(plugName + " - " + pdfFile.getVersion() + " by Sleaker is enabled!");
        
        if ( LootEconHandler.isInvalidHandler() || LootPermissions.isInvalidHandler() )
            getPluginLoader().disablePlugin(this);    
    }

    public static void setupWorld (String worldName) {

        worldConfig.put(worldName, new LootWorldConfig());
        if ( !wConfig.getKeys(null).contains(worldName) ) {  
            setConfigDefaults(worldName);
            log.info(plugName + " " + worldName + " - Generating defaults.");   
        }        

        LootWorldConfig conf = worldConfig.get(worldName);

        for (CreatureType creature : CreatureType.values() ) {
            if (creature == CreatureType.MONSTER)
                continue;
            
            String cName = creature.getName().toLowerCase();
            if (wConfig.getNode(worldName + "." + cName) == null) {
                wConfig.setProperty(worldName + "." + cName + ".minReward", 0.0);
                wConfig.setProperty(worldName + "." + cName + ".maxReward", 0.0);
                wConfig.setProperty(worldName + "." + cName + ".rewardChance", 0.0);
                wConfig.save();
            } else {
                double minReward = wConfig.getDouble(worldName + "." + cName + ".minReward", 0.0);
                double maxReward = wConfig.getDouble(worldName + "." + cName + ".maxReward", 0.0);
                double chance = wConfig.getDouble(worldName + "." + cName + ".rewardChance", 0.0);
                
                //Sanity Checks
                if (minReward < 0) {
                    minReward = 0;
                    wConfig.setProperty(worldName + "." + cName + ".minReward", minReward);
                    wConfig.save();
                }
                //Max can't be less than min.
                if (maxReward < minReward) {
                    maxReward = minReward;
                    wConfig.setProperty(worldName + "." + cName + ".maxReward", maxReward);
                    wConfig.save();
                }
                if (chance < 0) {
                    chance = 0;
                    wConfig.getDouble(worldName + "." + cName + ".rewardChance", chance);
                    wConfig.save();
                }
                
                conf.set(creature, minReward, maxReward, chance);
            }
        }
    }

    public static void setConfigDefaults (String worldName) {

        for (CreatureType creature : CreatureType.values() ) {
            //Skip these records
            if (creature == CreatureType.MONSTER )
                continue;
            
            wConfig.setProperty(worldName + "." + creature.getName().toLowerCase() + ".minReward", 0.0);
            wConfig.setProperty(worldName + "." + creature.getName().toLowerCase() + ".maxReward", 0.0);
            wConfig.setProperty(worldName + "." + creature.getName().toLowerCase() + ".rewardChance", 0.0);
        }
        wConfig.save();
        return;
    }
    
    private void setupFile(File file) {
        if (!file.exists()) {
            new File(getDataFolder().toString()).mkdir();
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                log.info(plugName + " - Cannot create configuration file. And none to load check your folder permission!");
            }
        }   
    }
    
    private void setupDependencies() {
		if (ceCore == null) {
            Plugin ceCore = this.getServer().getPluginManager().getPlugin("CombatEventsCore");
            if (ceCore != null) {
                this.ceCore = ((CombatEventsCore) ceCore);
                log.info(plugName + " - Successfully hooked " + ceCore.getDescription().getName() + "v" + ceCore.getDescription().getVersion());
            }
        } 
    }
}
