package net.mcbat.MobBounty.Utils;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class CreatureID {
	public static CreatureType identifyCreature(LivingEntity mob) {
		if (mob instanceof Chicken)			return CreatureType.CHICKEN;
		else if (mob instanceof Cow)		return CreatureType.COW;
		else if (mob instanceof Creeper)	return CreatureType.CREEPER;
		else if (mob instanceof Ghast)		return CreatureType.GHAST;
		else if (mob instanceof Pig)		return CreatureType.PIG;
		else if (mob instanceof PigZombie)	return CreatureType.PIG_ZOMBIE;
		else if (mob instanceof Sheep)		return CreatureType.SHEEP;
		else if (mob instanceof Skeleton)	return CreatureType.SKELETON;
		else if (mob instanceof Slime)		return CreatureType.SLIME;
		else if (mob instanceof Spider)		return CreatureType.SPIDER;
		else if (mob instanceof Squid)		return CreatureType.SQUID;
		else if (mob instanceof Wolf)		return CreatureType.WOLF;
		else if (mob instanceof Zombie)		return CreatureType.ZOMBIE;
		
		return null;
	}
	
	public static CreatureType identifyCreatureByName(String mob) {
		CreatureType identCreature = null;
		
		for (CreatureType creature : CreatureType.values()) {
			if (creature.getName().equalsIgnoreCase(mob)) {
				identCreature = creature;
				break;
			}
		}
		
		return identCreature;
	}
}
