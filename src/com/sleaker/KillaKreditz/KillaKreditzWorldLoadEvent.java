/**
 * 
 */
package com.sleaker.KillaKreditz;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

/**
 * @author sleaker
 *
 * Detects when a new world is loaded in and runs the configuration loader for the world.
 */
public class KillaKreditzWorldLoadEvent extends WorldListener {

    public final KillaKreditz plugin;

    public KillaKreditzWorldLoadEvent(KillaKreditz instance) {
        plugin = instance;
    }

    public void onWorldLoad(WorldLoadEvent event) {
        KillaKreditz.setupWorld(event.getWorld().getName());
    }
}
