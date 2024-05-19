package it.scopped.kitpvp.commands;

import it.scopped.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrashCommand extends Command {
    public TrashCommand() {
        super("trash");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            KitPvP.LOG.warning("This command can't be run from the console.");
            return false;
        }
        Player player = (Player) commandSender;
        player.openInventory(Bukkit.createInventory(null, KitPvP.get().getConfig().getInt("settings.trash_gui.size"), KitPvP.get().getConfig().getString("settings.trash_gui.title")));
        return true;
    }
}