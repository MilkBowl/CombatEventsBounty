/**
 * 
 */
package com.sleaker.KillaKreditz;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import com.iConomy.iConomy;
import com.iConomy.system.Account;

/**
 * @author sleaker
 *
 */
public class KKKreditzHandler {
    public static EconomyHandler handler;
    public static Logger log = Logger.getLogger("Minecraft");

    public enum EconomyHandler {
        ICONOMY5, NONE
    }

    public static void initialize(Server server) {
        Plugin iConomy5 = null;
        if (server.getPluginManager().getPlugin("iConomy").getDescription().getVersion().contains("5.")) {
            iConomy5 = server.getPluginManager().getPlugin("iConomy");
        }

        if (iConomy5 != null) {
            handler = EconomyHandler.ICONOMY5;
            String version = iConomy5.getDescription().getVersion();
            log.info(KillaKreditz.plugName + " - Economy enabled using: iConomy v" + version);
        } else {
            handler = EconomyHandler.NONE;
            log.warning(KillaKreditz.plugName + " - An economy plugin isn't loaded.");
        }
    }

    public static void rewardPlayer(String player, double amount) {
        if (handler == EconomyHandler.ICONOMY5) {
            Account pAccount = iConomy.getAccount(player);
            if (pAccount == null) {
                log.warning(KillaKreditz.plugName + " - Error fetching iConomy account for " + player);
            } else
                iConomy.getAccount(player).getHoldings().add(amount);
        }
    }

    public static EconomyHandler getHandler() {
        return handler;
    }

    public static void setHandler(EconomyHandler handler) {
        KKKreditzHandler.handler = handler;
    }
    
    public static String formatCurrency(double amount) {
        if (handler == EconomyHandler.ICONOMY5)
            return iConomy.format(amount);
        else
            return amount+"";
    }
    
    public static boolean isInvalidHandler() {
        if (handler == EconomyHandler.NONE)
            return true;
        else
            return false;
    }
}
