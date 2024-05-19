/**
 * Author: Madhukar Chavali (mchavali)
 * Email: mchavali@andrew.cmu.edu

 * The JokeApiClient class is responsible for fetching jokes from an external API.
 * It contains a static method fetchJoke which takes a joke type as an argument.
 * The method constructs an HTTP GET request to the official joke API and retrieves a joke based on the provided type.
 * It uses the Apache HTTPClient for making the HTTP request and Google's GSON library to parse the JSON response.
 * If the request is successful and a joke is retrieved, it is returned as a Joke object; otherwise, in case of an error, null is returned.

 * Dependencies:
 * - com.google.gson.Gson
 * - org.apache.http.client.methods.CloseableHttpResponse
 * - org.apache.http.client.methods.HttpGet
 * - org.apache.http.impl.client.CloseableHttpClient
 * - org.apache.http.impl.client.HttpClients
 * - org.apache.http.HttpEntity
 * - org.apache.http.util.EntityUtils

 * Example usage:
 * Joke randomJoke = JokeApiClient.fetchJoke("general");
 */
package com.example.demo3;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class JokeApiClient {

    public static Joke fetchJoke(String jokeType) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String API_ENDPOINT = String.format("https://official-joke-api.appspot.com/jokes/%s/random", jokeType);
            HttpGet get = new HttpGet(API_ENDPOINT);
            CloseableHttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            Gson gson = new Gson();
            Joke[] joke = gson.fromJson(result, Joke[].class);

            System.out.println("Joke fetched: " + joke);
            return joke.length > 0 ? joke[0] : null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}