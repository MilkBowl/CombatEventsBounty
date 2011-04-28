package net.mcbat.MobBounty.Utils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.EntityWolf;

import org.bukkit.craftbukkit.entity.CraftWolf;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public enum CreatureID {
	CHICKEN("Chicken"),
	COW("Cow"),
	CREEPER("Creeper"),
	ELECTRIFIED_CREEPER("ElectrifiedCreeper"),
	GHAST("Ghast"),
	GIANT("Giant"),
	MONSTER("Monster"),
	PIG("Pig"),
	PIG_ZOMBIE("PigZombie"),
	SHEEP("Sheep"),
	SKELETON("Skeleton"),
	SLIME("Slime"),
	SPIDER("Spider"),
	SQUID("Squid"),
	TAMED_WOLF("TamedWolf"),
	WOLF("Wolf"),
	ZOMBIE("Zombie");
	
	private String _name;
	private static final Map<String, CreatureID> _mapping = new HashMap<String, CreatureID>();
	
	static {
		for (CreatureID id : EnumSet.allOf(CreatureID.class)) {
			_mapping.put(id.name(), id);
		}
	}
	
	private CreatureID(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public static CreatureID fromName(String name) {
		return _mapping.get(name);
	}
	
	public static CreatureID fromEntity(Entity entity) {
		if (entity instanceof Chicken)			return CreatureID.CHICKEN;
		else if (entity instanceof Cow)			return CreatureID.COW;
		else if (entity instanceof Creeper) {
			Creeper creeper = (Creeper) entity;
			
			if (creeper.isPowered())	return CreatureID.ELECTRIFIED_CREEPER;
			else						return CreatureID.CREEPER;
		}
		else if (entity instanceof Ghast)		return CreatureID.GHAST;
		else if (entity instanceof Giant)		return CreatureID.GIANT;
		else if (entity instanceof Pig)			return CreatureID.PIG;
		else if (entity instanceof PigZombie)	return CreatureID.PIG_ZOMBIE;
		else if (entity instanceof Sheep)		return CreatureID.SHEEP;
		else if (entity instanceof Skeleton)	return CreatureID.SKELETON;
		else if (entity instanceof Slime)		return CreatureID.SLIME;
		else if (entity instanceof Spider)		return CreatureID.SPIDER;
		else if (entity instanceof Squid)		return CreatureID.SQUID;
		else if (entity instanceof Wolf) {
			CraftWolf wolf = (CraftWolf) entity;
			EntityWolf wolfEntity = wolf.getHandle();
			
			if (wolfEntity.x().equalsIgnoreCase(""))	return CreatureID.WOLF;
			else										return CreatureID.TAMED_WOLF;
		}
		else if (entity instanceof Zombie)		return CreatureID.ZOMBIE;
		
		return null;
	}	
}
