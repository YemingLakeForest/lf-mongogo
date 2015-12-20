package com.lf.mongogo.m101j.hw23;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.io.StringWriter;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;

public class Homework23 {


    public static void main(String[] args) {

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("students").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> collection = db.getCollection("grades");

        Bson filter = eq("type", "homework");
        Bson sort = orderBy(ascending("student_id"), ascending("score"));

        MongoCursor<Document> cursor = collection.find(filter)
                                                 .sort(sort).iterator();

        int id = -1;

        while (cursor.hasNext()) {
            Document entry = cursor.next();
            int studentId = (int) entry.get("student_id");

            if (id != studentId) {
                System.out.println("removing this record!!!");
                printJson(entry);
                ObjectId objectId = entry.getObjectId("_id");

                collection.deleteOne(eq("_id", objectId));
            }
            id = studentId;
        }
    }

    public static void printJson(Document document) {
        JsonWriter jsonWriter = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, false));

        new DocumentCodec().encode(jsonWriter, document, EncoderContext.builder().isEncodingCollectibleDocument(true).build());

        System.out.println(jsonWriter.getWriter());
        System.out.flush();
    }

}
