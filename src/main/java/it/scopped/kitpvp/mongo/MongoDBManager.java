package it.scopped.kitpvp.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import it.scopped.kitpvp.player.model.PlayerData;
import lombok.Getter;
import org.bson.Document;

import java.util.UUID;

@Getter
public class MongoDBManager {
    private final MongoClient client;
    private final MongoDatabase database;
    private final MongoCollection<Document> players;

    public MongoDBManager() {
        this.client = new MongoClient();
        this.database = client.getDatabase("test");
        this.players = database.getCollection("players");
    }

    public Document findPlayer(UUID uuid) {
        return this.players.find(Filters.eq("uuid", uuid.toString())).first();
    }

    public void replacePlayer(PlayerData data, Document document) {
        this.players.replaceOne(Filters.eq("uuid", data.getUuid().toString()), document, (new ReplaceOptions())
                .upsert(true));
    }

    public void insertPlayer(Document document) {
        this.players.insertOne(document);
    }
}