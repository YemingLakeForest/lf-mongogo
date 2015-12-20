package com.lf.mongogo.m101j.hw23;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
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
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;

public class Homework23 {


    public static void main(String[] args) {

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).build();
        MongoClient client = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("students").withReadPreference(ReadPreference.secondary());
        MongoCollection collection = db.getCollection("grades");

        Bson filter = new Document("type", "homework");
        Bson sort = orderBy(ascending("student_id"), ascending("score"));

        List<Document> all = (List<Document>) collection
                .find(filter)
                .sort(sort)
                .into(new ArrayList<Document>());

        int id = -1;

        for (Document document : all) {

            int studentId = (int) document.get("student_id");

            if (id != studentId) {
                System.out.println("removing this record!!!");
                printJson(document);
                ObjectId objectId = document.getObjectId("_id");

                collection.deleteOne(new Document("_id", objectId));
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
