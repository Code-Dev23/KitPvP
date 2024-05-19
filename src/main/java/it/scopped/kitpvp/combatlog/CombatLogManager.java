package it.scopped.kitpvp.combatlog;

import it.scopped.kitpvp.utils.cooldown.Cooldown;
import org.bukkit.entity.Player;

public class CombatLogManager {
    private final Cooldown combatTime = new Cooldown();

    public Cooldown getCombatTime() {
        return this.combatTime;
    }

    public boolean combatTagged(Player player) {
        return this.combatTime.isIn(player.getUniqueId());
    }
}