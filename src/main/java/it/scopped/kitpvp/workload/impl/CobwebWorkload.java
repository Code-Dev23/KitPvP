package it.scopped.kitpvp.workload.impl;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.workload.Workload;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@RequiredArgsConstructor
public class CobwebWorkload implements Workload {
    private final Location centerLocation;

    @Override
    public void compute() {
        Block centerBlock = centerLocation.getBlock();

        int centerX = centerBlock.getX();
        int centerY = centerBlock.getY();
        int centerZ = centerBlock.getZ();

        int[][] offsets = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0}
        };

        for (int[] offset : offsets) {
            int offsetX = offset[0];
            int offsetZ = offset[1];

            Block block = centerBlock.getWorld().getBlockAt(centerX + offsetX, centerY, centerZ + offsetZ);
            if (block.getType() == Material.AIR)
                block.setType(Material.WEB);
        }

        Bukkit.getScheduler().runTaskLater(KitPvP.get(), () -> {
            for (int[] offset : offsets) {
                int offsetX = offset[0];
                int offsetZ = offset[1];

                Block block = centerBlock.getWorld().getBlockAt(centerX + offsetX, centerY, centerZ + offsetZ);
                if (block.getType() == Material.WEB)
                    block.setType(Material.AIR);
            }

            centerLocation.getBlock().setType(Material.AIR);
        }, KitPvP.get().getConfig().getInt("settings.blocks.cobweb") * 20L);
    }
}