/**
 * 
 */
package net.milkbowl.combatevents.bounty;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

/**
 * @author sleaker
 *
 * Detects when a new world is loaded in and runs the configuration loader for the world.
 */
public class BountyWorldLoadEvent extends WorldListener {

    public final CombatEventsBounty plugin;

    public BountyWorldLoadEvent(CombatEventsBounty instance) {
        plugin = instance;
    }

    public void onWorldLoad(WorldLoadEvent event) {
        CombatEventsBounty.setupWorld(event.getWorld().getName());
    }
}
