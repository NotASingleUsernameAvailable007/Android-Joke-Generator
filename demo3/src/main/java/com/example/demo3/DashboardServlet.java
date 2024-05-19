/**
 * Author: Madhukar Chavali (mchavali)
 * Email: mchavali@andrew.cmu.edu

 * DashboardServlet extends HttpServlet and handles GET requests to display a dashboard of joke analytics.
 * It interacts with a MongoDB database to fetch and aggregate data about jokes, phone models, and API response times.
 * The doGet method performs several MongoDB aggregations to retrieve this data and sets them as attributes in the request.
 * These attributes are then used in a JSP page to display the dashboard.
 * The convertDocumentListToMapList method converts MongoDB Document objects into a list of maps for easier handling in JSP.

 * This servlet is annotated with @WebServlet and mapped to the "/dashboard" URL pattern.

 */

package com.example.demo3;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "dashboardServlet", value = "/dashboard")
public class DashboardServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MongoDatabase database = MongoDBClient.getClient().getDatabase("jokesdb");
        MongoCollection<Document> myCollection = database.getCollection("logJokes");


        /**
         * Converts a list of MongoDB Document objects into a list of Map<String, Object>.
         * This facilitates easier data handling and manipulation in JSP pages.
         *
         * @param documentList List of Document objects to be converted.
         * @return List of Map<String, Object> converted from Document objects.
         */
            // Aggregate top phone models
            List<Document> topPhoneModels  = myCollection.aggregate(
                    Arrays.asList(
                            Aggregates.match(Filters.ne("phoneModel", null)),
                            Aggregates.group("$phoneModel", Accumulators.sum("count", 1)),
                            Aggregates.sort(Sorts.descending("count")),
                            Aggregates.limit(5),
                            Aggregates.project(Projections.fields(
                                    Projections.excludeId(),
                                    Projections.include("count"),
                                    Projections.computed("phoneModel", "$_id")
                            ))
                    )).into(new ArrayList<>());

             List<Map<String, Object>> topPhoneModelsList = convertDocumentListToMapList(topPhoneModels);
             request.setAttribute("topPhoneModels", topPhoneModelsList);

            List<Document> getMostFrequentJokeTypes = myCollection.aggregate(
                    Arrays.asList(
                            Aggregates.match(Filters.ne("jokeResponse.type", null)),
                            Aggregates.group("$jokeResponse.type", Accumulators.sum("count", 1)),
                            Aggregates.sort(Sorts.descending("count"))
                            ,Aggregates.project(Projections.fields(
                                    Projections.excludeId(),
                                    Projections.include("count"),
                                    Projections.computed("jokeType", "$_id")
                            ))
                    )).into(new ArrayList<>());

            List<Map<String, Object>> mostFrequentJokeTypesList  = convertDocumentListToMapList(getMostFrequentJokeTypes);
            request.setAttribute("getMostFrequentJokeTypes", mostFrequentJokeTypesList);

            // Aggregate for average API response time
            List<Document> responseTimes = myCollection.aggregate(
                    Arrays.asList(
                            Aggregates.project(
                                    Projections.fields(
                                            Projections.computed("responseTime", new Document("$subtract", Arrays.asList("$apiRequestEnd", "$apiRequestStart")))
                                    )
                            ),
                            Aggregates.group(null, Accumulators.avg("avgResponseTime", "$responseTime"))
                    )
            ).into(new ArrayList<>());

            List<Map<String, Object>> responseTimesList   = convertDocumentListToMapList(responseTimes);
            request.setAttribute("responseTimes", responseTimesList );

        List<Document> logs = myCollection.find().into(new ArrayList<>());
        List<Map<String, Object>> logsList = convertDocumentListToMapList(logs);
        request.setAttribute("logs", logsList);

        // Forward to JSP page
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/dashboard.jsp");
        dispatcher.forward(request, response);
    }

    private List<Map<String, Object>> convertDocumentListToMapList(List<Document> documentList) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Document doc : documentList) {
            mapList.add(new HashMap<>(doc));
        }
        return mapList;
    }
}
