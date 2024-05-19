package it.scopped.kitpvp.utils.tasks;

import it.scopped.kitpvp.KitPvP;

import java.util.Objects;

public class Tasks {
    public static void run(Callable callable) {
        Objects.requireNonNull(callable);
        KitPvP.get().getServer().getScheduler().runTask(KitPvP.get(), callable::call);
    }

    public static void runAsync(Callable callable) {
        Objects.requireNonNull(callable);
        KitPvP.get().getServer().getScheduler().runTaskAsynchronously(KitPvP.get(), callable::call);
    }

    public interface Callable {
        void call();
    }
}