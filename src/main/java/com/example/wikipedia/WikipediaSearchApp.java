package com.example.wikipedia;

import java.util.List;
import java.util.Scanner;

public class WikipediaSearchApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите поисковый запрос: ");
            String query = scanner.nextLine();

            WikipediaApiClient apiClient = new WikipediaApiClient();
            List<WikipediaSearchResult> results = apiClient.search(query);

            System.out.println("Результаты поиска:");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).getTitle());
                System.out.println("Описание: " + results.get(i).getSnippet());
            }

            System.out.print("Введите номер статьи для открытия: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice > 0 && choice <= results.size()) {
                WikipediaSearchResult selectedResult = results.get(choice - 1);
                apiClient.openArticleInBrowser(selectedResult.getTitle());
            } else {
                System.out.println("Неверный выбор.");
            }
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
