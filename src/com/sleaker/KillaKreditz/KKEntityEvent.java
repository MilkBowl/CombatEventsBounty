/**
 * 
 */
package com.sleaker.KillaKreditz;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
    private KillaKreditz plugin;
    
    KKEntityEvent(KillaKreditz plugin) {
        this.plugin = plugin;
    }
    
    public void onEntityDamage(EntityDamageEvent event) {
        //Reasons to disregard this event
        if (event.isCancelled() || !isValidEntity(event.getEntity()) || plugin.iConomy == null)
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
        if (plugin.iConomy == null || !isValidEntity(event.getEntity()) )
            return;
        
        LivingEntity cEntity = (LivingEntity) event.getEntity();
        //Reward the player if the entity map for this creature exists
        if ( entityMap.containsKey(cEntity) ) {
            //Remove the mapping since we will be rewarding the player.
            Player player = entityMap.remove(cEntity);
            
            if (player == null)
                return;
            else {
              //TODO: Player reward
            }     
        }
    }
    
    private boolean isValidEntity (Entity thisEntity) {
        if ( !(thisEntity instanceof LivingEntity) || thisEntity instanceof HumanEntity )
            return false;
        
        return true;
    }
}
