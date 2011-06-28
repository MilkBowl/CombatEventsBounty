/**
 * 
 */
package com.sleaker.combatevents.loot;

import java.util.logging.Logger;

import ru.tehkode.permissions.bukkit.*;

import com.nijikokun.bukkit.Permissions.Permissions;
import org.anjocaido.groupmanager.GroupManager;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LootPermissions {
    public static Logger log = Logger.getLogger("Minecraft");
    private static PermissionsHandler handler;

    private static Plugin permissionPlugin;
    
    
    private enum PermissionsHandler {
        PERMISSIONSEX, PERMISSIONS3, PERMISSIONS, GROUPMANAGER, NONE
    }
    

    public static void initialize(Server server) {
        Plugin permissionsEx = server.getPluginManager().getPlugin("PermissionsEx");
        Plugin groupManager = server.getPluginManager().getPlugin("GroupManager");
        Plugin permissions = server.getPluginManager().getPlugin("Permissions");

        if (permissionsEx != null) {
            permissionPlugin = permissionsEx;
            handler = PermissionsHandler.PERMISSIONSEX;
            String version = permissionsEx.getDescription().getVersion();
            log.info(CombatEventsLoot.plugName + " - Permissions hooked using: PermissionsEx v" + version);
        } else if (groupManager != null) {
            permissionPlugin = groupManager;
            handler = PermissionsHandler.GROUPMANAGER;
            String version = groupManager.getDescription().getVersion();
            log.info(CombatEventsLoot.plugName + " - Permissions hooked using: GroupManager v" + version);
        } else if (permissions != null) {
            permissionPlugin = permissions;
            String version = permissions.getDescription().getVersion();
            if(version.contains("3.")) {
                // This shouldn't make any difference according to the Permissions API
                handler = PermissionsHandler.PERMISSIONS3;
            } else {
                handler = PermissionsHandler.PERMISSIONS;
            }
            log.info(CombatEventsLoot.plugName + " - Permissions hooked using: Permissions v" + version);
        } else {
            handler = PermissionsHandler.NONE;
            log.warning(CombatEventsLoot.plugName + " - A permission plugin was not detected.");
        }
    }

    public static boolean permission(Player player, String permission, boolean defaultPerm) {
        switch (handler) {
        case PERMISSIONSEX:
            return PermissionsEx.getPermissionManager().has(player, permission);
        case PERMISSIONS3:
            return ((Permissions) permissionPlugin).getHandler().has(player, permission);
        case PERMISSIONS:
            return ((Permissions) permissionPlugin).getHandler().has(player, permission);
        case GROUPMANAGER:
            return ((GroupManager) permissionPlugin).getWorldsHolder().getWorldPermissions(player).has(player, permission);
        case NONE:
            return defaultPerm;
        default:
            return defaultPerm;
        }
    }


    // User permissions
    // Checks if a player will be rewarded for killing a specific monster.
    public static boolean reward(Player player, String cName) {
        return permission(player, "combatevents.loot."+cName, true);
    }

    // Bonus Multiplier Permissions
    @SuppressWarnings({ "deprecation" })
    public static double multiplier(Player player) {
        switch (handler) {
        case PERMISSIONSEX:
            return PermissionsEx.getPermissionManager().getUser(player.getName()).getOptionDouble("cemultiplier", player.getWorld().getName(), 1);
        case PERMISSIONS3: {
        	try {
        		return Double.parseDouble(((Permissions) permissionPlugin).getHandler().getPermissionString(player.getWorld().getName(), player.getName(), "cemultiplier"));
        	} catch (Exception e) {
        		return 1.0;
        	}
        }
        case PERMISSIONS: 
            return ((Permissions) permissionPlugin).getHandler().getPermissionDouble(player.getWorld().getName(), player.getName(), "cemultiplier");
        case GROUPMANAGER:
            return ((GroupManager) permissionPlugin).getPermissionHandler().getUserPermissionDouble(player.getName(), "cemultipler");
        case NONE:
            return 1.0;
        default:
            return 1.0;
        }
    }
    
    public static boolean isInvalidHandler() {
        if (handler == PermissionsHandler.NONE)
            return true;
        else
            return false;
    }
    
}

