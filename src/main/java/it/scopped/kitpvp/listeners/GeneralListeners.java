package it.scopped.kitpvp.listeners;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.utils.Messages;
import it.scopped.kitpvp.utils.cooldown.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GeneralListeners implements Listener {
    private static final Cooldown pearlCooldown = new Cooldown();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.dispatchCommand(player, "spawn");
    }

    @EventHandler
    public void onDrank(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        if (e.getItem().getType().equals(Material.POTION))
            Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(KitPvP.get(), () -> {
                player.getInventory().remove(Material.GLASS_BOTTLE);
            }, 1L);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            if (player.getItemInHand().getType().equals(Material.ENDER_PEARL)) {
                if (pearlCooldown.isIn(player.getUniqueId())) {
                    Messages.send(player, "&eDevi aspettare ancora %seconds% secondi per utilizzare l'enderpearl!".replace("%seconds%", String.valueOf(pearlCooldown.getTime(player.getUniqueId()))));
                    event.setCancelled(true);
                    return;
                }
                pearlCooldown.put(player.getUniqueId(), KitPvP.get().getConfig().getInt("settings.enderpearl_cooldown"));
            }
    }
}
