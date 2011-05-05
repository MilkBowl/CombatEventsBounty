package net.mcbat.MobBounty.Listeners;

import net.mcbat.MobBounty.MobBounty;

import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LogoutListener extends PlayerListener {
	private final MobBounty _plugin;
	
	public LogoutListener(MobBounty plugin) {
		_plugin = plugin;
	}
	
	public void onPlayerKick(PlayerKickEvent event) {
		_plugin.onPlayerLeft(event.getPlayer().getName());
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		_plugin.onPlayerLeft(event.getPlayer().getName());
	}
}
