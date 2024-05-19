package it.scopped.kitpvp.listeners;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.workload.impl.CobwebWorkload;
import it.scopped.kitpvp.workload.impl.ObsidianWorkload;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@RequiredArgsConstructor
public class BlocksListeners implements Listener {
    private final KitPvP main;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block blockPlaced = event.getBlockPlaced();

        if (blockPlaced.getType().equals(Material.WEB)) {
            main.getWorkloadThread().addWorkload(new CobwebWorkload(blockPlaced.getLocation()));
        }
        if (blockPlaced.getType().equals(Material.OBSIDIAN)) {
            main.getWorkloadThread().addWorkload(new ObsidianWorkload(blockPlaced.getLocation()));
        }
    }
}
