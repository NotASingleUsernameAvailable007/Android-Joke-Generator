/**
 * Author: Madhukar Chavali (mchavali)
 * Email: mchavali@andrew.cmu.edu

 * The JokeServlet class extends HttpServlet and handles GET requests to fetch jokes.
 * On initialization, it establishes a connection to the MongoDB database and retrieves the collection to log jokes.
 * The doGet method handles incoming requests by fetching a joke from the JokeApiClient based on the requested joke type.
 * It then logs the joke along with request and response metadata to the MongoDB collection.
 * The logJokeToMongo static method is responsible for constructing and inserting the log document into MongoDB.

 * This servlet is annotated with @WebServlet and mapped to the "/joke-servlet" URL pattern.
 */
package com.example.demo3;

import java.io.*;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.google.gson.Gson;
import org.bson.Document;


@WebServlet(name = "jokeServlet", value = "/joke-servlet")
public class JokeServlet extends HttpServlet {
    private String message;
    MongoDatabase myMongoDb;

    static MongoCollection<Document> myCollection;

    public void init() {
        message = "Fetching Joke...";
        myMongoDb = MongoDBClient.getClient().getDatabase("jokesdb");
        myCollection = myMongoDb.getCollection("logJokes");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        long requestReceivedTime = System.currentTimeMillis();

        String jokeType = request.getParameter("jokeType");

        long apiRequestStart = System.currentTimeMillis();
        Joke joke = JokeApiClient.fetchJoke(jokeType);
        long apiRequestEnd = System.currentTimeMillis();


        long responseTime = System.currentTimeMillis();


        logJokeToMongo(request, requestReceivedTime, apiRequestStart,apiRequestEnd, joke, responseTime);
        response.setContentType("application/json");



        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String jokeJson = gson.toJson(joke);
        out.println(jokeJson );
        out.flush();
        out.close();
    }

    public void destroy() {
    }

    /**
     * Logs the joke and request/response details to a MongoDB collection.
     *
     * This static method creates a document with various information about the joke request and response.
     * It captures the times at which the request was received, when the API call started and ended,
     * and the time at which the response was generated. Additionally, it logs details about the joke itself
     * such as its type, setup, and punchline. If no joke is fetched, it records this information as well.
     * It also captures the client's phone model from the request header.
     *
     * The constructed document is then inserted into a specified MongoDB collection for logging purposes.
     *
     * @param request The HttpServletRequest object, representing the incoming joke request.
     * @param requestReceivedTime The timestamp (in milliseconds) when the request was received.
     * @param apiRequestStart The timestamp (in milliseconds) when the API request to fetch the joke started.
     * @param apiRequestEnd The timestamp (in milliseconds) when the API request to fetch the joke ended.
     * @param joke The Joke object fetched from the API. It can be null if no joke was fetched.
     * @param responseTime The timestamp (in milliseconds) when the response was ready to be sent.
     */
    public static void logJokeToMongo(HttpServletRequest request, long requestReceivedTime,
                                      long apiRequestStart,long apiRequestEnd, Joke joke, long responseTime) {

        Document logDoc = new Document();
        logDoc.append("requestReceivedTime", requestReceivedTime);
        logDoc.append("apiRequestStart", apiRequestStart);
        logDoc.append("apiRequestEnd", apiRequestEnd);

        Document jokeResponse = new Document();
        if (joke != null) {
            jokeResponse.append("type", joke.getType())
                    .append("setup", joke.getSetup())
                    .append("punchline", joke.getPunchline());
        } else {
            jokeResponse.append("type", "No joke fetched")
                    .append("setup", "")
                    .append("punchline", "");
        }
        logDoc.append("jokeType", joke.getType());
        logDoc.append("jokeResponse", jokeResponse);
        logDoc.append("responseTime", responseTime);

        String phoneModel = request.getHeader("Phone-Model");
        logDoc.append("phoneModel", phoneModel);

        myCollection.insertOne(logDoc);
    }
}