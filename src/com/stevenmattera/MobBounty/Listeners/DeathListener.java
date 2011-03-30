// MobBounty
// Version 1.0
//
// Created By Steven Mattera

package com.stevenmattera.MobBounty.Listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.coelho.iConomy.system.Account;
import com.stevenmattera.MobBounty.Main;
import com.stevenmattera.MobBounty.Utils.Colors;

public class DeathListener extends EntityListener {
	
	private final Main _plugin;
	private Map<Entity, Player> _deathNote;
	
	// ----
	// ==== Constructor Method ====
	// ----
	
	public DeathListener(Main plugin) {
		_plugin = plugin;																						//Save our Main Plugin to be used later.
		
		_deathNote = new HashMap<Entity, Player>();																//Create our Death Note of KILLING!!!!
	}
	
	// ----
	// ==== Private Methods ====
	// ----
	
	private void entityKilledByPlayer(Entity entity, Player player) {
		if (_plugin.iConomy != null) {																			//Was iConomy hooked?
			if ((_plugin.Permissions != null && _plugin.Permissions.has(player, "MobBounty.mb")) || (_plugin.Permissions == null)) {
				double reward = _plugin.getConfig().getReward(entity);														//Get how much the reward is based on the mob.
				
				if (reward > 0) {																						//Was there a reward?
					double multiplier = _plugin.getConfig().getWorldMultiplier(entity.getWorld());							//Get the multiplier for the world.
					
					Account account = iConomy.getBank().getAccount(player.getName());										//Get the player's account.
					account.add(reward*multiplier);																			//Give the player their reward.
					
					player.sendRawMessage(Colors.DarkGreen+"You have been awarded "+Colors.White+(reward*multiplier)+Colors.DarkGreen+" "+iConomy.getBank().getCurrency()+"(s)");
				}
			}
		}
	}
	
	// ----
	// ==== Public Methods ====
	// ----
	
	public void onEntityDamage(EntityDamageEvent event) {
		if (_plugin.iConomy != null) {																			//Was iConomy hooked?
			if (!(event.isCancelled())) {																			//Was this event cancelled?
				if (!(event.getEntity() instanceof Player)) {															//Was it something other than a player that was damaged.
					LivingEntity entity = (LivingEntity) event.getEntity();													//Grab our LivingEntity that was damaged.

					if (entity.getHealth() - event.getDamage() <= 0) {														//Was the damage fatal?
						if (event instanceof EntityDamageByEntityEvent) {														//Were the damage dealt by another entity?
							EntityDamageByEntityEvent subevent = (EntityDamageByEntityEvent)event;									//Change our event type accordingly.
								
							if (subevent.getDamager() instanceof Player) {															//Was it a player that wounded our mob?
								Player damager = (Player) subevent.getDamager();														//Get our lucky player.

								if (_deathNote.get(event.getEntity()) == null) 															//See if our mob is already in the Death Note!
									_deathNote.put(event.getEntity(), damager);																//Add our mob to the Death Note to be killed by our player.
							}
						}
						else if (event instanceof EntityDamageByProjectileEvent) {												//or was the damage dealt by a projectile?
							EntityDamageByProjectileEvent subevent = (EntityDamageByProjectileEvent)event;							//Change our event type accordingly.

							if (subevent.getDamager() instanceof Player) {															//Was it a player that wounded our mob?
								Player damager = (Player) subevent.getDamager();														//Get our lucky player.

								if (_deathNote.get(event.getEntity()) == null) 															//See if our mob is already in the Death Note!
									_deathNote.put(event.getEntity(), damager);																//Add our mob to the Death Note to be killed by our player.
							}
						}
					}
				}
			}
		}
	}

	public void onEntityDeath(EntityDeathEvent event) {
		Player damager = _deathNote.get(event.getEntity());														//Grab our killer from the Death Note.
		
		if (damager != null) {																					//Were we able to grab a killer?
			this.entityKilledByPlayer(event.getEntity(), damager);												//Lets reward our killer!
			_deathNote.remove(event.getEntity());																//Remove them from the Death Note as we are done with them.
		}
	}
}
