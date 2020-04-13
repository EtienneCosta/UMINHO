package DAO;


import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;

import java.sql.Connection;

public class MongoConnect {
    public static MongoClient mongoClient;
    public static MongoDatabase db;


    public  MongoConnect() {
        try { // Open a new connection
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("livraria");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public MongoDatabase getDb(){
        return db;
    }

    public void close() {
        try {
            mongoClient.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
