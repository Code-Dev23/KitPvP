package it.scopped.kitpvp.commands;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.utils.Messages;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends Command {

    public SpawnCommand() {
        super("spawn");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            KitPvP.LOG.warning("This command can't be run from the console.");
            return false;
        }
        Player player = (Player) commandSender;
        Location loc = (Location) KitPvP.get().getConfig().get("spawn");
        if (loc == null) {
            Messages.send(player, "&cLo spawn non e' stato trovato!");
            return false;
        }
        player.teleport(loc);
        return true;
    }
}
