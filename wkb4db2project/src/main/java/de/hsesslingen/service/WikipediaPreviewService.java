package de.hsesslingen.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class WikipediaPreviewService {

    private static final String API_URL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=info&inprop=url&titles=";

    public String getWikipediaArticle(String speciesName) {
        try {
            // URL for Wikipedia-API
            String queryUrl = API_URL + speciesName.replace(" ", "%20");
            URL url = new URL(queryUrl);

            // HTTP-Request
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject pages = jsonResponse.getJSONObject("query").getJSONObject("pages");

            for (String key : pages.keySet()) {
                JSONObject page = pages.getJSONObject(key);
                if (page.has("fullurl")) {
                    return page.getString("fullurl");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if no URL was found
    }
}
