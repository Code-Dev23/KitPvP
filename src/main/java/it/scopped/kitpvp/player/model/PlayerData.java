package it.scopped.kitpvp.player.model;

import it.scopped.kitpvp.KitPvP;
import it.scopped.kitpvp.utils.tasks.Tasks;
import lombok.Data;
import org.bson.Document;

import java.util.UUID;

@Data
public class PlayerData {
    private UUID uuid;
    private int kills, deaths, streaks;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        if (!KitPvP.get().getPlayerDataManager().getPlayersCache().containsKey(uuid))
            KitPvP.get().getPlayerDataManager().put(this);

        load(true);
    }

    public void save(boolean async) {
        if (async)
            Tasks.runAsync(this::save);
        else
            save();
    }

    public void load(boolean async) {
        if (async)
            Tasks.runAsync(this::load);
        else
            load();
    }

    public void load() {
        Document document = KitPvP.get().getMongoDB().findPlayer(this.uuid);
        System.out.println(document);
        if (document != null) {
            if (this.uuid == null)
                this.uuid = UUID.fromString(document.getString("uuid"));
            this.kills = document.getInteger("kills");
            this.deaths = document.getInteger("deaths");
            this.streaks = document.getInteger("killstreaks");
        } else {
            this.kills = 0;
            this.deaths = 0;
            this.streaks = 0;
            save();
        }
    }

    public void save() {
        Document document = KitPvP.get().getMongoDB().findPlayer(this.uuid);
        if (document == null) document = new Document();
        document.put("uuid", this.uuid.toString());
        document.put("kills", Integer.valueOf(this.kills));
        document.put("deaths", Integer.valueOf(this.deaths));
        document.put("killstreaks", Integer.valueOf(this.streaks));
        KitPvP.get().getMongoDB().replacePlayer(this, document);
    }
}
