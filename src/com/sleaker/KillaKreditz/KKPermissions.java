/**
 * 
 */
package com.sleaker.KillaKreditz;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ru.tehkode.permissions.bukkit.*;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.anjocaido.groupmanager.GroupManager;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class KKPermissions {
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
            log.info(KillaKreditz.plugName + " - Permissions hooked using: PermissionsEx v" + version);
        } else if (groupManager != null) {
            permissionPlugin = groupManager;
            handler = PermissionsHandler.GROUPMANAGER;
            String version = groupManager.getDescription().getVersion();
            log.info(KillaKreditz.plugName + " - Permissions hooked using: GroupManager v" + version);
        } else if (permissions != null) {
            permissionPlugin = permissions;
            String version = permissions.getDescription().getVersion();
            if(version.contains("3.")) {
                // This shouldn't make any difference according to the Permissions API
                handler = PermissionsHandler.PERMISSIONS3;
            } else {
                handler = PermissionsHandler.PERMISSIONS;
            }
            log.info(KillaKreditz.plugName + " - Permissions hooked using: Permissions v" + version);
        } else {
            handler = PermissionsHandler.NONE;
            log.warning(KillaKreditz.plugName + " - A permission plugin was not detected.");
        }
    }

    @SuppressWarnings("static-access")
    public static boolean permission(Player player, String permission, boolean defaultPerm) {
        switch (handler) {
        case PERMISSIONSEX:
            return ((PermissionsEx) permissionPlugin).getPermissionManager().has(player, permission);
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

    // Admin Permissions 
    // In case we need administrator permissions for anything
    public static boolean admin(Player player) {
        return permission(player, "kkreditz.admin", player.isOp());
    }

    // User permissions
    // Checks if a player will be rewarded for killing a specific monster.
    public static boolean reward(Player player, String cName) {
        return permission(player, "kkreditz.reward."+cName, true);
    }

    // Bonus Multiplier Permissions
    public static double multiplier(Player player) {
        List<Double> multipliers = new ArrayList<Double>(5);
        if (permission(player, "kkreditz.multipler.alt1", true))
            multipliers.add(KillaKreditz.altMultipliers[0]);
        if (permission(player, "kkreditz.multipler.alt2", true))
            multipliers.add(KillaKreditz.altMultipliers[1]);
        if (permission(player, "kkreditz.multipler.alt3", true))
            multipliers.add(KillaKreditz.altMultipliers[2]);
        if (permission(player, "kkreditz.multiplier.triple", true))
            multipliers.add(3.0);
        if (permission(player, "kkreditz.multiplier.double", true))
            multipliers.add(2.0);

        double highestMultiplier = 1.0;
        
        for (double multiple : multipliers) {
            if (multiple > highestMultiplier)
                highestMultiplier = multiple;
        }
        return highestMultiplier;
    }
    
    public static boolean isInvalidHandler() {
        if (handler == PermissionsHandler.NONE)
            return true;
        else
            return false;
    }
    
}

