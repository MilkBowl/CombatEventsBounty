/**
 * 
 */
package com.sleaker.KillaKreditz;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

/**
 * @author sleaker
 *
 */
public class KKEntityEvent extends EntityListener {
    public Map<LivingEntity, Player> entityMap = new HashMap<LivingEntity, Player>();
    @SuppressWarnings("unused")
    private KillaKreditz plugin;
    
    KKEntityEvent(KillaKreditz plugin) {
        this.plugin = plugin;
    }
    
    public void onEntityDamage(EntityDamageEvent event) {
        //Reasons to disregard this event
        if (event.isCancelled() || !isValidEntity(event.getEntity()) || KKKreditzHandler.isValidHandler() )
            return;
        
        LivingEntity cEntity = (LivingEntity) event.getEntity();
        //If the attack is going to kill the monster lets store it into our map.
        if (cEntity.getHealth() - event.getDamage() <= 0 ) {
            if (event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent thisEvent = (EntityDamageByEntityEvent) event;
                if (thisEvent.getDamager() instanceof Player) {
                    entityMap.put(cEntity, (Player) thisEvent.getDamager() );
                }
            } else if (event instanceof EntityDamageByProjectileEvent ){
                EntityDamageByProjectileEvent thisEvent = (EntityDamageByProjectileEvent) event;
                if (thisEvent.getDamager() instanceof Player) {
                    entityMap.put(cEntity, (Player)thisEvent.getDamager() );
                }
            }
        }
    }
    
    public void onEntityDeath (EntityDeathEvent event) {
        //Reasons to disregard this event
        if (KKKreditzHandler.isValidHandler() || !isValidEntity(event.getEntity()) )
            return;
        
        LivingEntity cEntity = (LivingEntity) event.getEntity();
        
        //Reward the player if the entity map for this creature exists
        if ( entityMap.containsKey(cEntity) ) {
            //Remove the mapping since we will be rewarding the player.
            Player player = entityMap.remove(cEntity);
            if (player == null)
                return;
            else {
                CreatureType cType = getCType(cEntity);
                if (!KKPermissions.reward(player, cType.getName())) 
                    return;
                
                
                KKWorldConfig conf = KillaKreditz.worldConfig.get(player.getWorld().getName());
                if (conf.get(cType) == null) 
                    return;
                else {
                    double reward = getReward(conf.getMinReward(cType), conf.getMaxReward(cType), conf.getChance(cType) ) * KKPermissions.multiplier(player);
                    if (reward == 0)
                        return;
                    else {
                        //TODO add multipliers
                        KKKreditzHandler.rewardPlayer(player.getName(), reward);
                        player.sendMessage("You have been awarded " + KKKreditzHandler.formatCurrency(reward) + " for killing a " + cType.getName() );
                    }
                }
            }     
        }
    }
    
    private boolean isValidEntity (Entity thisEntity) {
        if ( !(thisEntity instanceof LivingEntity) || thisEntity instanceof HumanEntity )
            return false;
        
        return true;
    }
    
    private double getReward(double minAmount, double maxAmount, double chance) {
        if (chance == 0)
            return 0;
        else {
            Random rand = new Random();
            if (rand.nextDouble() * 100 < chance ) {
                // rand * difference + min == num - between min and max
                return (rand.nextDouble() * (maxAmount - minAmount)) + minAmount;
            }
            else
                return 0;
        }
    }
    
    private CreatureType getCType(LivingEntity cEntity) {
        if (cEntity instanceof Chicken)
            return CreatureType.CHICKEN;
        else if (cEntity instanceof Cow)
            return CreatureType.COW;
        else if (cEntity instanceof Creeper)
            return CreatureType.CREEPER;
        else if (cEntity instanceof Ghast)
            return CreatureType.GHAST;
        else if (cEntity instanceof Giant)
            return CreatureType.GIANT;
        else if (cEntity instanceof Pig)
            return CreatureType.PIG;
        else if (cEntity instanceof Sheep)
            return CreatureType.SHEEP;
        else if (cEntity instanceof Skeleton)
            return CreatureType.SKELETON;
        else if (cEntity instanceof Slime)
            return CreatureType.SLIME;
        else if (cEntity instanceof Spider)
            return CreatureType.SPIDER;
        else if (cEntity instanceof Squid)
            return CreatureType.SQUID;
        else if (cEntity instanceof Wolf)
            return CreatureType.WOLF;
        else if (cEntity instanceof Zombie)
            return CreatureType.ZOMBIE;
        else
            return null;
            
    }
}
