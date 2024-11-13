import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.Desktop;
import java.net.URI;
public class WikipediaSearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите поисковый запрос: ");
            String query = scanner.nextLine();
            String encodedQuery = URLEncoder.encode(query, "UTF-8").replace("+", "%20");
            String apiUrl = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=" + encodedQuery;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = JsonParser.parseString(content.toString()).getAsJsonObject();
            JsonArray searchResults = jsonObject.getAsJsonObject("query").getAsJsonArray("search");

            System.out.println("Результаты поиска:");
            for (int i = 0; i < searchResults.size(); i++) {
                JsonObject result = searchResults.get(i).getAsJsonObject();
                System.out.println((i + 1) + ". " + result.get("title").getAsString());
                System.out.println("Описание: " + result.get("snippet").getAsString());
            }

            System.out.print("Введите номер статьи для открытия: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice > 0 && choice <= searchResults.size()) {
                String pageTitle = searchResults.get(choice - 1).getAsJsonObject().get("title").getAsString();
                String pageUrl = "https://ru.wikipedia.org/wiki/" + pageTitle.replace(" ", "%20");

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI(pageUrl));
                } else {
                    System.out.println("Открытие браузера не поддерживается.");
                }
            } else {
                System.out.println("Неверный выбор.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе с сетью: " + e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println("Ошибка в URL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
