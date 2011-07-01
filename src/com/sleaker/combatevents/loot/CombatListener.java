package com.sleaker.combatevents.loot;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

import com.sleaker.combatevents.CombatEventsListener;
import com.sleaker.combatevents.events.EntityKilledByEntityEvent;

public class CombatListener extends CombatEventsListener {
	
	@Override
	public void onEntityKilledByEntityEvent(EntityKilledByEntityEvent event) {
		if (event.getAttacker() instanceof Player) {
			Player player = (Player) event.getAttacker();
			CreatureType cType = getCType(event.getKilled());
			//Check if the player has Permission to recieve a reward
			if (!LootPermissions.reward(player, cType.getName().toLowerCase())) {
				return;
			}
			doReward(player, cType);

		} else if (event.getAttacker() instanceof Tameable) {
			if (((Tameable) event.getAttacker()).getOwner() instanceof Player) {
				Player player = (Player) ((Tameable) event.getAttacker()).getOwner();
				if (LootPermissions.permission(player, "combatevents.petrewards", true) && LootPermissions.reward(player, getCType(event.getKilled()).getName().toLowerCase())) {
					doReward(player, getCType(event.getKilled()));
				}
			}
		}
	}
	
	private void doReward(Player player, CreatureType cType) {
		LootWorldConfig conf = CombatEventsLoot.worldConfig.get(player.getWorld().getName());
		if (conf.get(cType) == null) 
			return;
		else {
			//Get the reward amount & multiply it out
			double reward = getReward(conf.getMinReward(cType), conf.getMaxReward(cType), conf.getChance(cType) ) * LootPermissions.multiplier(player);
			if (reward == 0)
				return;
			else {
				LootEconHandler.rewardPlayer(player.getName(), reward);
				player.sendMessage("You have been awarded " + ChatColor.DARK_GREEN + LootEconHandler.formatCurrency(reward) + ChatColor.WHITE + " for killing a " + ChatColor.DARK_RED + cType.getName() );
			}
		}
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

	public CreatureType getCType(LivingEntity cEntity) {
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
