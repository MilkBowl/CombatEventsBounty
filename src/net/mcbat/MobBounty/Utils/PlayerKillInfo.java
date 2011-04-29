package net.mcbat.MobBounty.Utils;

public class PlayerKillInfo {
	public CreatureID lastKill;
	public double lastRewardPercentage;
	
	public PlayerKillInfo() {
		lastKill = null;
		lastRewardPercentage = -1;
	}
}
