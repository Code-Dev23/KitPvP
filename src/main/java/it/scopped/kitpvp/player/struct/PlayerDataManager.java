package it.scopped.kitpvp.player.struct;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.player.model.PlayerData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PlayerDataManager {
    private final KitPvP main;
    private final Map<UUID, PlayerData> playersCache = new HashMap<>();

    public void put(PlayerData playerData) {
        playersCache.put(playerData.getUuid(), playerData);
    }

    public PlayerData findDataByUUID(UUID uuid) {
        PlayerData data = playersCache.get(uuid);
        if (data == null) {
            data = new PlayerData(uuid);
            data.load(true);
        }
        return data;
    }
}