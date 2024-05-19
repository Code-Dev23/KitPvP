package it.scopped.kitpvp.hooks;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.player.model.PlayerData;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class PlaceholdersHook extends PlaceholderExpansion {
    private final KitPvP main;

    @Override
    public @NotNull String getIdentifier() {
        return "kitpvp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Scopped_";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        PlayerData playerData = main.getPlayerDataManager().findDataByUUID(player.getUniqueId());

        switch (params) {
            case "in_combat":
                return String.valueOf(main.getCombatLogManager().combatTagged(player));
            case "combat_time":
                return String.valueOf(main.getCombatLogManager().getCombatTime().getTime(player.getUniqueId()));
            case "kills":
                return String.valueOf(playerData.getKills());
            case "deaths":
                return String.valueOf(playerData.getDeaths());
            case "killstreaks":
                return String.valueOf(playerData.getStreaks());
        }
        return "###";
    }
}
