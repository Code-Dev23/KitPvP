package it.scopped.kitpvp.listeners;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.utils.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PunchListeners implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerVelocity(PlayerVelocityEvent e) {
        if (e.isCancelled())
            return;
        Player p = e.getPlayer();
        EntityDamageEvent cause = p.getLastDamageCause();
        if (cause == null || cause.isCancelled() || !(cause instanceof EntityDamageByEntityEvent))
            return;
        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) cause;
        if (!(event.getDamager() instanceof Arrow))
            return;
        Arrow a = (Arrow) event.getDamager();
        if (a.getShooter() != p)
            return;
        Vector vector = getVector(e.getPlayer(), a, (a.getLocation().getPitch() < -85.0F));
        e.setVelocity(vector.setX(vector.getX() * ((a.getLocation().getPitch() < -85.0F) ? 1.0F : -1.0F)));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onArrowShoot(EntityShootBowEvent e) {
        if (!(e.getProjectile() instanceof Arrow))
            return;
        Arrow a = (Arrow) e.getProjectile();
        if (!(a.getShooter() instanceof Player))
            return;
        Player shooter = (Player) a.getShooter();
        a.setVelocity(getArrowStraight(a, shooter));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onArrowHit(EntityDamageByEntityEvent e) {
        if (e.isCancelled())
            return;
        if (!(e.getEntity() instanceof Player))
            return;
        if (!(e.getDamager() instanceof Arrow))
            return;
        Arrow a = (Arrow) e.getDamager();
        if (!(a.getShooter() instanceof Player))
            return;
        Player target = (Player) e.getEntity();
        Player shooter = (Player) a.getShooter();
        if (target != shooter)
            return;
        Bukkit.getScheduler().runTaskLaterAsynchronously(KitPvP.get(), () -> target.setNoDamageTicks(0), 2L);
        target.setVelocity(getVector(target, a, (a.getLocation().getPitch() < -85.0F)));
    }

    private Vector getArrowStraight(Arrow arrow, Player player) {
        return player.getLocation().getDirection().multiply(arrow.getVelocity().length());
    }

    private Vector getVector(Player target, Arrow arrow, boolean switcher) {
        ItemStack bow = target.getItemInHand();
        int lvl = bow.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);
        return (switcher ? target : arrow).getLocation().getDirection().setY(0.0D).multiply(Math.max(1, lvl) * Settings.PunchX.getDoubleValue())
                .setY(Settings.PunchY.getDoubleValue());
    }
}