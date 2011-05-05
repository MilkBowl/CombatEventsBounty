package net.mcbat.MobBounty.Listeners;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import net.mcbat.MobBounty.MobBounty;
import net.mcbat.MobBounty.Utils.Colors;
import net.mcbat.MobBounty.Utils.CreatureID;
import net.mcbat.MobBounty.Utils.PlayerKillInfo;
import net.mcbat.MobBounty.Utils.Time;

import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.iConomy.iConomy;
import com.iConomy.system.Account;

public class DeathListener extends EntityListener {
	private final MobBounty _plugin;
	
	private final NumberFormat _formatter;
	
	private Map<String, PlayerKillInfo> _playerData;
	private Map<LivingEntity, Player> _deathNote;
	
	public DeathListener(MobBounty plugin) {
		_plugin = plugin;
		_formatter = new DecimalFormat("#0.00");
		
		_playerData = new HashMap<String, PlayerKillInfo>();
		_deathNote = new HashMap<LivingEntity, Player>();
	}
	
	public void onPlayerLeft(String name) {
		_playerData.remove(name);
	}
	
	public void onEntityDamage(EntityDamageEvent event) {
		if (_plugin.iConomy == null && _plugin.BOSEconomy == null && _plugin.MineConomy == null)
			return;
		
		if (event.isCancelled())
			return;
		
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof HumanEntity)
			return;
		
		LivingEntity entity = (LivingEntity) event.getEntity();
		
		if (CreatureID.fromEntity(entity) == null)
			return;
		
		if (entity.getHealth() - event.getDamage() <= 0) {
			if (event instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent subevent = (EntityDamageByEntityEvent)event;
				
				if (subevent.getDamager() instanceof Player) {
					Player damager = (Player) subevent.getDamager();
					
					if (_deathNote.get(entity) == null)
						_deathNote.put(entity, damager);
				}
			}
			else if (event instanceof EntityDamageByProjectileEvent) {
				EntityDamageByProjectileEvent subevent = (EntityDamageByProjectileEvent)event;

				if (subevent.getDamager() instanceof Player) {
					Player damager = (Player) subevent.getDamager();
					
					if (_deathNote.get(entity) == null)
						_deathNote.put(entity, damager);
				}
			}
		}
	}

	public void onEntityDeath(EntityDeathEvent event) {
		if (_plugin.iConomy == null && _plugin.BOSEconomy == null && _plugin.MineConomy == null)
			return;
		
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof HumanEntity)
			return;
		
		LivingEntity mob = (LivingEntity) event.getEntity();
		Player player = _deathNote.get(mob);

		if (player != null) {
			CreatureID creature = CreatureID.fromEntity(mob);

			if (creature != null) {
				this.mobKilledByPlayer(mob.getWorld(), creature, player);
				_deathNote.remove(mob);
			}
		}
	}
	
	private void mobKilledByPlayer(World world, CreatureID creature, Player player) {
		if (_plugin.iConomy == null && _plugin.BOSEconomy == null && _plugin.MineConomy == null)
			return;
		
		if (_plugin.Permissions != null && !(_plugin.Permissions.has(player, "MobBounty.mb")))
			return;

		double multiplier = 1.0;
		if ((Boolean) _plugin.getConfig().getGeneralSetting("useEnvironmentMultiplier"))
			multiplier *= _plugin.getConfig().getEnvironmentMultiplier(world.getEnvironment());
		if ((Boolean) _plugin.getConfig().getGeneralSetting("useTimeMultiplier"))
			multiplier *= _plugin.getConfig().getTimeMultiplier(Time.getTimeOfDay(world.getTime()));			
		if ((Boolean) _plugin.getConfig().getGeneralSetting("useWorldMultiplier"))
			multiplier *= _plugin.getConfig().getWorldMultiplier(world.getName());

		double reward = _plugin.getConfig().getReward(world.getName(), creature);
		double rewardAmount = 1;

		if ((Boolean) _plugin.getConfig().getGeneralSetting("useDepreciativeReturn") && reward > 0.0) {
			PlayerKillInfo info = _playerData.get(player.getName());
			
			if (info == null) {
				info = new PlayerKillInfo();
				rewardAmount = 1;
			}
			else {
				if (info.lastKill == creature) {
					rewardAmount = info.lastRewardPercentage;
					rewardAmount -= (Double) _plugin.getConfig().getGeneralSetting("depreciativeReturnRate");
					
					if (rewardAmount < 0.0)
						rewardAmount = 0.0;
				}
				else {
					rewardAmount = 1;
				}
			}

			info.lastKill = creature;
			info.lastRewardPercentage = rewardAmount;

			_playerData.put(player.getName(), info);
		}
		
		reward = Double.valueOf(_formatter.format(reward*multiplier*rewardAmount));
		
		if (reward != 0.0) {
			
			if (_plugin.iConomy != null) {
				Account account = iConomy.getAccount(player.getName());
				
				if (account != null) {
					if (reward > 0.0) {
						account.getHoldings().add(reward);
						player.sendMessage(Colors.DarkGreen+"You have been awarded "+Colors.White+iConomy.format(reward)+Colors.DarkGreen+" for killing a "+Colors.White+creature.getName());
					}
					else if (reward < 0.0) {
						account.getHoldings().subtract(Math.abs(reward));
						player.sendMessage(Colors.DarkRed+"You have been fined "+Colors.White+iConomy.format(Math.abs(reward))+Colors.DarkRed+" for killing a "+Colors.White+creature.getName());
					}
				}
			}
			else if (_plugin.BOSEconomy != null) {
				if (reward > 0.0) {
					_plugin.BOSEconomy.addPlayerMoney(player.getName(), (int)reward, true);
					
					if (reward == 1.0)
						player.sendMessage(Colors.DarkGreen+"You have been awarded "+Colors.White+_formatter.format(reward)+Colors.DarkGreen+" "+_plugin.BOSEconomy.getMoneyName()+" for killing a "+Colors.White+creature.getName());
					else
						player.sendMessage(Colors.DarkGreen+"You have been awarded "+Colors.White+_formatter.format(reward)+Colors.DarkGreen+" "+_plugin.BOSEconomy.getMoneyNamePlural()+" for killing a "+Colors.White+creature.getName());
				}
				else if (reward < 0.0) {
					_plugin.BOSEconomy.addPlayerMoney(player.getName(), (int)reward, true);

					if (reward == -1.0)
						player.sendMessage(Colors.DarkRed+"You have been fined "+Colors.White+_formatter.format(Math.abs(reward))+Colors.DarkRed+" "+_plugin.BOSEconomy.getMoneyName()+" for killing a "+Colors.White+creature.getName());
					else
						player.sendMessage(Colors.DarkRed+"You have been fined "+Colors.White+_formatter.format(Math.abs(reward))+Colors.DarkRed+" "+_plugin.BOSEconomy.getMoneyNamePlural()+" for killing a "+Colors.White+creature.getName());
				}
			}
			else if (_plugin.MineConomy != null) {
				if (reward > 0.0) {
					_plugin.MineConomy.getBank().add(player.getName(), reward);
					player.sendMessage(Colors.DarkGreen+"You have been awarded "+Colors.White+_formatter.format(reward)+Colors.DarkGreen+" for killing a "+Colors.White+creature.getName());
				}
				else if (reward < 0.0) {
					_plugin.MineConomy.getBank().subtract(player.getName(), reward);
					player.sendMessage(Colors.DarkRed+"You have been fined "+Colors.White+_formatter.format(Math.abs(reward))+Colors.DarkRed+" for killing a "+Colors.White+creature.getName());
				}
			}
		}
	}
}
