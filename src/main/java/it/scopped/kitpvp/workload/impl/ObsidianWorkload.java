package it.scopped.kitpvp.workload.impl;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.workload.Workload;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@RequiredArgsConstructor
public class ObsidianWorkload implements Workload {
    private final Location location;

    @Override
    public void compute() {
        Block block = location.getBlock();

        Bukkit.getScheduler().runTaskLater(KitPvP.get(), () -> {
            if (block.getType().equals(Material.OBSIDIAN)) block.setType(Material.AIR);
        }, KitPvP.get().getConfig().getInt("settings.blocks.obsidian") * 20L);
    }
}