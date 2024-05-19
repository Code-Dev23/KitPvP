package it.scopped.kitpvp.utils.cooldown;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

public class Cooldown {
    private final HashMap<UUID, Integer> cooldownMap = new HashMap<>();

    public void put(UUID uuid, Integer seconds) {
        cooldownMap.put(uuid, Integer.valueOf((int) (Instant.now().getEpochSecond() + seconds.intValue())));
    }

    public void flush(UUID uuid) {
        cooldownMap.remove(uuid);
    }

    public boolean isIn(UUID uuid) {
        if (!cooldownMap.containsKey(uuid))
            return false;
        return (cooldownMap.getOrDefault(uuid, Integer.valueOf(0)).intValue() > (int) Instant.now().getEpochSecond());
    }

    public int getTime(UUID uuid) {
        if (!cooldownMap.containsKey(uuid))
            return 0;
        return Math.max(0, (int) (cooldownMap.getOrDefault(uuid, 0) - Instant.now().getEpochSecond()));
    }
}