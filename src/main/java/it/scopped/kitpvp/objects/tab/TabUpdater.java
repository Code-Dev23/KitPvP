package it.scopped.kitpvp.objects.tab;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.utils.Messages;
import org.bukkit.Bukkit;

public class TabUpdater implements Runnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            TabComponent component = new TabComponent();

            KitPvP.get().getConfig().getStringList("tablist.header")
                    .forEach(header -> component.addLineToHeader(Messages.color(player, header)));
            KitPvP.get().getConfig().getStringList("tablist.footer")
                    .forEach(footer -> component.addLineToFooter(Messages.color(player, footer)));

            component.sendToPlayer(player);
        });
    }
}
