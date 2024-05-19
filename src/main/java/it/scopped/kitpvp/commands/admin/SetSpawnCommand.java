package it.scopped.kitpvp.commands.admin;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends Command {
    public SetSpawnCommand() {
        super("setspawn");
        setPermission("kitpvp.admin.setspawn");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            KitPvP.LOG.warning("This command can't be run from the console.");
            return false;
        }
        Player player = (Player) commandSender;

        KitPvP.get().getConfig().set("spawn", player.getLocation());
        KitPvP.get().saveConfig();
        Messages.send(player, "&aSpawn configurato con successo.");
        return true;
    }
}
