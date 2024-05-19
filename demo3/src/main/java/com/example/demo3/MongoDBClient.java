/**
 * Author: Madhukar Chavali (mchavali)
 * Email: mchavali@andrew.cmu.edu

 * The MongoDBClient class serves as a utility for establishing a connection to a MongoDB database.
 * It encapsulates the logic for creating a MongoClient instance which is required to interact with the database.
 * The class ensures that only one instance of MongoClient is created (Singleton pattern),
 * and this instance is reused for all database operations throughout the application.

 * This class uses the MongoDB Java driver to establish connections to the database.
 * The connection string is stored as a private static final variable.

 * Dependencies:
 * - com.mongodb.client.MongoClient
 * - com.mongodb.client.MongoClients
 */
package com.example.demo3;
import com.mongodb.client.*;


public class MongoDBClient {
    private static final String CONNECTION_STRING = "mongodb+srv://madhukarchavali:zuDB0WRnXla6lTPv@cluster0.0gkaqc8.mongodb.net/?retryWrites=true&w=majority";
    private static MongoClient mongoClient;


    public static  MongoClient getClient() {
        if(mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_STRING);
        }
        return mongoClient;
    }

}

