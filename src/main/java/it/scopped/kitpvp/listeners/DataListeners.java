package it.scopped.kitpvp.listeners;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.player.model.PlayerData;
import it.scopped.kitpvp.utils.Messages;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

@RequiredArgsConstructor
public class DataListeners implements Listener {
    private final KitPvP main;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        main.getPlayerDataManager().put(new PlayerData(player.getUniqueId()));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerData playerData = main.getPlayerDataManager().findDataByUUID(player.getUniqueId());
        if (playerData != null) {
            playerData.setDeaths(playerData.getDeaths() + 1);
            playerData.setStreaks(0);
            playerData.save(true);
            player.spigot().respawn();
            main.getCombatLogManager().getCombatTime().flush(player.getUniqueId());
        }
        Player killer = player.getKiller();

        killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 7, 4));
        if(killer != null) {
            PlayerData killerData = main.getPlayerDataManager().findDataByUUID(killer.getUniqueId());
            if (killerData != null) {
                killerData.setKills(killerData.getKills() + 1);
                killerData.setStreaks(killerData.getStreaks() + 1);
                killerData.save(true);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location loc = (Location) KitPvP.get().getConfig().get("spawn");
        if (loc == null) {
            Messages.send(player, "&cLo spawn non e' stato trovato!");
            return;
        }
        event.setRespawnLocation(loc);
    }
}