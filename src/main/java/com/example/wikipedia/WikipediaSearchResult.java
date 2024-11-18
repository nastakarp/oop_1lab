package com.example.wikipedia;

public class WikipediaSearchResult {
    private final String title;
    private final String snippet;

    public WikipediaSearchResult(String title, String snippet) {
        this.title = title;
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }
}

