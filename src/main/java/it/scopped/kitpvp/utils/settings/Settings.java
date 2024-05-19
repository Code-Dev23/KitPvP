package it.scopped.kitpvp.utils.settings;

import it.scopped.kitpvp.KitPvP;
import lombok.Getter;

@Getter
public enum Settings {
    CombatLogSeconds("settings.combat_log", 0),
    PunchX("settings.punch.x", 0.0),
    PunchY("settings.punch.y", 0.0),
    FixPrice("settings.fix_price", 0),
    Blocks_CobWeb("settings.blocks.cobweb", 0),
    Blocks_Obsidian("settings.blocks.obsidian", 0),
    MoneyFirstJoin("settings.money_first_join", 0);

    private final String path;
    private final int intValue;
    private final double doubleValue;

    Settings(String path, int intValue) {
        this.path = path;
        this.intValue = KitPvP.get().getConfig().getInt(path, intValue);
        this.doubleValue = 0.0;
    }

    Settings(String path, double doubleValue) {
        this.path = path;
        this.intValue = 0;
        this.doubleValue = KitPvP.get().getConfig().getDouble(path, doubleValue);
    }
}