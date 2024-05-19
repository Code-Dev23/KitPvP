package it.scopped.kitpvp.combatlog;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.utils.Messages;
import it.scopped.kitpvp.utils.settings.Settings;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class CombatLogListeners implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();

        if (damager instanceof Player && damaged instanceof Player) {
            Player player = (Player) damager;
            Player target = (Player) damaged;

            putInCombat(player, Settings.CombatLogSeconds.getIntValue());
            putInCombat(target, Settings.CombatLogSeconds.getIntValue());
        }

        if (damager instanceof Arrow) {
            Arrow arrow = (Arrow) damager;
            ProjectileSource shooter = arrow.getShooter();

            if (shooter instanceof Player && damaged instanceof Player) {
                Player arrowShooter = (Player) shooter;
                Player target = (Player) damaged;

                putInCombat(arrowShooter, Settings.CombatLogSeconds.getIntValue());
                putInCombat(target, Settings.CombatLogSeconds.getIntValue());
            }
        }

        if (damager instanceof FishHook) {
            FishHook hook = (FishHook) damager;
            ProjectileSource shooter = hook.getShooter();

            if (shooter instanceof Player && damaged instanceof Player) {
                Player hookShooter = (Player) shooter;
                Player target = (Player) damaged;

                putInCombat(hookShooter, Settings.CombatLogSeconds.getIntValue());
                putInCombat(target, Settings.CombatLogSeconds.getIntValue());
            }
        }
    }

    private void putInCombat(Player player, int combatTime) {
        if (!player.hasPermission("kitpvp.combat.bypass"))
            KitPvP.get().getCombatLogManager().getCombatTime().put(player.getUniqueId(), combatTime);
    }


    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (KitPvP.get().getCombatLogManager().getCombatTime().isIn(player.getUniqueId())) {
            KitPvP.get().getServer().getScheduler().scheduleSyncDelayedTask(KitPvP.get(), () -> {
                dropInventory(player);
                player.damage(9999.0D);
                player.spigot().respawn();
                KitPvP.get().getCombatLogManager().getCombatTime().flush(player.getUniqueId());
            }, 1L);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExecuteCommand(PlayerCommandPreprocessEvent event) {
        if (KitPvP.get().getCombatLogManager().getCombatTime().isIn(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("test");
            Messages.send(event.getPlayer(), "&cNon puoi eseguire nessun comando in combattimento!");
        }
    }

    private void dropInventory(Player player) {
        for (ItemStack item : player.getInventory().getContents())
            if (item != null && item.getType() != Material.AIR)
                player.getWorld().dropItemNaturally(player.getLocation(), item);
        for (ItemStack item : player.getInventory().getArmorContents())
            if (item != null && item.getType() != Material.AIR)
                player.getWorld().dropItemNaturally(player.getLocation(), item);
    }
}