package it.scopped.kitpvp;

import it.scopped.kitpvp.combatlog.CombatLogListeners;
import it.scopped.kitpvp.combatlog.CombatLogManager;
import it.scopped.kitpvp.commands.SpawnCommand;
import it.scopped.kitpvp.commands.TrashCommand;
import it.scopped.kitpvp.commands.admin.SetSpawnCommand;
import it.scopped.kitpvp.hooks.PlaceholdersHook;
import it.scopped.kitpvp.listeners.BlocksListeners;
import it.scopped.kitpvp.listeners.DataListeners;
import it.scopped.kitpvp.listeners.GeneralListeners;
import it.scopped.kitpvp.listeners.PunchListeners;
import it.scopped.kitpvp.mongo.MongoDBManager;
import it.scopped.kitpvp.objects.tab.TabUpdater;
import it.scopped.kitpvp.player.struct.PlayerDataManager;
import it.scopped.kitpvp.workload.WorkloadThread;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Logger;

@Getter
public final class KitPvP extends JavaPlugin {

    public static final Logger LOG = Bukkit.getLogger();
    private static KitPvP instance;
    private WorkloadThread workloadThread;
    private CombatLogManager combatLogManager;
    private MongoDBManager mongoDB;
    private PlayerDataManager playerDataManager;

    public static KitPvP get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        loadManagers();
        startTasks();
        loadCommandsAndListeners();
        new PlaceholdersHook(this).register();
    }

    private void loadManagers() {
        workloadThread = new WorkloadThread();
        mongoDB = new MongoDBManager();
        combatLogManager = new CombatLogManager();
        playerDataManager = new PlayerDataManager(this);
    }

    private void loadCommandsAndListeners() {
        // COMMANDS
        Arrays.asList(
                new SpawnCommand(),
                new SetSpawnCommand(),
                new TrashCommand()
        ).forEach(command -> getCommandMap().register(getName(), command));

        // LISTENERS
        Arrays.asList(
                new BlocksListeners(this),
                new DataListeners(this),
                new CombatLogListeners(),
                new PunchListeners(),
                new GeneralListeners()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void startTasks() {
        Bukkit.getScheduler().runTaskTimer(this, this.workloadThread, 0L, 1L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new TabUpdater(), 10L, 10L);
        Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getWorlds().forEach(world -> world.getEntitiesByClass(Item.class).forEach(Item::remove)), 0L, this.getConfig().getInt("settings.scavenger") * 20L);
    }

    public CommandMap getCommandMap() {
        try {
            Object server = Bukkit.getServer();
            Method getCommandMapMethod = server.getClass().getDeclaredMethod("getCommandMap");
            getCommandMapMethod.setAccessible(true);
            return (CommandMap) getCommandMapMethod.invoke(server, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
