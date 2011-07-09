package net.milkbowl.combatevents.bounty;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

import net.milkbowl.combatevents.CombatEventsListener;
import net.milkbowl.combatevents.events.EntityKilledByEntityEvent;
import net.milkbowl.combatevents.Utility;
import net.milkbowl.vault.Vault;

public class CombatListener extends CombatEventsListener {
	
	@Override
	public void onEntityKilledByEntityEvent(EntityKilledByEntityEvent event) {
		//We don't handle PvP so exit
		if (event.getKilled() instanceof Player)
			return;
		
		if (event.getAttacker() instanceof Player) {
			Player player = (Player) event.getAttacker();
			CreatureType cType = Utility.getCType(event.getKilled());
			//Check if the player has Permission to recieve a reward
			if (!Vault.getPermission().has(player, "combatevents.bounty." + cType.getName().toLowerCase())) {
				return;
			}
			doReward(player, cType);

		} else if (event.getAttacker() instanceof Tameable) {
			if (((Tameable) event.getAttacker()).getOwner() instanceof Player) {
				Player player = (Player) ((Tameable) event.getAttacker()).getOwner();
				if (Vault.getPermission().has(player, "combatevents.petrewards") && Vault.getPermission().has(player, "combatevents.loot."+Utility.getCType(event.getKilled()).getName().toLowerCase())) {
					doReward(player, Utility.getCType(event.getKilled()));
				}
			}
		}
	}
	
	private void doReward(Player player, CreatureType cType) {
		BountyWorldConfig conf = CombatEventsBounty.worldConfig.get(player.getWorld().getName());
		if (conf.get(cType) == null) 
			return;
		else {
			//Get the reward amount & multiply it out
			double reward = getReward(conf.getMinReward(cType), conf.getMaxReward(cType), conf.getChance(cType) ) * Vault.getPermission().getPlayerInfoDouble(player.getWorld().getName(), player.getName(), "cemultiplier", 1);
			if (reward == 0)
				return;
			else {
				Vault.getEconomy().depositPlayer(player.getName(), reward);
				player.sendMessage("You have been awarded " + ChatColor.DARK_GREEN + Vault.getEconomy().format(reward) + ChatColor.WHITE + " for killing a " + ChatColor.DARK_RED + cType.getName() );
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
}
