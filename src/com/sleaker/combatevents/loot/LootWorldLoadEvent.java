/**
 * 
 */
package com.sleaker.combatevents.loot;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;

/**
 * @author sleaker
 *
 * Detects when a new world is loaded in and runs the configuration loader for the world.
 */
public class LootWorldLoadEvent extends WorldListener {

    public final CombatEventsLoot plugin;

    public LootWorldLoadEvent(CombatEventsLoot instance) {
        plugin = instance;
    }

    public void onWorldLoad(WorldLoadEvent event) {
        CombatEventsLoot.setupWorld(event.getWorld().getName());
    }
}
