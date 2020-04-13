package DAO;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

public class Mongo {

    public static MongoClient mongoClient;
    public static DB db;

    public static void main(String[] args) {

        try { // Open a new connection
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDB("livraria");
        }  catch (Exception e) {
            e.printStackTrace();
        }

        // Print out all the collections in the users database
        System.out.println("Printing all collections...");
        Set<String> colls = db.getCollectionNames();
        for (String s : colls) { System.out.println(s); }

        // Get the casey collection
        DBCollection coll = db.getCollection("livro");

        // Create a new document
        System.out.println("\nAdding document to database...");
        ArrayList<String> languages = new ArrayList<>();
        languages.add("Ruby");
        languages.add("Java");
        languages.add("Groovy");

        BasicDBObject doc = new BasicDBObject("ISBN:", "Casey Scarborough").
                append("titulo", 22).
                append("editora", "caseyscarborough@gmail.com").
                append("languages", languages);

        // Insert the document into the collection
        coll.insert(doc);

        // Retrieve the first document in the collection
        System.out.println("Retrieving document from database...");
        DBObject myDoc = coll.findOne();
        System.out.println(myDoc);

        // Retrieve all documents from the collection
        DBCursor cursor = coll.find();

        try { // Use a cursor to get all documents.
            while(cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

        // Retrieve a document using a query.
        BasicDBObject query = new BasicDBObject("name", "Casey Scarborough");
        cursor = coll.find(query);

        System.out.println("\nPrinting query results...");
        // Print each result.
        try {
            while(cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

        // Get a list of databases.
        System.out.println("\nPrinting a list of databases...");
        try {
            mongoClient = new MongoClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String s : mongoClient.getDatabaseNames()) {
            System.out.println(s);
        }
    }
}