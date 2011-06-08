/**
 * 
 */
package com.sleaker.KillaKreditz;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.CreatureType;

/**
 * @author sleaker
 *
 */
public class KKWorldConfig {
    private Map<CreatureType, double[]> creatureMap = new HashMap<CreatureType, double[]>();
    
    KKWorldConfig() {
        creatureMap.put(CreatureType.CHICKEN, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.COW, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.CREEPER, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.GHAST, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.GIANT, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.PIG, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.PIG_ZOMBIE, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.SHEEP, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.SKELETON, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.SLIME, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.SPIDER, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.SQUID, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.WOLF, new double[] {0.0, 0.0, 0.0});
        creatureMap.put(CreatureType.ZOMBIE, new double[] {0.0, 0.0, 0.0});      
    }
    
    public void set(CreatureType type, double minReward, double maxReward, double chance) {
        creatureMap.put(type, new double[] {minReward, maxReward, chance});
    }
    
    public double[] get(CreatureType type) {
        return creatureMap.get(type);
    }
    
    public double getMinReward(CreatureType type) {
        return creatureMap.get(type)[0];
    }
    
    public double getMaxReward(CreatureType type) {
        return creatureMap.get(type)[1];
    }
    
    public double getChance(CreatureType type) {
        return creatureMap.get(type)[2];
    }
    
}
