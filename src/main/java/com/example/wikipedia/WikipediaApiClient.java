package com.example.wikipedia;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.Desktop;

public class WikipediaApiClient {
    private static final String API_URL = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=";

    public List<WikipediaSearchResult> search(String query) throws IOException {
        String encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20");
        String apiUrl = API_URL + encodedQuery;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        JsonObject jsonObject = JsonParser.parseString(content.toString()).getAsJsonObject();
        JsonArray searchResults = jsonObject.getAsJsonObject("query").getAsJsonArray("search");

        List<WikipediaSearchResult> results = new ArrayList<>();
        for (JsonElement element : searchResults) {
            JsonObject resultObject = element.getAsJsonObject();
            String title = resultObject.get("title").getAsString();
            String snippet = resultObject.get("snippet").getAsString();
            results.add(new WikipediaSearchResult(title, snippet));
        }
        return results;
    }

    public void openArticleInBrowser(String title) throws IOException, URISyntaxException {
        String pageUrl = "https://ru.wikipedia.org/wiki/" + URLEncoder.encode(title, "UTF-8").replace("+", "%20");

        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(pageUrl));
        } else {
            System.out.println("Открытие браузера не поддерживается.");
        }
    }
}
