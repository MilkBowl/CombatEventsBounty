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

import com.nijiko.permissions.PermissionHandler;

/**
 * @author sleaker
 *
 */
public class KillaKreditz extends JavaPlugin {
    static final String plugName = "[KillaKreditz]";
    public static Map<String, KKWorldConfig> worldConfig = Collections.synchronizedMap(new HashMap<String, KKWorldConfig>());
    private final KKWorldLoadEvent worldLoadListener = new KKWorldLoadEvent(this);
    private final KKEntityEvent entityListener = new KKEntityEvent(this);
    public static double altMultipliers[] = new double[3];
    public static Logger log = Logger.getLogger("Minecraft");
    public PermissionHandler Permissions = null;
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
        
        wConfig = new Configuration(worldsYml);
        wConfig.load();
        mConfig = getConfiguration();
        setupMultipliers();
        
        List<World> worlds = getServer().getWorlds();

        for ( World world : worlds)
            setupWorld(world.getName());  

        //Create the pluginmanager pm and instantiate our listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.WORLD_LOAD, worldLoadListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Monitor, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Monitor, this);
        //Load up our permissions
        KKPermissions.initialize(getServer());  
        KKKreditzHandler.initialize(getServer());
        //Print that the plugin was successfully enabled!
        log.info(plugName + " - " + pdfFile.getVersion() + " by Sleaker is enabled!");
        
        if ( !KKKreditzHandler.isValidHandler() || !KKPermissions.isValidHandler() )
            getPluginLoader().disablePlugin(this);    
    }

    public static void setupWorld (String worldName) {

        worldConfig.put(worldName, new KKWorldConfig());
        if ( !wConfig.getKeys(null).contains(worldName) ) {  
            setConfigDefaults(worldName);
            log.info(plugName + " " + worldName + " - Generating defaults.");   
        }        

        KKWorldConfig conf = worldConfig.get(worldName);

        for (CreatureType creature : CreatureType.values() ) {
            String cName = creature.name();
            if (wConfig.getNode(worldName + "." + cName) == null) {
                wConfig.setProperty(worldName + "." + cName + ".minReward", 0.0);
                wConfig.setProperty(worldName + "." + cName + ".maxReward", 0.0);
                wConfig.setProperty(worldName + "." + cName + ".rewardChance", 0.0);
            } else {
                double minReward = wConfig.getDouble(worldName + "." + cName + ".minReward", 0.0);
                double maxReward = wConfig.getDouble(worldName + "." + cName + ".maxReward", 0.0);
                double chance = wConfig.getDouble(worldName + "." + cName + ".rewardChance", 0.0);
                conf.set(creature, minReward, maxReward, chance);
            }
        }
    }

    public static void setConfigDefaults (String worldName) {

        for (CreatureType creature : CreatureType.values() ) {
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
    
    private void setupMultipliers() {
        if (mConfig.getNode("alt1") == null)
            mConfig.setProperty("alt1", (double) 1.0);
        
        if (mConfig.getNode("alt2") == null)
            mConfig.setProperty("alt2", (double) 1.0);
        
        if (mConfig.getNode("alt3") == null)
            mConfig.setProperty("alt3", (double) 1.0);
        
        mConfig.save();
        
        altMultipliers[0] = mConfig.getDouble("alt1", 1.0);
        altMultipliers[1] = mConfig.getDouble("alt2", 1.0);
        altMultipliers[2] = mConfig.getDouble("alt3", 1.0);
        
        return;
    }
}
