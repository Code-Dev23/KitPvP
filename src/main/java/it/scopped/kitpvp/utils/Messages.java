package it.scopped.kitpvp.utils;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@UtilityClass
public class Messages {
    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public String color(Player player, String text) {
        return PlaceholderAPI.setPlaceholders(player, color(text));
    }

    public void send(Player player, String message) {
        player.sendMessage(color(player, message));
    }

    public void send(Player player, String title, String subTitle) {
        player.sendTitle(color(player, title), color(player, subTitle));
    }
}